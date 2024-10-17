package com.github.idankoblik.jukebox;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SongQueueTest<P extends PaperPlatform> extends AbstractPaperSongTest {

    private SongQueue<P> songQueue;
    private SongQueue<P> positionSongQueue;

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        songQueue.drop();
        positionSongQueue.drop();
        player.getHeardSounds().clear();
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Override
    protected void initializeSongs() {
        this.song = new PaperSong(plugin, 1F, this.nbsSong, Key.key(DEFAULT_NOTE), player, null);

        this.positionSongQueue = new SongQueue<>();
        this.songQueue = new SongQueue<>();
    }

    @Test
    void testAddSong() {
        helpAddSong(this.songQueue);
        helpAddSong(this.positionSongQueue);
    }

    @Test
    void testPlayPlaylist() {
        songQueue.addSong(song);
        songQueue.addSong(song);

        songQueue.playSongs();
        server.getScheduler().performTicks(8);

        assertEquals(player.getHeardSounds().size(), 6);
    }

    @Test
    void testStopPlaylist() {
        helpAddSong(this.songQueue);
        this.songQueue.addSong(song);

        this.songQueue.playSongs();
        server.getScheduler().performTicks(8);

        this.songQueue.stopSongs();

        assertEquals(player.getHeardSounds().size(), 6);
    }

    private void helpAddSong(SongQueue<P> queue) {
        assertFalse(queue.isPlaying());
        assertEquals(queue.getRemainingSongs(), 0);
        queue.addSong(song);
        assertEquals(queue.getRemainingSongs(), 1);
        assertFalse(queue.isPlaying());
    }
}
