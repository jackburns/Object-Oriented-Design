package rbtree;

import java.util.Comparator;

/** Comparator to compare strings by length
 * 
 * @author Jack Burns
 * @version %I% %G% */
class StringByLength implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
}