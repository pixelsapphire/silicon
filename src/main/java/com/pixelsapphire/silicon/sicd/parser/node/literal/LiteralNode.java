package com.pixelsapphire.silicon.sicd.parser.node.literal;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public abstract class LiteralNode extends Node {

    public abstract @NotNull String getLiteral();
}
