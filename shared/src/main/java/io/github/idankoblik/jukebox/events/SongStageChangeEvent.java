package io.github.idankoblik.jukebox.events;

import io.github.idankoblik.jukebox.NBSSequence;
import io.github.idankoblik.jukebox.SongState;
import org.jetbrains.annotations.ApiStatus;

/**
 * An event that being fired when a song stage changes
 */
@ApiStatus.AvailableSince("0.0.3")
public class SongStageChangeEvent extends SongEvent {

    protected final SongState previous, current;

    /**
     * {@inheritDoc}
     * @param previous the previous stage of the song
     * @param current the current stage of the song
     */
    public SongStageChangeEvent(NBSSequence song, SongState previous, SongState current) {
        super(song);
        this.previous = previous;
        this.current = current;
    }

    /**
     * Returns the previous stage of the song
     * @return the previous stage of the song
     */
    public SongState getPrevious() {
        return previous;
    }

    /**
     * Returns current the current stage of the song
     * @return current the current stage of the song
     */
    public SongState getCurrent() {
        return current;
    }
}
