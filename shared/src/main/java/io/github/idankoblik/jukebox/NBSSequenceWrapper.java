package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.EventManager;
import io.github.idankoblik.jukebox.events.SongStageChangeEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.AvailableSince("0.0.5")
public class NBSSequenceWrapper {

    @ApiStatus.AvailableSince("0.0.5")
    private final NBSSequence nbsSequence;

    @ApiStatus.AvailableSince("0.0.5")
    private final EventManager eventManager;

    /**
     * The state of the song `IDLE` by default
     */
    @ApiStatus.AvailableSince("0.0.5")
    private SongState state = SongState.IDLE;

    public NBSSequenceWrapper(NBSSequence nbsSequence, EventManager eventManager) {
        this.nbsSequence = nbsSequence;
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
        eventManager.fireEvent(new SongStageChangeEvent(nbsSequence, oldState, this.state));
    }

    public NBSSequence getNbsSequence() {
        return nbsSequence;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
