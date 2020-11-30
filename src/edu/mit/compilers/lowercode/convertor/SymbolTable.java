package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.decl.IRMemberDecl;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, IRMemberDecl> table = new HashMap<>();
    private int size = 0;

    public void put(String symbol, IRMemberDecl decl) {
        table.put(symbol, decl);
        ++size;
        assert table.size() == size;
    }

    public ArrayList<String> getNamesArray() {
        ArrayList<String> ret = new ArrayList<>();
        for (String name : table.keySet()) {
            ret.add(name);
        }
        return ret;
    }

    public String getNamesOfSymbol() {
        StringBuilder builder = new StringBuilder();
        for (String name : table.keySet()) {
            builder.append(name + '\n');
        }
        return builder.toString();
    }
}
