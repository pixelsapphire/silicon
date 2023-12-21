package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class IdentifierReferenceNode extends Node {

    private String name;

    public IdentifierReferenceNode(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull Type getType() {
        return Type.IDENTIFIER_REFERENCE;
    }
}
