package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.*;
import com.pixelsapphire.silicon.sicd.parser.node.operator.MinusOperatorNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.PlusOperatorNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.SubscriptOperatorNode;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommonNodes {

    public static @NotNull String compileCoordinates(@NotNull Node coordinates, @NotNull RootNode root) {
        switch (coordinates) {
            case final TupleNode pair -> {
                if (pair.getChildren().size() != 2)
                    throw new CompilationException("Expected 2 coordinates, got " + pair.getChildren().size(),
                                                   pair.getLocationOrUnknown());
                return "(" + compileExpression(pair.getChildren().get(0)) +
                       "," + compileExpression(new MinusOperatorNode(null, pair.getChildren().get(1))) + ")";
            }
            case final PlusOperatorNode plus -> {
                return compileCoordinates(plus.getLeft(), root) + "++" + compileCoordinates(plus.getRight(), root);
            }
            case final SubscriptOperatorNode subscript -> {
                final var component = root.<ComponentDefinitionNode>getSymbol(subscript.getIdentifier(), Node.Type.COMPONENT_DEFINITION)
                                          .orElseThrow(() -> new CompilationException("Unknown symbol: " + subscript.getIdentifier(),
                                                                                      subscript.getLocationOrUnknown()));
                assert component != null;
                if (subscript.getSubscript() instanceof final ListNode list && list.getElements().size() == 1 &&
                    list.get(0) instanceof final LiteralNode literal) {
                    final int pin;
                    if (literal instanceof NumberLiteralNode number) pin = Integer.parseInt(number.getLiteral());
                    else if (literal instanceof StringLiteralNode name)
                        pin = component.getPinNumber(name).orElseThrow(() -> new CompilationException(
                                "Pin " + name + " not found in component " + component.getName(), subscript.getLocationOrUnknown()));
                    else throw new CompilationException("Expected a pin name or number, got " + literal.getType(),
                                                        literal.getLocationOrUnknown());
                    return "(" + Objects.requireNonNull(component).getName().replace("_", "-") + ".bpin " + pin + ")";
                } else
                    throw new CompilationException("Expected a pin name or number, got " + subscript.getSubscript().getType(),
                                                   subscript.getSubscript().getLocationOrUnknown());
            }
            default -> throw new CompilationException("Expected a pair of coordinates, got " + coordinates.getType(),
                                                      coordinates.getLocationOrUnknown());
        }
    }

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
}
