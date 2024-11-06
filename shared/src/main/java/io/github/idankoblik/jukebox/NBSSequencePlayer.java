package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.Event;
import io.github.idankoblik.jukebox.events.EventManager;
import io.github.idankoblik.jukebox.events.EventListener;
import io.github.idankoblik.jukebox.events.SongStageChangeEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A class that represents a player for an NBS (Noteblock Studio) sequence.
 * It manages the state of the sequence and fires events when the state changes.
 *
 * <p>The sequence's state can be managed and observed through this class, allowing external systems
 * to listen for changes in the song's playback state. The player also manages the sequence itself and its associated events.</p>
 *
 */
@ApiStatus.AvailableSince("0.0.5")
public class NBSSequencePlayer {

    @ApiStatus.AvailableSince("0.0.5")
    private final NBSSequence nbsSequence;

    /**
     * The state of the song `IDLE` by default
     */
    @ApiStatus.AvailableSince("0.0.5")
    private SongState state = SongState.IDLE;

    private final EventManager eventManager;

    public NBSSequencePlayer(NBSSequence nbsSequence, List<EventListener<? extends Event>> events, @Nullable EventManager eventManager) {
        this.nbsSequence = nbsSequence;

        if (eventManager == null) {
            this.eventManager = new EventManager();
            events.forEach(this.eventManager::registerListener);
        } else
            this.eventManager = eventManager;
    }

    /**
     * Gets the current state of the song.
     *
     * @return the current {@link SongState} of the song.
     */
    @ApiStatus.AvailableSince("0.0.3")
    public SongState getState() {
        return state;
    }

    /**
     * Sets the state of the song.
     *
     * @param value the new {@link SongState} to set. Must not be null.
     * @throws IllegalArgumentException if the provided value is null.
     */
    @ApiStatus.AvailableSince("0.0.3")
    public void setState(@NotNull SongState value) {
        SongState oldState = this.state;
        if (value == oldState)
            return;

        this.state = value;
        this.eventManager.fireEvent(new SongStageChangeEvent(nbsSequence, oldState, this.state));
    }

    /**
     * Gets the nbs sequence of the nbs sequence player.
     *
     * @return the nbs sequence of the nbs sequence player.
     */
    public NBSSequence getNbsSequence() {
        return nbsSequence;
    }

    /**
     * Gets the event manager of the nbs sequence player.
     *
     * @return the event manager of the nbs sequence player.
     */
    public EventManager getEventManager() {
        return eventManager;
    }
}
