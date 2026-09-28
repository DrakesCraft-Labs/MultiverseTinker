package com.chagui68.multiversetinker.forge.structure;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class NbtReader {

    public static Map<String, Object> readCompressed(InputStream rawIn) throws IOException {
        try (GZIPInputStream gzipIn = new GZIPInputStream(rawIn);
             DataInputStream dis = new DataInputStream(gzipIn)) {
            byte rootType = dis.readByte();
            if (rootType != 10) {
                throw new IOException("Expected root compound tag, got " + rootType);
            }
            readString(dis); // root name
            return readCompound(dis);
        }
    }

    @SuppressWarnings("unchecked")
    private static Object readPayload(byte tagType, DataInputStream dis) throws IOException {
        return switch (tagType) {
            case 1 -> dis.readByte();
            case 2 -> dis.readShort();
            case 3 -> dis.readInt();
            case 4 -> dis.readLong();
            case 5 -> dis.readFloat();
            case 6 -> dis.readDouble();
            case 7 -> {
                int len = dis.readInt();
                byte[] bytes = new byte[len];
                dis.readFully(bytes);
                yield bytes;
            }
            case 8 -> readString(dis);
            case 9 -> {
                byte elemType = dis.readByte();
                int len = dis.readInt();
                List<Object> list = new ArrayList<>(len);
                for (int i = 0; i < len; i++) {
                    list.add(readPayload(elemType, dis));
                }
                yield list;
            }
            case 10 -> readCompound(dis);
            case 11 -> {
                int len = dis.readInt();
                int[] ints = new int[len];
                for (int i = 0; i < len; i++) {
                    ints[i] = dis.readInt();
                }
                yield ints;
            }
            case 12 -> {
                int len = dis.readInt();
                long[] longs = new long[len];
                for (int i = 0; i < len; i++) {
                    longs[i] = dis.readLong();
                }
                yield longs;
            }
            default -> throw new IOException("Unknown NBT tag type: " + tagType);
        };
    }

    private static Map<String, Object> readCompound(DataInputStream dis) throws IOException {
        Map<String, Object> compound = new HashMap<>();
        while (true) {
            byte tagType = dis.readByte();
            if (tagType == 0) {
                break;
            }
            String name = readString(dis);
            Object value = readPayload(tagType, dis);
            compound.put(name, value);
        }
        return compound;
    }

    private static String readString(DataInputStream dis) throws IOException {
        int length = dis.readUnsignedShort();
        byte[] bytes = new byte[length];
        dis.readFully(bytes);
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }
}
