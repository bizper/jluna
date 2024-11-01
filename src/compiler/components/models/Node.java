package compiler.components.models;

import compiler.components.define.NodeType;

import java.util.ArrayList;

public class Node {

    private int index;
    private NodeType type;
    private Node parent;
    private final ArrayList<Node> children;

    private Node() {
        this.children = new ArrayList<>();
    }

    public static Node getNode() {
        return new Node();
    }

    public static Node getNode(NodeType type) {
        return new Node().setType(type);
    }

    public static Node getNode(NodeType type, int index) {
        return new Node().setType(type).setIndex(index);
    }

    public Node addChild(Node child) {
        if(type == NodeType.calculate && childrenLength() == 2) {
            throw new RuntimeException("The Calculation Node can't have more than two child node");
        }
        child.setParent(this);
        this.children.add(child);
        return this;
    }

    public int getIndex() {
        return index;
    }

    public Node setIndex(int value) {
        this.index = value;
        return this;
    }

    public NodeType getType() {
        return type;
    }

    public Node setType(NodeType type) {
        this.type = type;
        return this;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public Node getFirstChild() {
        return children.getFirst();
    }

    public Node getLastChild() {
        return children.getLast();
    }

    public Node[] getChild() {
        return this.children.toArray(new Node[0]);
    }

    public int childrenLength() {
        return this.children.size();
    }



    public boolean full() {
        return switch(type) {
            case start, define, call -> false;
            case calculate -> childrenLength() == 2;
            case symbol, constant, constant_v -> childrenLength() == 0;
        };
    }

    public void printTree() {
        printTree("");
    }

    private void printTree(String prefix) {
        System.out.println(prefix + "-" + type + ":" + index);
        for (Node child : children) {
            child.printTree(prefix + "   "); // 每一层增加两个空格缩进
        }
    }

}
