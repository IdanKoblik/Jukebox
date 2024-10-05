package com.github.idankoblik.jukebox.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Utility class for reading and writing various data types to and from streams in a specific protocol format.
 * <p>
 * This class provides methods for reading and writing short integers, integers, and strings using {@link DataInputStream}
 * and {@link DataOutputStream}.
 * </p>
 */
public class ProtocolUtils {

    /**
     * Reads a short integer (16 bits) from a {@link DataInputStream}.
     *
     * @param dis The {@link DataInputStream} to read from.
     * @return The short integer read from the stream.
     * @throws IOException If an I/O error occurs while reading from the stream.
     */
    public static short readShort(DataInputStream dis) throws IOException {
        return (short) (dis.readUnsignedByte() | (dis.readUnsignedByte() << 8));
    }

    /**
     * Reads an integer (32 bits) from a {@link DataInputStream}.
     *
     * @param dis The {@link DataInputStream} to read from.
     * @return The integer read from the stream.
     * @throws IOException If an I/O error occurs while reading from the stream.
     */
    public static int readInt(DataInputStream dis) throws IOException {
        return dis.readUnsignedByte() | (dis.readUnsignedByte() << 8) | (dis.readUnsignedByte() << 16) | (dis.readUnsignedByte() << 24);
    }

    /**
     * Reads a string from a {@link DataInputStream}. The string is preceded by an integer indicating its length.
     *
     * @param dis The {@link DataInputStream} to read from.
     * @return The string read from the stream.
     * @throws IOException If an I/O error occurs while reading from the stream.
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
     * Writes a short integer (16 bits) to a {@link DataOutputStream}.
     *
     * @param dos The {@link DataOutputStream} to write to.
     * @param value The short integer to write.
     * @throws IOException If an I/O error occurs while writing to the stream.
     */
    public static void writeShort(DataOutputStream dos, short value) throws IOException {
        dos.writeByte(value & 0xFF);
        dos.writeByte((value >> 8) & 0xFF);
    }

    /**
     * Writes an integer (32 bits) to a {@link DataOutputStream}.
     *
     * @param dos The {@link DataOutputStream} to write to.
     * @param value The integer to write.
     * @throws IOException If an I/O error occurs while writing to the stream.
     */
    public static void writeInt(DataOutputStream dos, int value) throws IOException {
        dos.writeByte(value & 0xFF);
        dos.writeByte((value >> 8) & 0xFF);
        dos.writeByte((value >> 16) & 0xFF);
        dos.writeByte((value >> 24) & 0xFF);
    }

    /**
     * Writes a string to a {@link DataOutputStream}. The string is preceded by an integer indicating its length.
     *
     * @param dos The {@link DataOutputStream} to write to.
     * @param str The string to write.
     * @throws IOException If an I/O error occurs while writing to the stream.
     */
    public static void writeString(DataOutputStream dos, String str) throws IOException {
        writeInt(dos, str.length());
        for (char c : str.toCharArray()) {
            dos.writeByte(c);
        }
    }
}
