package compiler.components;

import compiler.components.define.TokenType;
import compiler.components.models.Cage;
import compiler.components.models.Env;
import compiler.components.models.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static compiler.components.Tools.isDigit;

public class Scanner {

    private final Cage cage;
    private final Map<Integer, String> symbols;
    private final List<Token> tokens;
    private final StringBuilder buffer;

    private int index = 100; //the index of identifier
    private boolean inString = false; //detect if in string mode right now

    private Scanner(String name) {
        this.symbols = new HashMap<>(Env.getMap());
        this.tokens = new ArrayList<>();
        this.buffer = new StringBuilder();
        this.cage = new Cage(name, this.symbols, this.tokens);
    }

    public static Scanner init(String name) {
        return new Scanner(name);
    }

    public void load(int line, String content) {
        int column = 0;
        char[] chars = content.toCharArray();
        for (char c : chars) {
            if (inString) {
                if (c == '"') {
                    inString = false;
                    trigger(line, column, TokenType.text);
                    clear();
                } else append(c);
            } else {
                if (c == '"') {
                    inString = true;
                    continue;
                }
                if (c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                    trigger(line, column++, assume());
                    clear();
                    append(c);
                    trigger(line, column, assume());
                    clear();
                    continue;
                }
                if(c == ' ') {
                    trigger(line, column, assume());
                    clear();
                } else append(c);
            }
            column++;
        }
        if (!isEmpty()) {
            trigger(line, column, assume());
            clear();
        }
    }

    private TokenType assume() {
        String v = getBuffer();
        if(isDigit(v)) {
            return TokenType.number;
        }
        return switch (v) {
            case "=" -> TokenType.assign;
            case "+" -> TokenType.plus;
            case "-" -> TokenType.minus;
            case "*" -> TokenType.multiply;
            case "/" -> TokenType.divide;
            case "(" -> TokenType.left_paren;
            case ")" -> TokenType.right_paren;
            default -> TokenType.identifier;
        };
    }

    private void trigger(int line, int column, TokenType type) {
        if(!isEmpty()) {
            if(symbols.containsValue(getBuffer())) {
                tokens.add(new Token(line, column, getIndex(), type));
            } else {
                tokens.add(new Token(line, column, index, type));
                symbols.put(index, getBuffer());
                index++;
            }

        }
    }

    private int getIndex() {
        var t = symbols.entrySet().stream().filter(i -> i.getValue().equals(getBuffer())).findAny();
        //due to the prior IF branch this is unnecessary
        if(t.isPresent()) {
            return t.get().getKey();
        }
        return -1;
    }

    public Cage getCage() {
        return cage;
    }

    private boolean isEmpty() {
        return buffer.isEmpty();
    }

    private String getBuffer() {
        return buffer.toString();
    }

    private void append(char c) {
        buffer.append(c);
    }

    private void clear() {
        buffer.setLength(0);
    }
}
