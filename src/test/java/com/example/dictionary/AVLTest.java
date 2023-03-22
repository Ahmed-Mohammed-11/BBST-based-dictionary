package com.example.dictionary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AVLTest {

    private Tree<String> avl;

    @BeforeEach
    public void setUp(){
        avl = new AVL<String>();
    }

    @Test
    void testInsert(){
        assertTrue(avl.insert("0"));
        assertTrue(avl.insert("7"));
        assertTrue(avl.insert("5"));
    }
    
    @Test
    void testInsertDuplicate() {
        assertTrue(avl.insert("5"));
        assertTrue(avl.insert("10"));
        assertTrue(avl.insert("3"));
        assertFalse(avl.insert("5")); // already inserted
    }

    @Test
    void testDeleteNull(){
        assertFalse(avl.delete("5")); // not inserted yet
        avl.insert("5");
        avl.insert("10");
        avl.insert("3");

        assertTrue(avl.delete("5"));
        assertFalse(avl.delete("5")); // already deleted
        assertTrue(avl.delete("10"));
        assertTrue(avl.delete("3"));
        assertFalse(avl.delete("5")); // already empty
    }

    @Test
    void testSearch(){
        avl.insert("5");
        avl.insert("10");
        avl.insert("3");
        assertTrue(avl.search("5"));
        assertTrue(avl.search("10"));
        assertTrue(avl.search("3"));
        assertFalse(avl.search("4"));
    }

    @Test
    void testHeight() {
        avl.insert("5");
        assertEquals(0, avl.height());
        avl.insert("2");
        assertEquals(1, avl.height());
        avl.insert("1");
        assertEquals(1, avl.height());
        avl.insert("4");
        assertEquals(2, avl.height());
        avl.insert("6");
        assertEquals(2, avl.height());
        avl.insert("7");
        assertEquals(2, avl.height());
        avl.insert("9");
        assertEquals(2, avl.height());
        avl.insert("15");
        assertEquals(3, avl.height());
    }

    @Test
    void testSize() {
        avl.insert("5");
        avl.insert("2");
        avl.insert("1");
        avl.insert("4");
        avl.insert("6");
        avl.insert("7");
        assertEquals(6, avl.size());
        avl.delete("4");
        avl.delete("7");
        assertEquals(4, avl.size());
    }

    @Test
    void testBalancing(){
        avl.insert("10");
        avl.insert("20");
        avl.insert("30");
        avl.insert("40");
        avl.insert("50");
        avl.delete("10");
        avl.delete("20");
        avl.delete("30");
        assertEquals(1, avl.height());
        avl.delete("40");
        assertEquals(0, avl.height());
        avl.delete("50");
        assertEquals(-1, avl.height());
    }

    @Test
    void testWorstCase(){
        avl.insert("5");
        avl.insert("4");
        avl.insert("3");
        avl.insert("2");
        avl.insert("1");
        assertEquals(2, avl.height());
        assertEquals(5, avl.size());
    }

    @Test
    void testLeftLeftCase(){
        avl.insert("5");
        avl.insert("4");
        avl.insert("3");
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
    }

    @Test
    void testRightRightCase(){
        avl.insert("5");
        avl.insert("6");
        avl.insert("7");
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
    }

    @Test
    void testLeftRightCase(){
        avl.insert("5");
        avl.insert("3");
        avl.insert("4");
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
    }

    @Test
    void testRightLeftCase(){
        avl.insert("5");
        avl.insert("7");
        avl.insert("6");
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
    }

    @Test
    void testMillionNodeInsertionThenDeletion(){
        for(int i = 0; i < 1000000; i++){
            avl.insert(Integer.toString(i));
        }
        assertEquals(1000000, avl.size());
        assertEquals(22, avl.height());

        for(int i = 0; i < 1000000; i++){
            avl.delete(Integer.toString(i));
        }
        assertEquals(0, avl.size());
        assertEquals(-1, avl.height());
    }

    @Test
    void testDeleteRootNode() {
        avl.insert("5");
        assertEquals(0, avl.height());
        assertEquals(1, avl.size());
        avl.delete("5");
        assertEquals(-1, avl.height());
        assertEquals(0, avl.size());
    }

    @Test
    void testDeleteNodeWithOneChild() {
        avl.insert("5");
        avl.insert("3");
        avl.insert("6");
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
        avl.delete("3");
        assertEquals(1, avl.height());
        assertEquals(2, avl.size());
        assertTrue(avl.search("5"));
        assertTrue(avl.search("6"));
        assertFalse(avl.search("3"));
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        avl.insert("5");
        avl.insert("3");
        avl.insert("6");
        avl.insert("2");
        avl.insert("4");
        avl.insert("7");
        assertEquals(2, avl.height());
        assertEquals(6, avl.size());
        avl.delete("3");
        assertEquals(2, avl.height());
        assertEquals(5, avl.size());
        assertTrue(avl.search("5"));
        assertTrue(avl.search("2"));
        assertTrue(avl.search("4"));
        assertTrue(avl.search("6"));
        assertTrue(avl.search("7"));
        assertFalse(avl.search("3"));
    }

    @Test
    void testDeleteNonExistentNode() {
        avl.insert("5");
        avl.insert("3");
        avl.insert("6");
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
        assertFalse(avl.delete("4"));
        assertEquals(1, avl.height());
        assertEquals(3, avl.size());
        assertTrue(avl.search("5"));
        assertTrue(avl.search("3"));
        assertTrue(avl.search("6"));
        assertFalse(avl.search("4"));
    }

    @Test
    void testEmptyTree() {
        assertEquals(0, avl.size());
        assertEquals(-1, avl.height());
        assertFalse(avl.search("5"));
        assertFalse(avl.delete("5"));
    }

    @Test
    void testInsertAndDeleteSameNode() {
        assertTrue(avl.insert("5"));
        assertFalse(avl.insert("5")); // Already inserted
        assertTrue(avl.delete("5"));
        assertFalse(avl.delete("5")); // Already deleted
        assertTrue(avl.insert("5"));
        assertTrue(avl.delete("5"));
        assertFalse(avl.delete("5")); // Already deleted
    }

    @Test
    void testInsertAndDeleteRandomOrder() {
        avl.insert("5");
        avl.insert("3");
        avl.insert("6");
        avl.insert("2");
        avl.insert("4");
        avl.insert("7");
        assertEquals(2, avl.height());
        assertEquals(6, avl.size());
        assertTrue(avl.search("5"));
        assertTrue(avl.search("3"));
        assertTrue(avl.search("6"));
        assertTrue(avl.search("2"));
        assertTrue(avl.search("4"));
        assertTrue(avl.search("7"));

        avl.delete("5");
        avl.delete("3");
        avl.delete("2");
        avl.delete("7");
        avl.delete("4");
        avl.delete("6");
        assertEquals(-1, avl.height());
        assertEquals(0, avl.size());
    }

    @Test
    void testInsertAndDeleteLongStrings() {
        assertTrue(avl.insert("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        assertTrue(avl.search("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        assertTrue(avl.delete("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        assertFalse(avl.search("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
    }   
    
}
