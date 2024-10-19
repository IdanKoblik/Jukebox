package com.github.idankoblik.jukebox.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A utility class for managing Minecraft protocols.
 * <p>
 * This class provides methods to read and write various data types
 * in a protocol-compliant manner. It handles short and integer values,
 * as well as strings, ensuring correct byte order and encoding.
 * </p>
 */
public class ProtocolUtils {

    /**
     * Reads a short value from the given DataInputStream.
     *
     * @param dis the DataInputStream to read from
     * @return the read short value
     * @throws IOException if an I/O error occurs
     */
    public static short readShort(DataInputStream dis) throws IOException {
        return (short) (dis.readUnsignedByte() | (dis.readUnsignedByte() << 8));
    }

    /**
     * Reads an integer value from the given DataInputStream.
     *
     * @param dis the DataInputStream to read from
     * @return the read integer value
     * @throws IOException if an I/O error occurs
     */
    public static int readInt(DataInputStream dis) throws IOException {
        return dis.readUnsignedByte() | (dis.readUnsignedByte() << 8) | (dis.readUnsignedByte() << 16) | (dis.readUnsignedByte() << 24);
    }

    /**
     * Reads a string from the given DataInputStream.
     * <p>
     * The string is prefixed by its length (as an integer).
     * Carriage return characters (0x0D) are converted to spaces.
     * </p>
     *
     * @param dis the DataInputStream to read from
     * @return the read string
     * @throws IOException if an I/O error occurs
     */
    public static String readString(DataInputStream dis) throws IOException {
        int length = readInt(dis);
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) dis.readByte();
            if (c == (char) 0x0D) // Convert carriage return to space
                c = ' ';
            builder.append(c);
        }
        return builder.toString();
    }

    /**
     * Writes a short value to the given DataOutputStream.
     *
     * @param dos the DataOutputStream to write to
     * @param value the short value to write
     * @throws IOException if an I/O error occurs
     */
    public static void writeShort(DataOutputStream dos, short value) throws IOException {
        dos.writeByte(value & 0xFF);
        dos.writeByte((value >> 8) & 0xFF);
    }

    /**
     * Writes an integer value to the given DataOutputStream.
     *
     * @param dos the DataOutputStream to write to
     * @param value the integer value to write
     * @throws IOException if an I/O error occurs
     */
    public static void writeInt(DataOutputStream dos, int value) throws IOException {
        dos.writeByte(value & 0xFF);
        dos.writeByte((value >> 8) & 0xFF);
        dos.writeByte((value >> 16) & 0xFF);
        dos.writeByte((value >> 24) & 0xFF);
    }

    /**
     * Writes a string to the given DataOutputStream.
     * <p>
     * The string is prefixed by its length (as an integer).
     * </p>
     *
     * @param dos the DataOutputStream to write to
     * @param str the string to write
     * @throws IOException if an I/O error occurs
     */
    public static void writeString(DataOutputStream dos, String str) throws IOException {
        writeInt(dos, str.length());
        for (char c : str.toCharArray()) {
            dos.writeByte(c);
        }
    }
}
