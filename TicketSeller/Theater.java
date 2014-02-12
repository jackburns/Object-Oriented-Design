import java.util.ArrayList;

/** Represents a Theater
 * 
 * @author Jack Burns
 * @version %I% %G% */
public class Theater {
    /** name of Theater */
    public String name;
    /** Capacity of Theater */
    int capacity;
    /** Shows in Theater */
    ArrayList<Show> shows;

    /** Constructor for theater
     * 
     * @param name String
     * @param capacity int */
    Theater(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.shows = new ArrayList<Show>();
    }

    /** add a show
     * 
     * @param movie int
     * @param theater int
     * @param time int
     * @param length int */
    void addShow(int movie, int theater, int time, int length) {
        for (int i = 0; i < shows.size(); i++) {
            Show s = shows.get(i);
            // Checks to make sure no shows overlap allowing for 5 minutes
            // inbetween shows
            if (s.time > time && (time + length) > s.time) {
                throw new IllegalArgumentException("Movie: " + s.movie
                        + "At: " + s.time + "overlaps with the given show");
            }
            if (time > s.time && (s.time + s.length) > time) {
                throw new IllegalArgumentException("Movie: " + s.movie
                        + "At: " + s.time + "overlaps with the given show");
            }
        }
        shows.add(new Show(movie, theater, time, length));
    }

    /** initialize prices array
     * 
     * @param numPrices int */
    void initPrices(int numPrices) {
        for (int i = 0; i < shows.size(); i++) {
            shows.get(i).initSales(numPrices);
        }
    }

    /** output this Theater for reporting */
    void output() {
        for (int i = 0; i < shows.size(); i++) {
            System.out.println(shows.get(i).toString()
                    + (capacity - shows.get(i).totalSales()));
        }
    }

    /** Get a show in this Theater
     * 
     * @param movie int
     * @param time int
     * @return Show */
    Show getShow(int movie, int time) {
        for (int i = 0; i < shows.size(); i++) {
            Show s = shows.get(i);
            if (s.movie == movie && s.time == time) {
                return s;
            }
        }
        throw new IllegalArgumentException("Show not found");
    }

}
