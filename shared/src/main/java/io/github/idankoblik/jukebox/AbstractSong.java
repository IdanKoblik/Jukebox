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

    protected final NBSSequencePlayer player;
    protected final NBSSequence sequence;
    protected final EventManager eventManager;
    protected final float volume;
    
    @ApiStatus.AvailableSince("0.0.2")
    private boolean loop;

    /**
     * @param player the player of the sequence that being handled
     * @param volume the volume of the sequence to be played
     */
    public AbstractSong(@NotNull NBSSequencePlayer player, float volume) {
        this.player = player;
        this.volume = volume;
        this.sequence = player.getNbsSequence();
        this.eventManager = player.getEventManager();
    }

    /**
     * Stops a song
     */
    public void stopSong() {
        if (player.getState() == SongState.ENDED)
            return;

        loop = false;
        player.setState(SongState.ENDED);
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
    public abstract CompletableFuture<NBSSequencePlayer> playSong();

    /**
     * An abstract method for handling note playing
     * @param instrument byte representing an instrument to be played
     * @param pitch the pitch of the note
     */
    public abstract void playNote(byte instrument, float pitch);
}