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
    
    public void addSong(AbstractSong<P> song) {
        songs.offer(song);
    }
    
    public void playSongs() {
        if (songs.isEmpty() || playing)
            return;

        playing = true;
        playNextSong();
    }
    
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

    
    public void stopSongs() {
        playing = false;
        songs.forEach(AbstractSong::stopSong);
        songs.clear();
    }

    
    public boolean isPlaying() {
        return playing;
    }

    
    public int getRemainingSongs() {
        return songs.size();
    }

    
    public void drop() {
        this.songs.clear();
    }
}