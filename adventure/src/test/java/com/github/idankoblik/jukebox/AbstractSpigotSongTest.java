package com.github.idankoblik.jukebox;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.github.idankoblik.jukebox.dummies.SongEndListenerDummy;
import com.github.idankoblik.jukebox.dummies.SongStageChangeListenerDummy;
import com.github.idankoblik.jukebox.dummies.SongStartListenerDummy;
import com.github.idankoblik.jukebox.events.EventManager;
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
    protected EventManager eventManager;

    protected SongStageChangeListenerDummy stageChangeListener;
    protected SongStartListenerDummy startListener;
    protected SongEndListenerDummy endListener;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.eventManager = EventManager.getInstance();
        this.eventManager.clear();

        this.stageChangeListener = new SongStageChangeListenerDummy();
        this.startListener = new SongStartListenerDummy();
        this.endListener = new SongEndListenerDummy();
        this.eventManager.registerListener(startListener);
        this.eventManager.registerListener(endListener);
        this.eventManager.registerListener(stageChangeListener);

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

    protected void testTriggeringEvents() {
        SongState state = this.nbsSong.getState();
        assertEquals(state, SongState.IDLE);

        this.song.playSong(100.f);
        assertEquals(this.nbsSong.getState(), SongState.PLAYING);
        assertTrue(stageChangeListener.isWorking());
        assertEquals(stageChangeListener.getCurrent(), SongState.PLAYING);
        assertEquals(stageChangeListener.getPrevious(), SongState.IDLE);

        assertTrue(startListener.isWorking());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(this.nbsSong.getState(), SongState.ENDED);
        assertTrue(endListener.isWorking());
        assertFalse(endListener.isForcing());

        assertTrue(stageChangeListener.isWorking());
        assertEquals(stageChangeListener.getCurrent(), SongState.ENDED);
        assertEquals(stageChangeListener.getPrevious(), SongState.PLAYING);
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
        assertEquals(this.nbsSong.getState(), SongState.ENDED);
        assertTrue(endListener.isWorking());
        assertTrue(endListener.isForcing());
    }

}
