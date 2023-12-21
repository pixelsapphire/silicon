package com.pixelsapphire.silicon.latex;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LaTeXSequence implements LaTeX {

    private final List<LaTeX> sequence;

    public LaTeXSequence() {
        this.sequence = new ArrayList<>();
    }

    private LaTeXSequence(Object @NotNull ... sequence) {
        this.sequence = new ArrayList<>(sequence.length);
        for (final Object object : sequence) {
            if (object instanceof LaTeX) {
                this.sequence.add((LaTeX) object);
            } else if (object instanceof String) {
                this.sequence.add(new LaTeXString((String) object));
            } else {
                throw new IllegalArgumentException("Unexpected object: " + object);
            }
        }
    }

    @Contract("_ -> new")
    public static @NotNull LaTeXSequence of(Object @NotNull ... sequence) {
        return new LaTeXSequence(sequence);
    }

    public LaTeXSequence append(@NotNull LaTeX latex) {
        sequence.add(latex);
        return this;
    }

    public LaTeXSequence append(@NotNull String latex) {
        return append(new LaTeXString(latex));
    }

    @Override
    public @NotNull String translate() {
        return sequence.stream().map(LaTeX::translate).reduce("", (a, b) -> a + b);
    }
}
