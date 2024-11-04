package yalc.d;

import java.util.ArrayList;
import java.util.List;

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
     */
    private int special;
    private final List<Pattern> list;

    private Pattern() {
        this.list = new ArrayList<>();
    }

    public static Pattern getPattern() {
        return new Pattern();
    }

    public Pattern addChild(Pattern n) {
        this.list.add(n);
        return this;
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
        System.out.println(prefix + "-" + modifier + ":" + name);
        for (Pattern child : list) {
            if(child == this) {
                System.out.println(prefix + "   " + "-itself:-1");
            }
            else child.printTree(prefix + "   "); // 每一层增加两个空格缩进
        }
    }

}
