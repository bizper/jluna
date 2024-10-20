package compiler.components;

import compiler.components.define.SyntaxStatus;
import compiler.components.define.SyntaxType;
import compiler.components.define.TokenType;
import compiler.components.models.SyntaxNode;
import compiler.components.models.SyntaxTree;
import compiler.components.models.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static compiler.components.define.SyntaxStatus.*;

public class SyntaxChecker {

    private int index = 0;
    private final List<Token> tokens;
    private List<SyntaxNode> list;

    private SyntaxChecker(List<Token> tokens) {
        this.list = new ArrayList<>(SyntaxTree._matchF(tokens.getFirst().getType()));
        this.tokens = tokens;
    }

    static SyntaxChecker load(List<Token> tokens) {
        return new SyntaxChecker(tokens);
    }

    public SyntaxStatus check() {
        if(index >= tokens.size() || list.isEmpty()) {
            return PANIC;
        }
        Token token = tokens.get(index);
        switch (token.getType()) {
            case identifier -> check(SyntaxType.identifier);
            case number -> check(SyntaxType.factor);
            case assign -> check(SyntaxType.assign);
            case plus, minus, multiply, divide -> check(SyntaxType.operator);
        }
        index ++;
        if(list.size() == 1 && index == tokens.size()) return list.getFirst().getStatus();
        else return UNDETERMINED;
    }

    private void check(SyntaxType type) {
        for (int i = 0; i < list.size(); i++) {
            SyntaxNode node = list.get(i);
            if(node != null) {
                if(node.getType() == type) {
                    if(node.getLoop() == 0) {
                        this.list.set(i, null);
                        this.list.addAll(node.getChildren());
                    }
                } else {
                    if(node.getLoop() == -1) {
                        var next = node.match(type);
                        if(next != null && !next.isEmpty()) {
                            this.list.set(i, null);
                            this.list.addAll(next);
                        }
                    }
                }
            }
        }
        list = new ArrayList<>(list.stream().filter(Objects::nonNull).toList());
    }

}