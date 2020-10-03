package edu.mit.compilers.lowcode;

/*
 * single-dir linked list
 * provide toArrayList func / begin-end iterator
 */
public class ThreeAddressCodeList {
    // [begin, end] all the elements
    private ListNode<ThreeAddressCode> begin = null;
    private ListNode<ThreeAddressCode> end = null;
    private int size = 0;

    public ThreeAddressCodeList(ThreeAddressCode code) {
        begin = end = new ListNode<>(code);
        size = 1;
    }

    public void add(ThreeAddressCode code) {
        ListNode<ThreeAddressCode> addNode = new ListNode<>(code);
        if (begin == null) {
            begin = end = addNode;
        } else {
            end.setNext(addNode);
        }
        ++size;
    }

    public int getSize() {
        return size;
    }

    /*
     * iterator func, return a iterator that provides hasNext/next function
     */
    public Iterator iterator() {
        return new Iterator(this);
    }

    /*
     * append other list to this list, and other list no longer used
     * return this list
     * thread unsafe
     */
    public ThreeAddressCodeList append(ThreeAddressCodeList other) {
        if (other == null) {
            return this;
        }

        if (begin == null) {
            begin = end = other.begin;
        } else {
            end.setNext(other.begin);
        }
        size += other.size;
        other.clear();
        return this;
    }

    public void clear() {
        begin = end = null;
        size = 0;
    }

    /*
     * single direction linked list with merge function
     */
    private class ListNode<T> {
        private ListNode<T> next;
        private T item;

        public T getItem() {
            return item;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public ListNode (T item) {
            this.item = item;
            this.next = null;
        }
    }

    /*
     * iterator for three address code list
     */
    public class Iterator {
        ListNode<ThreeAddressCode> cur;
        int curId;
        int size;

        public Iterator(ThreeAddressCodeList list) {
            cur = list.begin;
            size = list.size;
            curId = 0;
        }

        public boolean hasNext() {
            return curId < size;
        }

        public ThreeAddressCode next() {
            assert hasNext();
            ThreeAddressCode ret = cur.getItem();
            cur = cur.getNext();
            curId ++;
            return ret;
        }
    }
}
