package service;

public class LoanCalculator {
    // Calculate EMI using compound interest
    public static double calculateEMI(double principal, double annualRate, int tenureMonths) {
        double monthlyRate = annualRate / (12 * 100);
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
               (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    // Calculate compound interest for n years, compounded t times per year
    public static double calculateCompoundInterest(double principal, double annualRate, double years, int compoundingPerYear) {
        double rate = annualRate / 100.0;
        double amount = principal * Math.pow(1 + rate / compoundingPerYear, compoundingPerYear * years);
        return amount - principal;
    }

    // Calculate simple interest for comparison
    public static double calculateSimpleInterest(double principal, double annualRate, double years) {
        return principal * annualRate * years / 100.0;
    }
}
