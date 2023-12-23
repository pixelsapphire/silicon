package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.WireNode;
import org.jetbrains.annotations.NotNull;

public class Wire {

    public static @NotNull String compileWire(@NotNull WireNode wire, @NotNull RootNode root) {
        return "\\draw " + String.join("--", wire.getPoints().getElements().stream()
                                                 .map(p -> Coordinates.compileCoordinates(p, root)).toList()) + ";";
    }
}
