/* *****************************************************************************
 *  Name: Megha Jakhotia
 *  Date: 6th June
 *  Description: Rendomized Queue Implementation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n = 0;
    private Item[] array;

    public RandomizedQueue() {
        array = (Item[]) new Object[5];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("Enter an entry");
        if (n == array.length) resize(2 * array.length);
        array[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("There are no elements");
        int random = StdRandom.uniform(n);
        if (!(n == 1 || n - 1 == random)) {
            swap(random);
        }
        Item item = array[--n];
        array[n] = null;
        if (n > 1 && n == array.length / 4) resize(array.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("There are no elements");
        int random = StdRandom.uniform(n);
        return array[random];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) copy[i] = array[i];
        array = copy;
    }

    private void swap(int x) {
        Item txt;
        txt = array[x];
        array[x] = array[n - 1];
        array[n - 1] = txt;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    private class ListIterator implements Iterator<Item> {
        private int current = n;
        private final Item[] shuffled;

        public ListIterator() {
            shuffled = array;
            StdRandom.shuffle(shuffled);
        }

        public boolean hasNext() {
            return current > 0;
        }

        public Item next() {
            if (hasNext()) return shuffled[--current];
            else throw new java.util.NoSuchElementException("No more elements left!");
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Cannot do this operation!");
        }
    }

}
