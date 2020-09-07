package edu.mit.compilers.utils;

import java.util.ArrayList;

public class StringInfo {

    // string info list
    private ArrayList<String> info = new ArrayList<>();

    private String prefix;

    public StringInfo(String prefix) {
        this.prefix = prefix;
    }
    public StringInfo addInfo(String ele) {
        info.add(ele);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(prefix);
        for (String ele : info) {
            ret.append(ele + " ");
        }
        ret.append("\n");
        return ret.toString();
    }
}
