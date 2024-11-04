package io.github.idankoblik.jukebox.manager;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public interface InstrumentManager<T> {

    /**
     * Add instrument to the instrument map
     * @param b byte of the instrument
     * @param sound the corresponding sound of the instrument
     */
    void addInstrument(byte b, @NotNull T sound);

    /**
     * Remove an instrument from the map
     * @param b byte of the instrument
     * @return the corresponding sound of the instrument
     */
    T removeInstrument(byte b);

    /**
     * Get an instrument by byte
     * @param b byte of the instrument
     * @return optional corresponding sound of the instrument
     */
    Optional<T> getInstrument(byte b);

    /**
     * Clear the whole instrument map
     */
    void clear();

    /**
     * Load a preset of byte to instrument map
     * @param map a preset of byte to instrument map
     */
    void loadInstruments(Map<Byte, T> map);
}
