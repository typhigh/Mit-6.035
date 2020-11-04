package edu.mit.compilers.utils;

import java.util.ArrayList;

public class StringInfo {

    // string info list
    private ArrayList<String> info = new ArrayList<>();

    private String prefix;

    // may be ignored
    private boolean ignored;
    private final static StringInfo ignoredInfo = new StringInfo(null);

    public StringInfo(String prefix) {
        this.prefix = prefix;
        ignored = prefix == null;
    }

    public static StringInfo getIgnoredInfo() {
        return ignoredInfo;
    }

    public StringInfo addInfo(String ele) {
        info.add(ele);
        return this;
    }

    @Override
    public String toString() {
        if (ignored) {
            return "";
        }

        StringBuilder ret = new StringBuilder(prefix);
        for (String ele : info) {
            ret.append(ele + " ");
        }
        ret.append("\n");
        return ret.toString();
    }
}
