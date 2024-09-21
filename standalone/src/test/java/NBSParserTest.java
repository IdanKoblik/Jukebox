import com.github.idankoblik.NBSNote;
import com.github.idankoblik.NBSParser;
import com.github.idankoblik.NBSSong;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NBSParserTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadWriteNBS() throws IOException {
        NBSParser decoder = new NBSParser();

        List<NBSNote> notes = Arrays.asList(
                new NBSNote((short) 0, (short) 0, (byte) 0, (byte) 60),
                new NBSNote((short) 4, (short) 1, (byte) 1, (byte) 62),
                new NBSNote((short) 8, (short) 0, (byte) 0, (byte) 64)
        );
        NBSSong originalSong = new NBSSong("Test Song", "Test Author", 120.0f, notes);

        File tempFile = tempDir.resolve("test.nbs").toFile();
        decoder.writeNBS(originalSong, tempFile);

        assertTrue(tempFile.exists(), "File was not created");
        assertTrue(tempFile.length() > 0, "File is empty");

        NBSSong readSong = decoder.readNBS(tempFile);

        assertEquals(originalSong.name(), readSong.name(), "Song name mismatch");
        assertEquals(originalSong.author(), readSong.author(), "Author mismatch");
        assertEquals(originalSong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertEquals(originalSong.notes().size(), readSong.notes().size(), "Number of notes mismatch");

        for (int i = 0; i < originalSong.notes().size(); i++) {
            NBSNote originalNote = originalSong.notes().get(i);
            NBSNote readNote = readSong.notes().get(i);
            assertEquals(originalNote.tick(), readNote.tick(), "Tick mismatch at note " + i);
            assertEquals(originalNote.layer(), readNote.layer(), "Layer mismatch at note " + i);
            assertEquals(originalNote.instrument(), readNote.instrument(), "Instrument mismatch at note " + i);
            assertEquals(originalNote.key(), readNote.key(), "Key mismatch at note " + i);
        }
    }

    @Test
    void testEmptySong() throws IOException {
        NBSParser decoder = new NBSParser();
        NBSSong emptySong = new NBSSong("Empty Song", "Test Author", 100.0f, Collections.emptyList());

        File tempFile = tempDir.resolve("empty.nbs").toFile();
        decoder.writeNBS(emptySong, tempFile);

        NBSSong readSong = decoder.readNBS(tempFile);

        assertEquals(emptySong.name(), readSong.name(), "Song name mismatch");
        assertEquals(emptySong.author(), readSong.author(), "Author mismatch");
        assertEquals(emptySong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertTrue(readSong.notes().isEmpty(), "Notes should be empty");
    }

    @Test
    void testLongSong() throws IOException {
        NBSParser decoder = new NBSParser();
        List<NBSNote> longNoteList = generateLongNoteList(10000);
        NBSSong longSong = new NBSSong("Long Song", "Test Author", 140.0f, longNoteList);

        File tempFile = tempDir.resolve("long.nbs").toFile();
        decoder.writeNBS(longSong, tempFile);

        NBSSong readSong = decoder.readNBS(tempFile);

        assertEquals(longSong.name(), readSong.name(), "Song name mismatch");
        assertEquals(longSong.author(), readSong.author(), "Author mismatch");
        assertEquals(longSong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertEquals(longSong.notes().size(), readSong.notes().size(), "Number of notes mismatch");
    }

    private List<NBSNote> generateLongNoteList(int count) {
        List<NBSNote> notes = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            notes.add(new NBSNote((short) i, (short) (i % 10), (byte) (i % 16), (byte) (60 + (i % 12))));

        return notes;
    }
}