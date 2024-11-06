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
public class KyoriInstrumentManagerImpl implements InstrumentManager<Key> {

    private final Map<Byte, Key> sounds = new HashMap<>();

    private static KyoriInstrumentManagerImpl instance;

    public static KyoriInstrumentManagerImpl getInstance() {
        return (instance == null) ? new KyoriInstrumentManagerImpl() : instance;
    }

    @ApiStatus.AvailableSince("0.0.2")
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

    @Override
    public void addInstrument(byte b, @NotNull Key sound) {
        this.sounds.put(b, sound);
    }

    @Override
    public Key removeInstrument(byte b) {
        return this.sounds.remove(b);
    }

    @Override
    public Optional<Key> getInstrument(byte b) {
        return Optional.ofNullable(this.sounds.get(b));
    }

    @Override
    public void clear() {
        this.sounds.clear();
    }

    @Override
    public void loadInstruments(Map<Byte, Key> map) {
        this.sounds.clear();
        this.sounds.putAll(map);
    }
}
