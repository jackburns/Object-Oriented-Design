package rbtree;

import java.util.Comparator;

/** Represents an Empty BTree
 * 
 * @author Jack Burns
 * @version %I% %G% */
class Empty extends BTree {

    /** Constructor for Empty BTree
     * 
     * @param comp Comparator */
    Empty(Comparator<String> comp) {
        this.red = false;
        this.tree = this;
        this.comp = comp;
    }

    @Override
    BTree add(String s0) {
        // return node(k,v,this,this,this.color,this.c);
        return new Node(s0, this, this, true, this.comp);
    }

    @Override
    boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(String s) {
        return false;
    }

    @Override
    String getS() {
        throw new RuntimeException("No String in empty Node");
    }

    @Override
    BTree getLeft() {
        return this;
    }

    @Override
    BTree getRight() {
        return this;
    }
    
    public boolean repOK() {
        BTree oktree = new Empty(comp);
        return (oktree.isEmpty());
    }

    /** Is this BTree equal to given BTree
     * 
     * @param o Object to compare
     * @return boolean indicating that the objects are equal */
    public boolean equals(Object o) {
        return (o instanceof BTree);
    }

    /** Computes the hashcode of this BTree
     * 
     * @return int */
    public int hashCode() {
        return 0;
    }

    @Override
    BTree balance() {
        return this;
    }

    @Override
    boolean repOk() {
        return false;
    }

    @Override
    boolean isRed() {
        return red;
    }

    @Override
    BTree balanceit() {
        return this;
    }

    @Override
    BTree makeBlack() {
        return this;
    }
}