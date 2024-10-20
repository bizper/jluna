package vm.c;

import compiler.components.models.Node;

import java.util.HashMap;
import java.util.Map;

import static compiler.components.define.NodeType.*;

public class Frame {

    final Map<String, String> symbols;

    private Frame(Map<String, String> symbols) {

        this.symbols = new HashMap<>(symbols);

    }

    public static Frame getFrame(Map<String, String> symbols) {
        return new Frame(symbols);
    }

    public Frame add(String k, String v) {
        symbols.put(k, v);
        return this;
    }

    public int calculateInt(Node node) {
        return (int) calculate(node);
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
            return Float.parseFloat(symbols.get(node.getIndex()));
        } else if (node.getType() == symbol) {
            return Float.parseFloat(symbols.get(node.getIndex()));
        }
        return 0;
    }

}
