package io.github.idankoblik.jukebox;

import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * An implementation of the kyori song
 * @see KyoriSong
 */
public class PaperSong extends KyoriSong<PaperPlatform> {

    private final Plugin plugin;

    /**
     * {@inheritDoc}
     */
    public PaperSong(@NotNull Plugin plugin, float volume, @NotNull NBSSong song, @NotNull Key defaultSound, @NotNull Audience audience, @Nullable Position position) {
        super(song, volume, defaultSound, audience, position);
        if (volume < 0.0f || volume > 1.0f)
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");

        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
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