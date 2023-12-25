package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.ElementInitializer;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import org.jetbrains.annotations.NotNull;

public class Bipoles {

    public static @NotNull String compileBipole(@NotNull ElementInitializer initializer, @NotNull RootNode root) {
        if (initializer.getName().equals("resistor")) {
            return "to[R,/tikz/circuitikz/bipoles/length=0.3in]";
        } else
            throw new CompilationException("Unexpected bipole name: " + initializer.getName(),
                                           initializer.getLocationOrUnknown());
    }
}
