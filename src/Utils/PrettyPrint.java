package Utils;

import Abstract.IUserProfile; // import your IUserProfile interface

import Project.Project;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for formatting and printing detailed information about users and projects.
 * Provides methods to render data as ANSI-colored ASCII art cards in the console,
 * including project availability charts and user profiles.
 */
public class PrettyPrint {
    private static final int WIDTH      = 50;
    private static final DateTimeFormatter DATE_FMT  = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final int BAR_WIDTH = 20;

    // ANSI colors
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_CYAN   = "\u001B[36m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";


    /**
     * Builds a horizontal bar chart line segment of fixed BAR_WIDTH characters,
     * proportionally filled by '█' based on value/max.
     *
     * @param value current value for the bar
     * @param max   maximum scale value
     * @return a string consisting of filled and empty characters
     */
    private static String buildBar(int value, int max) {
        if (max == 0) return " ".repeat(BAR_WIDTH);
        int len = (int)Math.round(value / (double)max * BAR_WIDTH);
        return "█".repeat(len) + " ".repeat(BAR_WIDTH - len);
    }


    /**
     * Executes a block of printing code surrounded by ANSI color start and reset codes.
     *
     * @param card  a Runnable containing print operations
     * @param color ANSI color code to apply
     */
    private static void colorize(Runnable card, String color) {
        System.out.print(color);
        card.run();
        System.out.print(ANSI_RESET);
    }

    /**
     * Prints a detailed project card for a Manager, including all project fields
     * and availability bars in green.
     *
     * @param p Project instance to render
     */
    public static void printManager(Project p) {
        colorize(() -> {
            printTopBorder();
            printTitle(p.getProjectName() + " [" + p.getID() + "]");
            printSeparator();
            printField("Neighbourhood", p.getNeighbourhood());
            printField("Open Date",    p.getAppDateOpen().format(DATE_FMT));
            printField("Close Date",   p.getAppDateClose().format(DATE_FMT));
            printField("2-Room units", String.valueOf(p.getNoOfUnitsType1()));
            printField("2-Room price", "$" + (long)p.getSellPriceType1());
            printField("3-Room units", String.valueOf(p.getNoOfUnitsType2()));
            printField("3-Room price", "$" + (long)p.getSellPriceType2());
            printField("Manager",      p.getManagerID());
            printField("Officer Slots",String.valueOf(p.getNoOfficersSlots()));
            // <-- multi-line Officers here -->
            List<String> officers = p.getOfficer();
            if (officers.isEmpty()) {
                printField("Officers", "");
            } else {
                // first on the same line
                printField("Officers", officers.get(0));
                // subsequent entries indented
                for (int i = 1; i < officers.size(); i++) {
                    printField("", officers.get(i));
                }
            }
            printField("Visible",      String.valueOf(p.isVisibility()));
            printSeparator();

            // compute global max once
            int globalMax = Math.max(p.getNoOfUnitsType1(), p.getNoOfUnitsType2());

            // two colored bars
            printChartLine(p.getType1(),
                    p.getNoOfUnitsType1(),
                    p.getSellPriceType1(),
                    true,
                    ANSI_GREEN,
                    globalMax);

            printChartLine(p.getType2(),
                    p.getNoOfUnitsType2(),
                    p.getSellPriceType2(),
                    true,
                    ANSI_GREEN,
                    globalMax);

            printBottomBorder();
        }, ANSI_CYAN);
    }

