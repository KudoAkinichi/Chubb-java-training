package com;

import java.util.*;

public class CompanyEmployeeMap {
    
    public static void main(String[] args) {
        Map<String, List<String>> companyMap = new HashMap<>();
        
        companyMap.put("TCS", Arrays.asList("Amit", "Priya", "Suresh", "Kavita"));
        companyMap.put("Infosys", Arrays.asList("Rahul", "Sneha", "Arjun", "Deepa"));
        companyMap.put("Wipro", Arrays.asList("Vikram", "Anjali", "Ravi", "Pooja"));
        companyMap.put("Tech Mahindra", Arrays.asList("Sanjay", "Meera", "Karan", "Divya"));
        companyMap.put("HCL", Arrays.asList("Anil", "Swati", "Rajesh", "Neha"));
        
        System.out.println("Company-Employee Map:");
        for (Map.Entry<String, List<String>> entry : companyMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        String targetCompany = "Cognizant";
        if (!companyMap.containsKey(targetCompany)) {
            companyMap.put(targetCompany, new ArrayList<>(Arrays.asList("Ram", "Shivani", "Gaurav")));
            System.out.println("\nAdded new company: " + targetCompany);
        }

        List<String> cognizantEmployees = companyMap.get(targetCompany);
        if (cognizantEmployees.contains("Ram")) {
            System.out.println("Ram is working in Cognizant.");
        } else {
            System.out.println("Ram is not found in Cognizant.");
        }

        System.out.println("\nUpdated Company-Employee Map:");
        for (Map.Entry<String, List<String>> entry : companyMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }
}
