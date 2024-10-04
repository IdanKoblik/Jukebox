package com.github.idankoblik;

import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.audience.Audience;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages a queue of songs to be played in sequence.
 */
public class SongQueue {
    private final Queue<Song> songs = new LinkedList<>();
    private final Audience audience;
    private final String defaultSound;
    private Position position;
    private boolean playing = false;
    private float volume;

    /**
     * Constructs a SongQueue with the given parameters.
     *
     * @param defaultSound The default sound key.
     * @param audience     The audience that will hear the sound.
     */
    public SongQueue(String defaultSound, Audience audience) {
        this.audience = audience;
        this.defaultSound = defaultSound;
    }

    /**
     * Constructs a SongQueue with the given parameters, including a position.
     *
     * @param defaultSound The default sound key.
     * @param position     The location where the sound will be
     * @param audience     The audience that will hear the sound.
     */
    public SongQueue(String defaultSound, Position position, Audience audience) {
        this(defaultSound, audience);
        this.position = position;
    }

    /**
     * Adds a song to the queue.
     *
     * @param nbsSong The NBS song to be added to the queue.
     */
    public void addSong(NBSSong nbsSong) {
        Song song = new Song(nbsSong, defaultSound, audience, position);
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

        Song currentSong = songs.poll();
        currentSong.playSong(volume).thenAccept(v -> playNextSong());
    }

    /**
     * Stops all currently playing songs and clears the queue.
     */
    public void stopSongs() {
        playing = false;
        for (Song song : songs)
            song.stopSong();

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
}