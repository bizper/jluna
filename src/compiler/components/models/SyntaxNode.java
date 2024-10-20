package compiler.components.models;

import compiler.components.define.SyntaxStatus;
import compiler.components.define.SyntaxType;
import compiler.components.define.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyntaxNode {

    private SyntaxType type;
    private SyntaxStatus status;
    private List<SyntaxNode> children;
    /**
     * -1 unlimited
     * 0 no loop
     * 1 ...
     */
    private int loop = 0;

    public SyntaxNode() {
        children = new ArrayList<>();
    }

    public int getLoop() {
        return loop;
    }

    public SyntaxNode setLoop(int loop) {
        this.loop = loop;
        return this;
    }

    public SyntaxNode getFirstChild() {
        return children.getFirst();
    }

    public SyntaxNode getLastChild() {
        return children.getLast();
    }

    public SyntaxNode addChild(SyntaxNode child) {
        children.add(child);
        return this;
    }

    public SyntaxNode checkout() {
        var node = getSyntaxNode();
        this.addChild(node);
        return node;
    }

    public SyntaxStatus getStatus() {
        return status;
    }

    public SyntaxNode setStatus(SyntaxStatus status) {
        this.status = status;
        return this;
    }

    public List<SyntaxNode> match(SyntaxType type) {
        return children.stream().filter((i) -> i.getType() == type).toList();
    }

    public SyntaxNode getChild(int index) {
        return children.get(index);
    }

    public SyntaxNode setChildren(List<SyntaxNode> children) {
        this.children = children;
        return this;
    }

    public int length() {
        return children.size();
    }

    public List<SyntaxNode> getChildren() {
        return children;
    }

    public SyntaxType getType() {
        return type;
    }

    public SyntaxNode setType(SyntaxType type) {
        this.type = type;
        return this;
    }

    public static SyntaxNode getSyntaxNode() {
        return new SyntaxNode();
    }

    public static SyntaxNode getSyntaxNode(SyntaxType type) {
        return new SyntaxNode().setType(type);
    }

    public static SyntaxNode getSyntaxNode(SyntaxType type, int loop) {
        return new SyntaxNode().setType(type).setLoop(loop);
    }

    public void printTree() {
        printTree("");
    }

    private void printTree(String prefix) {
        System.out.println(prefix + type + "::" + loop);
        for (SyntaxNode child : children) {
            child.printTree(prefix + "  "); // 每一层增加两个空格缩进
        }
    }

    @Override
    public String toString() {
        return "<" + type + "/" + loop + ">";
    }
}
