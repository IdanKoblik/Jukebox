package com.github.idankoblik;

import java.util.List;

/**
 * Represents a song in the NBS format.
 * <p>
 * An NBS (Noteblock Studio) song consists of a name, an author, a tempo, and a list of notes.
 * </p>
 *
 * @param name The name of the song.
 * @param author The author of the song.
 * @param tempo The tempo of the song, expressed as a float.
 * @param notes A list of {@link NBSNote} objects representing the notes in the song.
 */
public record NBSSong(
        /**
         * The name of the song.
         */
        String name,

        /**
         * The author of the song.
         */
        String author,

        /**
         * The length of the song.
         */
        short length,

        /**
         * The height of the song.
         */
        short height,

        /**
         * The original author of the song.
         */
        String originalAuthor,

        /**
         * The description of the song.
         */
        String description,

        /**
         * The tempo of the song, expressed as a float. The tempo determines how fast or slow the song plays.
         */
        float tempo,

        /**
         * A list of {@link NBSNote} objects representing the notes in the song.
         * Each {@link NBSNote} contains information about the pitch, instrument, and other note properties.
         */
        List<NBSNote> notes
) {}
