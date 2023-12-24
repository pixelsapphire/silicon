package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.IdentifierReferenceNode;
import com.pixelsapphire.silicon.sicd.parser.node.MemberReferenceNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.*;
import com.pixelsapphire.silicon.sicd.parser.node.operator.MinusOperatorNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.PlusOperatorNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.SubscriptOperatorNode;
import com.pixelsapphire.silicon.util.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("SuspiciousNameCombination")
public class Coordinates {

    public static @NotNull String compileCoordinates(@NotNull Node coordinates, @NotNull RootNode root) {
        switch (coordinates) {
            case final TupleNode pair -> {
                if (pair.getChildren().size() != 2)
                    throw new CompilationException("Expected 2 coordinates, got " + pair.getChildren().size(),
                                                   pair.getLocationOrUnknown());
                Node x = pair.getChildren().get(0), y = pair.getChildren().get(1);
                final Set<String> requestedCoordinates = new HashSet<>();
                if (x instanceof final MemberReferenceNode xRef) {
                    final var xName = TextUtils.u2d(xRef.getParent());
                    x = new StringLiteralNode("\\" + root.requestShortId(xName) + "x");
                    requestedCoordinates.add(xName);
                }
                if (y instanceof final MemberReferenceNode yRef) {
                    final var yName = TextUtils.u2d(yRef.getParent());
                    y = new StringLiteralNode("-\\" + root.requestShortId(yName) + "y");
                    requestedCoordinates.add(yName);
                }
                root.insertCoordinatesRequest(requestedCoordinates);
                return "(" + CommonNodes.compileExpression(x) + "," + CommonNodes.compileExpression(MinusOperatorNode.unary(y)) + ")";
            }
            case final PlusOperatorNode plus -> {
                return "($" + compileCoordinates(plus.getLeft(), root) + "+" + compileCoordinates(plus.getRight(), root) + "$)";
            }
            case final IdentifierReferenceNode identifier -> {
                final var point = root.<PointDefinitionNode>getSymbol(identifier.getName(), Node.Type.POINT_DEFINITION)
                                      .orElseThrow(() -> new CompilationException("Unknown point: " + identifier.getName(),
                                                                                  identifier.getLocationOrUnknown()));
                return "(" + TextUtils.u2d(Objects.requireNonNull(point).getName()) + ")";
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
                    return "(" + TextUtils.u2d(component.getName()) + ".bpin " + pin + ")";
                } else
                    throw new CompilationException("Expected a pin name or number, got " + subscript.getSubscript().getType(),
                                                   subscript.getSubscript().getLocationOrUnknown());
            }
            default -> throw new CompilationException("Expected a pair of coordinates, got " + coordinates.getType(),
                                                      coordinates.getLocationOrUnknown());
        }
    }
}
