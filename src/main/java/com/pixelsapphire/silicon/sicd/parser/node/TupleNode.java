package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TupleNode extends Node {

    private final List<Node> children;

    public TupleNode(@NotNull Node... children) {
        this.children = new ArrayList<>(List.of(children));
    }

    public void addElement(@NotNull Node child) {
        children.add(child);
    }

    @UnmodifiableView
    public @NotNull List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

//    @Override
//    public @NotNull String compile(@NotNull RootNode root) {
//        return "(" + children.stream().map((node) -> node.compile(root)).reduce("", (a, b) -> a + "," + b).substring(1) + ")";
//    }

    @Override
    public @NotNull Type getType() {
        return Type.TUPLE;
    }
}
