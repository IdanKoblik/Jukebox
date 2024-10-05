package com.github.idankoblik.jukebox;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a song that plays a series of notes.
 */
public abstract class AbstractSong {

    protected final NBSSong song;
    protected final float tickLengthInSeconds;
    protected final String defaultSound;
    protected final ScheduledExecutorService scheduler;

    protected boolean playing = false;

    /**
     * Allow looping a song.
     */
    @ApiStatus.AvailableSince("0.0.2")
    private boolean loop;

    /**
     * Constructs a Song with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param defaultSound The default sound key.
     */
    public AbstractSong(@NotNull NBSSong song, @NotNull String defaultSound) {
        this.song = song;
        this.tickLengthInSeconds = 20f / song.tempo();
        this.defaultSound = defaultSound;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Play's the nbs song.
     * @param volume The volume at which to play the song.
     * @return Future when the song ends.
     */
    public CompletableFuture<Void> playSong(float volume) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        if (song.notes().isEmpty()) {
            future.complete(null);
            return future;
        }

        playing = true;
        List<NBSNote> notes = song.notes();
        int totalNotes = notes.size();

        for (int i = 0; i < totalNotes; i++) {
            final int index = i;
            long delayInMillis = Math.round(notes.get(i).getTick() * tickLengthInSeconds * 50);

            scheduler.schedule(() -> {
                if (!playing) {
                    future.complete(null);
                    return;
                }

                float pitch = (float) Math.pow(2, (notes.get(index).getKey() - 45) / 12.0);
                playSound(notes.get(index).getInstrument(), pitch, volume);

                if (index == totalNotes - 1) {
                    if (loop)
                        playSong(volume);
                    else
                        future.complete(null);
                }
            }, delayInMillis, TimeUnit.MILLISECONDS);
        }

        return future;
    }

    /**
     * Stop's the nbs song.
     */
    public void stopSong() {
        if (playing)
            scheduler.shutdown();

        loop = false;
        playing = false;
    }

    /**
     * Checking if song loop enabled
     * @return if song loop enabled.
     */
    @ApiStatus.AvailableSince("0.0.2")
    public boolean isLoop() {
        return loop;
    }

    /**
     * Enable/Disable song looping
     * @param loop Enable/Disable
     */
    @ApiStatus.AvailableSince("0.0.2")
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * Plays a sound associated with the given instrument and pitch.
     *
     * @param instrument The instrument byte value.
     * @param pitch      The pitch of the sound.
     * @param volume     The volume of the sound.
     */
    protected abstract void playSound(byte instrument, float pitch, float volume);
}