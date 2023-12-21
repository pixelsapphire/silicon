package com.pixelsapphire.silicon.sicd.parser.node.literal;

import com.pixelsapphire.silicon.sicd.parser.node.Negation;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringLiteralNode other) return value.equals(other.value) && negated == other.negated;
        return false;
    }

    @Override
    public String toString() {
        return negated ? "!" + value : value;
    }
}
