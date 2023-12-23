package com.pixelsapphire.silicon.sicd.parser.node;

import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import org.jetbrains.annotations.NotNull;

public class WireNode extends Node {

    private final ListNode points;

    public WireNode(@NotNull ListNode points) {
        this.points = points;
    }

    public @NotNull ListNode getPoints() {
        return points;
    }

    @Override
    public @NotNull Type getType() {
        return Type.WIRE;
    }
}
