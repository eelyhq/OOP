package org.example;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Parameterized hash table container.
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> nextNode;

        private Node(K key, V value, Node<K, V> nextNode) {
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }
    }

    private static final int DEFAULT_CAPACITY = 2;

    private int capacity;
    private int size;
    private int modCount = 0;

    private Node<K, V>[] table;

    /**
     * Constructs an empty hash table.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.table = (Node<K, V>[]) new Node[capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> current = table[i];

            while (current != null) {
                int newIndex = hash(current.key) & (newCapacity - 1);
                Node<K, V> nextNode = current.nextNode;

                current.nextNode = newTable[newIndex];
                newTable[newIndex] = current;

                current = nextNode;
            }
        }
        table = newTable;
        capacity = newCapacity;
    }

    private double getLoadFactor() {
        return (double) size / capacity;
    }

    private static int hash(Object key) {
        if (key == null) {
            return 0;
        }

        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    /**
     * Returns the number of elements.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the table is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the table contains the key.
     */
    public boolean contains(K key) {
        int index = hash(key) & (capacity - 1);

        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return true;
            }
            current = current.nextNode;
        }
        return false;
    }

    /**
     * Checks if the table contains the key-value pair.
     */
    public boolean contains(K key, V value) {
        int index = hash(key) & (capacity - 1);

        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key) && Objects.equals(current.value, value)) {
                return true;
            }
            current = current.nextNode;
        }
        return false;
    }

    /**
     * Adds or updates a key-value pair.
     */
    public boolean put(K key, V value) {
        if (key == null) {
            return false;
        }

        int index = hash(key) & (capacity - 1);

        Node<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return true;
            }
            current = current.nextNode;
        }

        current = table[index];
        table[index] = new Node<K, V>(key, value, current);
        size++;
        modCount++;

        if (getLoadFactor() >= 0.75) {
            resize(capacity * 2);
        }

        return true;
    }

    /**
     * Removes an element by key.
     */
    public boolean remove(K key) {
        if (key == null) {
            return false;
        }

        int index = hash(key) & (capacity - 1);
        Node<K, V> current = table[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    table[index] = current.nextNode;
                } else {
                    previous.nextNode = current.nextNode;
                }
                size--;
                modCount++;

                if (capacity > DEFAULT_CAPACITY && getLoadFactor() <= 0.25) {
                    resize(capacity / 2);
                }

                return true;
            }
            previous = current;
            current = current.nextNode;
        }

        return false;
    }

    /**
     * Removes an element by key and value.
     */
    public boolean remove(K key, V value) {
        if (key == null) {
            return false;
        }

        int index = hash(key) & (capacity - 1);
        Node<K, V> current = table[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (Objects.equals(current.value, value)) {
                    if (previous == null) {
                        table[index] = current.nextNode;
                    } else {
                        previous.nextNode = current.nextNode;
                    }
                    size--;
                    modCount++;

                    if (capacity > DEFAULT_CAPACITY && getLoadFactor() <= 0.25) {
                        resize(capacity / 2);
                    }

                    return true;
                }
                return false;
            }
            previous = current;
            current = current.nextNode;
        }

        return false;
    }

    /**
     * Returns the value by key.
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int index = hash(key) & (capacity - 1);

        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }

            current = current.nextNode;
        }

        return null;
    }

    /**
     * Updates the value by key.
     */
    public boolean update(K key, V value) {
        if (key == null) {
            return false;
        }

        int index = hash(key) & (capacity - 1);

        Node<K, V> current = table[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return true;
            }

            current = current.nextNode;
        }
        return false;
    }

    /**
     * Key-value pair container.
     */
    public static class Entry<K, V> {
        private final K key;
        private final V value;

        /**
         * Constructs an entry.
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value.
         */
        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entry<?, ?> entry)) {
                return false;
            }
            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private final int expectedModCount = modCount;
        private int currentBucket = 0;
        private Node<K, V> currentNode = null;

        public HashTableIterator() {
            advanceToNextBucket();
        }

        private void advanceToNextBucket() {
            while (currentBucket < capacity && table[currentBucket] == null) {
                currentBucket++;
            }
            if (currentBucket < capacity) {
                currentNode = table[currentBucket];
                currentBucket++;
            } else {
                currentNode = null;
            }
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Entry<K, V> next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException(
                    "HashTable was modified during iteration!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Entry<K, V> result = new Entry<>(currentNode.key, currentNode.value);

            if (currentNode.nextNode != null) {
                currentNode = currentNode.nextNode;
            } else {
                advanceToNextBucket();
            }

            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof HashTable<?, ?> otherTable)) {
            return false;
        }

        if (this.size != otherTable.size) {
            return false;
        }

        @SuppressWarnings("unchecked")
        HashTable<K, V> other = (HashTable<K, V>) otherTable;

        for (Entry<K, V> entry : this) {
            K key = entry.getKey();
            V value = entry.getValue();

            if (!other.contains(key)) {
                return false;
            }

            if (!Objects.equals(value, other.get(key))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for (Entry<K, V> entry : this) {
            int entryHash = Objects.hashCode(entry.getKey()) ^ Objects.hashCode(entry.getValue());
            sum += entryHash;
        }

        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean isFirst = true;

        for (Entry<K, V> entry : this) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }

        sb.append("}");

        return sb.toString();
    }
}
