package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ValuelessToken extends Token {

    private final Type type;

    private ValuelessToken(@NotNull Type type) {
        this.type = type;
    }

    /**
     * Creates a new left parenthesis ({@code (}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken leftParen() {
        return new ValuelessToken(Type.LPAREN);
    }

    /**
     * Creates a new right parenthesis ({@code )}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken rightParen() {
        return new ValuelessToken(Type.RPAREN);
    }

    /**
     * Creates a new left bracket ({@code [}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken leftBracket() {
        return new ValuelessToken(Type.LBRACKET);
    }

    /**
     * Creates a new right bracket ({@code ]}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken rightBracket() {
        return new ValuelessToken(Type.RBRACKET);
    }

    /**
     * Creates a new dot ({@code .}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken dot() {
        return new ValuelessToken(Type.DOT);
    }

    /**
     * Creates a new comma ({@code ,}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken comma() {
        return new ValuelessToken(Type.COMMA);
    }

    /**
     * Creates a new colon ({@code :}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken colon() {
        return new ValuelessToken(Type.COLON);
    }

    /**
     * Creates a new colon ({@code :}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken semicolon() {
        return new ValuelessToken(Type.SEMICOLON);
    }

    /**
     * Creates a new exclamation mark ({@code !}) token.
     *
     * @return the new token
     */
    @Contract(pure = true, value = " -> new")
    public static @NotNull ValuelessToken exclamationMark() {
        return new ValuelessToken(Type.EXCLAMATION_MARK);
    }

    @Override
    public @NotNull Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
