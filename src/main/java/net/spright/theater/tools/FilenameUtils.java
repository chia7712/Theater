/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.tools;

import java.io.File;
import java.util.Optional;

/**
 *
 * @author ChiaPing Tsai <chia7712@gmail.com>
 */
public class FilenameUtils {
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char EXTENSION_SEPARATOR = '.';
    public static Optional<String> getBaseName(File file) {
        return removeExtension(file.getName());
    }
    public static Optional<String> getExtension(File file) {
        return getExtension(file.getName());
    }
    public static Optional<String> getExtension(String filename) {
        if (filename == null) {
            return Optional.empty();
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return Optional.of("");
        } else {
            return Optional.of(filename.substring(index + 1));
        }
    }
    public static String removeLastChar(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }
    public static Optional<String> removeExtension(String filename) {
        if (filename == null) {
            return Optional.empty();
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return Optional.of(filename);
        } else {
            return Optional.of(filename.substring(0, index));
        }
    }
    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        }
        int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }
    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);
        return (lastSeparator > extensionPos ? -1 : extensionPos);
    }
    private FilenameUtils(){}
}
