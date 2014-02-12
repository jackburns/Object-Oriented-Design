/** Represents a Price
 * 
 * @author Jack Burns
 * @version %I% %G% */
public class Price {
    /** price category */
    String cat;
    /** price */
    int amount;

    /** Price constructor
     * 
     * @param cat String
     * @param amount int */
    Price(String cat, int amount) {
        this.cat = cat;
        this.amount = amount;
    }

}
