// StudentDAO.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Students (roll_number, name, grade, age, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getRollNumber());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getGrade());
            stmt.setInt(4, student.getAge());
            stmt.setString(5, student.getAddress());
            stmt.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Students ORDER BY roll_number";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Student student = new Student(
                    rs.getString("roll_number"),
                    rs.getString("name"),
                    rs.getString("grade"),
                    rs.getInt("age"),
                    rs.getString("address")
                );
                student.setId(rs.getInt("id"));
                students.add(student);
            }
        }
        return students;
    }

    // StudentDAO.java
    public void updateStudent(String rollNumber, Student updatedStudent) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE Students SET ");
        boolean needsComma = false;
    
        if (updatedStudent.getRollNumber() != null) {
            query.append("roll_number = ?");
            needsComma = true;
        }
        if (updatedStudent.getName() != null) {
            if (needsComma) query.append(", ");
            query.append("name = ?");
            needsComma = true;
        }
        if (updatedStudent.getGrade() != null) {
            if (needsComma) query.append(", ");
            query.append("grade = ?");
            needsComma = true;
        }
        if (updatedStudent.getAge() != -1) {
            if (needsComma) query.append(", ");
            query.append("age = ?");
            needsComma = true;
        }
        if (updatedStudent.getAddress() != null) {
            if (needsComma) query.append(", ");
            query.append("address = ?");
        }
    
        query.append(" WHERE roll_number = ?");
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int parameterIndex = 1;
    
            if (updatedStudent.getRollNumber() != null) {
                stmt.setString(parameterIndex++, updatedStudent.getRollNumber());
            }
            if (updatedStudent.getName() != null) {
                stmt.setString(parameterIndex++, updatedStudent.getName());
            }
            if (updatedStudent.getGrade() != null) {
                stmt.setString(parameterIndex++, updatedStudent.getGrade());
            }
            if (updatedStudent.getAge() != -1) {
                stmt.setInt(parameterIndex++, updatedStudent.getAge());
            }
            if (updatedStudent.getAddress() != null) {
                stmt.setString(parameterIndex++, updatedStudent.getAddress());
            }
    
            stmt.setString(parameterIndex, rollNumber); 
            stmt.executeUpdate();
        }
    }

    // StudentDAO.java
    public void deleteStudent(String rollNumber) throws SQLException {
        String query = "DELETE FROM Students WHERE roll_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, rollNumber);
            stmt.executeUpdate();
        }
    }

    // StudentDAO.java
public List<Student> searchStudents(String rollNumber, String name, String grade, int minAge, int maxAge) throws SQLException {
    List<Student> students = new ArrayList<>();
    String query = "SELECT * FROM Students WHERE roll_number LIKE ? AND name LIKE ? AND grade LIKE ? AND age BETWEEN ? AND ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, "%" + rollNumber + "%");
        stmt.setString(2, "%" + name + "%");
        stmt.setString(3, "%" + grade + "%");
        stmt.setInt(4, minAge);
        stmt.setInt(5, maxAge);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Student student = new Student(
                rs.getString("roll_number"),
                rs.getString("name"),
                rs.getString("grade"),
                rs.getInt("age"),
                rs.getString("address")
            );
            student.setId(rs.getInt("id"));
            students.add(student);
        }
    }
    return students;
}
// StudentDAO.java
public Student searchStudent(String rollNumber) throws SQLException {
    String query = "SELECT * FROM Students WHERE roll_number = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, rollNumber);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Student student = new Student(
                rs.getString("roll_number"),
                rs.getString("name"),
                rs.getString("grade"),
                rs.getInt("age"),
                rs.getString("address")
            );
            student.setId(rs.getInt("id"));
            return student;
        }
    }
    return null;
}
    // StudentDAO.java
public void updateGrade(String rollNumber, int marks) throws SQLException {
    String grade = calculateGrade(marks);
    String query = "UPDATE Students SET marks = ?, grade = ? WHERE roll_number = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, marks);
        stmt.setString(2, grade);
        stmt.setString(3, rollNumber);
        stmt.executeUpdate();
    }
}

private String calculateGrade(int marks) {
    if (marks >= 90) return "A";
    if (marks >= 80) return "B";
    if (marks >= 70) return "C";
    if (marks >= 60) return "D";
    return "F";
}
// StudentDAO.java
public void logAction(String action, String username) throws SQLException {
    String query = "INSERT INTO AuditLogs (action, username) VALUES (?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, action);
        stmt.setString(2, username);
        stmt.executeUpdate();
    }
}
}