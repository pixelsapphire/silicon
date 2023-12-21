package com.pixelsapphire.silicon.sicd.parser.node.operator;

import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public class SubscriptOperatorNode extends Node {

    private final String identifier;
    private final ListNode subscript;

    public SubscriptOperatorNode(@NotNull String identifier, @NotNull ListNode subscript) {
        this.identifier = identifier;
        this.subscript = subscript;
    }

    public @NotNull String getIdentifier() {
        return identifier;
    }

    public @NotNull ListNode getSubscript() {
        return subscript;
    }

    @Override
    public @NotNull Type getType() {
        return Type.SUBSCRIPT_OPERATOR;
    }
}
