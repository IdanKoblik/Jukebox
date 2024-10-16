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
 */
public abstract class KyoriSong extends AbstractSong {

    private final Audience audience;
    private final Position position;
    private final Key defaultSound;

    /**
     * Constructs a Song with the given parameters.
     * {@inheritDoc}
     * @param defaultSound The default sound key.
     * @param position     The position of the song to be played. (optional)
     */
    public KyoriSong(@NotNull NBSSong song, float volume, @NotNull Key defaultSound, @NotNull Audience audience, @Nullable Position position) {
        super(song, volume);

        this.audience = audience;
        this.position = position;
        this.defaultSound = defaultSound;
    }


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

    public abstract CompletableFuture<NBSSong> handle(float volume);
}