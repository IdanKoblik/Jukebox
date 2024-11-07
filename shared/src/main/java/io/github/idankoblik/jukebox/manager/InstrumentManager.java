package io.github.idankoblik.jukebox.manager;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;


/**
 * An interface defining a contract for managing a collection of instruments,
 * where each instrument is associated with a unique byte identifier.
 * <p>
 * Implementations of this interface must provide functionality to add, remove, retrieve,
 * and load instruments based on their byte identifier.
 * </p>
 *
 * @param <T> The type of the instrument, typically a sound key or other representation of an instrument
 */
@ApiStatus.AvailableSince("0.0.5")
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