    /**
     * Prints a project card for an Officer, showing key fields and
     * availability bars in yellow.
     *
     * @param p Project instance to render
     */
    public static void printOfficer(Project p) {
        colorize(() -> {
            printTopBorder();
            printTitle(p.getProjectName() + " [" + p.getID() + "]");
            printSeparator();
            printField("Neighbourhood", p.getNeighbourhood());
            printField("Open Date",    p.getAppDateOpen().format(DATE_FMT));
            printField("Close Date",   p.getAppDateClose().format(DATE_FMT));
            printField("Visible",      String.valueOf(p.isVisibility()));
            printSeparator();

            // compute global max once
            int globalMax = Math.max(p.getNoOfUnitsType1(), p.getNoOfUnitsType2());
            printChartLine(p.getType1(),
                    p.getNoOfUnitsType1(),
                    p.getSellPriceType1(),
                    true,
                    ANSI_YELLOW,
                    globalMax);
            printChartLine(p.getType2(),
                    p.getNoOfUnitsType2(),
                    p.getSellPriceType2(),
                    true,
                    ANSI_YELLOW,
                    globalMax);

            printBottomBorder();
        }, ANSI_CYAN);
    }

    /**
     * Prints a married-applicant view, showing both room types with prices,
     * and charts showing counts in green.
     *
     * @param p Project instance to render
     */
    public static void printApplicantMarried(Project p) {
        colorize(() -> {
            printTopBorder();
            printTitle(p.getProjectName() + " [" + p.getID() + "]");
            printSeparator();
            printField("Neighbourhood", p.getNeighbourhood());
            printField("Open Date",    p.getAppDateOpen().format(DATE_FMT));
            printField("Close Date",   p.getAppDateClose().format(DATE_FMT));
            printField(p.getType1(), p.getNoOfUnitsType1() + " @ $" + (long)p.getSellPriceType1());
            printField(p.getType2(), p.getNoOfUnitsType2() + " @ $" + (long)p.getSellPriceType2());
            printSeparator();

            int globalMax = Math.max(p.getNoOfUnitsType1(), p.getNoOfUnitsType2());
            // chart only counts
            printChartLine(p.getType1(), p.getNoOfUnitsType1(), 0, false, ANSI_GREEN, globalMax);
            printChartLine(p.getType2(), p.getNoOfUnitsType2(), 0, false, ANSI_GREEN, globalMax);

            printBottomBorder();
        }, ANSI_CYAN);
    }

    /**
     * Prints a single-applicant view for 2-Room only, with price and a count bar.
     *
     * @param p Project instance to render
     */
    public static void printApplicant2Room(Project p) {
        colorize(() -> {
            printTopBorder();
            printTitle(p.getProjectName() + " [" + p.getID() + "]");
            printSeparator();
            printField("Neighbourhood", p.getNeighbourhood());
            printField("Open Date",    p.getAppDateOpen().format(DATE_FMT));
            printField("Close Date",   p.getAppDateClose().format(DATE_FMT));
            printField(p.getType1(), p.getNoOfUnitsType1() + " @ $" + (long)p.getSellPriceType1());
            printSeparator();

            // only 2-Room bar
            printChartLine(p.getType1(),
                    p.getNoOfUnitsType1(),
                    0,
                    false,
                    ANSI_GREEN,
                    p.getNoOfUnitsType1()); // globalMax = this count

            printBottomBorder();
        }, ANSI_CYAN);
    }

    /**
     * Prints a single-applicant view for 3-Room only, with price and a count bar.
     *
     * @param p Project instance to render
     */
    public static void printApplicant3Room(Project p) {
        colorize(() -> {
            printTopBorder();
            printTitle(p.getProjectName() + " [" + p.getID() + "]");
            printSeparator();
            printField("Neighbourhood", p.getNeighbourhood());
            printField("Open Date",    p.getAppDateOpen().format(DATE_FMT));
            printField("Close Date",   p.getAppDateClose().format(DATE_FMT));
            printField(p.getType2(), p.getNoOfUnitsType2() + " @ $" + (long)p.getSellPriceType2());
            printSeparator();

            // only 3-Room bar
            printChartLine(p.getType2(),
                    p.getNoOfUnitsType2(),
                    0,
                    false,
                    ANSI_GREEN,
                    p.getNoOfUnitsType2());

            printBottomBorder();
        }, ANSI_CYAN);
    }


