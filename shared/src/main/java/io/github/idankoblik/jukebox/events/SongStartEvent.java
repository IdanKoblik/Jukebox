package io.github.idankoblik.jukebox.events;

import io.github.idankoblik.jukebox.NBSSong;
import org.jetbrains.annotations.ApiStatus;

/**
 * An event that being fired when a song starts
 */
@ApiStatus.AvailableSince("0.0.3")
public class SongStartEvent extends SongEvent {

    /**
     * {@inheritDoc}
     */
    public SongStartEvent(NBSSong song) {
        super(song);
    }

}
