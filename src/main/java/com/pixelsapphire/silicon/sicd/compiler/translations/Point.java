package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.LiteralNode;
import org.jetbrains.annotations.NotNull;

public class Point {

    public static @NotNull String compilePoint(@NotNull PointDefinitionNode pointDefinition, @NotNull RootNode root) {
        final var component = pointDefinition.getComponent();
        String coordinates = "\n\\draw " + Coordinates.compileCoordinates(pointDefinition.getCoordinates(), root) +
                             " coordinate(" + pointDefinition.getName().replace("_", "-") + ")";
        if (component != null) {
            final var params = component.getParameters();
            switch (component.getName()) {
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
                default -> throw new CompilationException("Unsupported point type: " + component.getName(),
                                                          component.getLocation());
            }
        }
        return coordinates + ";";
    }
}
