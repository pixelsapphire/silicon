package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.LiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.TupleNode;
import org.jetbrains.annotations.NotNull;

public class CommonNodes {

    public static @NotNull String compileCoordinates(@NotNull TupleNode coordinates) {
        if (coordinates.getChildren().size() != 2)
            throw new CompilationException("Expected 2 coordinates, got " + coordinates.getChildren().size(),
                                           coordinates.getLocationOrUnknown());
        return "(" + compileExpression(coordinates.getChildren().get(0)) +
               "," + compileExpression(coordinates.getChildren().get(1)) + ")";
    }

    public static @NotNull String compileExpression(@NotNull Node expression) {
        return switch (expression) {
            case final LiteralNode literal -> literal.getLiteral();
            default -> throw new CompilationException("Unexpected expression: " + expression.getType(),
                                                      expression.getLocationOrUnknown());
        };
    }
}
