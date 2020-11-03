package edu.mit.compilers.lowcode;

import edu.mit.compilers.utils.SimpleLinkedList;

/*
 * single-dir linked list
 * provide toArrayList func / begin-end iterator
 */
public class ThreeAddressCodeList extends SimpleLinkedList<ThreeAddressCode> {

    public ThreeAddressCodeList() {
        super();
    }

    public ThreeAddressCodeList(ThreeAddressCode code) {
        super(code);
    }
    // [begin, end] all the elements


}
