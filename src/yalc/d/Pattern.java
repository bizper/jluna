package yalc.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Pattern {

    /**
     * 0 no modifier
     * 1 + once or multiple times
     * 2 * none or multiple times
     * 3 ? none or once
     */
    private int modifier = 0;
    //node name, should be unique
    private String name;
    //represent some special value
    /**
     * 0 no special
     * 1 numbers from 0 to 9, both included
     * 2 letters from a to z and A to Z
     * 3 literal value node
     */
    private int special;
    private final Stack<Pattern> list;
    private Pattern parent;

    private Pattern() {
        this.list = new Stack<>();
    }

    public static Pattern getPattern() {
        return new Pattern();
    }

    public Pattern addChild(Pattern n) {
        this.list.add(n);
        n.setParent(this);
        return this;
    }

    public Pattern addChild(List<Pattern> list) {
        this.list.addAll(list);
        list.forEach(e -> e.setParent(this));
        return this;
    }

    public Pattern getParent() {
        return parent;
    }

    public void setParent(Pattern parent) {
        this.parent = parent;
    }

    public List<Pattern> getList() {
        return this.list.stream().toList();
    }

    public Pattern getFirst() {
        return this.list.getFirst();
    }

    public Pattern getLast() {
        return this.list.getLast();
    }

    public Pattern pop() {
        return this.list.pop();
    }

    public Pattern setModifier(int modifier) {
        this.modifier = modifier;
        return this;
    }

    public Pattern setName(String name) {
        this.name = name;
        return this;
    }

    public Pattern setSpecial(int special) {
        this.special = special;
        return this;
    }

    public int size() {
        return this.list.size();
    }

    public Pattern clear() {
        this.list.clear();
        return this;
    }

    public String getName() {
        return name;
    }

    public int getSpecial() {
        return special;
    }

    public int getModifier() {
        return modifier;
    }

    public void printTree() {
        printTree("");
    }

    private void printTree(String prefix) {
        System.out.println(prefix + "-" + modifier + ":" + (special != 0 ? special + ":" : "") + name);
        for (Pattern child : list) {
            if(child == this) {
                System.out.println(prefix + "   " + "-itself:-1");
            }
            else child.printTree(prefix + "   "); // 每一层增加两个空格缩进
        }
    }

}
