package rbtree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test; 


/**
 * Test Examples class for RBTree
 * 
 * @author Jack Burns
 * @version %I% %G%
 */
public class ExamplesRBTree {
    
    /**
     * Runs the tests.
     
     

    public void main() {

        ExamplesRBTree test = new ExamplesRBTree();
        test.testMethod();
    }
    */
    
    private BTree t0;
    /** tree with 5 elements
     */
    private BTree t1;
    /** Tree with same 5 elements, entered in a different order
     */
    private BTree t2;
    /** Tree with many different elements, ordered by length
     */
    private BTree t3;
    /** Tree with many, many, many random elements
     */
    private BTree t4;
    /** Tree with ma different danger case
     */
    private BTree t5;
    
    /**
     * Method to initialize the values
     */
    public void init() {
        
        /** Comparator to compare strings by their length
         */
        Comparator<String> len = new StringByLength();
        
        /** Comparator to compare strings by their lex order
         */
        Comparator<String> lex = new StringByLex();
        
        ArrayList<String> words = new ArrayList<String>();
        words.add("eah");
        words.add("bah");
        words.add("dah");
        words.add("cah");
        words.add("ah");
      
        ArrayList<String> words2 = new ArrayList<String>();
        words2.add("eah");
        words2.add("bah");
        words2.add("dah");
        words2.add("cah");
        words2.add("ah");
        
        ArrayList<String> words3 = new ArrayList<String>();
        words3.add("a");
        words3.add("ccc");
        words3.add("ffffff");
        words3.add("ggggggg");
        words3.add("bb");
        words3.add("dddd");
        words3.add("eeeee");

        ArrayList<String> words4 = new ArrayList<String>();
        words4.add("3");
        words4.add("2");
        words4.add("1");
        
        ArrayList<String> words5 = new ArrayList<String>();
        words5.add("10");
        words5.add("11");
        words5.add("12");
      
        t0 = BTree.binTree(lex);
        t1 = BTree.binTree(lex);
        t1.build(words);
        t2 = BTree.binTree(lex);
        t2.build(words2);
        t3 = BTree.binTree(len);
        t3.build(words3);
        t4 = BTree.binTree(lex);
        t4.build(words4);
        t5 = BTree.binTree(lex);
        t5.build(words5);
    }

    /**
     * Method to test all methods in BTree class
     * 
     */

    @Test
    public void testMethod() {
        
        ExamplesRBTree test = new ExamplesRBTree();
        test.init();
        Assert.assertEquals(test.t1.tree.isRed() == (false), true);
        Assert.assertEquals(test.t1.tree.isRed() == (true), false);
        Assert.assertEquals(test.t1.tree.getLeft().getS().equals("bah"),
                true);
        Assert.assertEquals(test.t1.tree.getLeft().isRed() == false,
                true);
        Assert.assertEquals(test.t1.tree.getRight().getS().equals("eah"),
                true);
        Assert.assertEquals(test.t1.tree.getRight().getS().equals("black"),
                true);
        Assert.assertEquals(test.t1.tree.getLeft()
                .getLeft().getS().equals("ah"), true);
        Assert.assertEquals(test.t1.tree.getLeft()
                .getLeft().isRed() == true, true);
        Assert.assertEquals(test.t1.tree.getLeft()
                .getRight().getS().equals("cah"), true);
        Assert.assertEquals(test.t1.tree.getLeft()
                .getRight().isRed() == true, true);

        Assert.assertEquals(test.t0.equals(test.t1), false);
        Assert.assertEquals(test.t1.equals(test.t2), true);
        
        String hi = "hi";
        
        Assert.assertEquals(test.t1.tree.equals(hi), false);
        Assert.assertEquals(test.t1.tree.equals(null), false);
        
        Iterator<String> it;
        it = test.t1.iterator();
        Assert.assertEquals(it.next().equals("ah"), true);
        Assert.assertEquals(it.next().equals("eh"), false);
        

        Assert.assertEquals(test.t1.tree.repOk(), true);
        Assert.assertEquals(test.t0.repOk(), true);
        
        Assert.assertEquals(test.t3.tree.getS().equals("ccc"), true);
        Assert.assertEquals(test.t3.tree.getRight().getS()
                .equals("eeeee"), true);
        Assert.assertEquals(test.t1.tree.toString()
                .equals("ah, bah, cah, dah, eah"), true);
        Assert.assertEquals(test.t4.tree.getS().equals("great"), true);
        Assert.assertEquals(test.t4.tree.getLeft()
                .getS().equals("cat"), true);
        Assert.assertEquals(test.t4.tree.getLeft()
                .getS().equals("what"), false);
        
        Assert.assertTrue(test.t5.tree.getS().equals("2"));
        Assert.assertTrue(test.t1.tree.contains("bah"));
        Assert.assertFalse(test.t1.contains("bdasddsadsaah"));
        
        Assert.assertTrue(test.t0.tree.balance().equals(test.t0.tree));
        
        //Testing for Exceptions
        Throwable caught = null;
        
        try {
            it.remove();
        } 
        catch (Throwable t) {
            caught = t;
        }
        Assert.assertNotNull(caught);
        Assert.assertSame(UnsupportedOperationException.class, 
            caught.getClass());
        
        try {
            it.next();
            it.next();
            it.next();
            it.next();
            it.next();
            test.t0.tree.getLeft();
            test.t0.tree.getRight();
            test.t0.tree.getS();
        } 
        catch (Throwable t) {
            caught = t;
        }
        Assert.assertNotNull(caught);
        Assert.assertSame(NoSuchElementException.class, 
            caught.getClass());
        
        try {
            ArrayList<String> moreWords = new ArrayList<String>();
            moreWords.add("i");
            moreWords.add("hate");
            moreWords.add("tests");
            Iterator<String> it2;
            it2 = test.t1.iterator();
            it2.next();
            test.t1.build(moreWords);
        } 
        catch (Throwable t) {
            caught = t;
        }
        Assert.assertNotNull(caught);
        Assert.assertSame(ConcurrentModificationException.class, 
            caught.getClass());
    }   
}