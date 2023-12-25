package com.pixelsapphire.silicon.util;

import com.pixelsapphire.toolbox.StringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.stream.Stream;

/**
 * A utility class containing several methods for manipulating strings.
 */
public class TextUtils {

    /**
     * Converts a string to a hex string. Each byte is represented by two characters (padding with 0 if necessary).
     *
     * @param text the string to convert
     * @return the hex string
     */
    public static @NotNull String encodeAsHexBytes(@NotNull String text) {
        final StringBuilder builder = new StringBuilder();
        builder.appendAll(text.getBytes(), b -> String.format("%02x", b));
        return builder.toString();
    }

    /**
     * Converts a string to a new string that contains only letters, a-f and A-K. This is done by
     * converting the string to hex bytes and then replacing all base-10 digits with capital letters.
     *
     * @param text the string to convert
     * @return the pseudo-hex string
     */
    public static @NotNull String encodeAsPseudoHexBytes(@NotNull String text) {
        final StringBuilder builder = new StringBuilder();
        for (final byte b : text.getBytes())
            builder.appendAll(String.format("%02x", b).toCharArray(), c -> Character.isDigit(c) ? (char) (c + 17) : c);
        return builder.toString();
    }

    /**
     * Replaces all occurrences of underscores ({@code _}) with dashes ({@code -}).
     *
     * @param text the string to convert
     * @return the converted string
     */
    public static @NotNull String u2d(@NotNull String text) {
        return text.replace("_", "-");
    }

    /**
     * Encodes an integer to a string. The integer is encoded as a sequence of
     * bytes, each represented by two hex digits. Leading zero bytes are omitted,
     * unless the integer is zero, in which case a single zero byte is encoded.
     *
     * @param value the integer to encode
     * @return the encoded string
     */
    public static @NotNull String encodeAsHexBytes(int value) {
        if (value == 0) return "00";
        final StringBuilder encoded = new StringBuilder();
        encoded.appendAll(Stream.of((byte) ((value >> 24) & 0xFF), (byte) ((value >> 16) & 0xFF), (byte) ((value >> 8) & 0xFF),
                                    (byte) (value & 0xFF)).dropWhile((b) -> b == 0).map(b -> String.format("%02x", b)).toList());
        return encoded.toString();
    }

    /**
     * Encodes an integer to a string that contains only letters, a-f and A-K. This is done
     * by encoding the integer as a sequence of bytes, each represented by two hex digits,
     * and then replacing all base-10 digits with capital letters. Leading zero bytes are
     * omitted, unless the integer is zero, in which case a single zero byte is encoded.
     *
     * @param value the integer to encode
     * @return the encoded string
     */
    public static @NotNull String encodeAsPseudoHexBytes(int value) {
        if (value == 0) return "AA";
        final StringBuilder encoded = new StringBuilder();
        for (final byte b : Stream.of((byte) ((value >> 24) & 0xFF), (byte) ((value >> 16) & 0xFF), (byte) ((value >> 8) & 0xFF),
                                      (byte) (value & 0xFF)).dropWhile((b) -> b == 0).toList())
            encoded.appendAll(String.format("%02x", b).toCharArray(), c -> Character.isDigit(c) ? (char) (c + 17) : c);
        return encoded.toString();
    }

    /**
     * Determines whether the character sequence defined by the specified array, start index and length matches
     * any of the specified strings. This method returns {@code false} instead of throwing an exception if
     * specified {@code length} is greater than the number of characters after the specified {@code start} index.
     *
     * @param string  the character array containing the sequence
     * @param start   the start index of the sequence
     * @param length  the length of the sequence
     * @param strings the strings to match against the sequence
     * @return {@code true} if the sequence matches any of the strings, {@code false} otherwise
     */
    public static boolean matchesAny(char @NotNull [] string, @Range(from = 0, to = Integer.MAX_VALUE) int start,
                                     @Range(from = 0, to = Integer.MAX_VALUE) int length, @NotNull String @NotNull ... strings) {
        if (length > string.length - start) return false;
        final String str = new String(string, start, length);
        for (final var s : strings) if (str.equals(s)) return true;
        return false;
    }
}
