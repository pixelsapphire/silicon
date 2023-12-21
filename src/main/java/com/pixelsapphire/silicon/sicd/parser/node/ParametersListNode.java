package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParametersListNode extends Node {

    private final Map<String, Node> parameters;

    public ParametersListNode() {
        this.parameters = new HashMap<>();
    }

    public void set(@NotNull String name, @NotNull Node value) {
        parameters.put(name, value);
    }

//    @Override
//    public @NotNull String compile(@NotNull RootNode root) {
//        return "{" + parameters.entrySet().stream()
//                               .map(e -> e.getKey() + ":" + e.getValue().compile(root))
//                               .reduce("", (a, b) -> a + "," + b).substring(1) + "}";
//    }

    @SuppressWarnings("unchecked")
    public <T extends Node> @NotNull Optional<@Nullable T> get(@NotNull String name) {
        return Optional.ofNullable((T) parameters.get(name));
    }

    @Override
    public @NotNull Type getType() {
        return Type.PARAMETERS_LIST;
    }
}
