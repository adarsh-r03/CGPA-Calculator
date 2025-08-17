<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h2>Error</h2>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
    <a href="index.jsp">Back to Calculator</a>
</body>
</html>