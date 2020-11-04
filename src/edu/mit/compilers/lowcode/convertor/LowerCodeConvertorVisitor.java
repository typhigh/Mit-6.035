package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRBreakStmt;
import edu.mit.compilers.lowcode.AssignSingleOperand;
import edu.mit.compilers.lowcode.PopParamCode;
import edu.mit.compilers.lowcode.ThreeAddressCodeList;

public class LowerCodeConvertorVisitor extends IRVisitor<ThreeAddressCodeList> {

    // TODO: avoid visit unused ir
    // default visit func
    @Override
    public ThreeAddressCodeList visit(IR ir) {
        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        for (IR child : ir.getChildren()) {
            ret.append(visit(child));
        }
        return ret;
    }

    @Override
    public ThreeAddressCodeList visit(IRMethodDecl ir) {
        ThreeAddressCodeList ret = visit((IR) ir);
        ret.front().setLabel(ir.getVariable().getName());
        return ret;
    }

    @Override
    public ThreeAddressCodeList visit(IRParameterList ir) {
        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        for (IRFieldDecl param : ir.getParaList()) {
            ret.add(new PopParamCode(param.getVariable().getName()));
        }
        return ret;
    }

    /*
     * left[location] = value
     * <=>
     * t1 = location
     * t2 = value
     * left[t1] = t2
     */
    @Override
    public ThreeAddressCodeList visit(IRAssignStmt ir) {
        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        IRLocation left = ir.getLocation();
        String leftVariable = left.getVariable().getName();
        String locationVariable = null;

        // t1 = location
        boolean isArray = left.isArrayLocation();
        if (isArray) {
            IRExpression location = left.getLocation();
            ret.append(visit(location));
            locationVariable = location.getNameInLowerCode();
        }

        // t2 = value
        IRExpression value = ir.getValue();
        ret.append(visit(value));
        String rightVariable = value.getNameInLowerCode();

        ret.add(new AssignSingleOperand(leftVariable, locationVariable, rightVariable, null));
        return ret;
    }

    @Override
    public ThreeAddressCodeList visit(IRBreakStmt ir) {
        return super.visit(ir);

    }


}
