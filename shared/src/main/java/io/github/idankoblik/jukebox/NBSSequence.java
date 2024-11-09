package io.github.idankoblik.jukebox;

import org.jetbrains.annotations.ApiStatus;

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
        List<NBSNote> notes,
        @ApiStatus.AvailableSince("0.0.5") boolean autoSave,
        @ApiStatus.AvailableSince("0.0.5") byte autoSaveDuration,
        @ApiStatus.AvailableSince("0.0.5") byte timeSignature,
        @ApiStatus.AvailableSince("0.0.5") int minutesSpent,
        @ApiStatus.AvailableSince("0.0.5") int leftClicks,
        @ApiStatus.AvailableSince("0.0.5") int rightClicks,
        @ApiStatus.AvailableSince("0.0.5") int noteBlocksAdded,
        @ApiStatus.AvailableSince("0.0.5") int noteBlocksRemoved,
        @ApiStatus.AvailableSince("0.0.5") String schematicFileName
) {

        /**
         * Constructor that ensures the notes list is immutable.
         */
        public NBSSequence {
                notes = List.copyOf(notes);
        }

}