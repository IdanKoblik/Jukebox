package io.github.idankoblik.jukebox;

import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import io.github.idankoblik.jukebox.events.EventManager;
import net.apartium.cocoabeans.space.Position;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractPaperSongTest extends AbstractPaperTest {

    protected static final String DEFAULT_NOTE = "note.drum";
    protected PaperSong song;
    protected PaperSong locationSong;
    protected NBSSequence nbsSequence;
    protected NBSSequencePlayer sequencePlayer;
    protected PlayerMock player;
    protected Position musicPosition;
    protected WorldMock world;
    protected EventManager eventManager = new EventManager();

    protected SongStageChangeListenerDummy stageChangeListener;
    protected SongStartListenerDummy startListener;
    protected SongEndListenerDummy endListener;


    protected List<NBSNote> notes = new ArrayList<>(3);

    protected void setUp() throws Exception {
        super.setUp();

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

        this.notes.add(note);
        this.notes.add(note2);
        this.notes.add(note3);

        this.nbsSequence = new NBSSequence(
                "test",
                "tester",
                (byte) 20,
                (byte) 1,
                "test",
                "test",
                20,
                notes,
                false,
                (byte) 0,
                (byte) 0,
                0,
                0,
                0,
                0,
                0,
                "testtt");
        this.sequencePlayer = new NBSSequencePlayer(this.nbsSequence, List.of(), this.eventManager);

        this.world = new WorldMock();
        this.server.addWorld(world);
        this.musicPosition = new Position(5, 5, 5);

        initializeSongs();
        player.getHeardSounds().clear();
    }

    @Override
    protected void tearDown() {
        super.tearDown();
    }

    protected abstract void initializeSongs() throws Exception;

    protected void testPlayNote() {
        // Known instrument
        this.song.playNote((byte) 1, 1);
        assertEquals(1, this.player.getHeardSounds().size());

        // Unknown instrument
        this.song.playNote((byte) 69, 1);
        assertEquals(2, this.player.getHeardSounds().size());
    }

    protected void testTriggeringEvents() {
        NBSSequencePlayer nullEManager = new NBSSequencePlayer(
                new NBSSequence(
                        "test with no event manager",
                        "tester",
                        (byte) 20,
                        (byte) 1,
                        "test",
                        "test",
                        20,
                        notes,
                        false,
                        (byte) 0,
                        (byte) 0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        "testtt"
                ),

                List.of(
                        stageChangeListener,
                        startListener,
                        endListener
                ), null);

        PaperSong nullEManagerSong = new PaperSong(plugin, 1F, nullEManager, Key.key(DEFAULT_NOTE), player, null);
        helpTestEvents(song);
        helpTestEvents(nullEManagerSong);
    }

    protected void helpTestEvents(PaperSong song) {
        NBSSequencePlayer sequencePlayer = song.player;
        SongState state = sequencePlayer.getState();
        assertEquals(state, SongState.IDLE);

        song.playSong();
        assertEquals(sequencePlayer.getState(), SongState.PLAYING);
        assertTrue(stageChangeListener.isWorking());
        assertEquals(stageChangeListener.getCurrent(), SongState.PLAYING);
        assertEquals(stageChangeListener.getPrevious(), SongState.IDLE);

        server.getScheduler().performTicks(100L);

        assertEquals(sequencePlayer.getState(), SongState.ENDED);
        assertFalse(endListener.isForcing());

        assertTrue(stageChangeListener.isWorking());
        assertEquals(stageChangeListener.getCurrent(), SongState.ENDED);
        assertEquals(stageChangeListener.getPrevious(), SongState.PLAYING);
    }

    protected void testLocationPlayNote() {
        // Known instrument
        this.locationSong.playNote((byte) 1, 1);
        assertEquals(0, this.player.getHeardSounds().size());

        // Unknown instrument
        this.locationSong.playNote((byte) 69, 1);
        assertEquals(0, this.player.getHeardSounds().size());

        this.player.teleport(new Location(world, 5, 5,5));

        // Known instrument
        this.locationSong.playNote((byte) 1, 1);
        assertEquals(1, this.player.getHeardSounds().size());

        // Unknown instrument
        this.locationSong.playNote((byte) 69, 1);
        assertEquals(2, this.player.getHeardSounds().size());
    }

    protected void testPlaySong() {
        PaperSong song = (PaperSong) this.song;
        assertFalse(song.toLoop());
        song.setLoop(true);
        assertTrue(song.toLoop());
        song.playSong();
        server.getScheduler().performTicks(4);
        song.stopSong();
        assertEquals(3, this.player.getHeardSounds().size());
        assertEquals(this.sequencePlayer.getState(), SongState.ENDED);
        assertTrue(endListener.isWorking());
        assertTrue(endListener.isForcing());
    }

}
