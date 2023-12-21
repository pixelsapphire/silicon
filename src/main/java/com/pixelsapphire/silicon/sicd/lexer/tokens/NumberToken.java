package com.pixelsapphire.silicon.sicd.lexer.tokens;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class NumberToken extends ValuedToken {

    /**
     * Constructs a new number literal token with the specified value.
     *
     * @param value the value of the token
     */
    public NumberToken(@NotNull String value) {
        super(value);
    }

    @Override
    public @NotNull Type getType() {
        return Type.NUMBER;
    }

    /**
     * Returns the value of this number literal as an integer.
     *
     * @return the integer value of this number literal
     */
    @Contract(pure = true)
    long asInteger() {
        return Long.parseLong(value);
    }

    /**
     * Returns the value of this number literal as a real (floating-point) number.
     *
     * @return the floating-point value of this number literal
     */
    @Contract(pure = true)
    double asReal() {
        return Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + getNumberType() + ")";
    }

    /**
     * Returns the type of this number literal.
     *
     * @return {@link NumberType#INTEGER} if the number literal is an integer,
     *         {@link NumberType#REAL} if it is a real (floating-point) number
     */
    @Contract(pure = true)
    public @NotNull NumberType getNumberType() {
        return value.contains(".") ? NumberType.REAL : NumberType.INTEGER;
    }

    /**
     * Represents the type of number literal represented by a {@link NumberToken}.
     */
    public enum NumberType {
        /**
         * An integer literal.
         */
        INTEGER,
        /**
         * A real number literal.
         */
        REAL
    }
}
