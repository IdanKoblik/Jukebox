package com.github.idankoblik;

/**
 * Represents a single note in an NBS (Noteblock Studio) song.
 * <p>
 * Each note includes information about when it occurs, its layer, the instrument used, and the pitch of the note.
 * </p>
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
     * @param tick The tick at which the note occurs in the song's timeline.
     * @param layer The layer in which the note is played. Different layers can be used to manage different tracks or channels.
     * @param instrument The instrument identifier used for this note. It determines the sound produced by the note.
     * @param key The pitch of the note, usually represented as a MIDI key number.
     */
    public NBSNote(short tick, short layer, byte instrument, byte key) {
        this.tick = tick;
        this.layer = layer;
        this.instrument = instrument;
        this.key = key;
    }

    public short getTick() {
        return tick;
    }

    public short getLayer() {
        return layer;
    }

    public byte getInstrument() {
        return instrument;
    }

    public byte getKey() {
        return key;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }
}
