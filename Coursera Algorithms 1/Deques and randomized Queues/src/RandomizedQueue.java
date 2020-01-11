import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] queue;
    private int capacity = 2;

    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[capacity];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(441);
        queue.enqueue(84);
        queue.enqueue(300);
        queue.enqueue(196);
        queue.enqueue(445);
        System.out.println(queue.sample());
        System.out.println(queue.dequeue());
        queue.enqueue(289);
        System.out.println(queue.size());
        queue.enqueue(166);
        queue.enqueue(453);
        System.out.println(queue.dequeue());
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        queue[size++] = item;

        if (size == capacity) resize(capacity * 2);
    }

    private void resize(int newCapacity) {
        Item[] copy = (Item[]) new Object[newCapacity];
        capacity = newCapacity;
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];
        rearrangeQueue(randomIndex);
        queue[--size] = null;
        if (size == queue.length / 4) resize(capacity / 2);
        return item;
    }

    private void rearrangeQueue( int index) {
        for (int i = index; i < size; i++) {
            queue[i] = queue[i+1];
        }
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(size);
        Item item = queue[randomIndex];
        return item;
    }

    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {
        private int i;
        private int[] randomIndexes;

        public MyIterator() {
            i = 0;
            randomIndexes = new int[size];
            for (int j = 0; j < size; j++) {
                randomIndexes[j] = j;
            }
            StdRandom.shuffle(randomIndexes);
        }

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue[randomIndexes[i++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

