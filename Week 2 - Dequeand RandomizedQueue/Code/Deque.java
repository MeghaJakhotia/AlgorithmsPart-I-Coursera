/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int count = 0;

    private class Node {
        Node prev;
        Item item;
        Node next;
    }

    public Deque() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return (first == null && last == null);
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Insert an Element!");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (oldFirst != null) oldFirst.prev = first;
        if (last == null) last = first;
        count++;
    }

    public void addLast(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Insert an Element!");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (oldLast != null) oldLast.next = last;
        if (first == null) first = last;
        count++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No more elements to remove");
        Item back = first.item;
        count--;
        if (first.equals(last)) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        return back;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("No more elements to remove");
        Item back = last.item;
        count--;
        if (first.equals(last)) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        return back;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more elements left!");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Cannot do this operation!");
        }
    }
}

