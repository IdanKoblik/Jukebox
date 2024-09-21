package com.github.idankoblik;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoopableSongTest extends AbstractSpigotSongTest {

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    @Override
    protected void testPlayNote() {
        super.testPlayNote();
    }

    @Test
    @Override
    protected void testLocationPlayNote() {
        super.testLocationPlayNote();
    }

    @Test
    @Override
    protected void testPlaySong() {
        super.testPlaySong();
    }

    @Test
    @Override
    protected void testIsPlaying() {
        super.testIsPlaying();
    }

    @Test
    @Override
    protected void initializeSongs() throws Exception {
        this.song = new LoopableSong(this.nbsSong, plugin, DEFAULT_NOTE, audience(player));
        this.locationSong = new LoopableSong(this.nbsSong, plugin, DEFAULT_NOTE, musicLocation, audience(world));
    }

    @Test
    void testPlayLoopedSong() {
        LoopableSong loopableSong = (LoopableSong) this.song;
        assertFalse(loopableSong.isLoop());
        loopableSong.setLoop(true);
        assertTrue(loopableSong.isLoop());
        loopableSong.playSong(100.f);
        server.getScheduler().performTicks(300L);
        loopableSong.stopSong();
        assertEquals(225, this.player.getHeardSounds().size());
    }
}