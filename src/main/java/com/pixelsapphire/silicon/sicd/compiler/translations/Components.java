package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.latex.LaTeX;
import com.pixelsapphire.silicon.latex.LaTeXCommand;
import com.pixelsapphire.silicon.latex.LaTeXMathMode;
import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.compiler.UndefinedParameterException;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.ParametersListNode;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.LiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.StringLiteralNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Components {

    public static @NotNull String compileComponent(@NotNull ComponentDefinitionNode componentDefinition,
                                                   @NotNull RootNode root) {
        return switch (componentDefinition.getDefinition().getName()) {
            case "dip_chip" -> compileDipChip(componentDefinition, root);
            default -> throw new CompilationException("Unexpected component: " + componentDefinition.getDefinition().getName(),
                                                      componentDefinition.getLocationOrUnknown());
        };
    }

    private static @NotNull String compileDipChip(@NotNull ComponentDefinitionNode component, @NotNull RootNode root) {

        final ParametersListNode params = component.getDefinition().getParameters();
        final Node label = params.get("label").orElse(new StringLiteralNode(""));
        final Node pins = params.get("pins").orElseThrow(() -> new UndefinedParameterException("pins", params));

        final StringBuilder pinout = new StringBuilder();
        final List<Node> elements = component.getPinout().getElements();
        for (int i = 0; i < elements.size(); i++) {

            final int half = elements.size() / 2;
            final String firstHalf = "above", secondHalf = "below";

            final Node node = elements.get(i);
            if (node instanceof final LiteralNode pin) {

                LaTeX pinLabel = new LaTeXCommand("texttt").withRequiredArgument(pin.getLiteral());
                if (pin instanceof final StringLiteralNode name && name.isNegated())
                    pinLabel = pinLabel.wrapWith(new LaTeXCommand("overline")).wrapWith(LaTeXMathMode.inline());
                pinLabel = pinLabel.wrapWith(new LaTeXCommand("scriptsize"))
                                   .wrapWith(new LaTeXCommand("rotatebox").withOptionalArgument("origin", "c").withRequiredArgument("90"));

                pinout.append("\nnode[").append(i < half ? firstHalf : secondHalf).append("] at (")
                      .append(component.getName()).append(".bpin ").append(i + 1)
                      .append("){").append(pinLabel.translate()).append("}");
            } else throw new CompilationException("Unexpected pin label: " + node.getType(), node.getLocationOrUnknown());
        }

        return "\\draw node[dipchip,num pins=" + CommonNodes.compileExpression(pins) +
               ",rotate=90,hide numbers,external pins width=0] at " +
               CommonNodes.compileCoordinates(component.getCoordinates(), root) +
               " (" + component.getName() + ") {" + CommonLaTeX.dipChipLabel(CommonNodes.compileExpression(label)) +
               "} " + pinout + ";";
    }
}
