package vm;

import compiler.components.Tools;
import compiler.components.models.Node;
import vm.c.Frame;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static compiler.components.define.NodeType.*;

public class Runnable {

    public static void run(Node node, Map<Integer, String> symbols) {
        Stack<Frame> frames = new Stack<>();
        if(node.getType() != start) {
            return;
        }
        Frame frame = Frame.getFrame(new HashMap<>());
        frames.push(frame);
//        for(Node n : node.getChild()) {
//            switch(n.getType()) {
//                case define:
//                    frame.add(symbols.get(n.getIndex()), n.getLastChild())
//                    break;
//                case calculate:
//                    System.out.println(frame.calculate(n));
//                    break;
//            }
//
//        }
    }



}
