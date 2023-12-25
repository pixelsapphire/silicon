package com.pixelsapphire.silicon.sicd.parser;

import com.pixelsapphire.silicon.io.FileCoordinates;
import com.pixelsapphire.silicon.sicd.lexer.tokens.*;
import com.pixelsapphire.silicon.sicd.lexer.tokens.KeywordToken.Keyword;
import com.pixelsapphire.silicon.sicd.parser.node.*;
import com.pixelsapphire.silicon.sicd.parser.node.definition.ComponentDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.definition.PointDefinitionNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.ListNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.NumberLiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.StringLiteralNode;
import com.pixelsapphire.silicon.sicd.parser.node.literal.TupleNode;
import com.pixelsapphire.silicon.sicd.parser.node.operator.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class SiCDParser {

    private final Token[] tokens;

    public SiCDParser(@NotNull List<Token> tokens) {
        this.tokens = tokens.toArray(new Token[0]);
    }

    public @NotNull RootNode parse() {
        final RootNode root = new RootNode();
        final NodeVisitor cursor = new NodeVisitor();
        while (cursor.lessThan(tokens.length)) parseStatement(root, cursor);
        return root;
    }

    private void parseStatement(@NotNull RootNode root, @NotNull NodeVisitor cursor) {
        if (cursor.peekType() == Token.Type.KEYWORD) {
            final KeywordToken keyword = cursor.consume(Token.Type.KEYWORD);
            if (keyword.getValue().equals(Keyword.COMPONENT.toString())) parseComponentDefinition(root, cursor);
            else if (keyword.getValue().equals(Keyword.POINT.toString())) parsePointDefinition(root, cursor);
            else if (keyword.getValue().equals(Keyword.WIRE.toString())) parseWire(root, cursor);
            else throwError(cursor.token - 1);
        } else if (cursor.peekType() == Token.Type.IDENTIFIER) {
            parsePinoutDefinition(root, cursor);
        } else {
            throwError(cursor.token);
        }
    }

    private void parseComponentDefinition(@NotNull RootNode root, @NotNull NodeVisitor cursor) {
        final ComponentDefinitionNode definition = new ComponentDefinitionNode();
        definition.setLocation(cursor.peekLocation(-1));
        final IdentifierToken name = cursor.consume(Token.Type.IDENTIFIER);
        definition.setName(name.getValue());
        cursor.consumeKeyword(Keyword.IS);
        definition.setInitializer(parseInitializer(cursor));
        cursor.consumeKeyword(Keyword.AT);
        definition.setCoordinates(parseExpression(cursor));
        cursor.consume(Token.Type.SEMICOLON);
        root.addNode(definition);
    }

    private void parsePinoutDefinition(@NotNull RootNode root, @NotNull NodeVisitor cursor) {
        final IdentifierToken identifier = cursor.consume(Token.Type.IDENTIFIER);
        final Optional<ComponentDefinitionNode> definition = root.getSymbol(identifier.getValue(), Node.Type.COMPONENT_DEFINITION);
        if (definition.isEmpty()) throwUndefinedError(cursor.token - 1, identifier.getValue());
        cursor.consumeKeyword(Keyword.PINOUT);
        cursor.consumeKeyword(Keyword.IS);
        definition.get().setPinout(parseList(cursor));
        cursor.consume(Token.Type.SEMICOLON);
    }

    private void parsePointDefinition(@NotNull RootNode root, @NotNull NodeVisitor cursor) {
        final PointDefinitionNode definition = new PointDefinitionNode();
        definition.setLocation(cursor.peekLocation(-1));
        final IdentifierToken name = cursor.consume(Token.Type.IDENTIFIER);
        definition.setName(name.getValue());
        cursor.consumeKeyword(Keyword.IS);
        if (cursor.peekType() == Token.Type.IDENTIFIER) definition.setInitializer(parseInitializer(cursor));
        cursor.consumeKeyword(Keyword.AT);
        definition.setCoordinates(parseExpression(cursor));
        cursor.consume(Token.Type.SEMICOLON);
        root.addNode(definition);
    }

    private void parseWire(@NotNull RootNode root, @NotNull NodeVisitor cursor) {
        cursor.consumeKeyword(Keyword.THROUGH);
        root.addNode(new WireNode(parseList(cursor)).at(cursor.peekLocation(-1)));
        cursor.consume(Token.Type.SEMICOLON);
    }

    private @NotNull ElementInitializer parseInitializer(@NotNull NodeVisitor cursor) {
        final IdentifierToken name = cursor.consume(Token.Type.IDENTIFIER);
        final ElementInitializer component = new ElementInitializer(name.getValue());
        component.setLocation(name.getLocationOrUnknown());
        component.setParameters(parseParametersList(cursor));
        return component;
    }

    private @NotNull ParametersListNode parseParametersList(@NotNull NodeVisitor cursor) {
        final Token paren = cursor.consume(Token.Type.LPAREN);
        final ParametersListNode list = new ParametersListNode();
        list.setLocation(paren.getLocationOrUnknown());
        while (cursor.peekType() != Token.Type.RPAREN) {
            final IdentifierToken parameterName = cursor.consume(Token.Type.IDENTIFIER);
            cursor.consume(Token.Type.COLON);
            list.set(parameterName.getValue(), parseExpression(cursor));
            if (cursor.peekType() == Token.Type.COMMA) cursor.consume(Token.Type.COMMA);
        }
        cursor.consume(Token.Type.RPAREN);
        return list;
    }

    private @NotNull TupleNode parseTuple(@NotNull NodeVisitor cursor) {
        final Token paren = cursor.consume(Token.Type.LPAREN);
        final TupleNode tuple = new TupleNode();
        tuple.setLocation(paren.getLocationOrUnknown());
        while (cursor.peekType() != Token.Type.RPAREN) {
            tuple.addElement(parseExpression(cursor));
            if (cursor.peekType() == Token.Type.COMMA) cursor.consume(Token.Type.COMMA);
        }
        cursor.consume(Token.Type.RPAREN);
        return tuple;
    }

    private @NotNull ListNode parseList(@NotNull NodeVisitor cursor) {
        final Token paren = cursor.consume(Token.Type.LBRACKET);
        final ListNode list = new ListNode();
        list.setLocation(paren.getLocationOrUnknown());
        while (cursor.peekType() != Token.Type.RBRACKET) {
            list.addElement(parseExpression(cursor));
            if (cursor.peekType() == Token.Type.COMMA) cursor.consume(Token.Type.COMMA);
        }
        cursor.consume(Token.Type.RBRACKET);
        return list;
    }

    private @NotNull Node parseExpression(@NotNull NodeVisitor cursor) {
        final var type = cursor.peekType();
        if (type == Token.Type.MINUS || type == Token.Type.CORNER_HV || type == Token.Type.CORNER_VH) {
            cursor.consume();
            final Node operand = parseExpression(cursor);
            final Function<Node, Node> unaryOperatorBuilder = switch (type) {
                case MINUS -> MinusOperatorNode::unary;
                case CORNER_HV -> CornerOperatorNode::hv;
                case CORNER_VH -> CornerOperatorNode::vh;
                default -> throw new IllegalStateException();
            };
            return unaryOperatorBuilder.apply(operand).at(operand.getLocationOrUnknown());
        } else {
            final Node left = parseValue(cursor);
            final BiFunction<Node, Node, Node> binaryOperatorBuilder;
            switch (cursor.peekType()) {
                case PLUS -> binaryOperatorBuilder = PlusOperatorNode::binary;
                case MINUS -> binaryOperatorBuilder = MinusOperatorNode::binary;
                default -> {
                    return left;
                }
            }
            cursor.consume();
            return binaryOperatorBuilder.apply(left, parseExpression(cursor)).at(left.getLocationOrUnknown());
        }
    }

    private @NotNull Node parseValue(@NotNull NodeVisitor cursor) {
        final Token.Type type = cursor.peekType();
        if (type == Token.Type.IDENTIFIER) return parseDotOperator(cursor);
        else if (type == Token.Type.NUMBER) {
            return new NumberLiteralNode(cursor.<NumberToken>consume(Token.Type.NUMBER).getValue()).at(cursor.peekLocation(-1));
        } else if (type == Token.Type.STRING)
            return new StringLiteralNode(cursor.<StringToken>consume(Token.Type.STRING).getValue()).at(cursor.peekLocation(-1));
        else if (type == Token.Type.EXCLAMATION_MARK) {
            cursor.consume(Token.Type.EXCLAMATION_MARK);
            if (cursor.peekType() == Token.Type.STRING)
                return new StringLiteralNode(cursor.<StringToken>consume(Token.Type.STRING).getValue())
                        .negated().at(cursor.peekLocation(-1));
        } else if (type == Token.Type.LPAREN) return parseTuple(cursor);
        else if (type == Token.Type.LBRACKET) return parseList(cursor);
        throwError(cursor.token);
        return null;
    }

    private @NotNull Node parseDotOperator(@NotNull NodeVisitor cursor) {
        final Node parent = parseIdentifier(cursor);
        if (cursor.peekType() == Token.Type.DOT) {
            cursor.consume(Token.Type.DOT);
            return new DotOperatorNode(parent, parseIdentifier(cursor)).at(parent.getLocationOrUnknown());
        }
        return parent;
    }

    private @NotNull Node parseIdentifier(@NotNull NodeVisitor cursor) {
        final IdentifierToken identifier = cursor.consume(Token.Type.IDENTIFIER);
        final Node node = switch (cursor.peekType()) {
            case LBRACKET -> new SubscriptOperatorNode(identifier.getValue(), parseList(cursor));
            default -> new IdentifierReferenceNode(identifier.getValue());
        };
        return node.at(identifier.getLocationOrUnknown());
    }

    private void throwError(int position) {
        throw new InvalidTokenException("Unexpected token: " + tokens[position], tokens[position].getLocationOrUnknown());
    }

    private void throwUndefinedError(int position, @NotNull String name) {
        throw new InvalidTokenException("Undefined symbol: " + name, tokens[position].getLocationOrUnknown());
    }

    private class NodeVisitor {

        private int token;

        private NodeVisitor() {
            this.token = 0;
        }

        private boolean lessThan(int position) {
            return this.token < position;
        }

        private void consumeKeyword(@NotNull Keyword keyword) {
            final KeywordToken token = consume(Token.Type.KEYWORD);
            if (!token.getValue().equals(keyword.toString())) throwError(this.token - 1);
        }

        @SuppressWarnings("unchecked")
        private <T extends Token> @NotNull T consume(@NotNull Token.Type... types) {
            if (Stream.of(types).noneMatch(type -> tokens[token].getType() == type)) throwError(token);
            final T token = (T) tokens[this.token];
            this.token++;
            return token;
        }

        private Token consume() {
            return tokens[token++];
        }

        private @NotNull FileCoordinates peekLocation(int offset) {
            return tokens[token + offset].getLocationOrUnknown();
        }

        private @NotNull FileCoordinates peekLocation() {
            return peekLocation(0);
        }

        private @NotNull Token.Type peekType(int offset) {
            return tokens[token + offset].getType();
        }

        private @NotNull Token.Type peekType() {
            return peekType(0);
        }

    }
}
