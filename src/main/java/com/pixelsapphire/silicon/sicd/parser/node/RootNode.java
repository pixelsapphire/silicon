package com.pixelsapphire.silicon.sicd.parser.node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public class RootNode extends Node {

    private final List<Node> nodes;
    private final Map<String, Node> symbols;

    public RootNode() {
        nodes = new ArrayList<>();
        symbols = new LinkedHashMap<>();
    }

    public void addNode(@NotNull Node node) {
        nodes.add(node);
        if (node instanceof final SymbolDefinitionNode symbol) symbols.put(symbol.getName(), symbol);
    }

    @UnmodifiableView
    public @NotNull List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @SuppressWarnings("unchecked")
    public @NotNull <T extends Node> Optional<@Nullable T> getSymbol(@NotNull String name, @NotNull Type type) {
        if (!symbols.containsKey(name)) return Optional.empty();
        if (symbols.get(name).getType() != type) throw new IllegalArgumentException("Symbol " + name + " is not " + type);
        return Optional.ofNullable((T) symbols.get(name));
    }

    public boolean isDefined(@NotNull String name) {
        return symbols.containsKey(name);
    }

    @Override
    public @NotNull Type getType() {
        return Type.ROOT;
    }
}
