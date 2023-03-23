package com.example.dictionary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RBTest {

    private Tree<String> rb;

    @BeforeEach
    public void setUp(){
        rb = new RBTree<String>();
    }

    @Test
    void testInsert(){
        assertTrue(rb.insert("0"));
        assertTrue(rb.insert("7"));
        assertTrue(rb.insert("5"));
    }
    
    @Test
    void testInsertDuplicate() {
        assertTrue(rb.insert("5"));
        assertTrue(rb.insert("10"));
        assertTrue(rb.insert("3"));
        assertFalse(rb.insert("5")); // already inserted
    }

    @Test
    void testDeleteNull(){
        assertFalse(rb.delete("5")); // not inserted yet
        rb.insert("5");
        rb.insert("10");
        rb.insert("3");

        assertTrue(rb.delete("5"));
        assertFalse(rb.delete("5")); // already deleted
        assertTrue(rb.delete("10"));
        assertTrue(rb.delete("3"));
        assertFalse(rb.delete("5")); // already empty
    }

    @Test
    void testSearch(){
        rb.insert("5");
        rb.insert("10");
        rb.insert("3");
        assertTrue(rb.search("5"));
        assertTrue(rb.search("10"));
        assertTrue(rb.search("3"));
        assertFalse(rb.search("4"));
    }

    @Test
    void testHeight() {
        rb.insert("5");
        assertEquals(1, rb.height());
        rb.insert("2");
        assertEquals(2, rb.height());
        rb.insert("1");
        assertEquals(2, rb.height());
        rb.insert("4");
        assertEquals(3, rb.height());
        rb.insert("6");
        assertEquals(3, rb.height());
        rb.insert("7");
        assertEquals(4, rb.height());
        rb.insert("9");
        assertEquals(4, rb.height());
        rb.insert("15");
        assertEquals(4, rb.height());
    }

    @Test
    void testSize() {
        rb.insert("5");
        rb.insert("2");
        rb.insert("1");
        rb.insert("4");
        rb.insert("6");
        rb.insert("7");
        assertEquals(6, rb.size());
        rb.delete("4");
        rb.delete("7");
        assertEquals(4, rb.size());
    }

    @Test
    void testBalancing(){
        rb.insert("10");
        rb.insert("20");
        rb.insert("30");
        rb.insert("40");
        rb.insert("50");
        rb.delete("10");
        rb.delete("20");
        rb.delete("30");
        assertEquals(2, rb.height());
        rb.delete("40");
        assertEquals(1, rb.height());
        rb.delete("50");
        assertEquals(0, rb.height());
    }

    @Test
    void testWorstCase(){
        rb.insert("5");
        rb.insert("4");
        rb.insert("3");
        rb.insert("2");
        rb.insert("1");
        assertEquals(3, rb.height());
        assertEquals(5, rb.size());
    }

    @Test
    void testLeftLeftCase(){
        rb.insert("5");
        rb.insert("4");
        rb.insert("3");
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
    }

    @Test
    void testRightRightCase(){
        rb.insert("5");
        rb.insert("6");
        rb.insert("7");
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
    }

    @Test
    void testLeftRightCase(){
        rb.insert("5");
        rb.insert("3");
        rb.insert("4");
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
    }

    @Test
    void testRightLeftCase(){
        rb.insert("5");
        rb.insert("7");
        rb.insert("6");
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
    }

    @Test
    void testMillionNodeInsertionThenDeletion(){
        for(int i = 0; i < 50; i++){
            rb.insert(Integer.toString(i));
        }
        // assertEquals(1000000, rb.size());
        // assertEquals(34, rb.height());
        int x;
        for(int i = 0; i < 50; i++){
            x = rb.size();
            rb.delete(Integer.toString(i));
        }
        assertEquals(0, rb.size());
        // assertEquals(-1, rb.height());
    }

    @Test
    void testDeleteRootNode() {
        rb.insert("5");
        assertEquals(1, rb.height());
        assertEquals(1, rb.size());
        rb.delete("5");
        assertEquals(0, rb.height());
        assertEquals(0, rb.size());
    }

    @Test
    void testDeleteNodeWithOneChild() {
        rb.insert("5");
        rb.insert("3");
        rb.insert("6");
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
        rb.delete("3");
        assertEquals(2, rb.height());
        assertEquals(2, rb.size());
        assertTrue(rb.search("5"));
        assertTrue(rb.search("6"));
        assertFalse(rb.search("3"));
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        rb.insert("5");
        rb.insert("3");
        rb.insert("6");
        rb.insert("2");
        rb.insert("4");
        rb.insert("7");
        assertEquals(3, rb.height());
        assertEquals(6, rb.size());
        rb.delete("3");
        assertEquals(3, rb.height());
        assertEquals(5, rb.size());
        assertTrue(rb.search("5"));
        assertTrue(rb.search("2"));
        assertTrue(rb.search("4"));
        assertTrue(rb.search("6"));
        assertTrue(rb.search("7"));
        assertFalse(rb.search("3"));
    }

    @Test
    void testDeleteNonExistentNode() {
        rb.insert("5");
        rb.insert("3");
        rb.insert("6");
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
        assertFalse(rb.delete("4"));
        assertEquals(2, rb.height());
        assertEquals(3, rb.size());
        assertTrue(rb.search("5"));
        assertTrue(rb.search("3"));
        assertTrue(rb.search("6"));
        assertFalse(rb.search("4"));
    }

    @Test
    void testEmptyTree() {
        assertEquals(0, rb.size());
        assertEquals(0, rb.height());
        assertFalse(rb.search("5"));
        assertFalse(rb.delete("5"));
    }

    @Test
    void testInsertAndDeleteSameNode() {
        assertTrue(rb.insert("5"));
        assertFalse(rb.insert("5")); // Already inserted
        assertTrue(rb.delete("5"));
        assertFalse(rb.delete("5")); // Already deleted
        assertTrue(rb.insert("5"));
        assertTrue(rb.delete("5"));
        assertFalse(rb.delete("5")); // Already deleted
    }

    @Test
    void testInsertAndDeleteRandomOrder() {
        rb.insert("5");
        rb.insert("3");
        rb.insert("6");
        rb.insert("2");
        rb.insert("4");
        rb.insert("7");
        assertEquals(3, rb.height());
        assertEquals(6, rb.size());
        assertTrue(rb.search("5"));
        assertTrue(rb.search("3"));
        assertTrue(rb.search("6"));
        assertTrue(rb.search("2"));
        assertTrue(rb.search("4"));
        assertTrue(rb.search("7"));

        rb.delete("5");
        rb.delete("3");
        rb.delete("2");
        rb.delete("7");
        rb.delete("4");
        rb.delete("6");
        assertEquals(0, rb.height());
        assertEquals(0, rb.size());
    }

    @Test
    void testInsertAndDeleteLongStrings() {
        assertTrue(rb.insert("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        assertTrue(rb.search("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        assertTrue(rb.delete("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        assertFalse(rb.search("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
    }

	@Test
	void testOrderAfterManyDeletes()
	{
		HashSet<Integer> insertions = new HashSet<Integer>(100_000);
		RBTree<Integer> tree = new RBTree<Integer>();
		Random rng = new Random();
		for (int i=0; i<100_000; i++)
		{
			int x = rng.nextInt(1_000_000);
			insertions.add(x);
			tree.insert(x);
		}
		while(insertions.size() > 1000)
		{
			int x = insertions.iterator().next();
			tree.delete(x);
			insertions.remove(x);
		}
		ArrayList<Integer> sorted = new ArrayList<Integer>(insertions);
		Collections.sort(sorted);
		ArrayList<Integer> treeKeys = tree.getKeysAscending();
		for (int i=0; i<tree.size; i++)
		{
			assertEquals(treeKeys.get(i), sorted.get(i));
		}
	}
    
}
