package com.github.idankoblik.jukebox;

import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Scheduled task that handles the playback of a song's notes.
 * <p>
 * This class is responsible for playing each note at the appropriate time
 * based on the song's tick count.
 * </p>
 */
class SongTick extends BukkitRunnable {
    private final List<NBSNote> notes;
    private final AbstractSong abstractSong;
    private final CompletableFuture<NBSSong> future;
    private int tick = -1;

    /**
     * Constructs a SongTick with the given parameters.
     *
     * @param notes       The list of notes to be played.
     * @param abstractSong The song that is currently being played.
     * @param future      A CompletableFuture to complete when the song ends.
     */
    public SongTick(@NotNull List<NBSNote> notes, AbstractSong abstractSong, CompletableFuture<NBSSong> future) {
        this.notes = notes;
        this.abstractSong = abstractSong;
        this.future = future;
    }

    /**
     * Executes the scheduled task, playing the appropriate note for the current tick.
     */
    @Override
    public void run() {
        if (notes.isEmpty()) {
            completeSong();
            return;
        }

        if (tick >= notes.size()) {
            if (!abstractSong.toLoop()) {
                abstractSong.song.setState(SongState.ENDED);
                completeSong();
                return;
            }

            tick = 0;
        }

        if (tick >= 0 && tick < notes.size()) {
            NBSNote note = notes.get(tick);
            if (note != null) {
                float pitch = (float) Math.pow(2, (note.getKey() - 45) / 12.0);
                this.abstractSong.playNote(note.getInstrument(), pitch);
            }
        }

        tick++;
    }

    /**
     * Completes the song playback and cancels the task.
     */
    private void completeSong() {
        future.complete(abstractSong.song);
        cancel();
    }
}