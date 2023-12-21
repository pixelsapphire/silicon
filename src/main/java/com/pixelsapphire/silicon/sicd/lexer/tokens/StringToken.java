package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.NotNull;

public class StringToken extends ValuedToken {

    /**
     * Constructs a new string literal token with the specified value.
     *
     * @param value the value of the token
     */
    public StringToken(@NotNull String value) {
        super(value);
    }

    @Override
    public @NotNull Type getType() {
        return Type.STRING;
    }
}
