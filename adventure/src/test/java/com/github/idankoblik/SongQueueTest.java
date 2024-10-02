package com.github.idankoblik;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SongQueueTest extends AbstractSpigotSongTest {

    private SongQueue songQueue;
    private SongQueue locationSongQueue;

    @BeforeEach
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    @Override
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    void testAddSong() {
        helpAddSong(this.songQueue);
        helpAddSong(this.locationSongQueue);
    }

    private void helpAddSong(SongQueue queue) {
        assertFalse(queue.isPlaying());
        assertEquals(queue.getRemainingSongs(), 0);
        queue.addSong(this.song.song);
        assertEquals(queue.getRemainingSongs(), 1);
        assertFalse(queue.isPlaying());
    }

    @Test
    void testPlaySongs() {
        this.songQueue.playSongs(100.f);
        assertEquals(player.getHeardSounds().size(), 0);

        this.songQueue.addSong(this.song.song);
        this.songQueue.addSong(this.locationSong.song);
        assertEquals(this.songQueue.getRemainingSongs(), 2);
        this.songQueue.playSongs(100.f);
        assertTrue(this.songQueue.isPlaying());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(player.getHeardSounds().size(), 2);
        assertEquals(this.songQueue.getRemainingSongs(), 1);

        // Test for locationSongQueue
        player.getHeardSounds().clear();
        this.locationSongQueue.playSongs(100.f);
        assertEquals(player.getHeardSounds().size(), 0);

        this.locationSongQueue.addSong(this.locationSong.song);
        this.locationSongQueue.addSong(this.song.song);
        assertEquals(this.locationSongQueue.getRemainingSongs(), 2);
        this.locationSongQueue.playSongs(100.f);
        assertTrue(this.locationSongQueue.isPlaying());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(player.getHeardSounds().size(), 10);
        assertEquals(this.locationSongQueue.getRemainingSongs(), 0);
    }

    @Test
    void testStopSongs() {
        this.songQueue.stopSongs();
        assertEquals(player.getHeardSounds().size(), 0);
        assertFalse(this.songQueue.isPlaying());
        this.songQueue.addSong(this.song.song);
        this.songQueue.addSong(this.song.song);
        this.songQueue.playSongs(100.f);
        this.songQueue.stopSongs();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(this.songQueue.isPlaying());
        assertEquals(player.getHeardSounds().size(), 3);
        assertEquals(this.songQueue.getRemainingSongs(), 0);

        // Test for locationSongQueue
        player.getHeardSounds().clear();
        this.locationSongQueue.stopSongs();
        assertEquals(player.getHeardSounds().size(), 0);
        assertFalse(this.locationSongQueue.isPlaying());
        this.locationSongQueue.addSong(this.locationSong.song);
        this.locationSongQueue.addSong(this.locationSong.song);
        this.locationSongQueue.playSongs(100.f);
        this.locationSongQueue.stopSongs();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(this.locationSongQueue.isPlaying());
        assertEquals(player.getHeardSounds().size(), 3);
        assertEquals(this.locationSongQueue.getRemainingSongs(), 0);
    }

    @Override
    protected void initializeSongs() throws Exception {
        this.song = new Song(this.nbsSong, DEFAULT_NOTE, audience(player));
        this.locationSong = new Song(this.nbsSong, DEFAULT_NOTE, musicPosition, audience(world));

        this.locationSongQueue = new SongQueue(DEFAULT_NOTE, musicPosition, audience(player));
        this.songQueue = new SongQueue(DEFAULT_NOTE, audience(player));
    }
}