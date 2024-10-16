package com.github.idankoblik.jukebox;

import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of KyoriSong for the Paper Minecraft server.
 * <p>
 * This class manages song playback within the Paper server environment,
 * handling the scheduling of note playback.
 * </p>
 */
public class PaperSong extends KyoriSong {

    private final Plugin plugin;

    /**
     * Constructs a PaperSong with the given parameters.
     *
     * @param plugin      The plugin instance that manages the song.
     * @param volume      The volume at which the song should be played.
     * @param song        The underlying NBSSong to be played.
     * @param defaultSound The default sound key used if no specific instrument is found.
     * @param audience    The audience that will hear the song.
     * @param position    The position of the song in the world. (optional)
     */
    public PaperSong(@NotNull Plugin plugin, float volume, @NotNull NBSSong song, @NotNull Key defaultSound, @NotNull Audience audience, @Nullable Position position) {
        super(song, volume, defaultSound, audience, position);
        if (volume < 0.0f || volume > 1.0f)
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");

        this.plugin = plugin;
    }

    /**
     * Handles the playback of the song by scheduling note playback tasks.
     *
     * @param volume The volume at which to play the song.
     * @return A CompletableFuture that will be completed when the playback is finished.
     */
    @Override
    public CompletableFuture<NBSSong> handle(float volume) {
        List<NBSNote> notes = this.song.getNotes();

        CompletableFuture<NBSSong> future = new CompletableFuture<>();
        if (notes.isEmpty()) {
            future.complete(this.song);
            return future;
        }

        new SongTick(notes, this, future).runTaskTimer(plugin, 0L, 1L);
        return future;
    }
}