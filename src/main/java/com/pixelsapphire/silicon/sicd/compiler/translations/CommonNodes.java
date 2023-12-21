package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.latex.LaTeX;
import com.pixelsapphire.silicon.latex.LaTeXCommand;
import com.pixelsapphire.silicon.latex.LaTeXMathMode;
import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.LiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.StringLiteralNode;
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

    public static @NotNull String makePinLabel(@NotNull LiteralNode pin, int rotation) {
        LaTeX pinLabel = new LaTeXCommand("texttt").withRequiredArgument(pin.getLiteral());
        if (pin instanceof final StringLiteralNode name && name.isNegated())
            pinLabel = pinLabel.wrappedWith(new LaTeXCommand("overline")).wrappedWith(LaTeXMathMode.inline());
        pinLabel = pinLabel.wrappedWith(new LaTeXCommand("scriptsize"))
                           .wrappedWith(new LaTeXCommand("rotatebox").withOptionalArgument("origin", "c")
                                                                     .withRequiredArgument("" + rotation));
        return pinLabel.translate();
    }
}
