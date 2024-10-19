package com.github.idankoblik.jukebox;

import com.github.idankoblik.jukebox.events.EventManager;
import com.github.idankoblik.jukebox.events.SongEndEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for manging a song in different platforms
 * @param <P> the platform of the song
 */
public abstract class AbstractSong<P extends Platform> {

    protected final NBSSong song;
    protected final float volume;
    
    @ApiStatus.AvailableSince("0.0.2")
    private boolean loop;

    /**
     * @param song the song that being handled
     * @param volume the volume of the song to be played
     */
    public AbstractSong(@NotNull NBSSong song, float volume) {
        this.song = song;
        this.volume = volume;
    }

    /**
     * Stops a song
     */
    public void stopSong() {
        if (song.getState() == SongState.ENDED)
            return;

        loop = false;
        song.setState(SongState.ENDED);
        EventManager.getInstance().fireEvent(new SongEndEvent(song, true));
    }

    /**
     * Returns if a song can be looped
     * @return if a song can be looped
     */
    @ApiStatus.AvailableSince("0.0.2")
    public boolean toLoop() {
        return loop;
    }

    /**
     * Allow song to be looped
     * @param loop allow/disallow song to be looped
     */
    @ApiStatus.AvailableSince("0.0.2")
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * An abstract method for handling song playing
     * @return A future of the song
     */
    public abstract CompletableFuture<NBSSong> playSong();

    /**
     * An abstract method for handling note playing
     * @param instrument byte representing an instrument to be played
     * @param pitch the pitch of the note
     */
    public abstract void playNote(byte instrument, float pitch);
}