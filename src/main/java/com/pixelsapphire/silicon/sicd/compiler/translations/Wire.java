package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.BipoleNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.WireNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.CornerOperatorNode;
import com.pixelsapphire.toolbox.StringBuilder;
import org.jetbrains.annotations.NotNull;

public class Wire {

    public static @NotNull String compileWire(@NotNull WireNode wire, @NotNull RootNode root) {
        final StringBuilder builder = new StringBuilder("\\draw ");
        for (final Node path : wire.getPaths()) {
            if (path instanceof final ListNode points) {
                final StringBuilder pointsBuilder = new StringBuilder();
                points.getElements()
                      .forEach(p -> pointsBuilder.appendAll(p instanceof final CornerOperatorNode corner
                                                            ? corner.getOperator().replace("_", "-") : "--",
                                                            Coordinates.compileCoordinates(p, root)));
                pointsBuilder.delete(0, 2);
                builder.append(pointsBuilder);
            } else if (path instanceof final BipoleNode bipole)
                builder.append(Coordinates.compileCoordinates(bipole.getEndpoints().getElements().get(0), root))
                       .append(Bipoles.compileBipole(bipole.getInitializer(), root))
                       .append(Coordinates.compileCoordinates(bipole.getEndpoints().getElements().get(1), root));
            else
                throw new CompilationException("Unexpected path element: " + path.getType(), path.getLocationOrUnknown());
        }
        return builder.append(";").toString();
    }
}
