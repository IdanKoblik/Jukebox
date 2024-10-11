package com.github.idankoblik.jukebox.events;

import com.github.idankoblik.jukebox.NBSSong;
import com.github.idankoblik.jukebox.SongState;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an event that signifies a change in the state of a song.
 * <p>
 * The {@code SongStageChangeEvent} captures both the previous and current
 * states of the song, allowing listeners to react to transitions between
 * states.
 * </p>
 *
 */
@ApiStatus.AvailableSince("0.0.3")
public class SongStageChangeEvent extends SongEvent {

    protected final SongState previous, current;

    /**
     * Constructs a {@code SongStageChangeEvent} with the specified song and state changes.
     *
     * @param song the {@link NBSSong} associated with this event; cannot be null.
     * @param previous the previous {@link SongState} before the change; cannot be null.
     * @param current the current {@link SongState} after the change; cannot be null.
     */
    public SongStageChangeEvent(NBSSong song, SongState previous, SongState current) {
        super(song);
        this.previous = previous;
        this.current = current;
    }

    /**
     * Returns the previous state of the song.
     *
     * @return the previous {@link SongState} before the change.
     */
    public SongState getPrevious() {
        return previous;
    }

    /**
     * Returns the current state of the song.
     *
     * @return the current {@link SongState} after the change.
     */
    public SongState getCurrent() {
        return current;
    }
}
