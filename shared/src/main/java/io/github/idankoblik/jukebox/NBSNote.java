package io.github.idankoblik.jukebox;

/**
 * A single note inside a nbs file
 */
public record NBSNote(

    /**
     * The tick of the note
     */
    short tick,

    /**
     * The layer of the note
     */
    short layer,

    /**
     * The instrument of the note
     */
    byte instrument,

    /**
     * The key of the note
     */
    byte key
) {

}
