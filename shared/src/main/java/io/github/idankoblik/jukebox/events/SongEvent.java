package io.github.idankoblik.jukebox.events;

import io.github.idankoblik.jukebox.NBSSequence;
import org.jetbrains.annotations.ApiStatus;

/**
 * An Abstract class for manging events
 */
@ApiStatus.AvailableSince("0.0.3")
public abstract class SongEvent implements Event {

    protected final NBSSequence song;

    /**
     *
     * @param song the song that the event works on
     */
    public SongEvent(NBSSequence song) {
        this.song = song;
    }

    /**
     * Returns the song that the events works on
     * @return the song that the events works on
     */
    public NBSSequence getSong() {
        return song;
    }
}

