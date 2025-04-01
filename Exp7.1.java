

1. **Setup MySQL Database**  
   - Ensure MySQL is installed and running.  
   - Create a database and an `Employee` table with columns `EmpID`, `Name`, and `Salary`.
   -- Create a new database
CREATE DATABASE EmployeeDB;

-- Use the newly created database
USE EmployeeDB;

-- Create Employee table
CREATE TABLE Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(100),
    Salary DECIMAL(10,2)
);

2. **Update Database Credentials**  
   - Replace `your_database`, `your_username`, and `your_password` in the code with actual database credentials.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLConnection {
    public static void main(String[] args) {
        // Updated database credentials
        String url = "jdbc:mysql://localhost:3306/EmployeeDB";
        String user = "your_username"; // Replace with actual username
        String password = "your_password"; // Replace with actual password

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            
            // Query to fetch employee records
            String query = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(query);

            // Display results
            System.out.println("Employee Records:");
            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID") + 
                                   ", Name: " + rs.getString("Name") + 
                                   ", Salary: " + rs.getDouble("Salary"));
            }

            // Close connection
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

3. **Add MySQL JDBC Driver**  
   - Download and add `mysql-connector-java.jar` to your project’s classpath.
javac -cp .:mysql-connector-java.jar MySQLConnection.java
java -cp .:mysql-connector-java.jar MySQLConnection

4. **Compile and Run the Program**  
   - Compile: `javac MySQLConnection.java`  
   - Run: `java MySQLConnection`
java -cp .:mysql-connector-java.jar MySQLConnection

5. **Verify Output**  
   - Ensure that employee records are displayed correctly from the database.
