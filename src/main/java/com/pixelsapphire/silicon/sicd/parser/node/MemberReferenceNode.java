package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class MemberReferenceNode extends Node {

    private final String parent;
    private final Node member;

    public MemberReferenceNode(@NotNull String parent, @NotNull Node member) {
        this.parent = parent;
        this.member = member;
    }

    public @NotNull String getParent() {
        return parent;
    }

    public @NotNull Node getMember() {
        return member;
    }

    @Override
    public @NotNull Type getType() {
        return Type.MEMBER_REFERENCE;
    }

    @Override
    public String toString() {
        return parent + "." + member;
    }
}
