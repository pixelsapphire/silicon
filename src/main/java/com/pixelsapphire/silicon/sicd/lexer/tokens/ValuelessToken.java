package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public class ValuelessToken extends Token {

    private final Type type;

    private ValuelessToken(@NotNull Type type) {
        this.type = type;
    }

    public static @NotNull Optional<ValuelessToken> of(@NotNull String symbol) {
        return Arrays.stream(Type.values()).filter(t -> symbol.equals(t.symbol)).findFirst().map(ValuelessToken::new);
    }

    public static @NotNull Optional<ValuelessToken> of(char symbol) {
        return of(String.valueOf(symbol));
    }

    /**
     * Creates a new left parenthesis ({@code (}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken leftParen() {
        return new ValuelessToken(Type.LPAREN);
    }

    /**
     * Creates a new right parenthesis ({@code )}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken rightParen() {
        return new ValuelessToken(Type.RPAREN);
    }

    /**
     * Creates a new left bracket ({@code [}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken leftBracket() {
        return new ValuelessToken(Type.LBRACKET);
    }

    /**
     * Creates a new right bracket ({@code ]}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken rightBracket() {
        return new ValuelessToken(Type.RBRACKET);
    }

    /**
     * Creates a new dot ({@code .}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken dot() {
        return new ValuelessToken(Type.DOT);
    }

    /**
     * Creates a new comma ({@code ,}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken comma() {
        return new ValuelessToken(Type.COMMA);
    }

    /**
     * Creates a new colon ({@code :}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken colon() {
        return new ValuelessToken(Type.COLON);
    }

    /**
     * Creates a new colon ({@code :}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken semicolon() {
        return new ValuelessToken(Type.SEMICOLON);
    }

    /**
     * Creates a new exclamation mark ({@code !}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken exclamationMark() {
        return new ValuelessToken(Type.EXCLAMATION_MARK);
    }

    /**
     * Creates a new plus ({@code +}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken plus() {
        return new ValuelessToken(Type.PLUS);
    }

    /**
     * Creates a new minus ({@code -}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken minus() {
        return new ValuelessToken(Type.MINUS);
    }

    /**
     * Creates a VH corner ({@code |_}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken cornerVH() {
        return new ValuelessToken(Type.CORNER_VH);
    }

    /**
     * Creates a HV corner ({@code _|}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = "-> new")
    public static @NotNull ValuelessToken cornerHV() {
        return new ValuelessToken(Type.CORNER_HV);
    }

    @Override
    public @NotNull Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.symbol;
    }
}
