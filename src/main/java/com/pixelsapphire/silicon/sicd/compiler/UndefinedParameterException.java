package com.pixelsapphire.silicon.sicd.compiler;

import com.pixelsapphire.silicon.sicd.parser.node.ParametersListNode;
import org.jetbrains.annotations.NotNull;

/**
 * Signals that an undefined required parameter was encountered during compilation.
 */
public class UndefinedParameterException extends CompilationException {

    /**
     * Constructs a new exception with the specified message and coordinates.
     * The coordinates are appended as a new line to the message.
     *
     * @param name the name of the undefined parameter
     * @param node the list that should contain the parameter
     */
    public UndefinedParameterException(@NotNull String name, @NotNull ParametersListNode node) {
        super("Required parameter " + name + " is undefined", node.getLocationOrUnknown());
    }
}
