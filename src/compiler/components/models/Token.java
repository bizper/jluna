package compiler.components.models;

import compiler.components.define.TokenType;

public class Token {

    private int line;
    private int column;
    private int index;//refer to symbol table
    private TokenType type;

    public Token(int line, int column, int index, TokenType type) {
        this.line = line;
        this.column = column;
        this.index = index;
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "<" + type + ", " + index + ">";
    }
}
