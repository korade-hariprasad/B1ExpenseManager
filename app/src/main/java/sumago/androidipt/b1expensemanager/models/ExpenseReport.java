package sumago.androidipt.b1expensemanager.models;

public class ExpenseReport {
    private String categoryName;
    private String percentageOfTotal;
    private double totalAmount;
    private double maxAmount;
    private double minAmount;
    private double averageAmount;

    // Constructor
    public ExpenseReport(String categoryName, String percentageOfTotal, double totalAmount, double maxAmount, double minAmount, double averageAmount) {
        this.categoryName = categoryName;
        this.percentageOfTotal = percentageOfTotal;
        this.totalAmount = totalAmount;
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.averageAmount = averageAmount;
    }
    public ExpenseReport() {
    }

    // Getters and setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPercentageOfTotal() {
        return percentageOfTotal;
    }

    public void setPercentageOfTotal(String percentageOfTotal) {
        this.percentageOfTotal = percentageOfTotal;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getAverageAmount() {
        return averageAmount;
    }

    public void setAverageAmount(double averageAmount) {
        this.averageAmount = averageAmount;
    }

    @Override
    public String toString() {
        return "ExpenseReport{" +
                "categoryName='" + categoryName + '\'' +
                ", percentageOfTotal='" + percentageOfTotal + '\'' +
                ", totalAmount=" + totalAmount +
                ", maxAmount=" + maxAmount +
                ", minAmount=" + minAmount +
                ", averageAmount=" + averageAmount +
                '}';
    }
}
