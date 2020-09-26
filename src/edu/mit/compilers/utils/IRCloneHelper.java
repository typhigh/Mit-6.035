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
        assert ret != null;
        return ret;
    }

    public static void put(IR old, IR clone) {
        assert old != null && clone != null;
        cloneTable.put(old, clone);
    }

    public static <T extends IR> ArrayList<T> getArrayClone (ArrayList<T> list) throws CloneNotSupportedException {
        if (list == null) {
            // null -> null
            return null;
        }

        ArrayList<T> clone = new ArrayList<>();
        for (T node : list) {
            clone.add((T) node.clone());
        }
        return clone;
    }
}