package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.EventManager;
import io.github.idankoblik.jukebox.events.SongEndEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for manging a song in different platforms
 * @param <P> the platform of the song
 */
@SuppressWarnings("unused")
public abstract class AbstractSong<P extends Platform> {

    protected final NBSSequenceWrapper wrapper;
    protected final NBSSequence sequence;
    protected final EventManager eventManager;
    protected final float volume;
    
    @ApiStatus.AvailableSince("0.0.2")
    private boolean loop;

    /**
     * @param wrapper the wrapper of the sequence that being handled
     * @param volume the volume of the sequence to be played
     */
    public AbstractSong(@NotNull NBSSequenceWrapper wrapper, float volume) {
        this.wrapper = wrapper;
        this.volume = volume;
        this.sequence = wrapper.getNbsSequence();
        this.eventManager = wrapper.getEventManager();
    }

    /**
     * Stops a song
     */
    public void stopSong() {
        if (wrapper.getState() == SongState.ENDED)
            return;

        loop = false;
        wrapper.setState(SongState.ENDED);
        eventManager.fireEvent(new SongEndEvent(sequence, true));
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
    public abstract CompletableFuture<NBSSequenceWrapper> playSong();

    /**
     * An abstract method for handling note playing
     * @param instrument byte representing an instrument to be played
     * @param pitch the pitch of the note
     */
    public abstract void playNote(byte instrument, float pitch);
}