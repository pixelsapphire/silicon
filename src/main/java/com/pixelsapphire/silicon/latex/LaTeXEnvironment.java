package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaTeXEnvironment extends LaTeXWrapper {

    private final String name;
    private final Map<String, LaTeX> arguments;
    private String option;
    private LaTeX content;

    public LaTeXEnvironment(@NotNull String name) {
        this.name = name;
        this.arguments = new HashMap<>();
        this.content = new LaTeXSequence();
    }

    public void addArgument(@NotNull String name, @NotNull LaTeX value) {
        arguments.put(name, value);
    }

    public void addArgument(@NotNull String name, @NotNull String value) {
        addArgument(name, new LaTeXString(value));
    }

    public void setOption(@NotNull String option) {
        this.option = option;
    }

    public LaTeXEnvironment withArgument(@NotNull String name, @NotNull LaTeX value) {
        addArgument(name, value);
        return this;
    }

    public LaTeXEnvironment withArgument(@NotNull String name, @NotNull String value) {
        addArgument(name, value);
        return this;
    }

    public LaTeXEnvironment withOption(@NotNull String option) {
        setOption(option);
        return this;
    }

    public void setContent(@NotNull LaTeX content) {
        this.content = content;
    }

    public void setContent(@NotNull String content) {
        setContent(new LaTeXString(content));
    }

    @Contract("_ -> this")
    public LaTeXEnvironment withContent(@NotNull LaTeX content) {
        setContent(content);
        return this;
    }

    @Contract("_ -> this")
    public LaTeXEnvironment withContent(@NotNull String content) {
        return withContent(new LaTeXString(content));
    }

    public LaTeXEnvironment append(@NotNull LaTeX content) {
        this.content = new LaTeXSequence().append(this.content).append(content);
        return this;
    }

    public LaTeXEnvironment append(@NotNull String content) {
        return append(new LaTeXString(content));
    }

    @Override
    public @NotNull String translate() {
        final List<String> options = new ArrayList<>();
        if (option != null) options.add(option);
        arguments.forEach((name, value) -> options.add(name + "=" + value.translate()));
        return "\\begin{" + name + "}"
               + (!options.isEmpty() ? "[" + String.join(",", options) + "]" : "")
               + "\n" + content.translate() + "\n\\end{" + name + "}";
    }

    @Override
    protected @NotNull LaTeXEnvironment wrap(@NotNull LaTeX latex) {
        return withContent(latex);
    }
}
