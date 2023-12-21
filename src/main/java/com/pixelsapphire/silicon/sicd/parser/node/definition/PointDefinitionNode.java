package com.pixelsapphire.silicon.sicd.parser.node.definition;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;

public class PointDefinitionNode extends SymbolDefinitionNode {

    private String name;
    private Node coordinates;

    @Override
    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull Node getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NotNull Node coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public @NotNull Type getType() {
        return Type.POINT_DEFINITION;
    }

}
