import tester.*;

/** Tests
 * 
 * @author Jack Burns
 * @version %I% %G% */
public class ExamplesTicketSales {
    /** new cinema system */
    TicketSales prog;
    /** sales data */
    String sales;
    /** manager data */
    String manager;
    /** system log */
    String log;

    /** Initializes the tests variables */
    public void initTests() {
        prog = new TicketSales();
        prog.initCinema("cinema.txt");
        prog.processOrders("orders.txt");
        sales = prog.reportSales();
        manager = prog.managerReport();
        log = prog.logReport();
    }

    /** Tests sales reports
     * 
     * @param t is tester */
    public void testSales(Tester t) {
        initTests();
        String salesReport = "1,1,960,20,30,40,730\n"
                + "1,1,960,30,20,50,840\n" + "1,1,960,40,30,50,0\n"
                + "1,3,1020,20,10,30,510\n" + "2,2,990,20,40,20,640\n"
                + "2,2,990,10,5,10,0\n";
        t.checkExpect(salesReport.equals(sales), true);
    }

    /** Tests manager reports
     * 
     * @param t is tester */
    public void testManager(Tester t) {
        initTests();
        String managerReport = "Report 1\n"
                + "Harry Potter,A,960,300,50,50,90\n"
                + "Harry Potter,A,1080,300,0,0,0\n"
                + "Harry Potter,A,1200,300,0,0,0\n"
                + "Harry Potter,C,1020,500,0,0,0\n"
                + "Harry Potter,C,1140,500,0,0,0\n"
                + "Great Expectations,B,990,90,0,0,0\n"
                + "Great Expectations,B,1210,90,0,0,0\n" + "Report 2\n"
                + "Harry Potter,A,960,300,50,50,90\n"
                + "Harry Potter,A,1080,300,0,0,0\n"
                + "Harry Potter,A,1200,300,0,0,0\n"
                + "Harry Potter,C,1020,500,20,10,30\n"
                + "Harry Potter,C,1140,500,0,0,0\n"
                + "Great Expectations,B,990,90,20,40,20\n"
                + "Great Expectations,B,1210,90,0,0,0\n";

        t.checkExpect(managerReport.equals(manager), true);
    }

}