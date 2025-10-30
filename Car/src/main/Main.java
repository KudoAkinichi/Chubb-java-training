package main;

import model.Car;
import model.CarModel;
import service.LoanCalculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter car model (ALPHA, BETA, DELTA): ");
        String modelName = sc.nextLine().toUpperCase();
        CarModel model = CarModel.valueOf(modelName);

        System.out.print("Enter car cost: ");
        double cost = sc.nextDouble();

        Car car = new Car(model, cost);

        System.out.print("Enter loan tenure in months: ");
        int tenureMonths = sc.nextInt();
        double tenureYears = tenureMonths / 12.0;

        System.out.print("Enter annual interest rate (%): ");
        double interestRate = sc.nextDouble();

        System.out.print("Enter compounding frequency per year (e.g., 12 for monthly): ");
        int n = sc.nextInt();

        double principal = car.getDiscountedCost();

        double emi = LoanCalculator.calculateEMI(principal, interestRate, tenureMonths);
        double ci = LoanCalculator.calculateCompoundInterest(principal, interestRate, tenureYears, n);
        double si = LoanCalculator.calculateSimpleInterest(principal, interestRate, tenureYears);

        System.out.printf("Model Selected: %s\n", model);
        System.out.printf("Original Price: %.2f\n", car.getCost());
        System.out.printf("Discounted Price: %.2f\n", principal);

        System.out.printf("EMI for %s: %.2f\n", car.getModel(), emi);
        System.out.printf("Compound Interest over %.2f years: %.2f\n", tenureYears, ci);
        System.out.printf("Simple Interest over %.2f years: %.2f\n", tenureYears, si);

        sc.close();
    }
}
