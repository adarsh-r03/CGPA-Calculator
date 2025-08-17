<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.cgpacalculator.model.Student" %>
<%@ page import="com.example.cgpacalculator.model.Subject" %> <!-- Add this import -->
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CGPA Result</title>
</head>
<body>
    <h2>Student CGPA Result</h2>
    <%
        Student student = (Student) request.getAttribute("student");
        if (student != null) {
    %>
            <p><strong>Enrollment Number:</strong> <%= student.getEnrollmentNo() %></p>
            <p><strong>Name:</strong> <%= student.getName() %></p>
            <p><strong>CGPA:</strong> <%= String.format("%.2f", student.getCgpa()) %></p>
            <h3>Subjects:</h3>
            <table border="1">
                <tr>
                    <th>Subject Name</th>
                    <th>Marks</th>
                </tr>
                <%
                    for (Subject subject : student.getSubjects()) { // Changed Student.Subject to Subject
                %>
                        <tr>
                            <td><%= subject.getName() %></td>
                            <td><%= subject.getMarks() %></td>
                        </tr>
                <%
                    }
                %>
            </table>
    <%
        } else {
    %>
            <p>No student data found.</p>
    <%
        }
    %>
    <br>
    <a href="index.jsp">Back to Calculator</a>
</body>
</html>