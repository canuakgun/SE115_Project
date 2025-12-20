
// Main.java – Students version
import java.io.*;
import java.util.*;
import java.nio.file.Paths;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = { "Gold", "Oil", "Silver", "Wheat", "Copper" };
    static String[] months = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };

    // Data arrays - 1680 entries (12 months * 28 days * 5 commodities)
    static int[] dataMonth = new int[1680];
    static int[] dataDay = new int[1680];
    static int[] dataCommodityIndex = new int[1680];
    static int[] dataProfit = new int[1680];

    public static int getCommodityIndex(String name) {
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int getProfitForDayAndCommodity(int month, int day, int commodityIndex) {
        for (int i = 0; i < dataMonth.length; i++) {
            if (dataMonth[i] == month && dataDay[i] == day && dataCommodityIndex[i] == commodityIndex) {
                return dataProfit[i];
            }
        }
        return 0;
    }

    public static void loadData() {
        String[] monthFiles = { "January.txt", "February.txt", "March.txt", "April.txt", "May.txt", "June.txt",
                "July.txt", "August.txt", "September.txt", "October.txt", "November.txt", "December.txt" };
        int arrayIndex = 0;

        for (int month = 0; month < 12; month++) {
            Scanner reader = null;
            try {
                reader = new Scanner(Paths.get("Data_Files/" + monthFiles[month]));

                // İlk satırı atla (başlık satırı)
                if (reader.hasNextLine()) {
                    reader.nextLine();
                }

                // Dosyayı satır satır oku
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] parts = line.split(",");

                    int day = Integer.parseInt(parts[0]);
                    int commodityIndex = getCommodityIndex(parts[1]);
                    int profit = Integer.parseInt(parts[2]);

                    dataMonth[arrayIndex] = month;
                    dataDay[arrayIndex] = day;
                    dataCommodityIndex[arrayIndex] = commodityIndex;
                    dataProfit[arrayIndex] = profit;
                    arrayIndex++;
                }

            } catch (IOException e) {
                System.err.println("Dosya okuma hatası: " + monthFiles[month]);
            } catch (NumberFormatException e) {
                System.err.println("Sayı formatı hatası");
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11) {
            return "INVALID_MONTH";
        }

        int[] sumArr = new int[5];
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;

        for (int i = 0; i < dataMonth.length; i++) {
            if (dataMonth[i] == month) {
                sumArr[dataCommodityIndex[i]] += dataProfit[i];
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
        for (int i = 0; i < dataMonth.length; i++) {
            if (dataMonth[i] == month && dataDay[i] == day) {
                totalProfit += dataProfit[i];
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
        for (int i = 0; i < dataMonth.length; i++) {
            if (dataCommodityIndex[i] == commodityIndex && dataDay[i] >= from && dataDay[i] <= to) {
                totalProfit += dataProfit[i];
            }
        }
        return totalProfit;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month > 11) {
            return -1;
        }
        int[] dailyTotals = new int[28];
        for (int i = 0; i < dataMonth.length; i++) {
            if (dataMonth[i] == month) {
                int day = dataDay[i];
                dailyTotals[day - 1] += dataProfit[i];
            }
        }
        int maxDay = 1;
        for (int i = 1; i < 28; i++) {
            if (dailyTotals[i] > dailyTotals[maxDay - 1]) {
                maxDay = i + 1;
            }
        }
        return maxDay;
    }

    public static String bestMonthForCommodity(String comm) {
        int commodityIndex = getCommodityIndex(comm);
        if (commodityIndex == -1) {
            return "INVALID_COMMODITY";
        }
        int[] monthlyTotals = new int[12];
        for (int i = 0; i < dataMonth.length; i++) {
            if (dataCommodityIndex[i] == commodityIndex) {
                int month = dataMonth[i];
                monthlyTotals[month] += dataProfit[i];
            }
        }

        int maxMonthIndex = 0;
        for (int i = 0; i < 12; i++) {
            if (monthlyTotals[i] > monthlyTotals[maxMonthIndex]) {
                maxMonthIndex = i;
            }
        }
        return months[maxMonthIndex];
    }

    public static int consecutiveLossDays(String comm) {
        int commodityIndex = getCommodityIndex(comm);
        if (commodityIndex == -1) {
            return -1;
        }
        int currentStreak = 0;
        int maxStreak = 0;

        for (int month = 0; month < 12; month++) {
            for (int day = 1; day <= 28; day++) {
                int profit = getProfitForDayAndCommodity(month, day, commodityIndex);

                if (profit < 0) {
                    currentStreak++;
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                } else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int count = 0;
        int commodityIndex = getCommodityIndex(comm);
        if (commodityIndex == -1) {
            return -1;
        }
        for (int i = 0; i < dataMonth.length; i++) {
            if (dataCommodityIndex[i] == commodityIndex) {
                if (dataProfit[i] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month > 11) {
            return -99999;
        }
        int maxSwing = 0;

        for (int day = 1; day < 28; day++) {
            int today = totalProfitOnDay(month, day);
            int nextDay = totalProfitOnDay(month, day + 1);
            int currentSwing = Math.abs(today - nextDay);

            if (currentSwing > maxSwing) {
                maxSwing = currentSwing;
            }
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int commodityIndex1 = getCommodityIndex(c1);
        int commodityIndex2 = getCommodityIndex(c2);
        if (commodityIndex1 == -1 || commodityIndex2 == -1) {
            return "INVALID_COMMODITY";
        }
        int totalC1 = 0;
        int totalC2 = 0;

        for (int i = 0; i < dataMonth.length; i++) {
            if (dataCommodityIndex[i] == commodityIndex1) {
                totalC1 += dataProfit[i];
            }
            if (dataCommodityIndex[i] == commodityIndex2) {
                totalC2 += dataProfit[i];
            }
        }
        if (totalC1 > totalC2) {
            return "C1 is better by" + " " + Math.abs(totalC1 - totalC2);
        } else if (totalC1 < totalC2) {
            return "C2 is better by" + " " + Math.abs(totalC1 - totalC2);
        } else {
            return "Equal";
        }
    }

    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month > 11) {
            return "INVALID_MONTH";
        }
        int[] weeklyTotals = new int[4];
        for (int day = 1; day <= 28; day++) {
            int weekIndex = (day - 1) / 7;
            weeklyTotals[weekIndex] += totalProfitOnDay(month, day);
        }
        int maxWeekIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (weeklyTotals[i] > weeklyTotals[maxWeekIndex]) {
                maxWeekIndex = i;
            }
        }
        return "Week" + " " + (maxWeekIndex + 1);
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded- ready for queries");
    }
}