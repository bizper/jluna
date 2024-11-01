package compiler.components;

import compiler.components.models.Cage;
import compiler.components.models.Node;

import java.io.FileOutputStream;
import java.io.IOException;

import static compiler.components.define.NodeType.*;

public class Gen {

    record _D(boolean hasNext, byte[] data) {}

    private static final byte[] head = {0xf, 0xf};

    public static void gen(Cage cage) {
        String filepath = "./" + cage.getName() + ".l";
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            var data = data(cage.getRoot());
            while(data.hasNext()) {
                fos.write(data.data());
                fos.flush();
            }
            System.out.println("Compile Successfully");
        } catch (IOException e) {
            e.printStackTrace(); // 处理异常
        }
    }

    /**
     * 0x11         : new frame
     * 0x12 name    : new label
     * 0x13
     * 0x20 gtx     : put the value to top
     */
    private static _D data(Node node) {
        if(node.getType() != start) {
            return new _D(false, null);
        }
        for(Node n : node.getChild()) {
            switch(n.getType()) {
                case define -> {

                }
                case calculate -> {

                }
                case call -> {

                }
                case null, default -> {
                    return new _D(false, null);
                }
            }
        }
        return new _D(false, null);
    }

}
