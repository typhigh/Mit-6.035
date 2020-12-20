package edu.mit.compilers.lowercode;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    // method name -> method info
    private HashMap<String, MethodInfo> methodInfoTable = new HashMap<>();
    private ArrayList<FieldInfo> globalFieldsInfo = new ArrayList<>();
    private ArrayList<FieldInfo> constStringFieldsInfo = new ArrayList<>();

    public ArrayList<FieldInfo> getGlobalFieldsInfo() {
        return globalFieldsInfo;
    }

    public String show() {
        StringBuilder builder = new StringBuilder();
        builder.append("Global:\n");
        getStringOfFieldsInfo(globalFieldsInfo, "\t", builder);

        for (MethodInfo methodInfo : methodInfoTable.values()) {
            builder.append(methodInfo.getMethodName() + "\n");
            getStringOfFieldsInfo(methodInfo.getFieldsInfo(), "\t", builder);
        }

        builder.append("Const String:\n");
        getStringOfFieldsInfo(constStringFieldsInfo, "\t", builder);
        return builder.toString();
    }

    public void putMethodInfo(String methodName, MethodInfo currentMethodInfo) {
        methodInfoTable.put(methodName, currentMethodInfo);
    }

    public void getStringOfFieldsInfo(ArrayList<FieldInfo> fieldsInfo, String prefix, StringBuilder builder) {
        for (FieldInfo fieldInfo : fieldsInfo) {
            builder.append(prefix);
            builder.append(fieldInfo.getLiteralString());
            builder.append("\n");
        }
    }

    public void putGlobalFieldInfo(FieldInfo fieldInfo) {
        globalFieldsInfo.add(fieldInfo);
    }

    public void putConstStringInfo(FieldInfo fieldInfo) {
        constStringFieldsInfo.add(fieldInfo);
    }

    public ArrayList<FieldInfo> getConstStringInfo() {
        return constStringFieldsInfo;
    }
}
