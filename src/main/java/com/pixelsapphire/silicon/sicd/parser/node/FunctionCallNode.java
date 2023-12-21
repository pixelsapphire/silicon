package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class FunctionCallNode extends Node {

    private String name;
    private ParametersListNode parameters;

    public FunctionCallNode(@NotNull String name, @NotNull ParametersListNode parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public @NotNull Type getType() {
        return Type.FUNCTION_CALL;
    }
}
