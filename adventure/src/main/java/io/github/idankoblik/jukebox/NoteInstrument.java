package io.github.idankoblik.jukebox;

import org.jetbrains.annotations.NotNull;

/**
 * Enum representing types of note block notes (1.8)
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
     *
     * @param name The name of the note key
     */
    NoteInstrument(@NotNull String name) {
        this.name = name;
    }

    /**
     * Returns the name of the note key
     * @return the name of the note key
     */
    public @NotNull String getName() {
        return name;
    }
}