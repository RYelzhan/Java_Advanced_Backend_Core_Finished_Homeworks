package com.yelzhan.sonar_qube_analysis.util;

public class StringUtils {

    // CODE SMELL: Utility class should have private constructor

    // CODE SMELL: Method can be static
    public String concatenate(String a, String b) {
        return a + b;
    }

    // BUG: String concatenation in loop
    public static String joinStrings(String[] strings) {
        String result = "";
        for (String s : strings) {
            result += s; // Inefficient string concatenation
        }
        return result;
    }

    // CODE SMELL: Duplicated string literals
    public static boolean validate(String input) {
        if (input.equals("admin")) {
            return true;
        }
        if (input.equals("admin")) {
            return false;
        }
        return input.equals("admin");
    }

    // VULNERABILITY: Regular expression DoS (ReDoS)
    public static boolean validateEmail(String email) {
        return email.matches("(a+)+@example\\.com");
    }
}