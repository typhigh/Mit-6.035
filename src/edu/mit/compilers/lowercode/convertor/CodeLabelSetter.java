package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.lowercode.code.GotoCode;
import edu.mit.compilers.lowercode.code.ThreeAddressCode;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;
import edu.mit.compilers.lowercode.ThreeAddressCodeListProcessor;

public class CodeLabelSetter implements ThreeAddressCodeListProcessor<Void> {

    private int labelFillCount = 0;

    @Override
    public Void process(ThreeAddressCodeList codes) {
        // fill every code's label
        for (ThreeAddressCode code : codes) {
            if (!code.isNeedLabel()) {
                continue;
            }

            if (code.getLabel() != null) {
                continue;
            }

            ++labelFillCount;
            code.setLabel("L" + labelFillCount);
        }

        // fill gotoLabel
        for (ThreeAddressCode code : codes) {
            if (code instanceof GotoCode) {
                ((GotoCode) code).fillGotoLabel();
            }
        }

        return null;
    }
}
