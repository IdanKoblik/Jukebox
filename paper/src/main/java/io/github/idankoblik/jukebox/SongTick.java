package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.SongEndEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Handling each tick of the song
 */
/* package-private */ class SongTick extends BukkitRunnable {
    private final List<NBSNote> notes;
    private final PaperSong abstractSong;
    private final CompletableFuture<NBSSequencePlayer> completionSignal;
    private int tick;
    private final float tickLengthInSeconds;

    /**
     *
     * @param notes the notes to be handled
     * @param abstractSong the song to be handled
     * @param completionSignal a future of the song
     */
    public SongTick(@NotNull List<NBSNote> notes, PaperSong abstractSong, CompletableFuture<NBSSequencePlayer> completionSignal) {
        this.notes = notes;
        this.abstractSong = abstractSong;
        this.completionSignal = completionSignal;
        this.tick = 0;
        this.tickLengthInSeconds = 20f / abstractSong.sequence.tempo();
    }

    /**
     * Handling each tick of the runnable
     */
    @Override
    public void run() {
        NBSSequencePlayer player = abstractSong.player;
        if (player.getState() == SongState.ENDED) {
            completeSong();
            return;
        }

        List<NBSNote> notesAtCurrentTick = notes.stream()
                .filter(note -> Math.round(note.getTick() * tickLengthInSeconds) == tick)
                .toList();

        for (NBSNote note : notesAtCurrentTick)
            playNote(note);

        tick++;

        if (tick > notes.stream().mapToLong(note -> Math.round(note.getTick() * tickLengthInSeconds)).max().orElse(0)) {
            if (!abstractSong.toLoop()) {
                player.getEventManager().fireEvent(new SongEndEvent(abstractSong.sequence, false));
                player.setState(SongState.ENDED);
                completeSong();
            } else
                tick = 0;
        }
    }

    /**
     * Play a note in the song
     * @param note the note to play
     */
    private void playNote(NBSNote note) {
        if (note != null) {
            float pitch = (float) Math.pow(2, (note.getKey() - 45) / 12.0);
            this.abstractSong.playNote(note.getInstrument(), pitch);
        }
    }

    /**
     * Completing the song
     */
    private void completeSong() {
        completionSignal.complete(abstractSong.player);
        cancel();
    }
}