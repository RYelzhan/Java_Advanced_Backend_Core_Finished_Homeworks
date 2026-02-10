# REST API Architecture Module Practical Task

## Overall Evaluation Criteria:
- Task 1 carries a maximum of 75 points for completeness.
- Task 2 carries a maximum of 25 points for completeness.

## Necessary Tools:
- Java Development Kit 17+
- Apache Maven 3.6.0+, Gradle
- Spring Boot 3.x

## TASK 1. BUILDING A REST API

### 1. Set Up the Project Environment _(step 1-3 = 55 points)_
- **Create a New Spring Boot Project:**
    - Use Spring Initializr or your favorite IDE to create a new Spring Boot project.
    - Choose the following dependencies:
        - Spring Web: For building web applications.
        - Spring Data JPA: For data persistence.
        - Spring Boot Security: For authentication and authorization.
        - H2 Database: For an in-memory database (or use MySQL/PostgreSQL for a production-like environment).
        - Spring Boot Actuator: For monitoring and management.

- **Configure the Application Properties:**
    - Open application.properties or application.yml and configure the following:

### 2. Define the Data Model _(5 points)_
- **Create a User Entity:**
    - Define a User entity to represent the user data in the database.
- **Create a User Repository:**
    - Create a repository interface for the User entity.

### 3. Implement CRUD Operations _(50 points)_
- **Create a User Service:**
    - Implement a service class to handle the business logic for the User entity.
- **Create a User Controller:**
    - Implement a controller class to handle HTTP requests and map them to the service methods.

### 4. Implement Authentication and Authorization _(5 points)_
- **Configure Security:**
    - Create a security configuration class to set up basic authentication.

### 5. Implement Error Handling _(5 points)_
- **Create a Global Exception Handler:**
    - Use @ControllerAdvice to handle exceptions globally.
- **Define Error Response Structure:**
    - Create an ErrorResponse class to structure error messages.

### 6. Implement Versioning _(5 points)_
- **Use URI Versioning:**
    - Implement versioning by creating different versions of the controllers.

### 7. Implement Pagination _(5 points)_
- **Add Pagination to the User Controller:**
    - Modify the getUsers method to support pagination.
## Task 2.
Please make a code review. 
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASS = "password"; 

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM Users WHERE id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            
            List<String> users = new ArrayList<>();
            while (rs.next()) {
                users.add(rs.getString("name"));
            }

            response.setContentType("application/json");
            response.setStatus(200); 

            response.getWriter().println(users.toString());

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.length() < 5) {
            try {
                resp.getWriter().write("Username is too short!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        this.DB_URL = "jdbc:mysql://localhost:3306/anotherdb";

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            resp.setStatus(500); 
            return;
        }

        System.out.println("Received user: " + username);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(i + " ");
            }
        }
        resp.getWriter().write("User successfully created!");
    }
}
```



