package io.github.idankoblik.jukebox.manager;

import io.github.idankoblik.jukebox.NoteInstrument;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A class managing all instruments
 */
public class InstrumentManager {

    private static InstrumentManager instance;

    /**
     * Returns the instance of the instrument manager
     * @return the instance of the instrument manager
     */
    public static InstrumentManager getInstance() {
        if (instance == null)
            instance = new InstrumentManager();

        return instance;
    }

    protected final Map<Byte, Key> sounds = new HashMap<>();

    @ApiStatus.AvailableSince("0.0.2")
    public InstrumentManager() {
        sounds.put((byte) 0, Key.key(NoteInstrument.HARP.getName()));
        sounds.put((byte) 1, Key.key(NoteInstrument.BASS.getName()));
        sounds.put((byte) 2, Key.key(NoteInstrument.BASEDRUM.getName()));
        sounds.put((byte) 3, Key.key(NoteInstrument.SNARE.getName()));
        sounds.put((byte) 4, Key.key(NoteInstrument.HAT.getName()));
        sounds.put((byte) 5, Key.key(NoteInstrument.GUITAR.getName()));
        sounds.put((byte) 6, Key.key(NoteInstrument.FLUTE.getName()));
        sounds.put((byte) 7, Key.key(NoteInstrument.BELL.getName()));
        sounds.put((byte) 8, Key.key(NoteInstrument.CHIME.getName()));
        sounds.put((byte) 9, Key.key(NoteInstrument.XYLOPHONE.getName()));
    }

    /**
     * Add instrument to the instrument map
     * @param b byte of the instrument
     * @param sound the corresponding key of the instrument
     */
    public void addInstrument(byte b, @NotNull Key sound) {
        this.sounds.put(b, sound);
    }

    /**
     * Remove an instrument from the map
     * @param b byte of the instrument
     * @return the corresponding key of the instrument
     */
    public Key removeInstrument(byte b) {
        return this.sounds.remove(b);
    }

    /**
     * Get an instrument by byte
     * @param b byte of the instrument
     * @return optional corresponding key of the instrument
     */
    public Optional<Key> getInstrument(byte b) {
        return Optional.ofNullable(this.sounds.get(b));
    }

    /**
     * Clear the whole instrument map
     */
    public void clear() {
        this.sounds.clear();
    }

    /**
     * Load a preset of byte to instrument map
     * @param map a preset of byte to instrument map
     */
    public void loadInstruments(Map<Byte, Key> map) {
        this.sounds.clear();
        this.sounds.putAll(map);
    }
}
