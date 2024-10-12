package com.github.idankoblik.jukebox.events;

import com.github.idankoblik.jukebox.NBSSong;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an event that signifies the end of a song.
 * <p>
 * The {@code SongEndEvent} can indicate whether the song was stopped
 * </p>
 *
 */
@ApiStatus.AvailableSince("0.0.3")
public class SongEndEvent extends SongEvent {

    protected final boolean force;

    /**
     * Constructs a {@code SongEndEvent} with the specified song and stop status.
     *
     * @param song the {@link NBSSong} that has ended; cannot be null.
     * @param force true if the song was stopped; false if it ended naturally.
     */
    public SongEndEvent(NBSSong song, boolean force) {
        super(song);
        this.force = force;
    }

    /**
     * Returns whether the song was force stopped before its natural end.
     *
     * @return true if the song was stopped; false otherwise.
     */
    public boolean isForced() {
        return force;
    }
}
