package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.LiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.MinusOperatorNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.PlusOperatorNode;
import org.jetbrains.annotations.NotNull;

public class CommonNodes {

    public static @NotNull String compileExpression(@NotNull Node expression) {
        return switch (expression) {
            case final LiteralNode literal -> literal.getLiteral();
            case final PlusOperatorNode plus -> compileExpression(plus.getLeft()) + "++" + compileExpression(plus.getRight());
            case final MinusOperatorNode minus -> (minus.getLeft() == null ? "" : compileExpression(minus.getLeft())) +
                                                  "-" + compileExpression(minus.getRight());
            default -> throw new CompilationException("Unexpected expression: " + expression.getType(),
                                                      expression.getLocationOrUnknown());
        };
    }

    public static @NotNull String compileComponent(@NotNull ComponentDefinitionNode componentDefinition,
                                                   @NotNull RootNode root) {
        return switch (componentDefinition.getInitializer().getName()) {
            case "dip_chip" -> Components.compileDipChip(componentDefinition, root);
            default -> throw new CompilationException("Unexpected component: " + componentDefinition.getInitializer().getName(),
                                                      componentDefinition.getLocationOrUnknown());
        };
    }

}
