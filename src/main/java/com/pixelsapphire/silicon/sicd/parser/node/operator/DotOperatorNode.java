package com.pixelsapphire.silicon.sicd.parser.node.operator;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public class DotOperatorNode extends Node {

    private final Node parent, member;

    public DotOperatorNode(@NotNull Node parent, @NotNull Node member) {
        this.parent = parent;
        this.member = member;
    }

    public @NotNull Node getParent() {
        return parent;
    }

    public @NotNull Node getMember() {
        return member;
    }

    @Override
    public @NotNull Type getType() {
        return Type.DOT_OPERATOR;
    }

    @Override
    public String toString() {
        return parent + "." + member;
    }
}
