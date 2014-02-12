package rbtree;

import java.util.ArrayList;
import java.util.Comparator;

/** Represents a Node of a BTree
 * 
 * @author Jack Burns
 * @version %I% %G% */
class Node extends BTree {

    /** Left of a BTree */
    private BTree left;
    /** Right of a BTree */
    private BTree right;
    /** The (String) data of a BTree node */
    private String s;

    /** Constructor for Node
     * 
     * @param s String
     * @param left BTree
     * @param right BTree
     * @param red boolean
     * @param comp Comparator<String> */
    Node(String s, BTree left, BTree right, boolean red,
            Comparator<String> comp) {
        this.s = s;
        this.left = left;
        this.right = right;
        this.red = true;
        this.tree = this;
        this.comp = comp;

    }

    /** Inserts a new Node into a BTree Duplicates will not be added
     * 
     * @param s0 String
     * @return BTree */
    BTree add(String s0) {
        BTree n;
        if (comp.compare(s0, s) < 0) {
            n = new Node(s, left.add(s0), right, red, comp);
            return n;
        }
        if (comp.compare(s0, s) > 0) {
            n = new Node(s, left, right.add(s0), red, comp);
            return n;
        }
        // n = new Node( s, left, right, black, comp);
        // return balance(n);
        // return n;
        return new Node(s, left, right, red, comp);
    }

    protected BTree balance() {
        BTree temp = new Node(s, left.balance(), right.balance(), red,
                comp);
        if (temp.getLeft().getLeft().isRed()
                || temp.getLeft().getRight().isRed()
                || temp.getRight().getLeft().isRed()
                || temp.getRight().getRight().isRed()) {
            return temp.balanceit();
        }
        else {
            return temp;
        }

    }

    /** The method to balance the tree and return the new tree
     * 
     * @return BTree - a balanced tree */
    protected BTree balanceit() {
        if (!this.left.isEmpty()) {
            if ((this.left.isRed()) && (this.left.getLeft().isRed())) {
                return new Node(left.getS(), left.getLeft().makeBlack(),
                        new Node(s, left.getRight(), this.right, false,
                                comp), true, comp);

            }
            else if ((left.isRed()) && (left.getRight().isRed())) {
                return new Node(this.left.getRight().getS(), new Node(
                        left.getS(), left.getLeft(), left.getRight()
                                .getLeft(), false, this.comp), new Node(s,
                        this.left.getRight().getRight(), this.right,
                        false, comp), true, comp);
            }
        }
        else if (!this.right.isEmpty()) {
            if ((this.right.isRed()) && (this.right.getLeft().isRed())) {
                return new Node(this.right.getLeft().getS(),
                        new Node(s, left, right.getLeft().getLeft(),
                                false, this.comp), new Node(right.getS(),
                                right.getLeft().getRight(),
                                right.getRight(), false, comp), true, comp);

            }
            else if ((this.right.isRed())
                    && (this.right.getRight().isRed())) {
                return new Node(right.getS(), new Node(s, left,
                        right.getLeft(), false, comp), right.getRight()
                        .makeBlack(), true, comp);
            }
        }
        return this;
    }

    // makes the balanced tree out of the parts of the
    // imbalanced trees (helper method)
    BTree makeBalanced(BTree x, BTree y, BTree z, BTree a, BTree b,
            BTree c, BTree d) {
        // Convenience
        // Left child of the tree to return
        BTree left = new Node(x.getS(), a, b, false, comp);
        // Right child of the tree to return
        BTree right = new Node(z.getS(), c, d, false, comp);
        // Construct and return the balanced tree
        return new Node(y.getS(), left, right, true, comp);
    }

    /*
    *//** Balances the given BTree to maintain inherent properties Based off
     * Okasaki specifiations
     * 
     * @param bt BTree to be balanced
     * @return BTree balanced */
    /*
     * public BTree balance() {
     * 
     * Left Red && Left.Left Red Case if (this.left.isRed() &&
     * this.left.getLeft().isRed()) {
     * 
     * return rotate(this.left.getLeft().getLeft(), this.left
     * .getLeft().getRight(), this.left.getRight(), this.right,
     * this.left.getLeft(), this.left, this); }
     * 
     * Left Red Left.Right Red Case else if (this.left.isRed() &&
     * this.left.getRight().isRed()) {
     * 
     * return rotate(this.left.getLeft(), this.left.getRight() .getLeft(),
     * this.left.getRight().getRight(), this.right, this.left,
     * this.left.getRight(), this); }
     * 
     * Right Red && Right.Left Red Case else if (this.right.isRed() &&
     * this.right.getLeft().isRed()) {
     * 
     * return rotate(this.left, this.right.getLeft().getLeft(),
     * this.right.getLeft().getRight(), this.right.getRight(), this,
     * this.right.getLeft(), this.right); }
     * 
     * Right Red && Right.Right Red Case else if (this.right.isRed() &&
     * this.right.getRight().isRed()) {
     * 
     * return rotate(this.left, this.right.getLeft(), this.right
     * .getRight().getLeft(), this.right.getRight() .getRight(), this,
     * this.right, this.right.getRight()); } return this; }
     *//** Rotates the given BTree and changes color when necessary
     * 
     * @param a BTree
     * @param b BTree
     * @param c BTree
     * @param d BTree
     * @param x BTree
     * @param y BTree
     * @param z BTree
     * @return BTree rotated */
    /*
     * private BTree rotate(BTree a, BTree b, BTree c, BTree d, BTree x, BTree
     * y, BTree z) {
     * 
     * return new Node(y.getS(), new Node(x.getS(), a, b, false, this.comp), new
     * Node(z.getS(), c, d, false, this.comp), true, this.comp); }
     */
    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(String s0) {
        if (comp.compare(s0, s) < 0) {
            return left.contains(s0);
        }
        if (comp.compare(s0, s) > 0) {
            return right.contains(s0);
        }
        return s0.equals(s);
    }

    @Override
    String getS() {
        return s;
    }

    @Override
    BTree getLeft() {
        return left;
    }

    @Override
    BTree getRight() {
        return right;
    }

    public int hashCode() {
        return this.s.hashCode() + this.left.hashCode()
                + this.right.hashCode();
    }

    @Override
    boolean isRed() {
        return red;
    }

    @Override
    boolean repOk() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("Alice");
        temp.add("Bob");
        temp.add("Carol");
        temp.add("Dave");
        Comparator<String> lex = new StringByLex();
        BTree oktree = binTree(lex);

        if (!(oktree.isEmpty())) {
            return false;
        }
        oktree.build(temp);

        if (!(oktree.tree.getS().equals("Bob"))) {
            return false;
        }

        else if (!(oktree.tree.getRight().getS().equals("Dave"))) {
            return false;

        }
        else if (!(oktree.tree.getLeft().getS().equals("Carol"))) {
            return false;
        }
        if (oktree.tree.isEmpty()) {
            return false;
        }
        oktree.build(temp);
        return true;
    }

    @Override
    BTree makeBlack() {
        return new Node(s, left, right, false, comp);
    }

}