package com.pixelsapphire.silicon.util;

import org.jetbrains.annotations.NotNull;

/**
 * A utility class containing several methods for transforming text.
 */
public class TextTransform {

    /**
     * Converts a string to a hex string. Each byte is represented by two characters (padding with 0 if necessary).
     *
     * @param text the string to convert
     * @return the hex string
     */
    public static @NotNull String encodeAsHexBytes(@NotNull String text) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : text.getBytes()) builder.append(String.format("%02x", b));
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
        for (byte b : text.getBytes())
            for (char c : String.format("%02x", b).toCharArray())
                builder.append(Character.isDigit(c) ? (char) (c + 17) : c);
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
}
