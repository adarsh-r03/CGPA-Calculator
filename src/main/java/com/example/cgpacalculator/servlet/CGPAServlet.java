package com.example.cgpacalculator.servlet;

import com.example.cgpacalculator.dao.StudentDAO;
import com.example.cgpacalculator.model.Student;
import com.example.cgpacalculator.model.Subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cgpa")
public class CGPAServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        try {
            studentDAO = new StudentDAO();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize StudentDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("calculate".equals(action)) {
            try {
                String enrollmentNo = request.getParameter("enrollmentNo");
                String name = request.getParameter("name");
                String[] subjectNames = request.getParameterValues("subjectNames");
                String[] marks = request.getParameterValues("marks");

                Student student = new Student();
                student.setEnrollmentNo(enrollmentNo);
                student.setName(name);
                List<Subject> subjects = new ArrayList<>();
                for (int i = 0; i < subjectNames.length; i++) {
                    if (!subjectNames[i].isEmpty() && !marks[i].isEmpty()) {
                        subjects.add(new Subject(subjectNames[i], Double.parseDouble(marks[i])));
                    }
                }
                student.setSubjects(subjects);
                student.calculateCGPA();

                studentDAO.insertStudent(student); // This might throw SQLException

                request.setAttribute("student", student);
                request.getRequestDispatcher("result.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", "Database error: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid marks format: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("search".equals(action)) {
            try {
                String enrollmentNo = request.getParameter("enrollmentNo");
                Student student = studentDAO.selectStudent(enrollmentNo);
                request.setAttribute("student", student);
                request.getRequestDispatcher("result.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", "Database error: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }
}