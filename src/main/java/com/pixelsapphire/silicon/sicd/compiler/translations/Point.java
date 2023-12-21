package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.LiteralNode;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Point {

    public static @NotNull String compilePoint(@NotNull PointDefinitionNode pointDefinition, @NotNull RootNode root) {
        final var initializer = pointDefinition.getInitializer();
        String coordinates = "\n\\draw " + Coordinates.compileCoordinates(pointDefinition.getCoordinates(), root) +
                             " coordinate(" + pointDefinition.getName().replace("_", "-") + ")";
        if (initializer != null) {
            final var params = initializer.getParameters();
            switch (initializer.getName()) {
                case "terminal" -> {
                    coordinates += " to[short,-o]++(0,0)";
                    final var label = params.get("label");
                    if (label.isPresent()) {
                        if (label.get() instanceof final LiteralNode literal)
                            coordinates += " node[anchor=west]{" + CommonNodes.makePinLabel(literal, 0) + "}";
                        else throw new CompilationException("Unsupported label type: " + label.get().getType(),
                                                            label.get().getLocationOrUnknown());
                    }
                }
                case "junction" -> coordinates += " to[short,-*]++(0,0)";
                default -> throw new CompilationException("Unsupported point type: " + initializer.getName(),
                                                          Objects.requireNonNull(initializer.getLocation()));
            }
        }
        return coordinates + ";";
    }
}
