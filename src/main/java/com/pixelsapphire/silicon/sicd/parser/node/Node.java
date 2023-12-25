package com.pixelsapphire.silicon.sicd.parser.node;

import com.pixelsapphire.silicon.io.FileCoordinates;
import com.pixelsapphire.silicon.io.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Node implements Location {

    /**
     * The location of the node in the source file.
     */
    private FileCoordinates location;

    public @NotNull Node at(@NotNull FileCoordinates location) {
        this.location = location;
        return this;
    }

    public @Nullable FileCoordinates getLocation() {
        return location;
    }

    public void setLocation(@NotNull FileCoordinates location) {
        this.location = location;
    }

    public abstract @NotNull Type getType();

    public enum Type {
        BIPOLE,
        COMPONENT_DEFINITION,
        INITIALIZER,
        CORNER_OPERATOR,
        DOT_OPERATOR,
        FUNCTION_CALL,
        HERE,
        IDENTIFIER_REFERENCE,
        LIST,
        MINUS_OPERATOR,
        NUMBER_LITERAL,
        PARAMETERS_LIST,
        PLUS_OPERATOR,
        POINT_DEFINITION,
        STRING_LITERAL,
        SUBSCRIPT_OPERATOR,
        TUPLE,
        ROOT,
        UNARY_OPERATION,
        WIRE,
    }
}
