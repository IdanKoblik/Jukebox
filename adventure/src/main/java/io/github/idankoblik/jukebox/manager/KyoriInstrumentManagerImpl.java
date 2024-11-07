package io.github.idankoblik.jukebox.manager;

import io.github.idankoblik.jukebox.NoteInstrument;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The implementation of the {@link InstrumentManager} that manages a collection of instruments
 * associated with their respective byte identifiers and the corresponding sound keys.
 *
 */
@ApiStatus.AvailableSince("0.0.5")
public class KyoriInstrumentManagerImpl implements InstrumentManager<Key> {

    private final Map<Byte, Key> sounds = new HashMap<>();

    private static KyoriInstrumentManagerImpl instance;

    /**
     * Gets the singleton instance of the KyoriInstrumentManagerImpl.
     * If an instance does not already exist, a new one is created.
     *
     * @return The singleton instance of this class
     */
    public static KyoriInstrumentManagerImpl getInstance() {
        return (instance == null) ? new KyoriInstrumentManagerImpl() : instance;
    }

    @ApiStatus.AvailableSince("0.0.5")
    public KyoriInstrumentManagerImpl() {
        this.sounds.put((byte) 0, Key.key(NoteInstrument.HARP.getName()));
        this.sounds.put((byte) 1, Key.key(NoteInstrument.BASS.getName()));
        this.sounds.put((byte) 2, Key.key(NoteInstrument.BASEDRUM.getName()));
        this.sounds.put((byte) 3, Key.key(NoteInstrument.SNARE.getName()));
        this.sounds.put((byte) 4, Key.key(NoteInstrument.HAT.getName()));
        this.sounds.put((byte) 5, Key.key(NoteInstrument.GUITAR.getName()));
        this.sounds.put((byte) 6, Key.key(NoteInstrument.FLUTE.getName()));
        this.sounds.put((byte) 7, Key.key(NoteInstrument.BELL.getName()));
        this.sounds.put((byte) 8, Key.key(NoteInstrument.CHIME.getName()));
        this.sounds.put((byte) 9, Key.key(NoteInstrument.XYLOPHONE.getName()));
    }

    /**
     * Adds an instrument to the manager's map, associating it with a byte identifier.
     *
     * @param b     The byte identifier for the instrument
     * @param sound The {@link Key} representing the sound of the instrument
     */
    @Override
    public void addInstrument(byte b, @NotNull Key sound) {
        this.sounds.put(b, sound);
    }

    /**
     * Removes an instrument from the manager's map using the byte identifier.
     *
     * @param b The byte identifier of the instrument to remove
     * @return The corresponding sound key of the removed instrument, or null if not found
     */
    @Override
    public Key removeInstrument(byte b) {
        return this.sounds.remove(b);
    }

    /**
     * Retrieves an instrument by its byte identifier.
     *
     * @param b The byte identifier of the instrument to retrieve
     * @return An {@link Optional} containing the sound key of the instrument if it exists, or an empty Optional
     */
    @Override
    public Optional<Key> getInstrument(byte b) {
        return Optional.ofNullable(this.sounds.get(b));
    }

    /**
     * Clears all instruments from the manager's map.
     */
    @Override
    public void clear() {
        this.sounds.clear();
    }

    /**
     * Loads a preset map of instruments into the manager.
     * This replaces any existing instruments in the manager with the provided map.
     *
     * @param map The map of byte-to-sound key mappings to load into the manager
     */
    @Override
    public void loadInstruments(Map<Byte, Key> map) {
        this.sounds.clear();
        this.sounds.putAll(map);
    }
}
