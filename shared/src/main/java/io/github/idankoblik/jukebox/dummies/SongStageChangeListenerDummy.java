package io.github.idankoblik.jukebox.dummies;

import io.github.idankoblik.jukebox.SongState;
import io.github.idankoblik.jukebox.events.SongStageChangeEvent;

/**
 * @hidden
 */
public class SongStageChangeListenerDummy extends SongListenerDummy<SongStageChangeEvent> {

    private SongState previous, current;

    @Override
    public void testEvent(SongStageChangeEvent event) {
        this.previous = event.getPrevious();
        this.current = event.getCurrent();
    }

    public SongState getPrevious() {
        return previous;
    }

    public SongState getCurrent() {
        return current;
    }
}
