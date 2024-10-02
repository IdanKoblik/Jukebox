package com.github.idankoblik;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.apartium.cocoabeans.space.Position;
import org.bukkit.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractSpigotSongTest extends AbstractSpigotTest {

    protected static final String DEFAULT_NOTE = "note.drum";
    protected AbstractSong song;
    protected AbstractSong locationSong;
    protected NBSSong nbsSong;
    protected PlayerMock player;
    protected Position musicPosition;
    protected WorldMock world;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.player = server.addPlayer();
        NBSNote note = new NBSNote((short) 1, (short) 1, (byte) 1, (byte) 1);
        NBSNote note2 = new NBSNote((short) 2, (short) 1, (byte) 1, (byte) 1);
        NBSNote note3 = new NBSNote((short) 3, (short) 1, (byte) 1, (byte) 1);
        this.nbsSong = new NBSSong("test", "tester", (byte) 20, (byte) 1, "test", "test", 20, List.of(note, note2, note3));

        this.world = new WorldMock();
        this.server.addWorld(world);
        this.musicPosition = new Position(5, 5, 5);

        initializeSongs();
    }

    @Override
    protected void tearDown() {
        super.tearDown();
    }

    protected abstract void initializeSongs() throws Exception;

    protected void testPlayNote() {
        // Known instrument
        this.song.playSound((byte) 1, 1, 10);
        assertEquals(1, this.player.getHeardSounds().size());

        // Unknown instrument
        this.song.playSound((byte) 69, 1, 10);
        assertEquals(2, this.player.getHeardSounds().size());
    }

    protected void testLocationPlayNote() {
        // Known instrument
        this.locationSong.playSound((byte) 1, 1, 10);
        assertEquals(0, this.player.getHeardSounds().size());

        // Unknown instrument
        this.locationSong.playSound((byte) 69, 1, 10);
        assertEquals(0, this.player.getHeardSounds().size());

        this.player.teleport(new Location(world, 5, 5,5));

        // Known instrument
        this.locationSong.playSound((byte) 1, 1, 10);
        assertEquals(1, this.player.getHeardSounds().size());

        // Unknown instrument
        this.locationSong.playSound((byte) 69, 1, 10);
        assertEquals(2, this.player.getHeardSounds().size());
    }

    protected void testPlaySong() {
        Song song = (Song) this.song;
        assertFalse(song.isLoop());
        song.setLoop(true);
        assertTrue(song.isLoop());
        song.playSong(100.f);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        song.stopSong();
        assertEquals(19, this.player.getHeardSounds().size());
    }

    protected void testIsPlaying() {
        this.song.stopSong();
        assertFalse(this.song.isPlaying());
        this.song.playSong(100.f);
        assertTrue(this.song.isPlaying());
        this.song.stopSong();
        assertFalse(this.song.isPlaying());
    }
}
