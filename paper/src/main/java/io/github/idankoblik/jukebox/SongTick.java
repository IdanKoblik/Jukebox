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
    private final CompletableFuture<NBSSong> future;
    private int tick;
    private final float tickLengthInSeconds;

    /**
     *
     * @param notes the notes to be handled
     * @param abstractSong the song to be handled
     * @param future a future of the song
     */
    public SongTick(@NotNull List<NBSNote> notes, PaperSong abstractSong, CompletableFuture<NBSSong> future) {
        this.notes = notes;
        this.abstractSong = abstractSong;
        this.future = future;
        this.tick = 0;
        this.tickLengthInSeconds = 20f / abstractSong.song.getTempo();
    }

    /**
     * Handling each tick of the runnable
     */
    @Override
    public void run() {
        NBSSong song = abstractSong.song;
        if (song.getState() == SongState.ENDED) {
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
                EventManager.getInstance().fireEvent(new SongEndEvent(song, false));
                abstractSong.song.setState(SongState.ENDED);
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
        future.complete(abstractSong.song);
        cancel();
    }
}