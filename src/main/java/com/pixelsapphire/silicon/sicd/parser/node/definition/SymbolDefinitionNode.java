package com.pixelsapphire.silicon.sicd.parser.node.definition;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public abstract class SymbolDefinitionNode extends Node {

    public abstract @NotNull String getName();
}
