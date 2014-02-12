package rbtree;

import java.util.Comparator;

/** Comparator to compare strings lexiographically Utilizes compareTo
 * 
 * @author Jack Burns
 * @version %I% %G% */
class StringByLex implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}