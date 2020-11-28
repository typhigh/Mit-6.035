package edu.mit.compilers.utils;

import edu.mit.compilers.ir.common.IR;

import java.util.ArrayList;
import java.util.HashMap;

// single instance
public class IRCloneHelper {
    private static HashMap<IR, IR> cloneTable = new HashMap<>();

    public static IR getClone(IR ir) {
        if (ir == null) {
            return null;
        }

        IR ret = cloneTable.get(ir);
        // System.out.println("OLD:" + ir + " NEW:" + ret);
        if (ret == null) {
            // if not found, return itself
            return ir;
        }
        return ret;
    }

    public static void put(IR old, IR clone) {
        assert old != null && clone != null;

        // Debug
        // System.out.println("old:" + old + " " + "clone:" + clone);
        cloneTable.put(old, clone);
    }

    public static <T extends IR> ArrayList<T> getArrayClone (ArrayList<T> list) throws CloneNotSupportedException {
        if (list == null) {
            // null -> null
            return null;
        }

        ArrayList<T> clone = new ArrayList<>();
        for (T node : list) {
            // assert node instanceof Cloneable;
            clone.add((T) node.clone());
        }
        return clone;
    }
}
