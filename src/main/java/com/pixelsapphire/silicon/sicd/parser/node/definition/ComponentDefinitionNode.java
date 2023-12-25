package com.pixelsapphire.silicon.sicd.parser.node.definition;

import com.pixelsapphire.silicon.sicd.parser.node.ElementInitializerNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.StringLiteralNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ComponentDefinitionNode extends SymbolDefinitionNode {

    private String name;
    private Node coordinates;
    private ElementInitializerNode initializer;
    private ListNode pinout;

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

    public @NotNull ElementInitializerNode getInitializer() {
        return initializer;
    }

    public void setInitializer(@NotNull ElementInitializerNode initializer) {
        this.initializer = initializer;
    }

    public ListNode getPinout() {
        return pinout;
    }

    public void setPinout(@NotNull ListNode pinout) {
        this.pinout = pinout;
    }

    @Override
    public @NotNull Node.Type getType() {
        return Node.Type.COMPONENT_DEFINITION;
    }

    public Optional<Integer> getPinNumber(@NotNull StringLiteralNode name) {
        final int number = pinout.getElements().indexOf(name);
        return number == -1 ? Optional.empty() : Optional.of(number + 1);
    }
}
