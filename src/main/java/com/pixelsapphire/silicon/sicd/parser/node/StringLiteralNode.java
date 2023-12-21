package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class StringLiteralNode extends LiteralNode implements Negation {

    private final String value;
    private boolean negated;

    public StringLiteralNode(@NotNull String value) {
        this.value = value;
        negated = false;
    }

    @Override
    public @NotNull String getLiteral() {
        return value;
    }

    @Override
    public @NotNull Type getType() {
        return Type.STRING_LITERAL;
    }

    @Override
    public StringLiteralNode negated() {
        negated = true;
        return this;
    }

    @Override
    public boolean isNegated() {
        return negated;
    }
}
