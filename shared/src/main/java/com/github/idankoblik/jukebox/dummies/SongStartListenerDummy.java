package com.github.idankoblik.jukebox.dummies;

import com.github.idankoblik.jukebox.events.SongStartEvent;

/**
 * @hidden
 */
public class SongStartListenerDummy extends SongListenerDummy<SongStartEvent> {

    @Override
    public void testEvent(SongStartEvent event) {
    }

}
