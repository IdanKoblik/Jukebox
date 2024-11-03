package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.SongStartEvent;

/**
 * @hidden
 */
public class SongStartListenerDummy extends SongListenerDummy<SongStartEvent> {

    @Override
    public void testEvent(SongStartEvent event) {
    }

}
