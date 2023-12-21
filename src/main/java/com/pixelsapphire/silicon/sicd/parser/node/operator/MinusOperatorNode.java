package com.pixelsapphire.silicon.sicd.parser.node.operator;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinusOperatorNode extends Node {

    private final Node left, right;

    public MinusOperatorNode(@Nullable Node left, @NotNull Node right) {
        this.left = left;
        this.right = right;
    }

    public @Nullable Node getLeft() {
        return left;
    }

    public @NotNull Node getRight() {
        return right;
    }

    @Override
    public @NotNull Type getType() {
        return Type.MINUS_OPERATOR;
    }
}
