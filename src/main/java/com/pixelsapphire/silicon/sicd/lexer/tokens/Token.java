package com.pixelsapphire.silicon.sicd.lexer.tokens;

import com.pixelsapphire.silicon.io.FileCoordinates;
import com.pixelsapphire.silicon.io.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a SiCD token.
 */
public abstract class Token implements Location {

    /**
     * The location of the token in the source file.
     */
    private FileCoordinates location;

    public @NotNull Token at(@NotNull FileCoordinates location) {
        this.location = location;
        return this;
    }

    public @Nullable FileCoordinates getLocation() {
        return location;
    }

    /**
     * Returns the type of the token.
     *
     * @return the type of the token
     */
    @Contract(pure = true)
    public abstract @NotNull Type getType();

    /**
     * Represents a type of token.
     */
    public enum Type {
        /**
         * A SiCD keyword token.
         */
        KEYWORD(null),
        /**
         * An identifier token.
         */
        IDENTIFIER(null),
        /**
         * A string token.
         */
        STRING(null),
        /**
         * A number token.
         */
        NUMBER(null),
        /**
         * A left bracket ({@code [}) token.
         */
        LBRACKET("["),
        /**
         * A right bracket ({@code ]}) token.
         */
        RBRACKET("]"),
        /**
         * A left parenthesis ({@code (}) token.
         */
        LPAREN("("),
        /**
         * A right parenthesis ({@code )}) token.
         */
        RPAREN(")"),
        /**
         * A dot ({@code .}) token.
         */
        DOT("."),
        /**
         * A comma ({@code ,}) token.
         */
        COMMA(","),
        /**
         * A colon ({@code :}) token.
         */
        COLON(":"),
        /**
         * A semicolon ({@code ;}) token.
         */
        SEMICOLON(";"),
        /**
         * An exclamation mark ({@code !}) token.
         */
        EXCLAMATION_MARK("!"),
        /**
         * A plus ({@code +}) token.
         */
        PLUS("+"),
        /**
         * A plus ({@code -}) token.
         */
        MINUS("-"),
        /**
         * A VH corner ({@code |_}) token.
         */
        CORNER_VH("|_"),
        /**
         * A HV corner ({@code _|}) token.
         */
        CORNER_HV("_|"),
        ;

        public final String symbol;

        Type(@Nullable String symbol) {
            this.symbol = symbol;
        }
    }
}
