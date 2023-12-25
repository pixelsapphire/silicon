package com.pixelsapphire.silicon.sicd.compiler.translations;

import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.ElementInitializerNode;
import com.pixelsapphire.silicon.sicd.parser.node.RootNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Bipoles {

    private static final List<String> names = List.of("resistor");

    public static boolean isBipole(@NotNull ElementInitializerNode initializer) {
        return names.stream().anyMatch(initializer.getName()::equals);
    }

    public static @NotNull String compileBipole(@NotNull ElementInitializerNode initializer, @NotNull RootNode root) {
        if (initializer.getName().equals("resistor")) {
            return "to[R,/tikz/circuitikz/bipoles/length=0.3in]";
        } else
            throw new CompilationException("Unexpected bipole name: " + initializer.getName(),
                                           initializer.getLocationOrUnknown());
    }
}
