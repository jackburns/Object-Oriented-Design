package rbtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/*
 * Jack Burns
 * burns.ja@husky.neu.edu
 * 
 * Whenever I submit my test files webcat refuses to 
 * grade anything so I've submitted without.
 * It's getting kindof rediculous with WebCat, 
 * instead of getting better each week it seems to be getting worse.
 * 
 * 
 */
/** @author Jack Burns
 * @version %I% %G% */
public abstract class BTree implements Iterable<String> {

    /** Boolean indicating if this BTree is red or black */
    protected boolean red;
    /** Comparator<String> field */
    protected Comparator<String> comp;
    /** > 0 if an iterator is running */
    protected int active;

    /** Stores the RBTree */
    BTree tree;

    /** Factory method to generate an empty binary search tree with the given
     * <code>Comparator</code>
     * 
     * @param comp given Comparator
     * @return new empty RBTree that uses the given Comparator for ordering */
    public static BTree binTree(Comparator<String> comp) {
        return new Empty(comp);
    }

    /** Builds an RBTree
     * 
     * @param iter Iterable<String> */
    public void build(Iterable<String> iter) {
        if (active == 0) {
            Iterator<String> it = iter.iterator();
            while (it.hasNext()) {
                String s = it.next();
                if (this.tree.equals(null)) {
                    this.tree = binTree(comp);
                }
                this.tree = this.tree.add(s);
                this.tree = this.tree.balance();
                this.tree = this.tree.makeBlack();
            }
        }
        else {
            throw new ConcurrentModificationException();
        }
    }

    /** Adds a String onto an RBTree
     * 
     * @param s String
     * @return BTree */
    abstract BTree add(String s);

    /** Returns the left of this
     * 
     * @return BTree this.left */
    abstract BTree getLeft();

    /** Returns the right of this
     * 
     * @return BTree this.right */
    abstract BTree getRight();

    /** Returns the string of this
     * 
     * @return String this.s */
    abstract String getS();

    /** Is this RBTree empty?
     * 
     * @return boolean */
    abstract boolean isEmpty();

    /**
     * Balance the RBTree
     * 
     * @return BTree
     */
    abstract BTree balance();
    
    abstract BTree balanceit();

    /**
     * Check that the representation of the RBTree is correct
     * 
     * @return boolean 
     */
    abstract boolean repOk();

    /** Is this RBTree red?
     * 
     * @return boolean */
    abstract boolean isRed();
    
    abstract BTree makeBlack();

    static boolean isEmpty(BTree bt) {
        return bt.isEmpty();
    }

    static BTree add(BTree bt, String s) {
        return bt.add(s);
    }

    static BTree getLeft(BTree bt) {
        return bt.getLeft();
    }

    static BTree getRight(BTree bt) {
        return bt.getRight();
    }

    static String getS(BTree bt) {
        return bt.getS();
    }

    static BTree balance(BTree bt) {
        return bt.balance();
    }
    
    static BTree balanceit(BTree bt){
        return bt.balanceit();
    }

    static boolean repOK(BTree bt) {
        return bt.repOk();
    }

    static boolean isRed(BTree bt) {
        return bt.isRed();
    }
    
    public static BTree makeBlack(BTree bt){
        return bt.makeBlack();
    }

    /** Creates a new String Iterator
     * 
     * @return Iterator<String> */
    public Iterator<String> iterator() {
        return new BTreeIterator(this);
    }

    /** Does this RBTree contain this String
     * 
     * @param s String
     * @return boolean */
    public boolean contains(String s) {
        return this.tree.contains(s);
    }

    /** Converts this RBTree to a String
     * 
     * @return String */
    public String toString() {
        if (isEmpty() && this.getRight().isEmpty()
                && this.getLeft().isEmpty()) {
            return "";
        }
        if (this.getRight().isEmpty() && this.getLeft().isEmpty()) {
            return this.getS();
        }
        if (!isEmpty() && this.getRight().isEmpty()) {
            return this.getLeft().toString() + ", " + this.getS();
        }
        if (!isEmpty() && this.getLeft().isEmpty()) {
            return this.getS() + ", " + this.getRight().toString();
        }
        else {
            return this.getS().toString() + ", " + this.getS() + ", "
                    + this.getRight().toString();
        }
    }

    /** Is this BTree equal to given BTree
     * 
     * @param o Object to be compared with this
     * @return boolean indicating equality */
    public boolean equals(Object o) {
        if (!(o instanceof BTree)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    /** Computes the hashcode of this BTree
     * 
     * @return int */
    public abstract int hashCode();

    static int hashCode(BTree bt) {
        return bt.hashCode();
    }

    /** BTreeIterator class
     * 
     * @author Jack Burns
     * @version %I%, %G% */
    private class BTreeIterator implements Iterator<String> {

        private ArrayList<Node> backtrack = new ArrayList<Node>();

        /** surpress "unchecked" from Iterator<String> cast */
        @SuppressWarnings("unchecked")
        BTreeIterator(BTree bt) {
            if (((Iterator<String>) bt).hasNext()) {
                Node next = (Node) bt;

                backtrack.add(next);
                active = active + 1;

                while (((Iterator<String>) next.getLeft()).hasNext()) {
                    backtrack.add((Node) next.getLeft());
                    next = (Node) next.getLeft();
                }
            }
        }

        @Override
        public boolean hasNext() {
            return this.backtrack.isEmpty();
        }

        /** surpress "unchecked" from Iterator<String> cast */
        @SuppressWarnings("unchecked")
        @Override
        public String next() {
            int last = this.backtrack.size() - 1;
            Node result = this.backtrack.remove(last);
            if (((Iterator<String>) result.getRight()).hasNext()) {

                Node node = (Node) result.getRight();
                backtrack.add(node);
                while (((Iterator<String>) node.getLeft()).hasNext()) {
                    backtrack.add((Node) node.getLeft());
                    node = (Node) node.getLeft();
                }
            }
            return this.backtrack.get(last).getS();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Unsupported Operation: remove");
        }
    }

}




