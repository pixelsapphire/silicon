package com.pixelsapphire.silicon.sicd.compiler;

import com.pixelsapphire.silicon.sicd.compiler.translations.Components;
import com.pixelsapphire.silicon.sicd.parser.node.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import org.jetbrains.annotations.NotNull;

public class SiCDCompiler {

    private final RootNode circuit;

    public SiCDCompiler(@NotNull RootNode circuit) {
        this.circuit = circuit;
    }

    public @NotNull String compile() {
        final StringBuilder code = new StringBuilder();
        for (final Node node : circuit.getNodes()) {
            if (node instanceof final ComponentDefinitionNode componentDefinition) {
                code.append(Components.compileComponent(componentDefinition));
            } else {
                throw new CompilationException("Unexpected node: " + node.getType(), node.getLocationOrUnknown());
            }
        }
        return code.toString();
    }
}
