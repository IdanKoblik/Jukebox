package com.github.idankoblik.jukebox.events;

import com.github.idankoblik.jukebox.NBSSong;
import org.jetbrains.annotations.ApiStatus;

/**
 * An Abstract class for manging events
 */
@ApiStatus.AvailableSince("0.0.3")
public abstract class SongEvent implements Event {

    protected final NBSSong song;

    /**
     *
     * @param song the song that the event works on
     */
    public SongEvent(NBSSong song) {
        this.song = song;
    }

    /**
     * Returns the song that the events works on
     * @return the song that the events works on
     */
    public NBSSong getSong() {
        return song;
    }
}

