package com.pixelsapphire.silicon.sicd.compiler.translations;

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
import java.util.Objects;

public class Components {

    public static @NotNull String compileDipChip(@NotNull ComponentDefinitionNode component, @NotNull RootNode root) {

        final ParametersListNode params = component.getInitializer().getParameters();
        final Node label = params.get("label").orElse(new StringLiteralNode(""));
        final Node pins = params.get("pins").orElseThrow(() -> new UndefinedParameterException("pins", params));

        final StringBuilder pinout = new StringBuilder();
        final List<Node> elements = component.getPinout().getElements();
        for (int i = 0; i < elements.size(); i++) {

            final int half = elements.size() / 2;
            final String firstHalf = "above", secondHalf = "below";

            final Node node = elements.get(i);
            if (node instanceof final LiteralNode pin)
                pinout.append("\nnode[").append(i < half ? firstHalf : secondHalf).append("] at (")
                      .append(component.getName()).append(".bpin ").append(i + 1)
                      .append("){").append(CommonNodes.makePinLabel(pin, 90)).append("}");
            else throw new CompilationException("Unexpected pin label: " + node.getType(), node.getLocationOrUnknown());
        }

        return "\\draw node[dipchip,num pins=" + CommonNodes.compileExpression(Objects.requireNonNull(pins)) +
               ",rotate=90,hide numbers,external pins width=0] at " +
               Coordinates.compileCoordinates(component.getCoordinates(), root) +
               " (" + component.getName() + ") {" + CommonLaTeX.dipChipLabel(CommonNodes.compileExpression(label)) +
               "} " + pinout + ";";
    }
}
