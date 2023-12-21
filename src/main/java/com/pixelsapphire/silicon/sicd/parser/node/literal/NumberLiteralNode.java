package com.pixelsapphire.silicon.sicd.parser.node.literal;

import org.jetbrains.annotations.NotNull;

public class NumberLiteralNode extends LiteralNode {

    private final String value;

    public NumberLiteralNode(@NotNull String value) {
        this.value = value;
    }

    @Override
    public @NotNull String getLiteral() {
        return value;
    }

    @Override
    public @NotNull Type getType() {
        return Type.NUMBER_LITERAL;
    }

    @Override
    public String toString() {
        return value;
    }
}
