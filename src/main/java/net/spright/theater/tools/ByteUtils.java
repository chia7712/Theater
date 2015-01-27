/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.tools;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Tsai ChiaPing <chia7712@gmail.com>
 */
public class ByteUtils {
    private static final String UTF8_ENCODING = "UTF-8";
    private static final Charset UTF8_CHARSET = Charset.forName(UTF8_ENCODING);
    public static String toString(byte[] buf) {
        return toString(buf, 0, buf.length);
    }
    public static String toString(byte[] buf, int offset, int length) {
        return new String(buf, offset, length, UTF8_CHARSET);
    }
    public static long toLong(byte[] buf) {
        return toLong(buf, 0, buf.length);
    }
    public static long toLong(byte[] buf, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(buf, offset, length);
        return buffer.getLong();
    }
    public static int toInteger(byte[] buf) {
        return toInteger(buf, 0, buf.length);
    }
    public static int toInteger(byte[] buf, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(buf, offset, length);
        return buffer.getInt();
    }
    public static byte[] toBytes(String value) {
        return value.getBytes(UTF8_CHARSET);
    }
    public static byte[] toBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return buffer.array();
    }
    public static byte[] toBytes(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);
        return buffer.array();
    }
    
    private ByteUtils(){}
}
