package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.EventManager;
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
    private final CompletableFuture<NBSSequenceWrapper> completionSignal;
    private int tick;
    private final float tickLengthInSeconds;

    /**
     *
     * @param notes the notes to be handled
     * @param abstractSong the song to be handled
     * @param completionSignal a future of the song
     */
    public SongTick(@NotNull List<NBSNote> notes, PaperSong abstractSong, CompletableFuture<NBSSequenceWrapper> completionSignal) {
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
        NBSSequenceWrapper wrapper = abstractSong.wrapper;
        if (wrapper.getState() == SongState.ENDED) {
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
                wrapper.getEventManager().fireEvent(new SongEndEvent(abstractSong.sequence, false));
                wrapper.setState(SongState.ENDED);
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
        completionSignal.complete(abstractSong.wrapper);
        cancel();
    }
}