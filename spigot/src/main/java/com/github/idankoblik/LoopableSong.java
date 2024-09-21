package com.github.idankoblik;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a loopable song that can be played repeatedly.
 */
public class LoopableSong extends AbstractSong {
    private boolean loop = false;

    /**
     * Constructs a LoopableSong with the given parameters.
     *
     * @param song         The NBS song to be played.
     * @param plugin       The plugin instance.
     * @param defaultSound The default sound key.
     * @param audience     The audience that will hear the sound.
     */
    public LoopableSong(NBSSong song, Plugin plugin, @NotNull String defaultSound, Audience audience) {
        super(song, plugin, defaultSound, audience);
    }

    /**
     * Constructs a LoopableSong with the given parameters, including a location.
     *
     * @param song         The NBS song to be played.
     * @param plugin       The plugin instance.
     * @param defaultSound The default sound key.
     * @param location     The location where the sound will be played.
     * @param audience     The audience that will hear the sound.
     */
    public LoopableSong(NBSSong song, Plugin plugin, @NotNull String defaultSound, @NotNull Location location, Audience audience) {
        super(song, plugin, defaultSound, location, audience);
    }

    @Override
    public void playSong(float volume) {
        if (song.notes().isEmpty())
            return;

        playing = true;
        playNotes(volume);
    }

    private void playNotes(float volume) {
        long lastTick = song.notes().get(song.notes().size() - 1).tick();

        for (NBSNote note : song.notes()) {
            if (!playing)
                break;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!playing)
                        return;

                    float pitch = (float) Math.pow(2, (note.key() - 45) / 12.0);
                    playSound(note.instrument(), pitch, volume);

                    if (loop && note.tick() == lastTick) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (playing) {
                                    playNotes(volume);
                                }
                            }
                        }.runTaskLater(plugin, 1);
                    }
                }
            }.runTaskLater(plugin, Math.round(note.tick() * tickLengthInSeconds));
        }
    }

    @Override
    public void stopSong() {
        if (playing)
            this.playing = false;
    }

    /**
     * Checks if the song is set to loop.
     *
     * @return True if the song is set to loop, otherwise false.
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Sets whether the song should loop.
     *
     * @param loop True to loop the song, otherwise false.
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}