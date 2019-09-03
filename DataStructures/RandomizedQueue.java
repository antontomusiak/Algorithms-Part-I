import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int n;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int removeAt = StdRandom.uniform(n);
        Item item = items[removeAt];
        items[removeAt] = items[--n];
        items[n] = null;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int takeAt = StdRandom.uniform(n);
        return items[takeAt];
    }

    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int itemIndex;
        private final int[] itemsIndexes;

        public RQIterator() {
            this.itemIndex = 0;
            this.itemsIndexes = StdRandom.permutation(n);
        }

        @Override
        public boolean hasNext() {
            return itemIndex < n;
        }

        public Item next() {
            if (itemIndex >= n) throw new NoSuchElementException();
            Item item = items[itemsIndexes[itemIndex]];
            itemIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
