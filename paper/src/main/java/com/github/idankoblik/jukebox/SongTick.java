package com.github.idankoblik.jukebox;

import com.github.idankoblik.jukebox.events.EventManager;
import com.github.idankoblik.jukebox.events.SongEndEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Scheduled task that handles the playback of a song's notes.
 * <p>
 * This class is responsible for playing each note at the appropriate time
 * based on the song's tick count and tempo.
 * </p>
 */
class SongTick extends BukkitRunnable {
    private final List<NBSNote> notes;
    private final AbstractSong abstractSong;
    private final CompletableFuture<NBSSong> future;
    private int tick;
    private final float tickLengthInSeconds;

    /**
     * Constructs a SongTick with the given parameters.
     *
     * @param notes        The list of notes to be played.
     * @param abstractSong The song that is currently being played.
     * @param future       A CompletableFuture to complete when the song ends.
     */
    public SongTick(@NotNull List<NBSNote> notes, AbstractSong abstractSong, CompletableFuture<NBSSong> future) {
        this.notes = notes;
        this.abstractSong = abstractSong;
        this.future = future;
        this.tick = 0;
        this.tickLengthInSeconds = 20f / abstractSong.song.getTempo();
    }

    /**
     * Executes the scheduled task, playing the appropriate notes for the current time.
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
     * Plays a single note.
     *
     * @param note The note to be played.
     */
    private void playNote(NBSNote note) {
        if (note != null) {
            float pitch = (float) Math.pow(2, (note.getKey() - 45) / 12.0);
            this.abstractSong.playNote(note.getInstrument(), pitch);
        }
    }

    /**
     * Completes the song playback and cancels the task.
     */
    private void completeSong() {
        future.complete(abstractSong.song);
        cancel();
    }
}