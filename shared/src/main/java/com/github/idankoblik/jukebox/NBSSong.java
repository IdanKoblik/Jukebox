package com.github.idankoblik.jukebox;

import com.github.idankoblik.jukebox.events.EventManager;
import com.github.idankoblik.jukebox.events.SongStageChangeEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class NBSSong {
        /**
         * The name of the song.
         */
        private final String name;

        /**
         * The author of the song.
         */
        private final String author;

        /**
         * The length of the song.
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final short length;

        /**
         * The height of the song.
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final short height;

        /**
         * The original author of the song.
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final String originalAuthor;

        /**
         * The description of the song.
         */
        @ApiStatus.AvailableSince("0.0.2")
        private final String description;

        /**
         * The tempo of the song, expressed as a float. The tempo determines how fast or slow the song plays.
         */
        private final float tempo;

        /**
         * A list of {@link NBSNote} objects representing the notes in the song.
         * Each {@link NBSNote} contains information about the pitch, instrument, and other note properties.
         */
        private final List<NBSNote> notes;

        /**
         * The state of the song.
         */
        @ApiStatus.AvailableSince("0.0.3")
        private SongState state = SongState.IDLE;

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

        public String getName() {
                return name;
        }

        public String getAuthor() {
                return author;
        }

        public short getLength() {
                return length;
        }

        public short getHeight() {
                return height;
        }

        public String getOriginalAuthor() {
                return originalAuthor;
        }

        public String getDescription() {
                return description;
        }

        public float getTempo() {
                return tempo;
        }

        public List<NBSNote> getNotes() {
                return Collections.unmodifiableList(this.notes);
        }

        @ApiStatus.AvailableSince("0.0.3")
        public SongState getState() {
                return state;
        }

        @ApiStatus.AvailableSince("0.0.3")
        public void setState(@NotNull SongState value) {
                SongState oldState = this.state;
                if (value == oldState)
                        return;

                this.state = value;
                EventManager.getInstance().fireEvent(new SongStageChangeEvent(this, oldState, this.state));
        }
}