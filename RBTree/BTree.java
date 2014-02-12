import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Jack Burns
 * burns.ja@husky.neu.edu
 * 
 */

/**
 * 
 * BTree is a mutable data type whose values represent String data items,
 * organized as a binary search tree, with the ordering specified by the
 * provided Comparator<String>. The BTree class implements the Iterable<String>
 * interface by providing an Iterator<String> that generates the individual
 * elements of the BTree in the order specified by the provided
 * Comparator<String>.
 * 
 * @author Jack Burns
 * @version %I% %G%
 * 
 */
public class BTree implements Iterable<String> {
    /**
     * left node of BTree
     */
    private BTree left;

    /**
     * right node of BTree
     */
    private BTree right;

    /**
     * Comparator<String> field
     */
    private Comparator<String> comp;

    /**
     * Data of node
     */
    private String data;

    /**
     * Iterator active field
     */
    private int active = 0;

    /**
     * Is the BTree empty?
     */
    private boolean empty;

    /**
     * Consturctor for BTree
     * 
     * @param comp Comparator
     */
    BTree(Comparator<String> comp) {
        this.comp = comp;
        this.empty = true;
    }

    /**
     * Factory method to generate an empty binary search tree with the given
     * <code>Comparator</code>
     * 
     * @param comp the given <code>Comparator</code>
     * @return new empty binary search tree that uses the given
     *         <code>Comparator</code> for ordering
     */
    public static BTree binTree(Comparator<String> comp) {
        return new BTree(comp);
    }

    /**
     * Modifies: this binary search tree by inserting the <code>String</code>s
     * from the given <code>Iterable</code> collection The tree will not have
     * any duplicates - if an item to be added equals an item that is already in
     * the tree, it will not be added.
     * 
     * @param iter the given <code>Iterable</code> collection
     */
    public void build(Iterable<String> iter) {

        if (active != 0) {
            throw new ConcurrentModificationException(
                    "An iterator is trasversing");
        }

        for (String s : iter) {
            add(s);
        }
    }

    /**
     * Modifies: this binary search tree by inserting the first numStrings
     * <code>String</code>s from the given <code>Iterable</code> collection The
     * tree will not have any duplicates - if an item to be added equals an item
     * that is already in the tree, it will not be added.
     * 
     * @param iter the given <code>Iterable</code> collection
     * @param numStrings number of <code>String</code>s to iterate through and
     *            add to BTree If numStrings is negative or larger than the
     *            number of <code>String</code>s in iter then all
     *            <code>String</code>s in iter should be inserted into the tree
     */
    public void build(Iterable<String> iter, int numStrings) {
        Iterator<String> iterator = iter.iterator();
        if (active != 0) {
            throw new ConcurrentModificationException(
                    "An iterator is trasversing");
        }

        for (int i = 0; i < numStrings; i++) {
            this.add(iterator.next());
        }
    }

    /**
     * Effect: Checks to see if this BTree contains s
     * 
     * @param s <code>String</code> to look for in this
     * @return whether this contains s
     */
    public boolean contains(String s) {
        Iterator<String> iter = this.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add an element to a BTree
     * 
     * @param s String
     */
    public void add(String s) {
        if (empty) {
            left = new BTree(comp);
            right = new BTree(comp);
            data = s;
            empty = false;
        }
        if (this.comp.compare(s, data) > 0) {
            right.add(s);
        }
        else {
            if (this.comp.compare(s, data) < 0) {
                left.add(s);
            }
        }
    }

    /**
     * Effect: Produces false if o is not an instance of BTree. Produces true if
     * this tree and the given BTree contain the same <code>String</code>s and
     * are ordered by the same <code>Comparator</code>. So if the first tree was
     * built with Strings "hello" "bye" and "aloha" ordered lexicographically,
     * and the second tree was built with <code>String</code>s "aloha" "hello"
     * and "bye" and ordered lexicographically, the result would be true.
     * 
     * @param o the object to compare with this
     * @return boolean
     */
    public boolean equals(Object o) {
        if (o.equals(null)) {
            return false;
        }
        if (!(o instanceof BTree)) {
            return false;
        }
        else {
            BTree obtree = (BTree) o;
            return this.toString().equals(obtree.toString())
                    && this.comp.equals(obtree.comp);
        }
    }

    /**
     * Effect: Produces an integer that is compatible with the implemented
     * equals method and is likely to be different for objects that are not
     * equal.
     * 
     * @return int
     */
    public int hashCode() {
        if (empty) {
            return 0;
        }
        else {
            return this.left.hashCode() + this.data.hashCode()
                    + this.right.hashCode();
        }
    }

    /**
     * Creates a new BTreeIterator
     * 
     * @return Iterator<String>
     */
    public Iterator<String> iterator() {
        return new BTreeIterator(this, this.size());
    }

    /**
     * Effect: Produces a <code>String</code> that consists of all
     * <code>String</code>s in this tree separated by comma and a space,
     * generated in the order defined by this tree's <code>Comparator</code>. So
     * for a tree with <code>Strings</code> "hello" "bye" and "aloha" ordered
     * lexicographically, the result would be "aloha, bye, hello"
     * 
     * @return String
     */
    public String toString() {
        if (empty && this.right.empty && this.left.empty) {
            return "";
        }
        if (this.right.empty && this.left.empty) {
            return this.data;
        }
        if (!empty && this.right.empty) {
            return this.left.toString() + ", " + this.data;
        }
        if (!empty && this.left.empty) {
            return this.data + ", " + this.right.toString();
        }
        else {
            return this.left.toString() + ", " + this.data + ", "
                    + this.right.toString();
        }
    }

    /**
     * Augments the abstraction function by providing a way to test the
     * properties of the implementation
     * 
     * @return boolean
     */
    public boolean repOK() {
        if (this.left == null || this.right == null) {
            return false;
        }
        else if (this.left != null) {
            return (this.comp.compare(left.data, this.data) < 0);
        }
        else {
            return (this.comp.compare(right.data, this.data) > 0);
        }
    }

    /**
     * 
     * @return int
     */
    private int size() {
        if (empty) {
            return 0;
        }
        else {
            return left.size() + 1 + right.size();
        }
    }

    /**
     * Subclass BTree Iterator
     * 
     * @author Jack Burns
     * @version %I% %G%
     */
    private class BTreeIterator implements Iterator<String> {

        /**
         * number left to iterate
         */
        private int current;
        /**
         * sub-iterator
         */
        private BTreeIterator iter;
        /**
         * current node
         */
        private BTree btree;

        /**
         * constructor for BTreeIterator
         * 
         * @param btree BTree
         * @param x int
         */
        BTreeIterator(BTree btree, int x) {
            current = x;
            this.btree = btree;
            if (this.current > 0) {
                active = active + 1;
                iter = new BTreeIterator(btree.left, btree.left.size());
            }
        }

        /**
         * Is there another element left to iterate?
         * 
         * @return boolean
         */
        public boolean hasNext() {
            return current > 0;
        }

        /**
         * 
         * Produce the next element and decrease current Throw
         * NoSuchElementException if there is nothing left to iterate
         * 
         * @return String the next element
         */
        public String next() throws NoSuchElementException {
            if (!hasNext()) {
                active = active - 1;
                throw new NoSuchElementException();
            }

            current--;

            try {
                return iter.next();
            }
            catch (NoSuchElementException e) {
                iter = new BTreeIterator(btree.right, current);
                return btree.data;
            }
        }

        /**
         * Not supported iteration method
         */
        public void remove() {
            throw new UnsupportedOperationException(
                    "Operation 'remove' is not supported");
        }
    }
}