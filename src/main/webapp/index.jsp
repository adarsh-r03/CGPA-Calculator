<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CGPA Calculator</title>
</head>
<body>
    <h2>CGPA Calculator</h2>
    <form action="cgpa" method="post">
        <input type="hidden" name="action" value="calculate">
        <label for="enrollmentNo">Enrollment Number:</label>
        <input type="text" id="enrollmentNo" name="enrollmentNo" required><br><br>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
        <h3>Enter Subjects and Marks</h3>
        <div id="subjects">
            <div>
                <label>Subject 1:</label>
                <input type="text" name="subjectNames" required>
                <label>Marks:</label>
                <input type="number" name="marks" step="0.01" required><br><br>
            </div>
            <div>
                <label>Subject 2:</label>
                <input type="text" name="subjectNames" required>
                <label>Marks:</label>
                <input type="number" name="marks" step="0.01" required><br><br>
            </div>
        </div>
        <button type="submit">Calculate CGPA</button>
    </form>

    <h3>Search Student</h3>
    <form action="cgpa" method="get">
        <input type="hidden" name="action" value="search">
        <label for="searchEnrollmentNo">Enrollment Number:</label>
        <input type="text" id="searchEnrollmentNo" name="enrollmentNo" required>
        <button type="submit">Search</button>
    </form>
</body>
</html>