package com.pixelsapphire.silicon.sicd.parser.node;

import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WireNode extends Node {

    private final List<Node> paths;

    public WireNode() {
        paths = new ArrayList<>();
    }

    public void addPath(@NotNull ListNode path) {
        paths.add(path);
    }

    public void addBipole(@NotNull BipoleNode bipole) {
        paths.add(bipole);
    }

    public @NotNull List<Node> getPaths() {
        return paths;
    }

    @Override
    public @NotNull Type getType() {
        return Type.WIRE;
    }
}
