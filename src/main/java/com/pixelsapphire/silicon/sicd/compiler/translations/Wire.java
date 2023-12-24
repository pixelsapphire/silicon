package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.WireNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.CornerOperatorNode;
import com.pixelsapphire.silicon.util.StringBuilder;
import org.jetbrains.annotations.NotNull;

public class Wire {

    public static @NotNull String compileWire(@NotNull WireNode wire, @NotNull RootNode root) {
        final StringBuilder builder = new StringBuilder("\\draw ");
        boolean first = true;
        for (final Node p : wire.getPoints().getElements()) {
            final String path = first ? "" : (p instanceof final CornerOperatorNode corner
                                              ? corner.getDirection().replace("_", "-") : "--");
            builder.appendAll(path, Coordinates.compileCoordinates(p, root));
            first = false;
        }
        return builder.append(";").toString();
    }
}
