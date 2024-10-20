package io.github.idankoblik.jukebox.dummies;

import io.github.idankoblik.jukebox.events.Event;
import io.github.idankoblik.jukebox.events.EventListener;

/**
 * @hidden
 */
public abstract class SongListenerDummy<E extends Event> extends EventListener<E> {

    private boolean work = false;

    @Override
    public void handle(E event) {
        this.work = true;
        testEvent(event);
    }

    public abstract void testEvent(E event);

    public boolean isWorking() {
        boolean old = this.work;
        this.work = false;
        return old;
    }
}
