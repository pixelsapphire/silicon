package com.pixelsapphire.silicon.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class StringBuilder {

    private final java.lang.StringBuilder builder;

    public StringBuilder() {
        builder = new java.lang.StringBuilder();
    }

    public StringBuilder(@NotNull String str) {
        builder = new java.lang.StringBuilder(str);
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

    public StringBuilder appendAll(@Nullable Object @NotNull ... objects) {
        for (final Object object : objects) builder.append(object);
        return this;
    }

    public StringBuilder appendAll(@NotNull Iterable<?> collection) {
        for (final Object object : collection) builder.append(object);
        return this;
    }

    public StringBuilder appendAll(byte @NotNull [] array) {
        for (final byte b : array) builder.append(b);
        return this;
    }

    public StringBuilder appendAll(char @NotNull [] array) {
        for (final char c : array) builder.append(c);
        return this;
    }

    public StringBuilder appendAll(int @NotNull [] array) {
        for (final int i : array) builder.append(i);
        return this;
    }

    public StringBuilder appendAll(long @NotNull [] array) {
        for (final long l : array) builder.append(l);
        return this;
    }

    public StringBuilder appendAll(float @NotNull [] array) {
        for (final float f : array) builder.append(f);
        return this;
    }

    public StringBuilder appendAll(double @NotNull [] array) {
        for (final double d : array) builder.append(d);
        return this;
    }

    public StringBuilder appendAll(@NotNull Iterable<?> collection, @NotNull String delimiter) {
        for (final Object object : collection) builder.append(object).append(delimiter);
        return this;
    }

    public StringBuilder appendAll(byte @NotNull [] array, @NotNull String delimiter) {
        for (final byte b : array) builder.append(b).append(delimiter);
        return this;
    }

    public StringBuilder appendAll(char @NotNull [] array, @NotNull String delimiter) {
        for (final char c : array) builder.append(c).append(delimiter);
        return this;
    }

    public StringBuilder appendAll(int @NotNull [] array, @NotNull String delimiter) {
        for (final int i : array) builder.append(i).append(delimiter);
        return this;
    }

    public StringBuilder appendAll(long @NotNull [] array, @NotNull String delimiter) {
        for (final long l : array) builder.append(l).append(delimiter);
        return this;
    }

    public StringBuilder appendAll(float @NotNull [] array, @NotNull String delimiter) {
        for (final float f : array) builder.append(f).append(delimiter);
        return this;
    }

    public StringBuilder appendAll(double @NotNull [] array, @NotNull String delimiter) {
        for (final double d : array) builder.append(d).append(delimiter);
        return this;
    }

    public <T, R> StringBuilder appendAll(@NotNull Iterable<T> collection, @NotNull Function<T, R> mapper) {
        for (final T object : collection) builder.append(mapper.apply(object));
        return this;
    }

    public <R> StringBuilder appendAll(byte @NotNull [] array, @NotNull Function<Byte, R> mapper) {
        for (final byte b : array) builder.append(mapper.apply(b));
        return this;
    }

    public <R> StringBuilder appendAll(char @NotNull [] array, @NotNull Function<Character, R> mapper) {
        for (final char c : array) builder.append(mapper.apply(c));
        return this;
    }

    public <R> StringBuilder appendAll(int @NotNull [] array, @NotNull Function<Integer, R> mapper) {
        for (final int i : array) builder.append(mapper.apply(i));
        return this;
    }

    public <R> StringBuilder appendAll(long @NotNull [] array, @NotNull Function<Long, R> mapper) {
        for (final long l : array) builder.append(mapper.apply(l));
        return this;
    }

    public <R> StringBuilder appendAll(float @NotNull [] array, @NotNull Function<Float, R> mapper) {
        for (final float f : array) builder.append(mapper.apply(f));
        return this;
    }

    public <R> StringBuilder appendAll(double @NotNull [] array, @NotNull Function<Double, R> mapper) {
        for (final double d : array) builder.append(mapper.apply(d));
        return this;
    }

    public <T, R> StringBuilder appendAll(@NotNull Iterable<T> collection, @NotNull String delimiter,
                                          @NotNull Function<T, R> mapper) {
        for (final T object : collection) builder.append(mapper.apply(object)).append(delimiter);
        return this;
    }

    public <R> StringBuilder appendAll(byte @NotNull [] array, @NotNull String delimiter,
                                       @NotNull Function<Byte, R> mapper) {
        for (final byte b : array) builder.append(mapper.apply(b)).append(delimiter);
        return this;
    }

    public <R> StringBuilder appendAll(char @NotNull [] array, @NotNull String delimiter,
                                       @NotNull Function<Character, R> mapper) {
        for (final char c : array) builder.append(mapper.apply(c)).append(delimiter);
        return this;
    }

    public <R> StringBuilder appendAll(int @NotNull [] array, @NotNull String delimiter,
                                       @NotNull Function<Integer, R> mapper) {
        for (final int i : array) builder.append(mapper.apply(i)).append(delimiter);
        return this;
    }

    public <R> StringBuilder appendAll(long @NotNull [] array, @NotNull String delimiter,
                                       @NotNull Function<Long, R> mapper) {
        for (final long l : array) builder.append(mapper.apply(l)).append(delimiter);
        return this;
    }

    public <R> StringBuilder appendAll(float @NotNull [] array, @NotNull String delimiter,
                                       @NotNull Function<Float, R> mapper) {
        for (final float f : array) builder.append(mapper.apply(f)).append(delimiter);
        return this;
    }

    public <R> StringBuilder appendAll(double @NotNull [] array, @NotNull String delimiter,
                                       @NotNull Function<Double, R> mapper) {
        for (final double d : array) builder.append(mapper.apply(d)).append(delimiter);
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
