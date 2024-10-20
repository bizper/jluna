package compiler.components.models;

import java.util.List;
import java.util.Map;

public class Cage {

    private String name;
    private Map<Integer, String> symbols;
    private List<Token> tokens;
    private Node root; // the ast tree root node for whole cage.

    public Cage(String name) {
        this.name = name;
    }

    public Cage(String name, Map<Integer, String> symbols, List<Token> tokens) {
        this.name = name;
        this.symbols = symbols;
        this.tokens = tokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Map<Integer, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<Integer, String> symbols) {
        this.symbols = symbols;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "C@" + name;
    }
}
