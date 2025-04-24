package Utils;

import Project.Project;

public class AvailabilityChart {
    // Max characters per bar
    private static final int BAR_WIDTH = 20;

    public static void print(Project project) {
        int count1 = project.getNoOfUnitsType1();
        int count2 = project.getNoOfUnitsType2();
        // find the larger of the two so we can scale both bars proportionally
        int max = Math.max(count1, count2);
        if (max == 0) {
            System.out.println("No units left for either flat type.");
            return;
        }

        // helper to build a bar of '#' characters
        String bar1 = buildBar(count1, max);
        String bar2 = buildBar(count2, max);

        System.out.println("\n--- Flat Availability for " + project.getProjectName() + " ---");
        System.out.printf(" %s: %s (%d)%n", project.getType1(), bar1, count1);
        System.out.printf(" %s: %s (%d)%n%n", project.getType2(), bar2, count2);
    }
    public static void printWithPrice(Project project) {
        int count1 = project.getNoOfUnitsType1();
        int count2 = project.getNoOfUnitsType2();
        double price1 = project.getSellPriceType1();
        double price2 = project.getSellPriceType2();
        int max = Math.max(count1, count2);
        if (max == 0) {
            System.out.println("No units left for either flat type.");
            return;
        }
        String bar1 = buildBar(count1, max);
        String bar2 = buildBar(count2, max);

        System.out.println("\n--- Flat Availability for " + project.getProjectName() + " ---");
        System.out.printf(" %s: %s (%d units @ $%.2f each)%n",
                project.getType1(), bar1, count1, price1);
        System.out.printf(" %s: %s (%d units @ $%.2f each)%n%n",
                project.getType2(), bar2, count2, price2);
    }


    private static String buildBar(int value, int max) {
        int len = (int)Math.round(value / (double)max * BAR_WIDTH);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) sb.append('█');
        for (int i = len; i < BAR_WIDTH; i++) sb.append(' ');
        return sb.toString();
    }
}
