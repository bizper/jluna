package compiler.components;

import compiler.components.define.TokenType;
import compiler.components.models.Cage;
import compiler.components.models.Node;
import compiler.components.models.Token;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static compiler.components.Tools.*;
import static compiler.components.define.NodeType.*;
import static compiler.components.define.TokenType.*;
import static compiler.components.models.Node.getNode;

public class Parser {

    private Parser(Cage cage) {
        this.cage = cage;
    }

    private final Cage cage;

    public static Parser init(Cage cage) {
        return new Parser(cage);
    }

    /**
     * define :: identifier [identifier | _] = expr
     */
    public Parser parse() {
        Node root = getNode(start);
        Map<Integer, String> symbols = cage.getSymbols();

        cage.setRoot(root);
        return this;
    }

    private Node assignPart(List<Token> tokens, Map<Integer, String> symbols) {
        for(Token t : tokens) {
            if(t.getType() == assign) {
                var left = getPart(tokens, (item) -> item.getColumn() < t.getColumn());
                var right = getPart(tokens, (item) -> item.getColumn() > t.getColumn());
                Node node = getNode();
                for (Token token : left) {
                    if (Objects.requireNonNull(token.getType()) == TokenType.identifier) {
                        if (node.getType() == null) {
                            node.setType(define).setIndex(token.getIndex());
                        } else {
                            node.addChild(getNode(symbol, token.getIndex()));
                        }
                    } else {
                        err(String.format("Token %s shouldn't be here.", cage.getSymbols().get(token.getIndex())));
                        return null;
                    }
                }
                Node rp = buildExprTree(reversePolishNotation(right));
                if(isCalculableExpression(rp)) {
                    node.addChild(getNode(constant_v, calculateInt(rp, symbols)));
                } else {
                    node.addChild(rp);
                }

                return node;
            }
        }
        return null;
    }

    private Node buildExprTree(List<Token> tokens) {
        Node root = getNode();
        Node cursor = root;
        for (Token token : tokens.reversed()) {
            if (isOperator(token.getType())) {
                if (root.getType() == calculate) {
                    if (root.full()) {
                        root = root.getParent();
                        root.addChild(getNode(calculate, token.getIndex()));
                        root = root.getLastChild();
                    } else {
                        Node n = getNode(calculate, token.getIndex());
                        root.addChild(n);
                        root = n;
                    }
                } else {
                    root.setType(calculate);
                    root.setIndex(token.getIndex());
                }
            } else {
                if (root.getType() == calculate) {
                    if (token.getType() == identifier) {
                        root.addChild(getNode(symbol, token.getIndex()));
                    } else {
                        root.addChild(getNode(constant, token.getIndex()));
                    }
                } else {
                    if (token.getType() == identifier) {
                        root = root.setType(symbol).setIndex(token.getIndex());
                    } else if (token.getType() == number) {
                        root = root.setType(constant).setIndex(token.getIndex());
                    }
                }
            }
        }
        return cursor;
    }

    public Cage getCage() {
        return cage;
    }

}
