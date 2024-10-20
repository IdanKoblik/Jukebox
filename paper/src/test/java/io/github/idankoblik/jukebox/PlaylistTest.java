package io.github.idankoblik.jukebox;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlaylistTest extends AbstractPaperSongTest {

    private Playlist<PaperPlatform> playlist;
    private Playlist<PaperPlatform> positionPlaylist;

    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        playlist.drop();
        positionPlaylist.drop();
        player.getHeardSounds().clear();
    }

    @AfterEach
    protected void tearDown() {
        super.tearDown();
    }

    @Override
    protected void initializeSongs() {
        this.song = new PaperSong(plugin, 1F, this.nbsSong, Key.key(DEFAULT_NOTE), player, null);

        this.positionPlaylist = new Playlist<>();
        this.playlist = new Playlist<>();
    }

    @Test
    void testAddSong() {
        helpAddSong(this.playlist);
        helpAddSong(this.positionPlaylist);
    }

    @Test
    void testPlayPlaylist() {
        playlist.addSong(song);
        playlist.addSong(song);

        playlist.playSongs();
        server.getScheduler().performTicks(8);

        assertEquals(player.getHeardSounds().size(), 6);
    }

    @Test
    void testStopPlaylist() {
        helpAddSong(this.playlist);
        this.playlist.addSong(song);

        this.playlist.playSongs();
        server.getScheduler().performTicks(8);

        this.playlist.stopSongs();

        assertEquals(player.getHeardSounds().size(), 6);
    }

    private void helpAddSong(Playlist<PaperPlatform> queue) {
        assertFalse(queue.isPlaying());
        assertEquals(queue.getRemainingSongs(), 0);
        queue.addSong(song);
        assertEquals(queue.getRemainingSongs(), 1);
        assertFalse(queue.isPlaying());
    }
}
