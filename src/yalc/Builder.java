package yalc;

import yalc.d.Pattern;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static yalc.d.Pattern.getPattern;

public class Builder {

    private final Pattern root = getPattern();

    private final Map<String, Pattern> map = new HashMap<>();

    private final StringBuilder buffer;

    private int group = 0;

    private Builder() {
        this.buffer = new StringBuilder();
    }

    public static void build() {
        Builder builder = new Builder();
        File file = new File("./src/yalc/syntax.l");
        try (BufferedReader fr = new BufferedReader(new FileReader(file))) {
            while(fr.ready()) {
                builder.parse(fr.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.map.values().forEach(Pattern::printTree);
    }

    private void parse(String line) {
        char[] chars = line.toCharArray();
        boolean left = true;
        Pattern p = getPattern();
        for(char c : chars) {
            if(c != ' ') {
                if(c == '=') {
                    if(!left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    } else {
                        String name = getBuffer();
                        if(map.containsKey(name)) {
                            System.err.println("duplicate rule name '" + name + "' appears.");
                            return;
                        }
                        left = false;
                        p.setName(name);
                        map.put(name, p);
                        clear();
                    }
                } else if(c == '(') {
                    if(left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    }
                } else if(c == ')') {
                    if(left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    }
                } else if(c == '"') {
                    if(left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    }
                } else if(c == '+') {
                    if(left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    }
                } else if(c == '*') {
                    if(left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    }
                } else if(c == '?') {
                    if(left) {
                        System.err.println("no '=' allowed here.");
                        return;
                    }
                } else append(c);
            }
        }
        if(!isEmpty()) {
            String name = getBuffer();
            if(name.equals("NUMBER")) {
                p.addChild(getPattern().setName(name).setSpecial(1));
            }
            clear();
        }
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
