
// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = { "Gold", "Oil", "Silver", "Wheat", "Copper" };
    static String[] months = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    static Data[] allData = new Data[1680]; // 12 months * 28 days * 5 commodity

    static int getCommodityIndex(String name) {
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    static int getProfitForDayAndCommodity(int month, int day, int commodityIndex) {

        for (int i = 0; i < allData.length; i++) {
            if (allData[i].getMonth() == month && allData[i].getDay() == day
                    && allData[i].getCommodityIndex() == commodityIndex) {
                return allData[i].getProfit();
            }
        }
        return 0;
    }

    public static void loadData() {
        String[] monthFiles = { "January.txt", "February.txt", "March.txt", "April.txt", "May.txt", "June.txt",
                "July.txt", "August.txt", "September.txt", "October.txt", "November.txt", "December.txt" };
        int arrayIndex = 0;
        try {
            for (int month = 0; month < 12; month++) {
                BufferedReader reader = new BufferedReader(
                        new FileReader("Data_Files/" + monthFiles[month]));

                String line = reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    int day = Integer.parseInt(parts[0]);
                    int commodityIndex = getCommodityIndex(parts[1]);
                    int profit = Integer.parseInt(parts[2]);

                    allData[arrayIndex] = new Data(month, day, commodityIndex, profit);
                    arrayIndex++;
                }

                reader.close();
            }
        } catch (Exception e) {

        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11) {
            return "INVALID_MONTH";
        }

        int[] sumArr = new int[5]; // GOld, Oil, Silver ,Wheat , Copper
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;

        for (int i = 0; i < allData.length; i++) {
            if (allData[i].getMonth() == month) {
                sumArr[allData[i].getCommodityIndex()] += allData[i].getProfit();
            }
        }
        for (int i = 0; i < sumArr.length; i++) {
            if (sumArr[i] > max) {
                max = sumArr[i];
                maxIndex = i;
            }
        }
        return commodities[maxIndex] + " " + max;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month > 11 || day < 1 || day > 28) {
            return -99999;
        }
        int totalProfit = 0;
        for (int i = 0; i < allData.length; i++) {
            if (allData[i].getMonth() == month && allData[i].getDay() == day) {
                totalProfit += allData[i].getProfit();
            }
        }

        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int totalProfit = 0;
        int commodityIndex = getCommodityIndex(commodity);
        if (commodityIndex == -1 || from > to || from < 1 || from > 28 || to < 1 || to > 28) {
            return -99999;
        }
        for (int i = 0; i < allData.length; i++) {
            if (allData[i].getCommodityIndex() == commodityIndex && allData[i].getDay() >= from
                    && allData[i].getDay() <= to) {
                totalProfit += allData[i].getProfit();
            }
        }
        return totalProfit;
    }

    public static int bestDayOfMonth(int month) {
        return 1234;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}