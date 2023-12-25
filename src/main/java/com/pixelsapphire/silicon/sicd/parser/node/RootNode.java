package com.pixelsapphire.silicon.sicd.parser.node;

import com.pixelsapphire.silicon.latex.LaTeXCommand;
import com.pixelsapphire.silicon.latex.RawLaTeX;
import com.pixelsapphire.silicon.sicd.compiler.CompilationException;
import com.pixelsapphire.silicon.sicd.parser.node.definition.SymbolDefinitionNode;
import com.pixelsapphire.silicon.util.TextUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;
import java.util.function.Consumer;

public class RootNode extends Node {

    private final List<Node> nodes;
    private final Map<String, Node> symbols;
    private final List<Consumer<String>> codeInsertionListeners;
    private final Map<String, String> shortIdentifiers;
    private final Set<String> requestedCoordinates;

    public RootNode() {
        nodes = new ArrayList<>();
        symbols = new LinkedHashMap<>();
        codeInsertionListeners = new ArrayList<>();
        shortIdentifiers = new HashMap<>();
        requestedCoordinates = new HashSet<>();
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
    public @NotNull List<@NotNull Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @SuppressWarnings("unchecked")
    public @NotNull <T extends Node> Optional<@Nullable T> getSymbol(@NotNull String name, @NotNull Type type) {
        if (!symbols.containsKey(name)) return Optional.empty();
        if (symbols.get(name).getType() != type) throw new IllegalArgumentException("Symbol " + name + " is not " + type);
        return Optional.ofNullable((T) symbols.get(name));
    }

    public @NotNull String requestShortId(@NotNull String id) {
        if (!shortIdentifiers.containsKey(id))
            shortIdentifiers.put(id, "SI" + TextUtils.encodeAsPseudoHexBytes(shortIdentifiers.size()));
        return shortIdentifiers.get(id);
    }

    public void insertCoordinatesRequest(@NotNull String point) {
        if (!requestedCoordinates.contains(point))
            insertCode(new LaTeXCommand("gettikzxy")
                               .withRequiredArgument(new RawLaTeX("(" + point + ")"))
                               .withRequiredArgument(new LaTeXCommand(requestShortId(point) + "x"))
                               .withRequiredArgument(new LaTeXCommand(requestShortId(point) + "y"))
                               .translate() + ";");
        requestedCoordinates.add(point);
    }

    public void addCodeInsertionListener(@NotNull Consumer<String> listener) {
        codeInsertionListeners.add(listener);
    }

    private void insertCode(@NotNull String code) {
        codeInsertionListeners.forEach(listener -> listener.accept(code));
    }

    @Override
    public @NotNull Type getType() {
        return Type.ROOT;
    }
}
