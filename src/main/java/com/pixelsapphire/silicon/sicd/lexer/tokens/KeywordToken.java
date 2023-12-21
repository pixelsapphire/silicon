package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class KeywordToken extends ValuedToken {

    /**
     * Constructs a new keyword token with the specified value.
     *
     * @param value the value of the token
     */
    public KeywordToken(@NotNull String value) {
        super(value);
        if (!isKeyword(value)) throw new IllegalArgumentException("Invalid keyword: " + value);
    }

    /**
     * Checks if the specified text is a valid keyword.
     *
     * @param value the text to check
     * @return {@code true} if the text is a valid keyword, {@code false} otherwise
     */
    public static boolean isKeyword(@NotNull String value) {
        return Stream.of(Keyword.values()).anyMatch(keyword -> keyword.toString().equals(value));
    }

    @Override
    public @NotNull Type getType() {
        return Type.KEYWORD;
    }

    public enum Keyword {

        /**
         * The {@code at} keyword.
         */
        AT,
        /**
         * The {@code component} keyword.
         */
        COMPONENT,
        /**
         * The {@code is} keyword.
         */
        IS,
        /**
         * The {@code pinout} keyword.
         */
        PINOUT,
        ;

        /**
         * Returns the keyword represented by the specified string.
         *
         * @param keyword the string to be converted (case-insensitive)
         * @return the keyword represented by the specified string
         * @throws IllegalArgumentException if the string is not a valid keyword
         */
        public static @NotNull Keyword fromString(@NotNull String keyword) {
            return Keyword.valueOf(keyword.toUpperCase());
        }

        /**
         * Returns the string representation of this keyword.
         *
         * @return the string representation of this keyword
         */
        @Override
        public @NotNull String toString() {
            return super.toString().toLowerCase();
        }
    }
}
