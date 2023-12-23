package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaTeXCommand extends LaTeXWrapper {

    private final String name;
    private final Map<String, LaTeX> optionalArguments;
    private final List<LaTeX> requiredArguments;

    public LaTeXCommand(@NotNull String name) {
        this.name = name;
        this.optionalArguments = new HashMap<>();
        this.requiredArguments = new ArrayList<>();
    }

    public void addOptionalArgument(@NotNull String name, @NotNull LaTeX value) {
        optionalArguments.put(name, value);
    }

    public void addOptionalArgument(@NotNull String name, @NotNull String value) {
        addOptionalArgument(name, new LaTeXString(value));
    }

    @Contract("_, _ -> this")
    public LaTeXCommand withOptionalArgument(@NotNull String name, @NotNull LaTeX value) {
        addOptionalArgument(name, value);
        return this;
    }

    @Contract("_, _ -> this")
    public LaTeXCommand withOptionalArgument(@NotNull String name, @NotNull String value) {
        addOptionalArgument(name, value);
        return this;
    }

    public void addRequiredArgument(@NotNull LaTeX value) {
        requiredArguments.add(value);
    }

    public void addRequiredArgument(@NotNull String value) {
        addRequiredArgument(new LaTeXString(value));
    }

    @Contract("_ -> this")
    public LaTeXCommand withRequiredArgument(@NotNull LaTeX value) {
        addRequiredArgument(value);
        return this;
    }

    @Contract("_ -> this")
    public LaTeXCommand withRequiredArgument(@NotNull String value) {
        addRequiredArgument(value);
        return this;
    }

    @Override
    public @NotNull String translate() {
        return "\\" + name
               + (optionalArguments.isEmpty()
                  ? "" : "[" + optionalArguments.entrySet().stream()
                                                .map(e -> e.getKey() + "=" + e.getValue().translate())
                                                .reduce("", (a, b) -> a + "," + b).substring(1) + "]")
               + (requiredArguments.isEmpty()
                  ? "" : requiredArguments.stream()
                                          .map(a -> "{" + a.translate() + "}")
                                          .reduce("", (a, b) -> a + b));
    }

    @Override
    protected @NotNull LaTeXCommand wrap(@NotNull LaTeX latex) {
        return withRequiredArgument(latex);
    }
}
