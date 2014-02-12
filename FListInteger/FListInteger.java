/*
 * Jack Burns
 * burns.ja@husky.neu.edu
 * Lack of input from webcat about equals was annoying,
 * Took awhile before I tried combining equals in FListInteger
 */

/**
 * @author Jack Burns
 * @version %I% %G%
 */
public abstract class FListInteger {

    /**
     * Is this FListInteger empty?
     * 
     * @return boolean
     */
    abstract boolean isEmpty();

    /**
     * Get the element at location i
     * 
     * @param i An int representing the location to get the element of
     * @return Integer
     */
    abstract Integer get(int i);

    /**
     * Set element x at location i
     * 
     * @param i An int representing the location to be set at
     * @param x An Integer representing an element to be set
     * @return FListInteger
     */
    abstract FListInteger set(int i, Integer x);

    /**
     * Return the size of this FListInteger
     * 
     * @return int
     */
    abstract int size();

    /**
     * Create new instance of EmptyList class
     * 
     * @return FListInteger
     */
    static FListInteger emptyList() {
        return new EmptyList();
    }

    /**
     * Create new instance of Add class
     * 
     * @param f An FListInteger
     * @param x An Integer representing an element to be added to f
     * @return FListInteger
     */
    static FListInteger add(FListInteger f, Integer x) {
        return new Add(f, x);
    }

    /**
     * Is this FListInteger empty?
     * 
     * @param f An FListInteger
     * @return boolean
     */
    static boolean isEmpty(FListInteger f) {
        return f.isEmpty();
    }

    /**
     * Get the element at location i
     * 
     * @param f A FListInteger
     * @param i An int representing the location to get the element of
     * @return Integer
     */
    static Integer get(FListInteger f, int i) {
        return f.get(i);
    }

    /**
     * Set element x at location i
     * 
     * @param f A FListInteger
     * @param i An int representing the location to be set at
     * @param x An Integer representing an element to be set
     * @return FListInteger
     */
    static FListInteger set(FListInteger f, int i, Integer x) {
        return f.set(i, x);
    }

    /**
     * Return the size of this FListInteger
     * 
     * @param f A FListInteger
     * @return int
     */
    static int size(FListInteger f) {
        return f.size();
    }

    /**
     * Returns true if two FListInteger are the same Accounts for null
     * 
     * @param o An Object
     * @return boolean
     */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } 
        else if ((o instanceof FListInteger)
                && this.size() == ((FListInteger) o).size()) {
            for (int i = 0; i < this.size(); i++) {
                if (!(this.get(i).equals(((FListInteger) o).get(i)))) {
                    return false;
                }
            }
            return true;
        } 
        else {
            return false;
        }
    }
}

/**
 * EmptyList Class
 * 
 * @author Jack Burns
 * @version %I% %G%
 */
class EmptyList extends FListInteger {

    @Override
    boolean isEmpty() {
        return true;
    }

    @Override
    Integer get(int i) {
        throw new RuntimeException("Cannot call get on an empty FListInteger");
    }

    @Override
    FListInteger set(int i, Integer x) {
        throw new RuntimeException(
                "Cannot set an element on an empty FListInteger");
    }

    @Override
    int size() {
        return 0;
    }

    /**
     * Returns a String containing an empty Array
     * 
     * @return String
     */
    public String toString() {
        return "[]";
    }

    /**
     * Returns 0 hashCode for EmptyList
     * 
     * @return int
     */
    public int hashCode() {
        return 0;
    }
}

/**
 * @author Jack Burns
 * @version %I% %G%
 */
class Add extends FListInteger {
    /**
     * Field for FListInteger in Add
     */
    FListInteger f;
    /**
     * Field for Integer in Add
     */
    Integer e;

    /**
     * Constructor for Add
     * 
     * @param f
     *            A FListInteger
     * @param e
     *            An Integer
     */
    Add(FListInteger f, Integer e) {
        this.f = f;
        this.e = e;
    }

    @Override
    boolean isEmpty() {
        return false;
    }

    @Override
    Integer get(int i) {
        if (i == 0) {
            return this.e;
        } 
        else {
            return this.f.get(i - 1);
        }
    }

    @Override
    FListInteger set(int i, Integer x) {
        if (i == 0) {
            return new Add(this.f, x);
        } 
        else {
            return new Add(this.f.set((i - 1), x), this.e);
        }
    }

    @Override
    int size() {
        return 1 + f.size();
    }

    /**
     * Returns a String containing an empty Array
     * 
     * @return String
     */
    public String toString() {
        if (this.f.isEmpty()) {
            return "[" + e.toString() + "]";
        } 
        else {
            return "[" + e.toString() + ", "
                    + f.toString().substring(1, f.toString().length());
        }
    }

    /**
     * Returns the hashCode for a nonempty FListInteger
     * 
     * @return int
     */
    public int hashCode() {
        return e + f.hashCode();
    }
}
