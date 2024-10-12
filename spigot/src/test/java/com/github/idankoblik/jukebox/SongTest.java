package com.github.idankoblik.jukebox;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongTest extends AbstractSpigotSongTest {

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Test
    protected void testPlayNote() {
        super.testPlayNote();
    }

    @Test
    protected void testLocationPlayNote() {
        super.testLocationPlayNote();
    }

    @Test
    protected void testPlaySong() {
        super.testPlaySong();
    }

    @Test
    protected void testTriggeringEvents() {
        super.testTriggeringEvents();
    }

    @Override
    protected void initializeSongs() {
        this.song = new Song(this.nbsSong, Key.key(DEFAULT_NOTE), player, null);
        this.locationSong = new Song(this.nbsSong, Key.key(DEFAULT_NOTE), world, musicPosition);
    }
}