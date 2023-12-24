package com.pixelsapphire.silicon.sicd.lexer;

import com.pixelsapphire.silicon.io.FileCoordinates;
import com.pixelsapphire.silicon.io.SiCDSourceProvider;
import com.pixelsapphire.silicon.sicd.lexer.tokens.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SiCDLexer {

    private final SiCDSourceProvider source;

    public SiCDLexer(@NotNull SiCDSourceProvider source) {
        this.source = source;
    }

    public @NotNull List<Token> tokenize() {

        final List<Token> tokens = new ArrayList<>();
        final char[] source = this.source.getSource().toCharArray();

        for (int i = 0; i < source.length; i++) {
            if (!Character.isWhitespace(source[i])) {

                final char c = source[i];
                final FileCoordinates pos = this.source.getCoordinates(i);

                if (source[i] == '#') {
                    while (i < source.length && source[i] != '\n') i++;
                } else if (IdentifierToken.isIdentifierInitialCharacter(source[i])) {
                    final StringBuilder builder = new StringBuilder();
                    while (i < source.length && IdentifierToken.isIdentifierCharacter(source[i])) builder.append(source[i++]);
                    final var value = builder.toString();
                    tokens.add(KeywordToken.isKeyword(value)
                               ? new KeywordToken(value).at(pos) : new IdentifierToken(value).at(pos));
                    i--;
                } else if (Character.isDigit(source[i])) {
                    final StringBuilder builder = new StringBuilder();
                    while (i < source.length && (Character.isDigit(source[i]) || source[i] == '.')) builder.append(source[i++]);
                    tokens.add(new NumberToken(builder.toString()).at(pos));
                    i--;
                } else if (source[i] == '\'') {
                    i++;
                    final StringBuilder builder = new StringBuilder();
                    while (i < source.length && source[i] != '\'') builder.append(source[i++]);
                    tokens.add(new StringToken(builder.toString()).at(pos));
                } else {
                    final var ex = new InvalidCharacterException("Unexpected character: " + source[i], this.source.getCoordinates(i));
                    tokens.add(ValuelessToken.of(c).orElseThrow(() -> ex).at(pos));
                }
            }
        }

        return tokens;
    }
}
