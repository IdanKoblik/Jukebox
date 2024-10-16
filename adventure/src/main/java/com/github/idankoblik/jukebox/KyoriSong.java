package com.github.idankoblik.jukebox;

import com.github.idankoblik.jukebox.events.EventManager;
import com.github.idankoblik.jukebox.events.SongStartEvent;
import com.github.idankoblik.jukebox.manager.InstrumentManager;
import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract base class representing a musical song.
 * <p>
 * This class provides the foundation for creating songs with audience interaction
 * and sound management.
 * </p>
 */
public abstract class KyoriSong extends AbstractSong {

    private final Audience audience;
    private final Position position;
    private final Key defaultSound;

    /**
     * Constructs a KyoriSong with the given parameters.
     *
     * @param song         The underlying NBSSong to be played.
     * @param volume       The volume at which the song should be played.
     * @param defaultSound The default sound key used if no specific instrument is found.
     * @param audience     The audience that will hear the song.
     * @param position     The position of the song in the world. (optional)
     */
    public KyoriSong(@NotNull NBSSong song, float volume, @NotNull Key defaultSound, @NotNull Audience audience, @Nullable Position position) {
        super(song, volume);
        this.audience = audience;
        this.position = position;
        this.defaultSound = defaultSound;
    }

    /**
     * Plays the song, handling the playback and triggering events.
     *
     * @return A CompletableFuture that will be completed when the song has finished playing.
     */
    @Override
    public CompletableFuture<NBSSong> playSong() {
        if (song.getNotes().isEmpty()) {
            CompletableFuture<NBSSong> future = new CompletableFuture<>();
            future.complete(song);
            return future;
        }

        song.setState(SongState.PLAYING);
        EventManager.getInstance().fireEvent(new SongStartEvent(song));

        return handle(volume);
    }

    /**
     * Plays a single note using the specified instrument and pitch.
     *
     * @param instrument The instrument to use for the note.
     * @param pitch      The pitch of the note.
     */
    @Override
    public void playNote(byte instrument, float pitch) {
        Sound sound = Sound.sound(
                InstrumentManager.getInstance().getInstrument(instrument).orElse(defaultSound),
                Sound.Source.MASTER,
                volume,
                pitch
        );

        if (position == null)
            audience.playSound(sound);
        else
            audience.playSound(sound, position.getX(), position.getY(), position.getZ());
    }

    /**
     * Handles the playback of the song, implementing the actual logic for playing notes.
     *
     * @param volume The volume at which to play the song.
     * @return A CompletableFuture that will be completed when the playback is finished.
     */
    public abstract CompletableFuture<NBSSong> handle(float volume);
}