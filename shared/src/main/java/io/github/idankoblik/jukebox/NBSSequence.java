package io.github.idankoblik.jukebox;

import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;

/**
 * A record representing an NBS song
 */
public record NBSSequence(
        String name,
        String author,
        @ApiStatus.AvailableSince("0.0.2") short length,
        @ApiStatus.AvailableSince("0.0.2") short height,
        @ApiStatus.AvailableSince("0.0.2") String originalAuthor,
        @ApiStatus.AvailableSince("0.0.2") String description,
        float tempo,
        List<NBSNote> notes) {

        /**
         * Gets an unmodifiable list of notes in the song.
         *
         * @return a list of {@link NBSNote} objects representing the notes in the song.
         */
        public List<NBSNote> getNotes() {
                return Collections.unmodifiableList(notes);
        }
}
