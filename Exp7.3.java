### Instructions for Java JDBC MVC Student Management System  

1. **Setup MySQL Database:**  
   - Install and start MySQL.  
   - Create a database (e.g., `StudentDB`).  
   - Create a table:  
     ```sql
     CREATE TABLE Student (
         StudentID INT PRIMARY KEY,
         Name VARCHAR(100),
         Department VARCHAR(50),
         Marks DOUBLE
     );
     ```

2. **Update Database Credentials:**  
   - Modify `URL`, `USER`, and `PASSWORD` in the code to match your MySQL database credentials.

3. **Add MySQL JDBC Driver:**  
   - Download and add `mysql-connector-java.jar` to your project's classpath.

4. **Compile and Run the Program:**  
   - Compile: `javac StudentManagementApp.java`  
   - Run: `java StudentManagementApp`  

5. **Menu-Driven Operations:**  
   - **Add Student:** Enter StudentID, Name, Department, and Marks.  
   - **View Students:** Displays all students in the table.  
   - **Update Student:** Modify Name, Department, or Marks using StudentID.  
   - **Delete Student:** Remove a student using StudentID.  
   - **Exit:** Quit the program.

6. **Transaction Handling:**  
   - Ensures data integrity by using `conn.setAutoCommit(false)` and `conn.commit()`.  
   - Rolls back changes in case of errors.

7. **Verify Database Changes:**  
   - Use `SELECT * FROM Student;` in MySQL to confirm modifications.

   public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentDB";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, student.getStudentID());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getDepartment());
            stmt.setDouble(4, student.getMarks());
            stmt.executeUpdate();
        }
    }

    public List<Student> viewStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getDouble("Marks")));
            }
        }
        return students;
    }

    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getDepartment());
            stmt.setDouble(3, student.getMarks());
            stmt.setInt(4, student.getStudentID());
            stmt.executeUpdate();
        }
    }

    public void deleteStudent(int studentID) throws SQLException {
        String query = "DELETE FROM Student WHERE StudentID=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentID);
            stmt.executeUpdate();
        }
    }
}
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

public class StudentController {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Department: ");
                        String department = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        studentDAO.addStudent(new Student(id, name, department, marks));
                        System.out.println("Student added successfully!");
                    }
                    case 2 -> {
                        List<Student> students = studentDAO.viewStudents();
                        if (students.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            students.forEach(s -> System.out.println("ID: " + s.getStudentID() + ", Name: " + s.getName() +
                                    ", Department: " + s.getDepartment() + ", Marks: " + s.getMarks()));
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter Student ID to update: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Department: ");
                        String department = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        studentDAO.updateStudent(new Student(id, name, department, marks));
                        System.out.println("Student updated successfully!");
                    }
                    case 4 -> {
                        System.out.print("Enter Student ID to delete: ");
                        int id = scanner.nextInt();
                        studentDAO.deleteStudent(id);
                        System.out.println("Student deleted successfully!");
                    }
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

CREATE DATABASE StudentDB;
USE StudentDB;
CREATE TABLE Student (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(100),
    Department VARCHAR(50),
    Marks DOUBLE
);
javac StudentController.java StudentDAO.java Student.java
java StudentController
