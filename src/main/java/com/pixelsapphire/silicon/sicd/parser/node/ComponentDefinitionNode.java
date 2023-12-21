package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;

public class ComponentDefinitionNode extends SymbolDefinitionNode {

    private String name;
    private TupleNode coordinates;
    private ComponentNode definition;
    private ListNode pinout;

    @Override
    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull TupleNode getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NotNull TupleNode coordinates) {
        this.coordinates = coordinates;
    }

    public @NotNull ComponentNode getDefinition() {
        return definition;
    }

    public void setDefinition(@NotNull ComponentNode definition) {
        this.definition = definition;
    }

    public ListNode getPinout() {
        return pinout;
    }

    public void setPinout(@NotNull ListNode pinout) {
        this.pinout = pinout;
    }

    @Override
    public @NotNull Type getType() {
        return Type.COMPONENT_DEFINITION;
    }
}
