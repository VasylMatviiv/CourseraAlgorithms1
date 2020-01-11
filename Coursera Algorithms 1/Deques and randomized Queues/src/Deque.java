import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    public Deque() {
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("asd");
        deque.addFirst("asd2");
        deque.removeLast();
        System.out.println(deque.isEmpty());

        //
        Deque<Integer> deque2 = new Deque<Integer>();
        deque2.addLast(1);
        deque2.removeFirst();
        deque2.addLast(5);
        deque2.addLast(6);
        deque2.removeFirst();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
            size++;
        } else {
            Node temp = new Node();
            temp.setNext(first);
            temp.setItem(item);
            temp.setPrev(null);
            first.setPrev(temp);
            first = temp;
            size++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            last = new Node();
            last.item = item;
            first = last;
            size++;
        } else {
            Node temp = new Node();
            temp.setPrev(last);
            temp.setItem(item);
            temp.setNext(null);
            last.setNext(temp);
            last = temp;
            size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node temp;
        if (size() == 1) {
            temp = first;
            first = null;
            last = null;
            size--;
        } else {
            temp = first;
            first = temp.next;
            first.setPrev(null);
            size--;
        }
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node temp;
        if (size() == 1) {
            temp = first;
            first = null;
            last = null;
            size--;
        } else {
            temp = last;
            last = temp.prev;
            last.setNext(null);
            size--;
        }
        return temp.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        private Node next;
        private Node prev;
        private Item item;

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.getItem();
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
