package model;

public class Car {
    private CarModel model;
    private double cost;

    public Car(CarModel model, double cost) {
        this.model = model;
        this.cost = cost;
    }

    public CarModel getModel() {
        return model;
    }

    public double getCost() {
        return cost;
    }

    public double getDiscountedCost() {
        double discount = (cost * model.getDiscountPercent()) / 100;
        return cost - discount;
    }
}
