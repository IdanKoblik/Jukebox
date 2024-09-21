package com.github.idankoblik;

import com.github.idankoblik.manager.InstrumentManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class representing a musical song.
 */
public abstract class AbstractSong {
    protected final NBSSong song;
    protected final Plugin plugin;
    protected final float tickLengthInSeconds;
    protected final String defaultSound;
    protected final Audience audience;
    protected Location location;
    protected boolean playing = false;

    /**
     * Constructs an AbstractSong with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param plugin       The plugin instance.
     * @param defaultSound The default sound key.
     * @param audience     The audience that will hear the sound.
     */
    public AbstractSong(NBSSong song, Plugin plugin, @NotNull String defaultSound, Audience audience) {
        this.song = song;
        this.plugin = plugin;
        this.tickLengthInSeconds = 20f / song.tempo();
        this.audience = audience;
        this.defaultSound = defaultSound;
    }

    /**
     * Constructs an AbstractSong with the given parameters, including a location.
     *
     * @param song         The NBS song to be played.
     * @param plugin       The plugin instance.
     * @param defaultSound The default sound key.
     * @param location     The location where the sound will be played.
     * @param audience     The audience that will hear the sound.
     */
    public AbstractSong(NBSSong song, Plugin plugin, @NotNull String defaultSound, @NotNull Location location, Audience audience) {
        this(song, plugin, defaultSound, audience);
        this.location = location;
    }

    /**
     * Plays the song with the specified volume.
     *
     * @param volume The volume at which to play the song.
     */
    public abstract void playSong(float volume);

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

        if (location == null)
            audience.playSound(sound);
        else
            audience.playSound(sound, location.getX(), location.getY(), location.getZ());
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