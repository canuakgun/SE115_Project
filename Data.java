public class Data {
    int month;
    int day;
    int commodityIndex;
    int profit;

    public Data(int month, int day, int commodityIndex, int profit) {
        this.month = month;
        this.day = day;
        this.commodityIndex = commodityIndex;
        this.profit = profit;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getCommodityIndex() {
        return commodityIndex;
    }

    public int getProfit() {
        return profit;
    }
}
