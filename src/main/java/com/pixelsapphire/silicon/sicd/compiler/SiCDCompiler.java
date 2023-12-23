package com.pixelsapphire.silicon.sicd.compiler;

import com.pixelsapphire.silicon.sicd.compiler.translations.CommonNodes;
import com.pixelsapphire.silicon.sicd.compiler.translations.Point;
import com.pixelsapphire.silicon.sicd.parser.node.Node;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import com.pixelsapphire.silicon.util.StringBuilder;
import org.jetbrains.annotations.NotNull;

public class SiCDCompiler {

    private final RootNode circuit;
    private final StringBuilder codeBuffer;

    public SiCDCompiler(@NotNull RootNode circuit) {
        this.circuit = circuit;
        this.circuit.addCodeInsertionListener(this::insertCode);
        codeBuffer = new StringBuilder();
    }

    public void insertCode(@NotNull String code) {
        codeBuffer.appendln(code);
    }

    public @NotNull String compile() {
        for (final Node node : circuit.getNodes()) {
            if (node instanceof final ComponentDefinitionNode componentDefinition)
                codeBuffer.appendln(CommonNodes.compileComponent(componentDefinition, circuit));
            else if (node instanceof final PointDefinitionNode pointDefinition)
                codeBuffer.appendln(Point.compilePoint(pointDefinition, circuit));
            else throw new CompilationException("Unexpected node: " + node.getType(), node.getLocationOrUnknown());
        }
        return codeBuffer.toString();
    }
}
