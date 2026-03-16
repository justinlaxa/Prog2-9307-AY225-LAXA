public class DataRecord {

    private String title;
    private double totalSales;

    public DataRecord(String title, double totalSales) {
        this.title = title;
        this.totalSales = totalSales;
    }

    public String getTitle() {
        return title;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void addSales(double sales) {
        this.totalSales += sales;
    }
}