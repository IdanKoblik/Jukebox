package com.github.idankoblik.jukebox;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages a queue of songs to be played in sequence.
 */
public class Playlist<P extends Platform> {

    private final Queue<AbstractSong<P>> songs = new LinkedList<>();
    private boolean playing = false;

    /**
     * Adds a song to the queue.
     *
     * @param song The song to be added to the queue.
     */
    public void addSong(AbstractSong<P> song) {
        songs.offer(song);
    }

    /**
     * Starts playing songs in the queue with the specified volume.
     */
    public void playSongs() {
        if (songs.isEmpty() || playing)
            return;

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

        AbstractSong<P> currentSong = songs.poll();
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
        songs.forEach(AbstractSong::stopSong);
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