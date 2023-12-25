package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.*;
import com.pixelsapphire.silicon.sicd.parser.node.literal.HereNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.CornerOperatorNode;
import com.pixelsapphire.toolbox.StringBuilder;
import org.jetbrains.annotations.NotNull;

public class Wire {

    public static @NotNull String compileWire(@NotNull WireNode wire, @NotNull RootNode root) {
        if (!wire.getPaths().isEmpty()) {
            if (wire.getPaths().getFirst() instanceof final ListNode points &&
                !points.getElements().isEmpty() && points.getElements().getFirst() instanceof HereNode)
                throw new CompilationException("Wire cannot start with \"here\"",
                                               points.getElements().getFirst().getLocationOrUnknown());
            else if (wire.getPaths().getFirst() instanceof final BipoleNode bipole &&
                     !bipole.getEndpoints().getElements().isEmpty() &&
                     bipole.getEndpoints().getElements().getFirst() instanceof HereNode)
                throw new CompilationException("Wire cannot start with \"here\"",
                                               bipole.getEndpoints().getElements().getFirst().getLocationOrUnknown());

        }
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
            } else if (path instanceof final BipoleNode bipole) {
                if (bipole.getEndpoints().getElements().size() != 2)
                    throw new CompilationException("Bipole must have exactly two endpoints",
                                                   bipole.getEndpoints().getLocationOrUnknown());
                builder.append(Coordinates.compileCoordinates(bipole.getEndpoints().getElements().get(0), root))
                       .append(Bipoles.compileBipole(bipole.getInitializer(), root))
                       .append(Coordinates.compileCoordinates(bipole.getEndpoints().getElements().get(1), root));
            } else if (path instanceof final ElementInitializerNode initializer) {
                if (initializer.getName().equals("junction")) builder.append("to[short,-*]++(0,0)");
                else throw new CompilationException("Unexpected initializer: " + initializer.getName(),
                                                    initializer.getLocationOrUnknown());
            } else
                throw new CompilationException("Unexpected path element: " + path.getType(), path.getLocationOrUnknown());
        }
        return builder.append(";").toString();
    }
}
