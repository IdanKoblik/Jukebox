package com.github.idan.koblik;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.idan.koblik.utils.ProtocolUtils.*;

/**
 * Provides functionality to read from and write to NBS (Noteblock Studio) song files.
 * <p>
 * This class can decode NBS files into {@link NBSSong} objects and encode {@link NBSSong} objects into NBS file format.
 * </p>
 */
public class NBSParser {

    /**
     * Reads an NBS file and decodes it into an {@link NBSSong} object.
     *
     * @param file The NBS file to read from.
     * @return An {@link NBSSong} object representing the content of the NBS file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public NBSSong readNBS(File file) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            short length = readShort(dis);
            short height = readShort(dis);
            String name = readString(dis);
            String author = readString(dis);
            String originalAuthor = readString(dis);
            String description = readString(dis);
            float tempo = readShort(dis) / 100f;  // Convert tempo to ticks per second
            dis.readBoolean(); // Auto-save (not used)
            dis.readByte(); // Auto-save duration (not used)
            dis.readByte(); // Time signature (not used)
            readInt(dis); // Minutes spent (not used)
            readInt(dis); // Left-clicks (not used)
            readInt(dis); // Right-clicks (not used)
            readInt(dis); // Note blocks added (not used)
            readInt(dis); // Note blocks removed (not used)
            readString(dis); // MIDI/Schematic file name (not used)

            List<NBSNote> notes = new ArrayList<>();
            short tick = -1;
            while (true) {
                short jumpTicks = readShort(dis);
                if (jumpTicks == 0) break;
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

            return new NBSSong(name, author, tempo, notes);
        }
    }

    /**
     * Encodes an {@link NBSSong} object and writes it to an NBS file.
     *
     * @param song The {@link NBSSong} object to write to the file.
     * @param file The file to write the NBS data to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeNBS(NBSSong song, File file) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            // Write header
            writeShort(dos, (short) song.notes().size()); // length
            writeShort(dos, (short) 1); // height
            writeString(dos, song.name());
            writeString(dos, song.author());
            writeString(dos, ""); // original author
            writeString(dos, ""); // description
            writeShort(dos, (short) (song.tempo() * 100)); // tempo
            dos.writeBoolean(false); // Auto-save (default value)
            dos.writeByte(0); // Auto-save duration (default value)
            dos.writeByte(4); // Time signature (default value)
            writeInt(dos, 0); // Minutes spent (default value)
            writeInt(dos, 0); // Left-clicks (default value)
            writeInt(dos, 0); // Right-clicks (default value)
            writeInt(dos, song.notes().size()); // Note blocks added
            writeInt(dos, 0); // Note blocks removed (default value)
            writeString(dos, ""); // MIDI/Schematic file name (default value)

            // Write notes
            short lastTick = -1;
            for (NBSNote note : song.notes()) {
                writeShort(dos, (short) (note.tick() - lastTick));
                lastTick = note.tick();
                writeShort(dos, (short) (note.layer() + 1)); // Add 1 to layer as 0 means end of tick
                dos.writeByte(note.instrument());
                dos.writeByte(note.key());
                writeShort(dos, (short) 0); // End of tick
            }
            writeShort(dos, (short) 0); // End of notes

            dos.writeByte(0); // End of layers
        }
    }
}
