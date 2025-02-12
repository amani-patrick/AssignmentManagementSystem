<%@ page import="java.util.List" %>
<%@ page import="com.amnii.submission.mis.assignmentmanagementsystem.model.Assignment" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.amnii.submission.mis.assignmentmanagementsystem.model.Teacher" %>

<%
    HttpSession sessionObj = request.getSession();
    Teacher teacher = (Teacher) sessionObj.getAttribute("loggedTeacher");
    int teacherId = (teacher != null) ? teacher.getId() : -1;
    List<Assignment> assignments = (List<Assignment>) request.getAttribute("assignments");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Assignments</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #343a40;
        }
        .teacher-info {
            text-align: center;
            background: #007bff;
            color: white;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .teacher-info p {
            margin: 5px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            background: white;
            border-radius: 5px;
            overflow: hidden;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background: #f2f2f2;
        }
        .no-assignments {
            text-align: center;
            padding: 20px;
            font-size: 18px;
            color: #dc3545;
        }
        @media (max-width: 600px) {
            table {
                display: block;
                overflow-x: auto;
                white-space: nowrap;
            }
            th, td {
                padding: 8px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Teacher Assignments</h1>

    <% if (teacher != null) { %>
    <div class="teacher-info">
        <p><strong>Teacher:</strong> <%= teacher.getFirstName() + " " + teacher.getLastName() %></p>
    </div>
    <% } else { %>
    <p class="no-assignments">No teacher logged in.</p>
    <% } %>

    <h2>Assignments</h2>

    <% if (assignments != null && !assignments.isEmpty()) { %>
    <table>
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Deadline</th>
        </tr>
        <% for (Assignment assignment : assignments) { %>
        <tr>
            <td><%= assignment.getTitle() %></td>
            <td><%= assignment.getDescription() %></td>
            <td><%= assignment.getDeadline() %></td>  <!-- Displaying the deadline -->
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p class="no-assignments">No assignments found.</p>
    <% } %>

</div>

</body>
</html>
