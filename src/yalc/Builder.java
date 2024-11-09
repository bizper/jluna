package yalc;

import compiler.components.Parser;
import yalc.d.Pattern;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static yalc.d.Pattern.getPattern;

public class Builder {

    private final Map<String, Pattern> map = new HashMap<>();

    private final StringBuilder buffer;

    private char[] chars;
    private int cursor = 0;

    private int group = 0;
    private Pattern p;

    private Builder() {
        this.buffer = new StringBuilder();
    }

    public static Builder build() {
        Builder builder = new Builder();
        File file = new File("./src/yalc/syntax.l");
        try (BufferedReader fr = new BufferedReader(new FileReader(file))) {
            while (fr.ready()) {
                String line = fr.readLine();
                if (line.startsWith("#")) continue;
                builder.parseLine(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.map.values().forEach(Pattern::printTree);
        return builder;
    }

    private void parseLine(String line) {
        this.chars = line.toCharArray();
        this.cursor = 0;
        this.p = getPattern();
        for (; this.cursor < this.chars.length; this.cursor++) {
            parse(this.chars[this.cursor]);
        }
        buildPattern();
    }

    private void parse(char c) {
        switch (c) {
            case '=':
                parseAssign();
                break;
            case '(':
                cursor++;
                parseParentheses();
                break;
            case '*':
                parseStar();
                break;
            case '+':
                parsePlus();
                break;
            case '?':
                parseQuestion();
                break;
            case '"':
                cursor++;
                parseString();
                break;
            case '|':
                parseSeparator();
                break;
            case ' ':
                buildPattern();
                break;
            default:
                append(c);
                break;
        }
    }

    private void parseString() {
        buildPattern();
        for (; this.cursor < this.chars.length; this.cursor++) {
            char c = this.chars[this.cursor];
            if (c == '"') {
                if (!isEmpty()) {
                    this.p.addChild(getPattern().setName(getBuffer()).setSpecial(3));
                    clear();
                }
                cursor++;
                break;
            } else {
                append(c);
            }
        }
    }

    private void parseSeparator() {
        buildPattern();
        if(!this.p.getName().equals("_s")) {
            Pattern pattern = getPattern().setName("_s");
            Pattern last = this.p.pop();
            this.p.addChild(pattern);
            pattern.addChild(last);
            this.p = pattern;
        }
    }

    private void parseStar() {
        buildPattern();
        Pattern last = p.getLast();
        if (last.getName().startsWith("_g")) p.setModifier(2);
        else last.setModifier(2);
    }

    private void parseQuestion() {
        buildPattern();
        Pattern last = p.getLast();
        if (last.getName().startsWith("_g")) p.setModifier(3);
        else last.setModifier(3);
    }

    private void parsePlus() {
        buildPattern();
        Pattern last = p.getLast();
        if (last.getName().startsWith("_g")) p.setModifier(1);
        else last.setModifier(1);
    }

    private void parseParentheses() {
        buildPattern();
        Pattern pattern = getPattern().setName("_g" + group++);
        p.addChild(pattern);
        p = pattern;
        for (; this.cursor < this.chars.length; this.cursor++) {
            char c = this.chars[this.cursor];
            if (c == ')') {
                buildPattern();
                cursor++;
                break;
            } else {
                parse(c);
            }
        }
        p = p.getParent();
    }

    private void buildPattern() {
        if (!isEmpty()) {
            String name = getBuffer();
            if(name.equals("NUMBER")) {
                this.p.addChild(getPattern().setSpecial(1).setName(name));
            } else if(name.equals("LETTER")) {
                this.p.addChild(getPattern().setSpecial(2).setName(name));
            } else {
                this.p.addChild(getPattern().setName(name));
                if(this.p.getName().equals("_s")) {
                    this.p = this.p.getParent();
                }
            }
            clear();
        }
    }

    private void parseAssign() {
        String name = getBuffer();
        if (map.containsKey(name)) {
            System.err.println("Duplicate rule: " + name);
        } else {
            p.setName(name);
            map.put(name, p);
        }
        clear();
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
