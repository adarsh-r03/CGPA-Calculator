package com.example.cgpacalculator.dao;

import com.example.cgpacalculator.model.Student;
import com.example.cgpacalculator.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/cgpadb?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "Adarshcet@001";
    private Connection connection;

    public StudentDAO() throws SQLException {
        try {
            // Explicitly load the JDBC driver (important in some servlet containers)
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw new SQLException("Cannot connect to database: " + e.getMessage(), e);
        }
    }

    // Method to ensure connection is always active
    private Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
        return connection;
    }

    // Insert a student and their subjects into the database
    public void insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (enrollment_no, name, cgpa) VALUES (?, ?, ?)";
        try (PreparedStatement studentStmt = getConnection().prepareStatement(sql)) {
            studentStmt.setString(1, student.getEnrollmentNo());
            studentStmt.setString(2, student.getName());
            studentStmt.setDouble(3, student.getCgpa());
            studentStmt.executeUpdate();
        }

        String subjectSql = "INSERT INTO subjects (enrollment_no, subject_name, marks) VALUES (?, ?, ?)";
        try (PreparedStatement subjectStmt = getConnection().prepareStatement(subjectSql)) {
            for (Subject subject : student.getSubjects()) {
                subjectStmt.setString(1, student.getEnrollmentNo());
                subjectStmt.setString(2, subject.getName());
                subjectStmt.setDouble(3, subject.getMarks());
                subjectStmt.executeUpdate();
            }
        }
    }

    // Retrieve student by enrollment number including their subjects
    public Student selectStudent(String enrollmentNo) throws SQLException {
        Student student = null;
        String sql = "SELECT * FROM students WHERE enrollment_no = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, enrollmentNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setEnrollmentNo(rs.getString("enrollment_no"));
                student.setName(rs.getString("name"));
                student.setCgpa(rs.getDouble("cgpa"));

                // Fetch related subjects
                List<Subject> subjects = new ArrayList<>();
                String subjectSql = "SELECT * FROM subjects WHERE enrollment_no = ?";
                try (PreparedStatement subjectStmt = getConnection().prepareStatement(subjectSql)) {
                    subjectStmt.setString(1, enrollmentNo);
                    ResultSet subjectRs = subjectStmt.executeQuery();

                    while (subjectRs.next()) {
                        Subject subject = new Subject(
                                subjectRs.getString("subject_name"),
                                subjectRs.getDouble("marks")
                        );
                        subjects.add(subject);
                    }
                }
                student.setSubjects(subjects);
            }
        }

        return student;
    }
}
