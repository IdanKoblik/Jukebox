package io.github.idankoblik.jukebox;

import io.github.idankoblik.jukebox.events.SongStartEvent;
import io.github.idankoblik.jukebox.manager.KyoriInstrumentManagerImpl;
import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for managing all variations of minecraft song impl
 * @param <P> the platform of the song
 * @see AbstractSong
 */
public abstract class KyoriSong<P extends KyoriPlatform> extends AbstractSong<P> {

    private final Audience audience;
    private final Position position;
    private final Key defaultSound;

    /**
     *
     * {@inheritDoc}
     * @param defaultSound the default note to be played in case that note not found
     * @param audience the audience to play the song to
     * @param position the position of the song to be played (optional)
     */
    public KyoriSong(@NotNull NBSSequencePlayer player, float volume, @NotNull Key defaultSound, @NotNull Audience audience, @Nullable Position position) {
        super(player, volume);
        this.audience = audience;
        this.position = position;
        this.defaultSound = defaultSound;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<NBSSequencePlayer> playSong() {
        if (sequence.getNotes().isEmpty()) {
            CompletableFuture<NBSSequencePlayer> future = new CompletableFuture<>();
            future.complete(player);
            return future;
        }

        player.setState(SongState.PLAYING);
        eventManager.fireEvent(new SongStartEvent(sequence));

        return handle(volume);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playNote(byte instrument, float pitch) {
        Sound sound = Sound.sound(
                KyoriInstrumentManagerImpl.getInstance().getInstrument(instrument).orElse(defaultSound),
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
     * Abstract method for handling song playing in different platforms
     * @param volume the volume of the song
     * @return future of the song
     */
    public abstract CompletableFuture<NBSSequencePlayer> handle(float volume);
}