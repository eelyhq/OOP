package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashTableTest {
    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testCreateEmptyTable() {
        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
    }

    @Test
    void testPut() {
        assertTrue(table.put("A", 1));
        assertTrue(table.put("B", 2));
        assertEquals(2, table.size());

        assertTrue(table.put("A", 10));
        assertEquals(2, table.size());
        assertEquals(10, table.get("A"));

        assertFalse(table.put(null, 5));
    }

    @Test
    void testRemove() {
        table.put("A", 1);
        table.put("B", 2);

        assertTrue(table.remove("A"));
        assertEquals(1, table.size());
        assertNull(table.get("A"));
        assertFalse(table.remove("A"));
        assertFalse(table.remove("UNKNOWN"));
        assertFalse(table.remove(null));

        table.put("C", 3);
        assertFalse(table.remove("C", 999));
        assertEquals(2, table.size());
        assertTrue(table.remove("C", 3));  
        assertEquals(1, table.size());
        assertFalse(table.remove(null, 3));
    }

    @Test
    void testGet() {
        table.put("A", 1);
        table.put("B", 2);

        assertEquals(1, table.get("A"));
        assertEquals(2, table.get("B"));
        assertNull(table.get("UNKNOWN"));
        assertNull(table.get(null));
    }

    @Test
    void testUpdate() {
        table.put("A", 1);

        assertTrue(table.update("A", 100));
        assertEquals(100, table.get("A"));

        assertFalse(table.update("UNKNOWN", 200));
        assertFalse(table.update(null, 200));
    }

    @Test
    void testContains() {
        table.put("A", 1);

        assertTrue(table.contains("A"));
        assertFalse(table.contains("B"));
        assertFalse(table.contains(null));

        assertTrue(table.contains("A", 1));
        assertFalse(table.contains("A", 999));
        assertFalse(table.contains("B", 1));
    }

    @Test
    void testIterator() {
        table.put("A", 1);
        table.put("B", 2);

        int count = 0;
        for (HashTable.Entry<String, Integer> entry : table) {
            count++;
            assertEquals(entry.getValue(), table.get(entry.getKey()));
        }
        assertEquals(2, count);

        Iterator<HashTable.Entry<String, Integer>> iterator = table.iterator();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testConcurrentModificationException() {
        table.put("A", 1);
        table.put("B", 2);

        Iterator<HashTable.Entry<String, Integer>> it1 = table.iterator();
        table.put("C", 3);
        assertThrows(ConcurrentModificationException.class, it1::next);

        Iterator<HashTable.Entry<String, Integer>> it2 = table.iterator();
        table.remove("A");
        assertThrows(ConcurrentModificationException.class, it2::next);
    }

    @Test
    void testEqualsAndHashCode() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());

        t1.put("A", 1);
        t1.put("B", 2);
        assertNotEquals(t1, t2);

        t2.put("B", 2);
        t2.put("A", 1);
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());

        assertEquals(t1, t1);
        assertNotEquals(t1, null);
        assertNotEquals(t1, "some string");

        t2.update("A", 999);
        assertNotEquals(t1, t2);
    }

    @Test
    void testToString() {
        assertEquals("{}", table.toString());

        table.put("A", 1);
        assertEquals("{A=1}", table.toString());

        table.put("B", 2);
        String str = table.toString();
        assertTrue(str.startsWith("{") && str.endsWith("}"));
        assertTrue(str.contains("A=1"));
        assertTrue(str.contains("B=2"));
    }

    @Test
    void testCollisionsAndResizing() {
        for (int i = 0; i < 20; i++) {
            table.put("key" + i, i);
        }
        assertEquals(20, table.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, table.get("key" + i));
        }

        for (int i = 0; i < 18; i++) {
            assertTrue(table.remove("key" + i));
        }
        assertEquals(2, table.size());
        assertEquals(18, table.get("key18"));
        assertEquals(19, table.get("key19"));
    }

    @Test
    void testEntry() {
        HashTable.Entry<String, Integer> e1 = new HashTable.Entry<>("X", 10);
        assertEquals("X", e1.getKey());
        assertEquals(10, e1.getValue());
        assertEquals("X=10", e1.toString());
        assertEquals(e1, e1);

        HashTable.Entry<String, Integer> e2 = new HashTable.Entry<>("X", 10);
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());

        HashTable.Entry<String, Integer> e3 = new HashTable.Entry<>("Y", 20);
        assertNotEquals(e1, e3);
        assertNotEquals(e1, null);
    }

    @Test
    void testMain() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
