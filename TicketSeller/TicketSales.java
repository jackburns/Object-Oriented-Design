import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/** Represents the Cinema
 * 
 * @author Jack Burns
 * @version %I% %G% */
public class TicketSales {
    /** Theater Array */
    ArrayList<Theater> theaters = new ArrayList<Theater>();
    /** Movie Array */
    ArrayList<Movie> movies = new ArrayList<Movie>();
    /** Prices Array */
    ArrayList<Price> prices = new ArrayList<Price>();
    /** Sales Array */
    ArrayList<String> sales = new ArrayList<String>();
    /** Log Array */
    ArrayList<String> log = new ArrayList<String>();
    /** Manager log Array */
    String manager = "";
    /** Number of report requests */
    int reportRequests = 0;

    /** Number of manager report requests */

    /** Initializes the Cinema
     * 
     * @param filename String */
    void initCinema(String filename) {
        try {
            String line;
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                while (!(line = br.readLine()).equals("Theaters")) {
                    if (line.length() == 6) {
                        continue;
                    }

                    String[] tokens = line.split(":");
                    String name = tokens[0];
                    int length = Integer.parseInt(tokens[1]);
                    this.movies.add(new Movie(name, length));

                }
                while (!(line = br.readLine()).equals("Shows")) {
                    try {
                        String[] tokens = line.split(":");
                        String name = tokens[0];
                        int capacity = Integer.parseInt(tokens[1]);
                        this.addTheater(name, capacity);
                    }
                    catch (IllegalArgumentException e) {
                        log.add("Could not add Theater to "
                                + "Cinema due to name conflict");
                    }
                }
                while (!(line = br.readLine()).equals("Prices")) {
                    try {
                        String[] tokens = line.split(",");
                        int movie = Integer.parseInt(tokens[0]);
                        int theater = Integer.parseInt(tokens[1]);
                        int time = Integer.parseInt(tokens[2]);
                        this.theaters.get(theater - 1).addShow(movie,
                                theater, time,
                                movies.get(movie - 1).length);
                    }
                    catch (IllegalArgumentException e) {
                        log.add("Could not add Show to +"
                                + "Theater due to time conflict");
                    }
                }
                while (!(line = br.readLine()).equals("End")) {

                    String[] tokens = line.split(":");
                    String cat = tokens[0];
                    int price = Integer.parseInt(tokens[1]);
                    this.addPrices(cat, price);
                }
            }

            for (int i = 0; i < theaters.size(); i++) {
                theaters.get(i).initPrices(prices.size());
            }
            br.close();
        }

