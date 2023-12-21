package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class ElementInitializer extends Node {

    private final String name;
    private ParametersListNode parameters;

    public ElementInitializer(@NotNull String name) {
        this.name = name;
        this.parameters = new ParametersListNode();
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull ParametersListNode getParameters() {
        return parameters;
    }

    public void setParameters(@NotNull ParametersListNode parameters) {
        this.parameters = parameters;
    }

    @Override
    public @NotNull Type getType() {
        return Type.COMPONENT;
    }
}
