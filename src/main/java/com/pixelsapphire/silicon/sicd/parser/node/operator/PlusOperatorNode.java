package com.pixelsapphire.silicon.sicd.parser.node.operator;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public class PlusOperatorNode extends Node {

    private final Node left, right;

    public PlusOperatorNode(@NotNull Node left, @NotNull Node right) {
        this.left = left;
        this.right = right;
    }

    public @NotNull Node getLeft() {
        return left;
    }

    public @NotNull Node getRight() {
        return right;
    }

    @Override
    public @NotNull Type getType() {
        return Type.PLUS_OPERATOR;
    }
}
