package com.yelzhan.sonar_qube_analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class VulnerableController {

    @Autowired
    private DataSource dataSource;

    // VULNERABILITY: SQL Injection
    @GetMapping("/users")
    public String getUsers(@RequestParam String username) {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();

            // Intentional SQL Injection vulnerability
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet rs = statement.executeQuery(query);

            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                result.append(rs.getString("username")).append(", ");
            }

            // BUG: Resources not closed properly (resource leak)
            return result.toString();
        } catch (SQLException e) {
            // CODE SMELL: Empty catch block
            return "Error occurred";
        }
    }

    // BUG: Division by zero
    @GetMapping("/calculate")
    public int calculateRisk(@RequestParam int value) {
        int divisor = 0;
        // Intentional division by zero
        return value / divisor;
    }

    // CODE SMELL: Method too long and complex
    @PostMapping("/process")
    public String processData(@RequestBody String data) {
        String result = "";

        // CODE SMELL: High cyclomatic complexity
        if (data != null) {
            if (data.length() > 0) {
                if (data.contains("admin")) {
                    if (data.contains("password")) {
                        if (data.contains("123")) {
                            result = "Weak password detected";
                        } else {
                            result = "Password acceptable";
                        }
                    } else {
                        result = "No password";
                    }
                } else {
                    result = "Not admin";
                }
            } else {
                result = "Empty data";
            }
        } else {
            result = "Null data";
        }

        // CODE SMELL: Duplicated code
        String log1 = "Processing: " + data;
        System.out.println(log1);
        String log2 = "Processing: " + data;
        System.out.println(log2);
        String log3 = "Processing: " + data;
        System.out.println(log3);

        return result;
    }

    // VULNERABILITY: Hardcoded credentials
    @GetMapping("/connect")
    public String connectToDatabase() {
        String username = "admin";
        String password = "password123"; // Hardcoded password

        return "Connected with user: " + username;
    }

    // BUG: Null pointer exception potential
    @GetMapping("/nullable")
    public String handleNullable(@RequestParam(required = false) String input) {
        // Intentional null pointer exception risk
        return input.toUpperCase();
    }

    // CODE SMELL: Dead code
    @GetMapping("/dead-code")
    public String deadCodeExample() {
        int x = 10;

        if (x > 5) {
            return "Greater than 5";
        }

        // Dead code - unreachable
        x = 20;
        System.out.println("This will never execute");
        return "Unreachable";
    }

    // VULNERABILITY: Weak cryptography
    @GetMapping("/random")
    public int generateToken() {
        Random random = new Random(); // Weak random number generator
        return random.nextInt(1000);
    }

    // CODE SMELL: Too many parameters
    @GetMapping("/many-params")
    public String methodWithManyParameters(
            String param1, String param2, String param3,
            String param4, String param5, String param6,
            String param7, String param8) {
        return param1 + param2 + param3 + param4 + param5 + param6 + param7 + param8;
    }
}