package com.github.idankoblik.jukebox.manager;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstrumentManagerTest {

    private InstrumentManager manager;

    @BeforeEach
    public void setUp() {
        manager = InstrumentManager.getInstance();
        manager.clear();

    }

    @Test
    public void testAddInstrument() {
        Key sound = Key.key("minecraft", "note.harp");
        manager.addInstrument((byte) 1, sound);
        assertEquals(Optional.of(sound), manager.getInstrument((byte) 1));
    }

    @Test
    public void testRemoveInstrument() {
        Key sound = Key.key("minecraft", "note.harp");
        manager.addInstrument((byte) 1, sound);
        assertEquals(sound, manager.removeInstrument((byte) 1));
        assertEquals(Optional.empty(), manager.getInstrument((byte) 1));
    }

    @Test
    public void testGetNonExistentInstrument() {
        assertEquals(Optional.empty(), manager.getInstrument((byte) 2));
    }

    @Test
    public void testLoadInstruments() {
        Map<Byte, Key> map = new HashMap<>();
        Key sound1 = Key.key("minecraft", "note.harp");
        Key sound2 = Key.key("minecraft", "note.bass");
        map.put((byte) 1, sound1);
        map.put((byte) 2, sound2);
        manager.loadInstruments(map);

        assertEquals(Optional.of(sound1), manager.getInstrument((byte) 1));
        assertEquals(Optional.of(sound2), manager.getInstrument((byte) 2));
    }

    @Test
    public void testClear() {
        Key sound = Key.key("minecraft", "note.harp");
        manager.addInstrument((byte) 1, sound);
        manager.clear();
        assertEquals(Optional.empty(), manager.getInstrument((byte) 1));
    }
}
