package com.github.idankoblik.jukebox;

import com.github.idankoblik.jukebox.manager.InstrumentManager;
import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract base class representing a musical song.
 */
public class Song extends AbstractSong {
    private final Audience audience;
    private final Position position;

    /**
     * Constructs a Song with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param defaultSound The default sound key.
     */
    public Song(@NotNull NBSSong song, @NotNull String defaultSound, @NotNull Audience audience, @Nullable Position position) {
        super(song, defaultSound);
        this.audience = audience;
        this.position = position;
    }

    /**
     * Plays a sound associated with the given instrument and pitch.
     *
     * @param instrument The instrument byte value.
     * @param pitch      The pitch of the sound.
     * @param volume     The volume of the sound.
     */
    @Override
    protected void playSound(byte instrument, float pitch, float volume) {
        Sound sound = Sound.sound(
                InstrumentManager.getInstance().getInstrument(instrument).orElse(Key.key(defaultSound)),
                Sound.Source.MASTER,
                volume,
                pitch
        );

        if (position == null)
            audience.playSound(sound);
        else
            audience.playSound(sound, position.getX(), position.getY(), position.getZ());
    }
}