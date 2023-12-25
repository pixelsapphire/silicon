package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.WireNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.CornerOperatorNode;
import com.pixelsapphire.toolbox.StringBuilder;
import org.jetbrains.annotations.NotNull;

public class Wire {

    public static @NotNull String compileWire(@NotNull WireNode wire, @NotNull RootNode root) {
        final StringBuilder builder = new StringBuilder("\\draw ");
        wire.getPoints().getElements()
            .forEach(p -> builder.appendAll(p instanceof final CornerOperatorNode corner
                                            ? corner.getOperator().replace("_", "-") : "--",
                                            Coordinates.compileCoordinates(p, root)));
        builder.delete(6, 8);
        return builder.append(";").toString();
    }
}
