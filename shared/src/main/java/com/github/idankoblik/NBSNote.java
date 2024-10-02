package com.github.idankoblik;

/**
 * Represents a single note in an NBS (Noteblock Studio) song.
 * Each note includes information about its timing, layer, instrument, and pitch.
 */
public class NBSNote {

    /**
     * The tick at which the note occurs in the song's timeline.
     * Ticks represent the timing of the note within the song.
     */
    private final short tick;

    /**
     * The layer in which the note is played.
     * Layers can be used to separate different tracks or channels within the song.
     */
    private final short layer;

    /**
     * The instrument identifier used for this note.
     * Different instruments produce different sounds when the note is played.
     */
    private final byte instrument;

    /**
     * The pitch of the note, represented as a MIDI key number.
     * This determines the musical note that is played.
     */
    private final byte key;

    private boolean played = false;

    /**
     * Constructs an NBSNote with specified tick, layer, instrument, and key.
     *
     * @param tick      The tick at which the note occurs in the song's timeline.
     * @param layer     The layer in which the note is played.
     * @param instrument The instrument identifier for the note.
     * @param key       The pitch of the note, represented as a MIDI key number.
     */
    public NBSNote(short tick, short layer, byte instrument, byte key) {
        this.tick = tick;
        this.layer = layer;
        this.instrument = instrument;
        this.key = key;
    }

    /**
     * Gets the tick of the note.
     *
     * @return The tick at which the note occurs.
     */
    public short getTick() {
        return tick;
    }

    /**
     * Gets the layer of the note.
     *
     * @return The layer in which the note is played.
     */
    public short getLayer() {
        return layer;
    }

    /**
     * Gets the instrument of the note.
     *
     * @return The instrument identifier used for this note.
     */
    public byte getInstrument() {
        return instrument;
    }

    /**
     * Gets the pitch of the note.
     *
     * @return The pitch of the note, represented as a MIDI key number.
     */
    public byte getKey() {
        return key;
    }

    /**
     * Checks if the note has been played.
     *
     * @return True if the note has been played, false otherwise.
     */
    public boolean isPlayed() {
        return played;
    }

    /**
     * Sets the played status of the note.
     *
     * @param played True if the note has been played, false otherwise.
     */
    public void setPlayed(boolean played) {
        this.played = played;
    }
}