    /**
     * Prints just the availability bars for both room types,
     * scaled against each other.  If showPrice is true, it also
     * appends “(@ $price)” to each line.
     */
    public static void printAvailabilityChart(Project p, boolean showPrice) {
        // compute the maximum of the two counts
        int c1 = p.getNoOfUnitsType1();
        int c2 = p.getNoOfUnitsType2();
        int max = Math.max(c1, c2);

        // header
        System.out.println("\n--- Availability Chart for " + p.getProjectName() + " ---");

        // 2-Room line
        String suffix1 = showPrice
                ? " (" + c1 + " @ $" + (long)p.getSellPriceType1() + ")"
                : " (" + c1 + ")";
        String bar1 = buildBar(c1, max);
        System.out.println(" " + p.getType1() + ": " + bar1 + suffix1);

        // 3-Room line
        String suffix2 = showPrice
                ? " (" + c2 + " @ $" + (long)p.getSellPriceType2() + ")"
                : " (" + c2 + ")";
        String bar2 = buildBar(c2, max);
        System.out.println(" " + p.getType2() + ": " + bar2 + suffix2 + "\n");
    }

    /**
     * Prints the full profile of a user implementing IUserProfile.
     * Includes name, NRIC, age, and marital status.
     *
     * @param user the user profile to be printed
     */
    public static void prettyPrint(IUserProfile user) {
        System.out.println("\n=== " + user.getClass().getSimpleName() + " Profile ===");
        System.out.println("Name: " + user.getName());
        System.out.println("NRIC: " + user.getNric());
        System.out.println("Age: " + user.getAge());
        System.out.println("MaritalStatus: " + user.getMaritalStatus().toString());
    }

    /**
     * Prints options for updating an existing user profile.
     * Shows current values for name, age, and marital status.
     *
     * @param user the user whose update options are being printed
     */
    public static void prettyUpdate(IUserProfile user) {
        System.out.println("1. Update Name: " + user.getName());
        System.out.println("2. Update Age: " + user.getAge());
        System.out.println("3. Update Marital Status: " + user.getMaritalStatus().toString());
        System.out.println("0. Back");
    }
    // -------------------
    // Common drawing helpers
    // -------------------
    // private drawing helpers omitted for brevity...
    private static void printTopBorder() {
        System.out.println("╔" + "═".repeat(WIDTH - 2) + "╗");
    }
    private static void printSeparator() {
        System.out.println("╠" + "═".repeat(WIDTH - 2) + "╣");
    }
    private static void printBottomBorder() {
        System.out.println("╚" + "═".repeat(WIDTH - 2) + "╝\n");
    }
    private static void printTitle(String title) {
        int pad = (WIDTH - 2 - title.length()) / 2;
        int rem = WIDTH - 2 - title.length() - pad;
        System.out.println("║" +
                " ".repeat(pad) +
                title +
                " ".repeat(rem) +
                "║");
    }
    private static void printField(String label, String value) {
        String line;
        if (label == null || label.isEmpty()) {
            // just indent
            line = "   " + value;
        } else {
            // normal label:value
            line = " " + label + " : " + value;
        }
        int pad = WIDTH - 2 - line.length();
        System.out.println("║" + line + " ".repeat(pad) + "║");
    }
    private static void printChartLine(String   type,
                                       int      count,
                                       double   price,
                                       boolean  showPrice,
                                       String   barColor,
                                       int      globalMax) {
        String suffix = showPrice
                ? " (" + count + " @ $" + (long)price + ")"
                : " (" + count + ")";
        String prefix = "   " + type + " : ";
        // build & color the bar
        String rawBar     = buildBar(count, globalMax);
        String coloredBar = barColor + rawBar + ANSI_RESET;
        String line       = prefix + coloredBar + suffix;

        // strip ANSI codes when calculating padding
        String visibleLine = prefix + rawBar + suffix;
        int visibleLen = stripAnsi(visibleLine).length();
        int pad        = WIDTH - 2 - visibleLen;
        System.out.println("║" + line + " ".repeat(pad) + "║");
    }

    private static String stripAnsi(String s) {
        // remove ANSI escape sequences
        return s.replaceAll("\\u001B\\[[;\\d]*m", "");
    }

}
