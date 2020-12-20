package edu.mit.compilers.assembly.x64.codes;

import edu.mit.compilers.assembly.common.AssemblyCode;
import edu.mit.compilers.assembly.common.AssemblyCodeList;
import edu.mit.compilers.utils.SimpleLinkedList;

import java.util.Iterator;

public class X64CodeList implements AssemblyCodeList {
    private SimpleLinkedList<X64Code> codes = new SimpleLinkedList<>();

    public X64CodeList append(X64CodeList other) {
        codes.append(other.codes);
        return this;
    }

    public X64CodeList append(X64Code other) {
        codes.add(other);
        return this;
    }

    @Override
    public String show() {
        StringBuilder builder = new StringBuilder();
        for (X64Code code : codes) {
            code.getStringForShow("", builder);
        }
        return builder.toString();
    }

    @Override
    public Iterator<AssemblyCode> iterator() {
        return null;
    }
}
