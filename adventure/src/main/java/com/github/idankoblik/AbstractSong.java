package com.github.idankoblik;

import com.github.idankoblik.manager.InstrumentManager;
import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Abstract base class representing a musical song.
 */
public abstract class AbstractSong {
    protected final NBSSong song;
    protected final float tickLengthInSeconds;
    protected final String defaultSound;
    protected final Audience audience;
    protected Position position;
    protected boolean playing = false;
    protected final ScheduledExecutorService scheduler;

    /**
     * Constructs an AbstractSong with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param defaultSound The default sound key.
     * @param audience     The audience that will hear the sound.
     */
    public AbstractSong(NBSSong song, @NotNull String defaultSound, Audience audience) {
        this.song = song;
        this.tickLengthInSeconds = 20f / song.tempo();
        this.audience = audience;
        this.defaultSound = defaultSound;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Constructs an AbstractSong with the given parameters, including a location.
     *
     * @param song         The NBS song to be played.
     * @param defaultSound The default sound key.
     * @param position     The position where the sound will be played.
     * @param audience     The audience that will hear the sound.
     */
    public AbstractSong(NBSSong song, @NotNull String defaultSound, @NotNull Position position, Audience audience) {
        this(song, defaultSound, audience);
        this.position = position;
    }

    /**
     * Plays the song with the specified volume.
     *
     * @param volume The volume at which to play the song.
     * @return
     */
    public abstract CompletableFuture<Void> playSong(float volume);

    /**
     * Stops the currently playing song.
     */
    public abstract void stopSong();

    /**
     * Plays a sound associated with the given instrument and pitch.
     *
     * @param instrument The instrument byte value.
     * @param pitch      The pitch of the sound.
     * @param volume     The volume of the sound.
     */
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

    /**
     * Checks if the song is currently playing.
     *
     * @return True if the song is playing, otherwise false.
     */
    public boolean isPlaying() {
        return playing;
    }
}