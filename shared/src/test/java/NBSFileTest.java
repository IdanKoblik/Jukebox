import com.github.idankoblik.NBSFile;
import com.github.idankoblik.NBSNote;
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

import static com.github.idankoblik.NBSFile.readNBS;
import static com.github.idankoblik.NBSFile.writeNBS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NBSFileTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadWriteNBS() throws IOException {
        List<NBSNote> notes = Arrays.asList(
                new NBSNote((short) 0, (short) 0, (byte) 0, (byte) 60),
                new NBSNote((short) 4, (short) 1, (byte) 1, (byte) 62),
                new NBSNote((short) 8, (short) 0, (byte) 0, (byte) 64)
        );
        NBSSong originalSong = new NBSSong("Test Song", "Test Author", (byte) 20, (byte) 1, "test", "test", 120.0f, notes);

        File tempFile = tempDir.resolve("test.nbs").toFile();
        writeNBS(originalSong, tempFile);

        assertTrue(tempFile.exists(), "File was not created");
        assertTrue(tempFile.length() > 0, "File is empty");

        NBSSong readSong = readNBS(tempFile);

        assertEquals(originalSong.name(), readSong.name(), "Song name mismatch");
        assertEquals(originalSong.author(), readSong.author(), "Author mismatch");
        assertEquals(originalSong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertEquals(originalSong.notes().size(), readSong.notes().size(), "Number of notes mismatch");

        for (int i = 0; i < originalSong.notes().size(); i++) {
            NBSNote originalNote = originalSong.notes().get(i);
            NBSNote readNote = readSong.notes().get(i);
            assertEquals(originalNote.getTick(), readNote.getTick(), "Tick mismatch at note " + i);
            assertEquals(originalNote.getLayer(), readNote.getLayer(), "Layer mismatch at note " + i);
            assertEquals(originalNote.getInstrument(), readNote.getInstrument(), "Instrument mismatch at note " + i);
            assertEquals(originalNote.getKey(), readNote.getKey(), "Key mismatch at note " + i);
        }
    }

    @Test
    void testEmptySong() throws IOException {
        NBSSong emptySong = new NBSSong("Empty Song", "Test Author",(byte) 20, (byte) 1, "test", "test", 100.0f, Collections.emptyList());

        File tempFile = tempDir.resolve("empty.nbs").toFile();
        writeNBS(emptySong, tempFile);

        NBSSong readSong = readNBS(tempFile);

        assertEquals(emptySong.name(), readSong.name(), "Song name mismatch");
        assertEquals(emptySong.author(), readSong.author(), "Author mismatch");
        assertEquals(emptySong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertTrue(readSong.notes().isEmpty(), "Notes should be empty");
    }

    @Test
    void testLongSong() throws IOException {
        List<NBSNote> longNoteList = generateLongNoteList(10000);
        NBSSong longSong = new NBSSong("Long Song", "Test Author",(byte) 20, (byte) 1, "test", "test", 140.0f, longNoteList);

        File tempFile = tempDir.resolve("long.nbs").toFile();
        writeNBS(longSong, tempFile);

        NBSSong readSong = readNBS(tempFile);

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