package io.github.idankoblik.jukebox;

/**
 * A single note inside a nbs file
 */
public class NBSNote {

    /**
     * The tick of the note
     */
    private final short tick;

    /**
     * The layer of the note
     */
    private final short layer;

    /**
     * The instrument of the note
     */
    private final byte instrument;

    /**
     * The key of the note
     */
    private final byte key;

    
    public NBSNote(short tick, short layer, byte instrument, byte key) {
        this.tick = tick;
        this.layer = layer;
        this.instrument = instrument;
        this.key = key;
    }

    /**
     * Gets the current tick value.
     *
     * @return the current tick as a short.
     */
    public short getTick() {
        return tick;
    }

    /**
     * Gets the current layer value.
     *
     * @return the current layer as a short.
     */
    public short getLayer() {
        return layer;
    }

    /**
     * Gets the instrument value.
     *
     * @return the current instrument as a byte.
     */
    public byte getInstrument() {
        return instrument;
    }

    /**
     * Gets the key value.
     *
     * @return the current key as a byte.
     */
    public byte getKey() {
        return key;
    }

}
