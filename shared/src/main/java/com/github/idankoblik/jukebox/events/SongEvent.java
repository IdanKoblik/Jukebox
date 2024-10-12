package com.github.idankoblik.jukebox.events;

import com.github.idankoblik.jukebox.NBSSong;
import org.jetbrains.annotations.ApiStatus;

/**
 * An abstract class representing a song-related event.
 * <p>
 * The {@code SongEvent} serves as a base class for internal.events related to songs,
 * encapsulating the common property of the associated {@link NBSSong}.
 * </p>
 *
 */
@ApiStatus.AvailableSince("0.0.3")
public abstract class SongEvent implements Event {

    protected final NBSSong song;

    /**
     * Constructs a {@code SongEvent} with the specified song.
     *
     * @param song the {@link NBSSong} associated with this event; cannot be null.
     */
    public SongEvent(NBSSong song) {
        this.song = song;
    }

    /**
     * Returns the song associated with this event.
     *
     * @return the {@link NBSSong} related to this event.
     */
    public NBSSong getSong() {
        return song;
    }
}

