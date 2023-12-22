package com.pixelsapphire.silicon.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringBuilder {

    private final java.lang.StringBuilder builder;

    public StringBuilder() {
        builder = new java.lang.StringBuilder();
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(@Nullable String str) {
        builder.append(str);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(@Nullable Object obj) {
        builder.append(obj);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(int i) {
        builder.append(i);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(long l) {
        builder.append(l);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(float f) {
        builder.append(f);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(double d) {
        builder.append(d);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(boolean b) {
        builder.append(b);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")

    public StringBuilder append(char c) {
        builder.append(c);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder append(char[] str) {
        builder.append(str);
        return this;
    }

    @Contract(value = "-> this", mutates = "this")
    public StringBuilder appendln() {
        builder.append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")

    public StringBuilder appendln(@NotNull String str) {
        builder.append(str).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder appendln(@NotNull Object obj) {
        builder.append(obj).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")

    public StringBuilder appendln(int i) {
        builder.append(i).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder appendln(long l) {
        builder.append(l).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder appendln(float f) {
        builder.append(f).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")

    public StringBuilder appendln(double d) {
        builder.append(d).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder appendln(boolean b) {
        builder.append(b).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder appendln(char c) {
        builder.append(c).append("\n");
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public StringBuilder appendln(char[] str) {
        builder.append(str).append("\n");
        return this;
    }

    @Contract(pure = true)
    public int length() {
        return builder.length();
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}
