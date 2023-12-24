package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.NotNull;

public class IdentifierToken extends ValuedToken {

    /**
     * Constructs a new keyword token with the specified value.
     *
     * @param value the value of the token
     */
    public IdentifierToken(@NotNull String value) {
        super(value);
    }

    /**
     * Returns whether the specified character is a valid character for an identifier.
     *
     * @param c the character to check
     * @return {@code true} if the character may be part of an identifier, {@code false} otherwise
     */
    public static boolean isIdentifierCharacter(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    /**
     * Returns whether the specified character is a valid initial character for an identifier.
     *
     * @param c the character to check
     * @return {@code true} if the character may start an identifier, {@code false} otherwise
     */
    public static boolean isIdentifierInitialCharacter(char c) {
        return Character.isLetter(c);
    }

    @Override
    public @NotNull Type getType() {
        return Type.IDENTIFIER;
    }
}
