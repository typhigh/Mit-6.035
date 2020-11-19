package edu.mit.compilers.lowercode;

import edu.mit.compilers.utils.SimpleLinkedList;

/*
 * single-dir linked list, implemented like slice
 * provide toArrayList func / begin-end iterator
 */
public class ThreeAddressCodeList {

    // the list of codes
    SimpleLinkedList<ThreeAddressCode> codes = new SimpleLinkedList<>();

    // tells if the lists is null (not fill)
    // empty may be not null
    private boolean isNull = true;

    // tells if list (or the code of this list) need label
    // used as a lazy flag from sublist to list, from list to code
    private boolean needLabel = false;

    public ThreeAddressCodeList() {}

    public ThreeAddressCodeList(ThreeAddressCode code) {
        this.codes = new SimpleLinkedList<>(code);
        isNull = false;
    }

    public ThreeAddressCodeList(ThreeAddressCodeList other) {
        this.codes = new SimpleLinkedList<>(other.codes);
        this.isNull = other.isNull;
        this.needLabel = other.needLabel;
    }

    public ThreeAddressCodeList append(ThreeAddressCode other) {
        if (isNull()) {
            return init(other);
        }

        codes.add(other);
        isNull = false;
        checkAndPushNeedLabel();
        return this;
    }

    public ThreeAddressCodeList append(ThreeAddressCodeList other) {
        if (isNull()) {
            return init(other);
        }

        if (other.isNull()) {
            return this;
        }

        codes.append(other.codes);
        isNull = false;
        checkAndPushNeedLabel();
        return this;
    }

    public ThreeAddressCodeList concat(ThreeAddressCodeList other) {
        ThreeAddressCodeList newCodes = new ThreeAddressCodeList(this);
        return newCodes.append(other);
    }

    public ThreeAddressCode front() {
        return codes.front();
    }

    public SimpleLinkedList<ThreeAddressCode>.Iterator iterator() {
        return codes.iterator();
    }

    // show the content in string-format
    public String show() {
        StringBuilder builder = new StringBuilder();
        for (SimpleLinkedList<ThreeAddressCode>.Iterator iter = iterator(); iter.hasNext(); ) {
            ThreeAddressCode code = iter.next();
            code.getStringForShow("", builder);
        }
        return builder.toString();
    }

    public boolean isNull() {
        return isNull;
    }

    public ThreeAddressCodeList init(ThreeAddressCode other) {
        codes = new SimpleLinkedList<>(other);
        isNull = false;
        checkAndPushNeedLabel();
        return this;
    }

    public ThreeAddressCodeList init(ThreeAddressCodeList other) {
        codes = new SimpleLinkedList<>(other.codes);
        this.isNull = other.isNull;
        this.needLabel |= other.needLabel;
        checkAndPushNeedLabel();
        return this;
    }

    public void setNeedLabelTrue() {
        needLabel = true;
        checkAndPushNeedLabel();
    }

    private void checkAndPushNeedLabel() {
        // push lazy flag "needLabel" to the front maybe
        if (!needLabel || isNull()) {
            return;
        }
        front().setNeedLabelTrue();
        needLabel = false;
    }
}