        catch (IOException e) {
            System.err.println("Filename: " + filename + " was not found");
        }
    }

    /** Adds a theater - Checks for similarly named theaters
     * 
     * @param name String
     * @param capacity int */
    void addTheater(String name, int capacity) {
        for (int i = 0; i < theaters.size(); i++) {
            if (theaters.get(i).name.equals(name)) {
                throw new IllegalArgumentException(
                        "Name of Theater already taken");
            }
        }
        theaters.add(new Theater(name, capacity));
    }

    /** Makes a new price - Overrides old price if new price found
     * 
     * @param cat String
     * @param price int */
    void addPrices(String cat, int price) {
        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i).cat == cat) {
                    prices.get(i).amount = price;
            }
        }
        prices.add(new Price(cat, price));
    }

    /** Process an orders file
     * 
     * @param filename String */
    void processOrders(String filename) {
        try {
            String line;
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if ((line.length() == 0)) {
                    continue;
                }
                if (line.equals("report")) {
                    reportRequests += 1;

                    System.out.println("Report " + reportRequests);
                    for (int i = 0; i < theaters.size(); i++) {
                        theaters.get(i).output();

                    }
                    manager += managerHelp();

                }
                else {
                    String[] tokens = line.split(",");
                    int movie = Integer.parseInt(tokens[0]);
                    int theater = Integer.parseInt(tokens[1]);
                    int time = Integer.parseInt(tokens[2]);

                    Theater t = theaters.get(theater - 1);
                    Show s = t.getShow(movie, time);

                    String listSales = "";
                    for (int i = 3; i < tokens.length; i++) {
                        listSales += tokens[i] + ",";
                    }
                    int newSales = 0;
                    for (int i = 0; i < prices.size(); i++) {
                        newSales += Integer.parseInt(tokens[i + 3]);

                    }

                    // Check to make sure sales don't exceed Theater capacity
                    if ((s.totalSales() + newSales) > theaters
                            .get(theater - 1).capacity) {
                        // add to log
                        log.add("Ticket sales for Movie " + movie
                                + " exceed capacity of Theater " + theater
                                + "and were not added");
                        // add to sales log
                        sales.add(movie + "," + theater + "," + s.time
                                + "," + listSales + 0);
                    }

                    else {

                        for (int i = 0; i < prices.size(); i++) {
                            s.updateSales(i,
                                    Integer.parseInt(tokens[i + 3]));
                        }

                        // Adding to sales log
                        int revenue = 0;
                        for (int i = 0; i < prices.size(); i++) {
                            revenue += (Integer.parseInt(tokens[i + 3]) * prices
                                    .get(i).amount);
                        }

                        sales.add(movie + "," + theater + "," + s.time
                                + "," + listSales + revenue);
                    }
                }
            }
            br.close();
        }
        catch (IOException e) {
            System.err.println("Filename: " + filename + " was not found");
        }
    }

    /** Return the Sales data for this Cinema
     * 
     * @return String */
    String reportSales() {
        String out = "";
        for (int i = 0; i < sales.size(); i++) {
            out += sales.get(i) + "\n";
        }
        return out;
    }

    /** Prints manager log
     * 
     * @return String */
    String managerReport() {
        return manager;
    }

    /** Return the Manager data for this Cinema
     * 
     * @return String */
    String managerHelp() {
        ArrayList<String> temp = new ArrayList<String>();
        String out;
        for (int i = 0; i < theaters.size(); i++) {
            Theater tempTheater = theaters.get(i);

            for (int j = 0; j < tempTheater.shows.size(); j++) {
                Show tempShow = tempTheater.shows.get(j);

                out = movies.get(tempShow.movie - 1).name + ","
                        + tempTheater.name + "," + tempShow.time + ","
                        + tempTheater.capacity + ",";

                for (int k = 0; k < theaters.get(i).shows.get(j).sales
                        .size(); k++) {
                    out += theaters.get(i).shows.get(j).sales.get(k) + ",";
                }
                out = out.substring(0, out.length() - 1);
                temp.add(out);

            }
        }
        temp = sortReportByMovie(temp);
        String done = "Report " + reportRequests + "\n";
        for (int i = 0; i < temp.size(); i++) {
            done += temp.get(i);
        }
        return done;
    }

    /** Sorts the manager report in order of movie index
     * 
     * @param list ArrayList<String>
     * @return ArrayList<String */
    ArrayList<String> sortReportByMovie(ArrayList<String> list) {
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < this.movies.size(); i++) {
            String name = this.movies.get(i).name;
            for (int j = 0; j < list.size(); j++) {
                String[] line = list.get(j).split(",");
                if (name.equals(line[0])) {
                    temp.add(list.get(j) + "\n");
                }
            }
        }
        return temp;
    }

    /** Return the Log data for this Cinema
     * 
     * @return String */
    String logReport() {
        String out = "";
        for (int i = 0; i < log.size(); i++) {
            out += log.get(i) + "\n";
        }
        return out;
    }

    /** The main method
     * 
     * @param args String */
    public static void main(String[] args) {

        TicketSales prog = new TicketSales();

        // Creating Scanner instance to scan console for User input
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to TicketSales");
        System.out.println("Type help for help");
        String in = "";

        while (!(in = console.nextLine().toLowerCase()).equals("exit")) {

            if (in.equals("help")) {
                System.out
                        .println("Commands:\n initcinemafile \n initcinema \n"
                                + " processordersfile \n processorders \n exit "
                                + "\n If writing an order/initialization from "
                                + "the console make sure to add End");
            }

            if (in.equals("initcinemafile")) {
                System.out.println("Please name Cinema.txt file");
                prog.initCinema(console.next());
                System.out.println("Cinema.txt file initialized");

            }

            if (in.equals("initcinema")) {
                System.out.println("Please select name the Cinema file");
                String fileName = console.next();
                System.out.println("You may now enter your "
                        + "Cinema initialization data");

                try {
                    File file = new File(fileName);
                    PrintWriter printWriter = new PrintWriter(file);
                    while (!((in = console.nextLine()).equals("End"))) {
                        printWriter.println(in);
                    }
                    printWriter.println("End");
                    printWriter.close();

                }
                catch (IOException ex) {
                    System.out.println("Error writing to file '"
                            + fileName + "'");
                }

                prog.initCinema(fileName);
                System.out.println("Cinema initialized");

            }
            if (in.equals("processordersfile")) {
                System.out.println("Please name Orders.txt file");
                prog.processOrders(console.next());
                System.out.println("Orders.txt file initialized");
            }

            if (in.equals("processorders")) {
                System.out.println("Please select name the Orders file");
                String fileName = console.next();
                System.out.println("You may now enter your Orders data");
                try {
                    File file = new File(fileName);
                    PrintWriter printWriter = new PrintWriter(file);
                    while (!((in = console.nextLine()).equals("End"))) {
                        printWriter.println(in);
                    }
                    printWriter.close();
                }
                catch (IOException ex) {
                    System.out.println("Error writing to file '"
                            + fileName + "'");
                }

                prog.processOrders(fileName);
                System.out.println("Orders processed");

            }

            if (in.equals("report")) {
                prog.reportRequests += 1;
                for (int i = 0; i < prog.theaters.size(); i++) {
                    prog.theaters.get(i).output();
                }
            }
        }
    }

}
