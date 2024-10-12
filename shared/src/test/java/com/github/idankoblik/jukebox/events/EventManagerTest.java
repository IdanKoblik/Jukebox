package com.github.idankoblik.jukebox.events;

import com.github.idankoblik.jukebox.NBSNote;
import com.github.idankoblik.jukebox.NBSSong;
import com.github.idankoblik.jukebox.dummies.SongStartListenerDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventManagerTest {

    private EventManager eventManager;
    private NBSSong nbsSong;

    @BeforeEach
    void setUp() {
        this.eventManager = EventManager.getInstance();
        this.eventManager.clear();
        NBSNote note = new NBSNote((short) 1, (short) 1, (byte) 1, (byte) 1);
        NBSNote note2 = new NBSNote((short) 2, (short) 1, (byte) 1, (byte) 1);
        NBSNote note3 = new NBSNote((short) 3, (short) 1, (byte) 1, (byte) 1);
        this.nbsSong = new NBSSong("test", "tester", (byte) 20, (byte) 1, "test", "test", 20, List.of(note, note2, note3));
    }

    @Test
    void testGetInstance() {
        assertNotNull(this.eventManager);
    }

    @Test
    void testRegisterListener() {
        EventListener<SongStartEvent> listener = new SongStartListenerDummy();
        this.eventManager.registerListener(listener);

        List<EventListener<? extends Event>> listeners = this.eventManager.getRegisteredListeners();
        assertEquals(listeners.size(), 1);
        assertEquals(listeners.get(0), listener);
    }

    @Test
    void testFireSongStartEvent() {
        SongStartListenerDummy listener = new SongStartListenerDummy();
        this.eventManager.registerListener(listener);

        SongStartEvent event = new SongStartEvent(this.nbsSong);
        this.eventManager.fireEvent(event);
        assertTrue(listener.isWorking());
    }
}
