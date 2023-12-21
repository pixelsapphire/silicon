package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a SiCD token with a value (e.g. a keyword, identifier, or literal).
 */
public abstract class ValuedToken extends Token {

    /**
     * The value of the token.
     */
    protected final String value;

    /**
     * Constructs a new token with the specified value.
     *
     * @param value the value of the token
     */
    protected ValuedToken(@NotNull String value) {
        this.value = value;
    }

    /**
     * Returns the value of the token
     *
     * @return the value of the token
     */
    @Contract(pure = true)
    public @NotNull String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getType() + ": " + value;
    }
}
