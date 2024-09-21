package com.github.idankoblik;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongTest extends AbstractSpigotSongTest {

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

    @Override
    protected void initializeSongs() throws Exception {
        this.song = new Song(this.nbsSong, plugin, DEFAULT_NOTE, audience(player));
        this.locationSong = new Song(this.nbsSong, plugin, DEFAULT_NOTE, musicLocation, audience(world));
    }
}