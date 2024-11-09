package io.github.idankoblik.jukebox;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A class handling a playlist of nbs songs
 * @param <P> platform of the songs inside the playlist
 */
public class Playlist<P extends Platform> {

    private final Queue<AbstractSong<P>> songs = new LinkedList<>();
    private boolean playing = false;

    /**
     * Adds a song to the playlist.
     *
     * @param song the song to be added to the playlist.
     */
    public void addSong(AbstractSong<P> song) {
        songs.offer(song);
    }

    /**
     * Starts playing the songs in the playlist. If no songs are available or a song is already playing,
     */
    public void playSongs() {
        if (songs.isEmpty() || playing)
            return;

        playing = true;
        playNextSong();
    }

    /**
     * Plays the next song in the playlist. If there are no songs left, the playlist stops playing.
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
     * Stops all songs in the playlist and clears the playlist.
     */
    public void stopSongs() {
        playing = false;
        songs.forEach(AbstractSong::stopSong);
        songs.clear();
    }

    /**
     * Returns whether any song is currently being played.
     *
     * @return true if a song is playing, false otherwise.
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Returns the number of remaining songs in the playlist.
     *
     * @return the number of remaining songs in the playlist.
     */
    public int getRemainingSongs() {
        return songs.size();
    }

    /**
     * Clears the playlist by removing all songs.
     */
    public void drop() {
        this.songs.clear();
    }
}