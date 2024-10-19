package com.github.idankoblik.jukebox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.idankoblik.jukebox.utils.ProtocolUtils.*;

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
    public static NBSSong readNBS(File file) throws IOException {
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

            return new NBSSong(name, author, length, height, originalAuthor, description,tempo, notes);
        }
    }

    /**
     * Encode a file to nbs file
     * @param song the nbs song to be encoded into a file
     * @param file a file to encode the nbs song into
     * @throws IOException
     */
    public static void writeNBS(NBSSong song, File file) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            // Write header
            writeShort(dos, (short) song.getNotes().size()); // length
            writeShort(dos, (short) 1); // height
            writeString(dos, song.getName());
            writeString(dos, song.getAuthor());
            writeString(dos, ""); // original author
            writeString(dos, ""); // description
            writeShort(dos, (short) (song.getTempo() * 100)); // tempo
            dos.writeBoolean(false); // Auto-save (default value)
            dos.writeByte(0); // Auto-save duration (default value)
            dos.writeByte(4); // Time signature (default value)
            writeInt(dos, 0); // Minutes spent (default value)
            writeInt(dos, 0); // Left-clicks (default value)
            writeInt(dos, 0); // Right-clicks (default value)
            writeInt(dos, song.getNotes().size()); // Note blocks added
            writeInt(dos, 0); // Note blocks removed (default value)
            writeString(dos, ""); // MIDI/Schematic file name (default value)

            // Write notes
            short lastTick = -1;
            for (NBSNote note : song.getNotes()) {
                writeShort(dos, (short) (note.getTick() - lastTick));
                lastTick = note.getTick();
                writeShort(dos, (short) (note.getLayer() + 1)); // Add 1 to layer as 0 means end of tick
                dos.writeByte(note.getInstrument());
                dos.writeByte(note.getKey());
                writeShort(dos, (short) 0); // End of tick
            }
            writeShort(dos, (short) 0); // End of notes

            dos.writeByte(0); // End of layers
        }
    }
}
