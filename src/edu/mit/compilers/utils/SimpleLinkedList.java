package edu.mit.compilers.utils;

/*
 * single-dir linked list
 * provide toArrayList func / begin-end iterator
 */
public class SimpleLinkedList<T> {
    // [begin, end] all the elements
    ListNode<T> begin;
    ListNode<T> end;
    private int size;

    public SimpleLinkedList() {
        begin = end = null;
        size = 0;
    }

    public SimpleLinkedList(T element) {
        begin = end = new ListNode<>(element);
        size = 1;
    }

    public void add(T element) {
        ListNode<T> addNode = new ListNode<>(element);
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

    public boolean isEmpty() {
        return size == 0;
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
    public SimpleLinkedList append(SimpleLinkedList other) {
        if (other == null || other.isEmpty()) {
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
     * iterator for three address  element list
     */
    public class Iterator {
        ListNode<T> cur;
        int curId;
        int size;

        public Iterator(SimpleLinkedList list) {
            cur = list.begin;
            size = list.size;
            curId = 0;
        }

        public boolean hasNext() {
            return curId < size;
        }

        public T next() {
            assert hasNext();
            T ret = cur.getItem();
            cur = cur.getNext();
            curId ++;
            return ret;
        }
    }
}
