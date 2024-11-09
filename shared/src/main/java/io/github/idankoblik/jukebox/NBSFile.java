package io.github.idankoblik.jukebox;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.github.idankoblik.jukebox.utils.ProtocolUtils.*;

/**
 * A util class for encoding and decoding nbs file
 */
public class NBSFile {

    /**
     * Decodes a nbs file
     * @param file the nbs file to be decoded
     * @return decoded version of the nbs song
     * @throws IOException
     */
    public static NBSSequence readNBS(File file) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            short length = readShort(dis);
            short height = readShort(dis);
            String name = readString(dis);
            String author = readString(dis);
            String originalAuthor = readString(dis);
            String description = readString(dis);
            float tempo = readShort(dis) / 100f;  // Convert tempo to ticks per second
            boolean autoSave = dis.readBoolean();// Auto-save (not used)
            byte authSaveDuration = dis.readByte(); // Auto-save duration (not used)
            byte timeSignature = dis.readByte(); // Time signature (not used)
            int minutesSpent = readInt(dis); // Minutes spent (not used)
            int leftClicks = readInt(dis); // Left-clicks (not used)
            int rightClicks = readInt(dis); // Right-clicks (not used)
            int noteBlocksAdded = readInt(dis); // Note blocks added (not used)
            int noteBlocksRemoved = readInt(dis); // Note blocks removed (not used)
            String schematicFileName = readString(dis); // MIDI/Schematic file name (not used)

            List<NBSNote> notes = new ArrayList<>();
            short tick = -1;
            while (true) {
                short jumpTicks = readShort(dis);
                if (jumpTicks == 0)
                    break;

                tick += jumpTicks;
                short layer = -1;
                while (true) {
                    short jumpLayers = readShort(dis);
                    if (jumpLayers == 0) break;
                    layer += jumpLayers;
                    byte instrument = dis.readByte();
                    byte key = dis.readByte();
                    notes.add(new NBSNote(tick, layer, instrument, key));
                }
            }

            return new NBSSequence(name, author, length, height, originalAuthor, description, tempo, notes, autoSave, authSaveDuration, timeSignature, minutesSpent, leftClicks, rightClicks, noteBlocksAdded, noteBlocksRemoved, schematicFileName);
        }
    }

    /**
     * Encode a file to nbs file
     * @param sequence the nbs sequence to be encoded into a file
     * @param file a file to encode the nbs sequence into
     * @throws IOException
     */
    public static void writeNBS(NBSSequence sequence, File file) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            // Write header
            writeShort(dos, sequence.length()); // Use length from the sequence instead of notes size
            writeShort(dos, (short) sequence.height());
            writeString(dos, sequence.name());
            writeString(dos, sequence.author());
            writeString(dos, sequence.originalAuthor());
            writeString(dos, sequence.description());
            writeShort(dos, (short) (sequence.tempo() * 100));
            dos.writeBoolean(sequence.autoSave()); // Auto-save
            dos.writeByte(sequence.autoSaveDuration()); // Auto-save duration
            dos.writeByte(sequence.timeSignature()); // Time signature
            writeInt(dos, sequence.minutesSpent()); // Minutes spent
            writeInt(dos, sequence.leftClicks()); // Left-clicks
            writeInt(dos, sequence.rightClicks()); // Right-clicks
            writeInt(dos, sequence.noteBlocksAdded()); // Note blocks added
            writeInt(dos, sequence.noteBlocksRemoved()); // Note blocks removed
            writeString(dos, sequence.schematicFileName()); // MIDI/Schematic file name

            List<NBSNote> sortedNotes = new ArrayList<>(sequence.notes());
            sortedNotes.sort(Comparator.comparingInt(NBSNote::tick).thenComparingInt(NBSNote::layer));

            if (!sortedNotes.isEmpty()) {
                short lastTick = -1;
                short lastLayer = -1;

                for (NBSNote note : sortedNotes) {
                    if (note.tick() != lastTick) {
                        if (lastTick != -1)
                            writeShort(dos, (short) 0); // End of layers for previous tick

                        // Write jump to new tick
                        writeShort(dos, (short) (note.tick() - lastTick));
                        lastTick = note.tick();
                        lastLayer = -1;
                    }

                    // Write layer jump and note data
                    writeShort(dos, (short) (note.layer() - lastLayer));
                    dos.writeByte(note.instrument());
                    dos.writeByte(note.key());

                    lastLayer = note.layer();
                }

                writeShort(dos, (short) 0);
            }

            writeShort(dos, (short) 0);
        }
    }
}