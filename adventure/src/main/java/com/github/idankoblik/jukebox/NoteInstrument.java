package com.github.idankoblik.jukebox;

import org.jetbrains.annotations.NotNull;

/**
 * Enumeration of note instruments with their corresponding sound names. (1.8)
 */
public enum NoteInstrument {
    HARP("note.harp"),
    BASS("note.bass"),
    BASEDRUM("note.basedrum"),
    SNARE("note.snare"),
    HAT("note.hat"),
    GUITAR("note.guitar"),
    FLUTE("note.flute"),
    BELL("note.bell"),
    CHIME("note.chime"),
    XYLOPHONE("note.xylophone");

    private @NotNull final String name;

    /**
     * Constructs a NoteInstrument with the given sound name.
     *
     * @param name The sound name of the instrument.
     */
    NoteInstrument(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the name of the instrument.
     *
     * @return The sound name of the instrument.
     */
    public @NotNull String getName() {
        return name;
    }
}