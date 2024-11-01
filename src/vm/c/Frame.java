package vm.c;

import compiler.components.models.Node;

import java.util.HashMap;
import java.util.Map;

import static compiler.components.define.NodeType.*;

public class Frame implements Runnable {

    final Map<Integer, String> indexes;
    final Map<String, String> symbols;

    private Frame(Map<Integer, String> symbols) {

        this.symbols = new HashMap<>();
        this.indexes = new HashMap<>(symbols);

    }

    public static Frame getFrame(Map<Integer, String> symbols) {
        return new Frame(symbols);
    }

    public Frame define(String k, String v) {
        symbols.put(k, v);
        return this;
    }

    public float calculate(Node node) {
        if (node.getType() == calculate) {
            return switch (node.getIndex()) {
                case 0 -> calculate(node.getFirstChild()) + calculate(node.getLastChild());
                case 1 -> calculate(node.getLastChild()) - calculate(node.getFirstChild());
                case 2 -> calculate(node.getFirstChild()) * calculate(node.getLastChild());
                case 3 -> calculate(node.getLastChild()) / calculate(node.getFirstChild());
                default -> throw new RuntimeException("Invalid index " + node.getIndex() + " of node " + node.getType());
            };
        } else if (node.getType() == constant) {
            return Float.parseFloat(indexes.get(node.getIndex()));
        } else if (node.getType() == symbol) {
            return Float.parseFloat(symbols.get(indexes.get(node.getIndex())));
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "indexes=" + indexes +
                ", symbols=" + symbols +
                '}';
    }
}
