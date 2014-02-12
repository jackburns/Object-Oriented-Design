import java.util.ArrayList;

/** Represents a show
 * 
 * @author Jack Burns
 * @version %I% %G% */
public class Show {
    /** movie index */
    int movie;
    /** theater index */
    int theater;
    /** show time */
    int time;
    /** movie length */
    int length;

    /** Sales array */
    ArrayList<Integer> sales = new ArrayList<Integer>();

    /** Show Constructor
     * 
     * @param movie int
     * @param theater int
     * @param time int
     * @param length int */
    Show(int movie, int theater, int time, int length) {
        this.movie = movie;
        this.theater = theater;
        this.time = time;
        this.length = length;
    }

    /** Update sales for this show for 1 price
     * 
     * @param position int
     * @param sale int */
    void updateSales(int position, int sale) {
        sales.set(position, sales.get(position) + sale);
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < sales.size(); i++) {
            out += sales.get(i) + ",";
        }
        return movie + "," + theater + "," + time + "," + out;
    }

    /** Calculates totalsales for this show
     * 
     * @return int */
    int totalSales() {
        int total = 0;
        for (int i = 0; i < sales.size(); i++) {
            total += sales.get(i);
        }
        return total;
    }

    /** initialize sales array
     * 
     * @param numPrices int */
    void initSales(int numPrices) {
        for (int i = 0; i < numPrices; i++) {
            sales.add(0);
        }

    }
}