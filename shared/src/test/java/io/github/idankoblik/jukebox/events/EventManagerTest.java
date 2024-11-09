package io.github.idankoblik.jukebox.events;

import io.github.idankoblik.jukebox.NBSNote;
import io.github.idankoblik.jukebox.NBSSequence;
import io.github.idankoblik.jukebox.SongStartListenerDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventManagerTest {

    private final EventManager eventManager = new EventManager();
    private NBSSequence nbsSequence;

    @BeforeEach
    void setUp() {
        this.eventManager.clear();
        NBSNote note = new NBSNote((short) 1, (short) 1, (byte) 1, (byte) 1);
        NBSNote note2 = new NBSNote((short) 2, (short) 1, (byte) 1, (byte) 1);
        NBSNote note3 = new NBSNote((short) 3, (short) 1, (byte) 1, (byte) 1);
        this.nbsSequence = new NBSSequence(
                "test",
                "tester",
                (byte) 20,
                (byte) 1,
                "test",
                "test",
                20,
                List.of(note, note2, note3),
                false,
                (byte) 0,
                (byte) 0,
                0,
                0,
                0,
                0,
                0,
                "testtt"
        );
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

        SongStartEvent event = new SongStartEvent(this.nbsSequence);
        this.eventManager.fireEvent(event);
        assertTrue(listener.isWorking());
    }
}
