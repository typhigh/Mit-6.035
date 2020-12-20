package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.lowercode.*;
import edu.mit.compilers.lowercode.code.EmptyCode;
import edu.mit.compilers.lowercode.code.GotoCode;
import edu.mit.compilers.lowercode.code.ThreeAddressCode;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

import java.util.HashMap;

/*
 * fold empty code
 * condition: every empty code is not the last code
 */
public class EmptyCodeFolder implements ThreeAddressCodeListProcessor<ThreeAddressCodeList> {

    private HashMap<String, String> oldToNewLabel = new HashMap<>();


    /*
     * fold empty code and update gotoLabel
     * example:
     * L1 :
     * L2 : a = 1
     * L3 :
     * L4 :
     * b = 2
     * goto L4
     * ==>
     * L1 : a = 1
     * L3 : b = 2
     * goto L3
     */
    @Override
    public ThreeAddressCodeList process(ThreeAddressCodeList codes) {
        ThreeAddressCodeList ret = foldImpl(codes);
        updateImpl(ret);
        return ret;
    }

    private ThreeAddressCodeList foldImpl(ThreeAddressCodeList codes) {
        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        String currentNewLabel = null;
        for (ThreeAddressCode code : codes) {
            if (code instanceof EmptyCode) {
                // empty code
                if (!code.hasLabel()) {
                    // no label for empty code, do nothing
                    continue;
                }

                if (currentNewLabel != null) {
                    // already exist currentNewLabel, just put old->new label
                    putOldToNewLabel(code.getLabel(), currentNewLabel);
                    continue;
                }

                currentNewLabel = code.getLabel();
                continue;
            }

            // general code
            if (currentNewLabel != null) {
                if (code.hasLabel()) {
                    putOldToNewLabel(code.getLabel(), currentNewLabel);
                }
                code.setLabel(currentNewLabel);

                // clean current new label, which has been pushed down to a non-empty code
                currentNewLabel = null;
            }
            ret.append(code);
        }
        return ret;
    }

    private void updateImpl(ThreeAddressCodeList codes) {
        for (ThreeAddressCode code : codes) {
            if (code instanceof GotoCode) {
                String gotoLabel = ((GotoCode) code).getGotoLabel();
                assert gotoLabel != null : "GotoCode's gotoLabel must be filled before EmptyCodeFolder processing";
                ((GotoCode) code).setGotoLabel(getNewLabel(gotoLabel));
            }
        }
    }

    private void putOldToNewLabel(String oldLabel, String newLabel) {
        oldToNewLabel.put(oldLabel, newLabel);
    }

    private String getNewLabel(String oldLabel) {
        return oldToNewLabel.getOrDefault(oldLabel, oldLabel);
    }
}
