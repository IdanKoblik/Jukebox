package com.github.idankoblik.jukebox.events;

import com.github.idankoblik.jukebox.NBSSong;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an event that signifies the start of a song.
 * <p>
 * The {@code SongStartEvent} is triggered when a song begins playing,
 * encapsulating the associated {@link NBSSong}.
 * </p>
 *
 */
@ApiStatus.AvailableSince("0.0.3")
public class SongStartEvent extends SongEvent {

    /**
     * Constructs a {@code SongStartEvent} with the specified song.
     *
     * @param song the {@link NBSSong} that has started playing; cannot be null.
     */
    public SongStartEvent(NBSSong song) {
        super(song);
    }

}
