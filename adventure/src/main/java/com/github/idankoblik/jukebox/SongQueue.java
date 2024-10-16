package com.github.idankoblik.jukebox;

import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages a queue of songs to be played in sequence.
 */
public class SongQueue {
    private final Queue<KyoriSong> songs = new LinkedList<>();
    private final Audience audience;
    private final Key defaultSound;
    private final Position position;
    private boolean playing = false;
    private float volume;

    /**
     * Constructs a SongQueue with the given parameters.
     *
     * @param defaultSound The default sound key.
     * @param audience     The audience that will hear the sound.
     * @param position     The position of the song to be played. (optional)
     */
    public SongQueue(@NotNull Key defaultSound, @NotNull Audience audience, @Nullable Position position) {
        this.audience = audience;
        this.defaultSound = defaultSound;
        this.position = position;
    }

    /**
     * Adds a song to the queue.
     *
     * @param song The song to be added to the queue.
     */
    public void addSong(KyoriSong song) {
        songs.offer(song);
    }

    /**
     * Starts playing songs in the queue with the specified volume.
     *
     * @param volume The volume at which to play the songs.
     */
    public void playSongs(float volume) {
        if (songs.isEmpty() || playing)
            return;

        this.volume = volume;
        playing = true;
        playNextSong();
    }

    /**
     * Plays the next song in the queue.
     */
    private void playNextSong() {
        if (!playing || songs.isEmpty()) {
            playing = false;
            return;
        }

        KyoriSong currentSong = songs.poll();
        if (currentSong == null)
            return;

        currentSong.playSong().thenAccept(song -> {
            song.setState(SongState.IDLE);
            playNextSong();
        });
    }

    /**
     * Stops all currently playing songs and clears the queue.
     */
    public void stopSongs() {
        playing = false;
        songs.forEach(KyoriSong::stopSong);
        songs.clear();
    }

    /**
     * Checks if any songs are currently playing.
     *
     * @return True if any songs are playing, otherwise false.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Gets the number of remaining songs in the queue.
     *
     * @return The number of songs left in the queue.
     */
    public int getRemainingSongs() {
        return songs.size();
    }

    /**
     * Drops all songs from the queue.
     */
    public void drop() {
        this.songs.clear();
    }
}