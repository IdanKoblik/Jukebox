package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.SongEndEvent;

/**
 * @hidden
 */
public class SongEndListenerDummy extends SongListenerDummy<SongEndEvent> {

    private boolean force = false;

    @Override
    public void testEvent(SongEndEvent event) {
        this.force = event.isForced();
    }

    public boolean isForcing() {
        return force;
    }
}
