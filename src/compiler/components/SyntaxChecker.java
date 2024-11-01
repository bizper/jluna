package compiler.components;

import compiler.components.define.SyntaxStatus;
import compiler.components.models.Token;

import java.util.List;

public class SyntaxChecker {


    public static SyntaxStatus check(List<Token> list) {
        return SyntaxStatus.ASSIGNMENT;
    }

    private static void check(Token token) {

    }

}