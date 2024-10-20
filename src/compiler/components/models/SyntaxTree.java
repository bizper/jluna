package compiler.components.models;

import compiler.components.define.SyntaxStatus;
import compiler.components.define.TokenType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static compiler.components.define.SyntaxType.*;
import static compiler.components.models.SyntaxNode.getSyntaxNode;

public class SyntaxTree {

    private static final Map<TokenType, SyntaxNode> map = new HashMap<>();

    private static final SyntaxNode ASSIGN_ROOT = getSyntaxNode(identifier, -1);

    private static final SyntaxNode EXPR_ROOT = getSyntaxNode(factor).setLoop(-1).setStatus(SyntaxStatus.EXPRESSION);

    static {
        var TEMP = getSyntaxNode(factor).setLoop(-1).setStatus(SyntaxStatus.ASSIGNMENT);
        ASSIGN_ROOT.checkout().setType(assign).setLoop(0)
                .addChild(TEMP
                        .checkout().setType(operator)
                        .checkout().setType(factor).setStatus(SyntaxStatus.ASSIGNMENT).addChild(TEMP));
        EXPR_ROOT.checkout().setType(operator)
                .checkout().setType(factor).setStatus(SyntaxStatus.EXPRESSION).addChild(EXPR_ROOT);
        map.put(TokenType.identifier, ASSIGN_ROOT);
        map.put(TokenType.number, EXPR_ROOT);
    }

    public static List<SyntaxNode> _matchF(TokenType type) {
        return map.entrySet().stream().filter((i) -> i.getKey() == type).map(Map.Entry::getValue).toList();
    }


}
