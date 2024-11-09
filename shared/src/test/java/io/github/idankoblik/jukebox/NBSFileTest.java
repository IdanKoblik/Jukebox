package io.github.idankoblik.jukebox;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.idankoblik.jukebox.NBSFile.readNBS;
import static io.github.idankoblik.jukebox.NBSFile.writeNBS;
import static org.junit.jupiter.api.Assertions.*;

class NBSFileTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadWriteNBS() throws IOException, URISyntaxException {
        URL resourceUrl = getClass().getClassLoader().getResource("game.nbs");
        assertNotNull(resourceUrl, "Resource not found!");

        URI resourceUri = resourceUrl.toURI();
        File file = new File(resourceUri);

        assertTrue(file.exists(), "File does not exist!");
        assertTrue(file.isFile(), "The path does not refer to a file!");

        NBSSequence originalSequence = readNBS(file);
        assertEquals("Cranium", originalSequence.author());
        assertEquals("Reptilia", originalSequence.name());
        assertEquals(27, originalSequence.height());
        assertEquals("", originalSequence.description());
        assertEquals("The Strokes", originalSequence.originalAuthor());
        assertEquals(2248, originalSequence.length());
        assertEquals(10, originalSequence.tempo());
        assertEquals(4121, originalSequence.notes().size());

        File testFile = tempDir.resolve("test.nbs").toFile();

        writeNBS(originalSequence, testFile);

        FileInputStream inputStream1 = new FileInputStream(file);
        FileInputStream inputStream2 = new FileInputStream(testFile);

        int byte1, byte2;
        while ((byte1 = inputStream1.read()) != -1 && (byte2 = inputStream2.read()) != -1)
            assertEquals(byte1, byte2);

        inputStream1.close();
        inputStream2.close();

        NBSSequence readSequence = readNBS(testFile);

        assertEquals(originalSequence.name(), readSequence.name(), "Name mismatch");
        assertEquals(originalSequence.author(), readSequence.author(), "Author mismatch");
        assertEquals(originalSequence.height(), readSequence.height(), "Height mismatch");
        assertEquals(originalSequence.description(), readSequence.description(), "Description mismatch");
        assertEquals(originalSequence.originalAuthor(), readSequence.originalAuthor(), "Original author mismatch");
        assertEquals(originalSequence.length(), readSequence.length(), "Length mismatch");
        assertEquals(originalSequence.tempo(), readSequence.tempo(), "Tempo mismatch");
        assertEquals(originalSequence.notes().size(), readSequence.notes().size(), "Notes size mismatch");
        assertEquals(originalSequence.autoSave(), readSequence.autoSave(), "Auto save mismatch");
        assertEquals(originalSequence.autoSaveDuration(), readSequence.autoSaveDuration(), "Auth save duration mismatch");
        assertEquals(originalSequence.timeSignature(), readSequence.timeSignature(), "Time signature mismatch");
        assertEquals(originalSequence.minutesSpent(), readSequence.minutesSpent(), "Minutes spent mismatch");
        assertEquals(originalSequence.leftClicks(), readSequence.leftClicks(), "Left clicks mismatch");
        assertEquals(originalSequence.rightClicks(), readSequence.rightClicks(), "Right clicks mismatch");
        assertEquals(originalSequence.noteBlocksAdded(), readSequence.noteBlocksAdded(), "Note blocks added size mismatch");
        assertEquals(originalSequence.noteBlocksRemoved(), readSequence.noteBlocksRemoved(), "Note blocks removed mismatch");
        assertEquals(originalSequence.schematicFileName(), readSequence.schematicFileName(), "Schematic file name mismatch");

        for (int i = 0; i < originalSequence.notes().size(); i++) {
            NBSNote originalNote = originalSequence.notes().get(i);
            NBSNote readNote = readSequence.notes().get(i);

            assertEquals(originalNote.tick(), readNote.tick(), "Note tick mismatch at index " + i);
            assertEquals(originalNote.layer(), readNote.layer(), "Note layer mismatch at index " + i);
            assertEquals(originalNote.instrument(), readNote.instrument(), "Note instrument mismatch at index " + i);
            assertEquals(originalNote.key(), readNote.key(), "Note key mismatch at index " + i);
        }
    }

    @Test
    void testEmptySong() throws IOException {
        NBSSequence emptySong = new NBSSequence(
                "Empty Song",
                "Test Author",
                (byte) 20,
                (byte) 1,
                "test",
                "test",
                100.0f,
                Collections.emptyList(),
                false,
                (byte) 0,
                (byte) 0,
                0,
                0,
                0,
                0,
                0,
                "testtt"
        );

        File tempFile = tempDir.resolve("empty.nbs").toFile();
        writeNBS(emptySong, tempFile);

        NBSSequence readSong = readNBS(tempFile);

        assertEquals(emptySong.name(), readSong.name(), "Song name mismatch");
        assertEquals(emptySong.author(), readSong.author(), "Author mismatch");
        assertEquals(emptySong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertTrue(readSong.notes().isEmpty(), "Notes should be empty");
    }

    @Test
    void testLongSong() throws IOException {
        List<NBSNote> longNoteList = generateLongNoteList(10000);
        NBSSequence longSong = new NBSSequence(
                "Long Song",
                "Test Author",
                (byte) 20,
                (byte) 1,
                "test",
                "test",
                140.0f,
                longNoteList,
                false,
                (byte) 0,
                (byte) 0,
                0,
                0,
                0,
                0,
                0,
                "testtt"
        );

        File tempFile = tempDir.resolve("long.nbs").toFile();
        writeNBS(longSong, tempFile);

        NBSSequence readSong = readNBS(tempFile);

        assertEquals(longSong.name(), readSong.name(), "Song name mismatch");
        assertEquals(longSong.author(), readSong.author(), "Author mismatch");
        assertEquals(longSong.tempo(), readSong.tempo(), 0.001, "Tempo mismatch");
        assertEquals(longSong.notes().size(), readSong.notes().size(), "Number of notes mismatch");
    }

    private List<NBSNote> generateLongNoteList(int count) {
        List<NBSNote> notes = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            notes.add(new NBSNote((short) i, (short) (i % 10), (byte) (i % 16), (byte) (60 + (i % 12))));
        }
        return notes;
    }
}