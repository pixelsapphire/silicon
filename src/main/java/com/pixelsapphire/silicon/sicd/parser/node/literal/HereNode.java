package com.pixelsapphire.silicon.sicd.parser.node.literal;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public class HereNode extends Node {

    @Override
    public @NotNull Type getType() {
        return Type.HERE;
    }
}
