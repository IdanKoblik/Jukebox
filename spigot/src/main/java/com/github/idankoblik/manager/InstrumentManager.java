package com.github.idankoblik.manager;

import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code InstrumentManager} class is a singleton that manages a collection of musical instrument sounds
 * represented by keys of type {@code Sound} or {@code Key}. Each instrument is mapped to a byte value,
 * allowing for easy retrieval and manipulation of sound keys within a game context, such as Minecraft.
 */
public class InstrumentManager {

    private static InstrumentManager instance;

    /**
     * Retrieves the singleton instance of the {@code InstrumentManager}.
     *
     * @return the singleton instance of {@code InstrumentManager}.
     */
    public static InstrumentManager getInstance() {
        if (instance == null)
            instance = new InstrumentManager();

        return instance;
    }

    protected final Map<Byte, Key> sounds = new HashMap<>();

    /**
     * Adds a new sound associated with the given byte value.
     *
     * @param b the byte value to associate with the sound.
     * @param sound the sound to add, must be of type {@code Sound} or {@code Key}.
     */
    public void addInstrument(byte b, @NotNull Key sound) {
        this.sounds.put(b, sound);
    }

    /**
     * Removes the sound associated with the given byte value.
     *
     * @param b the byte value for which to remove the sound.
     * @return the removed sound, or {@code null} if no sound was associated with the given byte.
     */
    public Key removeInstrument(byte b) {
        return this.sounds.remove(b);
    }

    /**
     * Retrieves the sound associated with the given byte value.
     *
     * @param b the byte value for which to retrieve the sound.
     * @return the sound associated with the byte value, or {@code Optional.empty()} if no sound is found.
     */
    public Optional<Key> getInstrument(byte b) {
        return Optional.ofNullable(this.sounds.get(b));
    }

    /**
     * Clears all sounds from the instrument manager.
     * <p>
     * This method removes all entries from the internal map, effectively resetting the state
     * of the manager. After calling this method, all previously stored sounds will be lost.
     * </p>
     */
    public void clear() {
        this.sounds.clear();
    }

    /**
     * Loads a collection of sounds into the instrument manager.
     *
     * @param map a map containing byte values as keys and sounds as values.
     *            Each entry in the map will be added to the instrument manager.
     * @throws NullPointerException if the provided map is {@code null}.
     */
    public void loadInstruments(Map<Byte, Key> map) {
        this.sounds.putAll(map);
    }
}
