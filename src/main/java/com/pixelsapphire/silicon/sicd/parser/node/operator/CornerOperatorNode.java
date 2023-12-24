package com.pixelsapphire.silicon.sicd.parser.node.operator;

import com.pixelsapphire.silicon.sicd.lexer.tokens.Token;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CornerOperatorNode extends Node {

    private final Token.Type direction;
    private final Node operand;

    private CornerOperatorNode(@NotNull Token.Type direction, @NotNull Node operand) {
        this.direction = direction;
        this.operand = operand;
    }

    public static @NotNull CornerOperatorNode vh(@NotNull Node operand) {
        return new CornerOperatorNode(Token.Type.CORNER_VH, operand);
    }

    public static @NotNull CornerOperatorNode hv(@NotNull Node operand) {
        return new CornerOperatorNode(Token.Type.CORNER_HV, operand);
    }

    public @NotNull String getDirection() {
        return Objects.requireNonNull(direction.symbol);
    }

    public @NotNull Node getOperand() {
        return operand;
    }

    @Override
    public @NotNull Type getType() {
        return Type.CORNER_OPERATOR;
    }
}
