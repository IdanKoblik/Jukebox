package com.github.idan.koblik;

/**
 * Represents a single note in an NBS (Noteblock Studio) song.
 * <p>
 * Each note includes information about when it occurs, its layer, the instrument used, and the pitch of the note.
 * </p>
 *
 * @param tick The tick at which the note occurs in the song's timeline.
 * @param layer The layer in which the note is played. Different layers can be used to manage different tracks or channels.
 * @param instrument The instrument identifier used for this note. It determines the sound produced by the note.
 * @param key The pitch of the note, usually represented as a MIDI key number.
 */
public record NBSNote(
        /**
         * The tick at which the note occurs in the song's timeline.
         * Ticks represent the timing of the note within the song.
         */
        short tick,

        /**
         * The layer in which the note is played.
         * Layers can be used to separate different tracks or channels within the song.
         */
        short layer,

        /**
         * The instrument identifier used for this note.
         * Different instruments produce different sounds when the note is played.
         */
        byte instrument,

        /**
         * The pitch of the note, represented as a MIDI key number.
         * This determines the musical note that is played.
         */
        byte key
) {}
