import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    public Deque() {
       this.first = null;
       this.last = null;
       this.n = 0;
    }

    public boolean isEmpty() {

        return size() == 0;
    }

    public int size() {

        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node<>();
            first.item = item;
            last = first;
        } else {
            Node<Item> oldFirst = first;
            first = new Node<>();
            first.item = item;
            first.previous = oldFirst;
            oldFirst.next = first;
        }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            last = new Node<>();
            last.item = item;
            first = last;
        } else {
            Node<Item> oldLast = last;
            last = new Node<>();
            last.item = item;
            last.next = oldLast;
            oldLast.previous = last;
        }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (size() == 1) {
            first = null;
            last = null;
        } else {
            first = first.previous;
            first.next = null;
        }
        n--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (size() == 1) {
            last = null;
            first = null;
        } else {
            last = last.next;
            last.previous = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item item = current.item;
            current = current.previous;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
    }
}
