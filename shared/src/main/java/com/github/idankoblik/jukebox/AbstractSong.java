package com.github.idankoblik.jukebox;

import com.github.idankoblik.jukebox.events.EventManager;
import com.github.idankoblik.jukebox.events.SongEndEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a song that plays a series of notes.
 */
public abstract class AbstractSong {

    protected final NBSSong song;
    protected final float volume;

    /**
     * Allow looping a song.
     */
    @ApiStatus.AvailableSince("0.0.2")
    private boolean loop;

    /**
     * Constructs a Song with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param volume       The volume of the song to be played
     */
    public AbstractSong(@NotNull NBSSong song, float volume) {
        this.song = song;
        this.volume = volume;
    }

    /**
     * Stop's the nbs song.
     */
    public void stopSong() {
        if (song.getState() == SongState.ENDED)
            return;

        loop = false;
        song.setState(SongState.ENDED);
        EventManager.getInstance().fireEvent(new SongEndEvent(song, true));
    }

    /**
     * Checking if song loop enabled
     * @return if song loop enabled.
     */
    @ApiStatus.AvailableSince("0.0.2")
    public boolean toLoop() {
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

    public abstract CompletableFuture<NBSSong> playSong();
    public abstract void playNote(byte instrument, float pitch);
}