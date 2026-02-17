package com.yelzhan.sonar_qube_analysis.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    // CODE SMELL: Large class with too many responsibilities
    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;
    private String data6;
    private String data7;
    private String data8;
    private String data9;
    private String data10;

    // CODE SMELL: Duplicated code blocks
    public List<String> processType1(String input) {
        List<String> results = new ArrayList<>();
        results.add("Processing: " + input);
        results.add("Validating: " + input);
        results.add("Storing: " + input);
        results.add("Logging: " + input);
        return results;
    }

    public List<String> processType2(String input) {
        List<String> results = new ArrayList<>();
        results.add("Processing: " + input);
        results.add("Validating: " + input);
        results.add("Storing: " + input);
        results.add("Logging: " + input);
        return results;
    }

    public List<String> processType3(String input) {
        List<String> results = new ArrayList<>();
        results.add("Processing: " + input);
        results.add("Validating: " + input);
        results.add("Storing: " + input);
        results.add("Logging: " + input);
        return results;
    }

    // CODE SMELL: Cognitive complexity too high
    public String complexMethod(int a, int b, int c) {
        String result = "";

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                for (int k = 0; k < c; k++) {
                    if (i > j) {
                        if (j > k) {
                            if (k > 0) {
                                result += i + j + k;
                            } else {
                                result += "zero";
                            }
                        } else {
                            result += "j <= k";
                        }
                    } else {
                        result += "i <= j";
                    }
                }
            }
        }

        return result;
    }

    // BUG: Infinite loop potential
    public void potentialInfiniteLoop(boolean condition) {
        while (condition) {
            System.out.println("Running...");
            // Missing condition change - potential infinite loop
        }
    }

    // CODE SMELL: Magic numbers
    public double calculateDiscount(double price) {
        if (price > 1000) {
            return price * 0.15; // Magic number
        } else if (price > 500) {
            return price * 0.10; // Magic number
        } else if (price > 100) {
            return price * 0.05; // Magic number
        }
        return price;
    }

    // BUG: Comparison with floating point
    public boolean isEqual(double a, double b) {
        return a == b; // Floating point comparison issue
    }

    // CODE SMELL: Unused private method
    private void unusedMethod() {
        System.out.println("This method is never called");
    }
}