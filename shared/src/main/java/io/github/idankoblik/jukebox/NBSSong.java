package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.EventManager;
import io.github.idankoblik.jukebox.events.SongStageChangeEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * A class repressing nbs song
 */
public class NBSSong {

        /**
         * The name of the song
         */
        private final String name;

        /**
         * The author of the song
         */
        private final String author;

        /**
         * The length of the song
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final short length;

        /**
         * The height of the song
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final short height;

        /**
         * The original author of the song
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final String originalAuthor;

        /**
         * The description of the song
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final String description;

        /**
         * The tempo of the song
         */
        private final float tempo;

        /**
         * The notes of the song
         */
        private final List<NBSNote> notes;

        /**
         * The state of the song `IDLE` by default
         */
        @ApiStatus.AvailableSince("0.0.3")
        private SongState state = SongState.IDLE;
        
        public NBSSong(String name, String author, short length, short height, String originalAuthor, String description, float tempo, List<NBSNote> notes) {
                this.name = name;
                this.author = author;
                this.length = length;
                this.height = height;
                this.originalAuthor = originalAuthor;
                this.description = description;
                this.tempo = tempo;
                this.notes = notes;
        }

        /**
         * Gets the name of the song.
         *
         * @return the name of the song as a String.
         */
        public String getName() {
                return name;
        }

        /**
         * Gets the author of the song.
         *
         * @return the author of the song as a String.
         */
        public String getAuthor() {
                return author;
        }

        /**
         * Gets the length of the song.
         *
         * @return the length of the song in a short format.
         */
        public short getLength() {
                return length;
        }

        /**
         * Gets the height associated with the song.
         *
         * @return the height as a short.
         */
        public short getHeight() {
                return height;
        }

        /**
         * Gets the original author of the song.
         *
         * @return the original author as a String.
         */
        public String getOriginalAuthor() {
                return originalAuthor;
        }

        /**
         * Gets a description of the song.
         *
         * @return the description of the song as a String.
         */
        public String getDescription() {
                return description;
        }

        /**
         * Gets the tempo of the song.
         *
         * @return the tempo as a float.
         */
        public float getTempo() {
                return tempo;
        }

        /**
         * Gets an unmodifiable list of notes in the song.
         *
         * @return a list of {@link NBSNote} objects representing the notes in the song.
         */
        public List<NBSNote> getNotes() {
                return Collections.unmodifiableList(this.notes);
        }

        /**
         * Gets the current state of the song.
         *
         * @return the current {@link SongState} of the song.
         */
        @ApiStatus.AvailableSince("0.0.3")
        public SongState getState() {
                return state;
        }

        /**
         * Sets the state of the song.
         *
         * @param value the new {@link SongState} to set. Must not be null.
         * @throws IllegalArgumentException if the provided value is null.
         */
        @ApiStatus.AvailableSince("0.0.3")
        public void setState(@NotNull SongState value) {
                SongState oldState = this.state;
                if (value == oldState)
                        return;

                this.state = value;
                EventManager.getInstance().fireEvent(new SongStageChangeEvent(this, oldState, this.state));
        }

}