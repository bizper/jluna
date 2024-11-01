package vm;

import compiler.components.Tools;
import compiler.components.models.Cage;
import compiler.components.models.Node;
import vm.c.Frame;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static compiler.components.define.NodeType.*;

public class Runtime {

    public static void load(Cage cage) {
        run(cage.getRoot(), cage.getSymbols());
    }

    public static void run(Node root, Map<Integer, String> symbols) {
        final Stack<Frame> frames = new Stack<>();
        if(root.getType() != start) {
            return;
        }
        Frame frame = Frame.getFrame(symbols);
        frames.push(frame);
        for(Node n : root.getChild()) {
            switch(n.getType()) {
                case define:
                    if(n.getLastChild().getType() == constant_v) {
                        frame.define(symbols.get(n.getIndex()), String.valueOf(n.getLastChild().getIndex()));
                    } else {
                        //frame.add(symbols.get(n.getIndex()), String.valueOf(n.getLastChild().getIndex()));
                    }
                    break;
                case calculate:
                    System.out.println(frame.calculate(n));
                    break;
                case call:
                    frames.push(null);
                    break;
            }

        }
    }



}
