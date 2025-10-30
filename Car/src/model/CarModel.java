package model;

public enum CarModel {
    ALPHA(10),   
    BETA(5),     
    DELTA(2);    

    private final double discountPercent;

    CarModel(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
}
