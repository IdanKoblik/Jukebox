package com.github.idankoblik.jukebox;

/*
public class SongQueueTest extends AbstractPaperSongTest {

    private SongQueue songQueue;
    private SongQueue positionSongQueue;

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        songQueue.drop();
        positionSongQueue.drop();
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Override
    protected void initializeSongs() {
        this.song = new Song(this.nbsSong, Key.key(DEFAULT_NOTE), player, null);

        this.positionSongQueue = new SongQueue(Key.key(DEFAULT_NOTE), player, musicPosition);
        this.songQueue = new SongQueue(Key.key(DEFAULT_NOTE), player, null);
    }

    @Test
    void testAddSong() {
        helpAddSong(this.songQueue);
        helpAddSong(this.positionSongQueue);
    }

    @Test
    void testPlayPlaylist() {
        helpTestPlayPlaylist(this.songQueue);
        helpTestPlayPlaylist(this.positionSongQueue);
    }

    private void helpTestPlayPlaylist(SongQueue queue) {
        player.getHeardSounds().clear();
        queue.addSong(this.nbsSong);
        queue.addSong(this.nbsSong);

        queue.playSongs(100.f);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(player.getHeardSounds().size(), 5);
    }

    @Test
    void testStopPlaylist() {
        helpAddSong(this.songQueue);
        this.songQueue.addSong(this.nbsSong);

        this.songQueue.playSongs(100.f);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.songQueue.stopSongs();

        assertEquals(player.getHeardSounds().size(), 3);
    }

    private void helpAddSong(SongQueue queue) {
        assertFalse(queue.isPlaying());
        assertEquals(queue.getRemainingSongs(), 0);
        queue.addSong(this.nbsSong);
        assertEquals(queue.getRemainingSongs(), 1);
        assertFalse(queue.isPlaying());
    }
}
*/
