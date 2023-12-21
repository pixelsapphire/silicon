package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class IdentifierReferenceNode extends Node {

    private final String name;

    public IdentifierReferenceNode(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull Type getType() {
        return Type.IDENTIFIER_REFERENCE;
    }
}
