package edu.mit.compilers.lowcode;

import edu.mit.compilers.utils.SimpleLinkedList;

/*
 * single-dir linked list
 * provide toArrayList func / begin-end iterator
 */
public class ThreeAddressCodeList extends SimpleLinkedList<ThreeAddressCode> {

    // tells if the lists is null (not fill)
    // empty may be not null
    private boolean isNull = true;

    // tells if list (or the code of this list) need label
    private boolean needLabel = false;

    public ThreeAddressCodeList() {
        super();
    }

    public ThreeAddressCodeList(ThreeAddressCode code) {
        super(code);
        isNull = false;
    }
    // [begin, end] all the elements

    // show the content in string-format
    public String show() {
        StringBuilder builder = new StringBuilder();
        for (Iterator iter = iterator(); iter.hasNext(); ) {
            ThreeAddressCode code = iter.next();
            code.getStringForShow("", builder);
        }
        return builder.toString();
    }

    public boolean isNull() {
        return isNull;
    }

    public void init(ThreeAddressCodeList other) {
        super.init(other);
        this.isNull = other.isNull;
        this.needLabel |= other.needLabel;
        if (!isNull && needLabel) {
            // TODO
        }
    }

    public void setNeedLabelTrue() {
        needLabel = true;
    }
}
