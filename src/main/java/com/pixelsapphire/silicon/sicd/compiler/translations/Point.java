package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import org.jetbrains.annotations.NotNull;

public class Point {

    public static @NotNull String compilePoint(@NotNull PointDefinitionNode pointDefinition, @NotNull RootNode root) {
        return "\n\\draw " + CommonNodes.compileCoordinates(pointDefinition.getCoordinates(), root) +
               " coordinate(" + pointDefinition.getName().replace("_", "-") + ");";
    }
}
