package io.github.idankoblik.jukebox.events;

import io.github.idankoblik.jukebox.NBSSong;
import org.jetbrains.annotations.ApiStatus;

/**
 * An event that being fired up when a song end
 */
@ApiStatus.AvailableSince("0.0.3")
public class SongEndEvent extends SongEvent {

    protected final boolean force;

    /**
     * {@inheritDoc}
     * @param force true if the song ended by external force and not because the time of the song has passed
     */
    public SongEndEvent(NBSSong song, boolean force) {
        super(song);
        this.force = force;
    }

    /**
     * Returns if a song was ended by force
     * @return ended by external force
     */
    public boolean isForced() {
        return force;
    }
}
