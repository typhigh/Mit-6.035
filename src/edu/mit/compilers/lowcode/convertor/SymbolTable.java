package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.decl.IRMemberDecl;

import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, IRMemberDecl> table = new HashMap<>();
    private int size = 0;

    public void put(String symbol, IRMemberDecl decl) {
        table.put(symbol, decl);
        ++size;
        assert table.size() == size;
    }
}
