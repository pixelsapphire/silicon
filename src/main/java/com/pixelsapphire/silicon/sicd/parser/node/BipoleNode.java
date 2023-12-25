package com.pixelsapphire.silicon.sicd.parser.node;

import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import org.jetbrains.annotations.NotNull;

public class BipoleNode extends Node {

    private final ElementInitializerNode initializer;
    private final ListNode endpoints;

    public BipoleNode(@NotNull ElementInitializerNode initializer, @NotNull ListNode endpoints) {
        this.initializer = initializer;
        this.endpoints = endpoints;
    }

    public @NotNull ElementInitializerNode getInitializer() {
        return initializer;
    }

    public @NotNull ListNode getEndpoints() {
        return endpoints;
    }

    @Override
    public @NotNull Type getType() {
        return Type.BIPOLE;
    }
}
