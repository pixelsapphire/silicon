package com.pixelsapphire.silicon.sicd.parser.node.definition;

import com.pixelsapphire.silicon.sicd.parser.node.ComponentNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PointDefinitionNode extends SymbolDefinitionNode {

    private String name;
    private Node coordinates;
    private ComponentNode component;

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

    public @Nullable ComponentNode getComponent() {
        return component;
    }

    public void setComponent(@Nullable ComponentNode component) {
        this.component = component;
    }

    @Override
    public @NotNull Type getType() {
        return Type.POINT_DEFINITION;
    }

}
