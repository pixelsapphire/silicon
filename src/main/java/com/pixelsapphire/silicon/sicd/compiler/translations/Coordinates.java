package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.IdentifierReferenceNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.*;
import com.pixelsapphire.silicon.sicd.parser.node.operator.*;
import com.pixelsapphire.silicon.util.TextUtils;
import com.pixelsapphire.toolbox.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("SuspiciousNameCombination")
public class Coordinates {

    private static @NotNull String compileExpression(@NotNull Node expression, @NotNull RootNode root) {
        return switch (expression) {
            case final LiteralNode literal -> literal.getLiteral();
            case final PlusOperatorNode plus -> compileExpression(plus.getLeft(), root) + "++" +
                                                compileExpression(plus.getRight(), root);
            case final MinusOperatorNode minus -> (minus.getLeft() == null ? "" : compileExpression(minus.getLeft(), root)) +
                                                  "-" + compileExpression(minus.getRight(), root);
            case final DotOperatorNode dot -> {
                final Node parent = dot.getParent(), member = dot.getMember();
                if (member instanceof final IdentifierReferenceNode componentId) {
                    String id;
                    if (parent instanceof final IdentifierReferenceNode parentId) id = TextUtils.u2d(parentId.getName());
                    else if (parent instanceof final SubscriptOperatorNode parentSubscript) {
                        id = compileCoordinates(parentSubscript, root).substring(1);
                        id = id.substring(0, id.length() - 1);
                    } else throw new CompilationException("Unexpected expression used as coordinates: " + parent.getType(),
                                                          parent.getLocationOrUnknown());
                    final String coord = componentId.getName();
                    root.insertCoordinatesRequest(id);
                    if (!coord.equals("x") && !coord.equals("y"))
                        throw new CompilationException("Unknown coordinate: " + coord, componentId.getLocationOrUnknown());
                    yield (coord.equals("y") ? "-\\" : "\\") + root.requestShortId(id) + coord;
                }
                throw new CompilationException("Unexpected expression used as coordinates: " + parent.getType() + "." + member.getType(),
                                               parent.getLocationOrUnknown());
            }
            default -> throw new CompilationException("Unexpected expression used as coordinates: " + expression.getType(),
                                                      expression.getLocationOrUnknown());
        };
    }

    public static @NotNull String compileCoordinates(@NotNull Node coordinates, @NotNull RootNode root) {
        switch (coordinates) {
            case HereNode ignored -> {
                return "";
            }
            case final TupleNode pair -> {
                if (pair.getChildren().size() != 2)
                    throw new CompilationException("Expected 2 coordinates, got " + pair.getChildren().size(),
                                                   pair.getLocationOrUnknown());
                final var components = List.of(Pair.of("x", pair.getChildren().get(0)), Pair.of("y", pair.getChildren().get(1)));
                return "(" + compileExpression(components.get(0).second(), root) + "," +
                       compileExpression(MinusOperatorNode.unary(components.get(1).second()), root) + ")";
            }
            case final PlusOperatorNode plus -> {
                final var left = plus.getLeft();
                if (left instanceof HereNode) return "++" + compileCoordinates(plus.getRight(), root);
                return "($" + compileCoordinates(plus.getLeft(), root) + "+" + compileCoordinates(plus.getRight(), root) + "$)";
            }
            case final CornerOperatorNode corner -> {
                return compileCoordinates(corner.getOperand(), root);
            }
            case final IdentifierReferenceNode identifier -> {
                final var point = root.<PointDefinitionNode>getSymbol(identifier.getName(), Node.Type.POINT_DEFINITION)
                                      .orElseThrow(() -> new CompilationException("Unknown point: " + identifier.getName(),
                                                                                  identifier.getLocationOrUnknown()));
                return "(" + TextUtils.u2d(Objects.requireNonNull(point).getName()) + ")";
            }
            case final SubscriptOperatorNode subscript -> {
                final var component = root.<ComponentDefinitionNode>getSymbol(subscript.getIdentifier(), Node.Type.COMPONENT_DEFINITION)
                                          .orElseThrow(() -> new CompilationException("Unknown component: " + subscript.getIdentifier(),
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
                    return "(" + TextUtils.u2d(component.getName()) + ".bpin " + pin + ")";
                } else
                    throw new CompilationException("Expected a pin name or number, got " + subscript.getSubscript().getType(),
                                                   subscript.getSubscript().getLocationOrUnknown());
            }
            default -> throw new CompilationException(coordinates.getType() + " cannot be converted to a point",
                                                      coordinates.getLocationOrUnknown());
        }
    }
}
