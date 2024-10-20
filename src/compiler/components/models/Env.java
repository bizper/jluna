package compiler.components.models;

import java.util.HashMap;
import java.util.Map;

public class Env {

    private static final HashMap<String, Integer> map = new HashMap<>();

    static {
        map.put("+", 0);
        map.put("-", 1);
        map.put("*", 2);
        map.put("/", 3);
        map.put("(", 4);
        map.put(")", 5);
        map.put("=", 6);
    }

    public static int getIndex(String s) {
        return map.getOrDefault(s, -1);
    }

    public static Map<Integer, String> getMap() {
        var result = new HashMap<Integer, String>();
        map.forEach((k, v) -> result.put(v, k));
        return result;
    }

}
