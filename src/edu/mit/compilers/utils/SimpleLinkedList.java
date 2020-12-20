package edu.mit.compilers.utils;

import java.util.Iterator;

/*
 * single-dir linked list
 * provide toArrayList func / begin-end iterator
 */
public class SimpleLinkedList<T> implements Iterable<T> {
    // [begin, end] all the elements
    private ListNode<T> begin;
    private ListNode<T> end;
    private int size;

    public SimpleLinkedList() {
        begin = end = null;
        size = 0;
    }

    public SimpleLinkedList(T element) {
        begin = end = new ListNode<>(element);
        size = 1;
    }

    public SimpleLinkedList(SimpleLinkedList<T> other) {
        this.begin = other.begin;
        this.end = other.end;
        this.size = other.size;
    }

    public void add(T element) {
        ListNode<T> addNode = new ListNode<>(element);
        if (begin == null) {
            begin = end = addNode;
        } else {
            end.setNext(addNode);
            end = end.next;
        }
        ++size;
    }

    public T front() {
        return begin.item;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
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
            assert size == 0;
            begin = other.begin;
        } else {
            end.setNext(other.begin);
            other.begin.setPrev(end);
        }
        end = other.end;
        size += other.size;
        return this;
    }

    public void clear() {
        begin = end = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Ltr(this);
    }

    /*
     * single direction linked list with merge function
     */
    private class ListNode<T> {
        private ListNode<T> next;
        private ListNode<T> prev;
        private T item;

        public ListNode<T> getNext() {
            return next;
        }

        public void setNext(ListNode<T> next) {
            this.next = next;
        }

        public ListNode<T> getPrev() {
            return prev;
        }

        public void setPrev(ListNode<T> prev) {
            this.prev = prev;
        }

        public T getItem() {
            return item;
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
     * iterator for three address element list
     */
    public class Ltr implements Iterator<T> {
        ListNode<T> cur;
        int curId;
        int size;

        public Ltr(SimpleLinkedList list) {
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
