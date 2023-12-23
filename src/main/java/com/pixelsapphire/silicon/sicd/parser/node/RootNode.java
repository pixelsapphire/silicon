package com.pixelsapphire.silicon.sicd.parser.node;

import com.pixelsapphire.silicon.sicd.parser.node.definition.SymbolDefinitionNode;
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
        if (node instanceof final SymbolDefinitionNode symbol) {
            if (symbols.containsKey(symbol.getName()))
                throw new CompilationException(symbol.getName() + " is already defined as " + symbol.getType(),
                                               Objects.requireNonNull(symbol.getLocation()));
            symbols.put(symbol.getName(), symbol);
        }
        nodes.add(node);
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

    @Override
    public @NotNull Type getType() {
        return Type.ROOT;
    }
}
