package compiler.components;

import compiler.components.define.TokenType;
import compiler.components.models.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Predicate;

import static compiler.components.define.TokenType.*;

public class Tools {

    public static int precedence(TokenType type) {
        return switch (type) {
            case plus, minus -> 1;
            case multiply, divide -> 2;
            default -> -1;
        };
    }

    /**
     * support for both DEX and HEX
     *
     * @param s string waiting for determine
     * @return whether it is a number or not
     */
    public static boolean isDigit(String s) {
        boolean hex = false;
        if (s.startsWith("0x") || s.startsWith("0X")) {
            s = s.substring(2);
            hex = true;
        }
        char[] arr = s.toCharArray();
        boolean isFloat = false;
        for (char c : arr) {
            if (c == '.') {
                if (!isFloat) isFloat = true;
                else return false;
            }
            if (!Character.isDigit(c) && !hex && (c < 'A' || c > 'F')) {
                return false;
            }
        }
        return true;
    }

    public static List<Token> reversePolishNotation(List<Token> list) {
        List<Token> result = new ArrayList<>();
        Stack<Token> stack = new Stack<>();
        for (Token token : list) {
            TokenType type = token.getType();
            if (type == number || type == identifier) {
                result.add(token);
            } else if (type == left_paren) {
                stack.push(token);
            } else if (type == right_paren) {
                while (!stack.isEmpty() && stack.peek().getType() != left_paren) {
                    result.add(stack.pop());
                }
                stack.pop();
            } else if (isOperator(type)) {
                while (!stack.isEmpty() && precedence(type) <= precedence(stack.peek().getType())) {
                    result.add(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    public static boolean isOperator(TokenType type) {
        return type == plus || type == minus || type == multiply || type == divide;
    }

    public static String readableString(List<Token> tokens, final Map<Integer, String> symbols) {
        StringBuilder sb = new StringBuilder();
        int line = tokens.getFirst().getLine();
        for (Token token : tokens) {
            if(token.getLine() != line) {
                sb.append("\r\n");
                line = token.getLine();
            }
            sb.append(symbols.get(token.getIndex())).append(' ');
        }
        return sb.toString();
    }

    public static <T> List<T> getPart(List<T> list, Predicate<? super T> predicate) {
        return list.stream().filter(predicate).toList();
    }

    public static void err(Object obj) {
        System.err.println(obj);
    }
}
