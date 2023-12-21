package com.pixelsapphire.silicon.sicd.parser.node.operator;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinusOperatorNode extends Node {

    private final Node left, right;

    private MinusOperatorNode(@Nullable Node left, @NotNull Node right) {
        this.left = left;
        this.right = right;
    }

    @Contract(pure = true, value = "_ -> new")
    public static @NotNull MinusOperatorNode unary(@NotNull Node right) {
        return new MinusOperatorNode(null, right);
    }

    @Contract(pure = true, value = "_, _ -> new")
    public static @NotNull MinusOperatorNode binary(@Nullable Node left, @NotNull Node right) {
        return new MinusOperatorNode(left, right);
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
