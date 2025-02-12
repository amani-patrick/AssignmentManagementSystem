<%@ page import="java.util.List" %>
<%@ page import="com.amnii.submission.mis.assignmentmanagementsystem.model.Assignment" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-r from-indigo-500 to-purple-600 min-h-screen text-white flex flex-col justify-between">

<%
    HttpSession sessionObj = request.getSession(false);
    String successMessage = (sessionObj != null) ? (String) sessionObj.getAttribute("successMessage") : null;
    String errorMessage = (sessionObj != null) ? (String) sessionObj.getAttribute("errorMessage") : null;
    String studentName = (sessionObj != null) ? (String) sessionObj.getAttribute("studentName") : "Student";
    List<Assignment> assignments = (List<Assignment>) request.getAttribute("assignments");

    int totalAssignments = (assignments != null) ? assignments.size() : 0;
    int pendingAssignments = totalAssignments;
%>

<% if (successMessage != null) { %>
<div id="successMessage" class="fixed top-4 left-1/2 transform -translate-x-1/2 bg-green-500 text-white py-3 px-6 rounded-lg shadow-lg">
    🎉 <%= successMessage %>
</div>
<script>
    setTimeout(() => document.getElementById("successMessage").style.display = "none", 3000);
</script>
<% sessionObj.removeAttribute("successMessage"); } %>

<% if (errorMessage != null) { %>
<div id="errorMessage" class="fixed top-4 left-1/2 transform -translate-x-1/2 bg-red-500 text-white py-3 px-6 rounded-lg shadow-lg">
    ⚠️ <%= errorMessage %>
</div>
<script>
    setTimeout(() => document.getElementById("errorMessage").style.display = "none", 3000);
</script>
<% sessionObj.removeAttribute("errorMessage"); } %>

<div class="bg-white text-gray-800 p-4 shadow-lg flex justify-between items-center">
    <h2 class="text-3xl font-bold text-indigo-700">📚 Student Portal</h2>
    <div class="text-gray-600">👋 Hey, <%= studentName %></div>
</div>

<!-- Main Content -->
<div class="flex justify-center items-center mt-8 px-4 sm:px-6 md:px-8 flex-grow">
    <div class="w-full max-w-4xl bg-white p-8 rounded-lg shadow-lg text-gray-800">
        <h3 class="text-2xl font-semibold mb-6 text-center">Welcome, <%= studentName %>!</h3>

        <!-- Stats -->
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
            <div class="bg-indigo-100 p-6 shadow-lg rounded-lg text-center">
                <h4 class="text-lg font-semibold"><a href="get_assignments">📂 Total Assignments</a></h4>
                <p class="text-3xl font-bold mt-2"><%= totalAssignments %></p>
            </div>
            <div class="bg-indigo-100 p-6 shadow-lg rounded-lg text-center">
                <h4 class="text-lg font-semibold">⏳ Pending Assignments</h4>
                <p class="text-3xl font-bold mt-2"><%= pendingAssignments %></p>
            </div>
        </div>

        <!-- Assignments List -->
        <div class="mt-8">
            <h3 class="text-xl font-semibold text-indigo-700 mb-4">📝 Available Assignments</h3>

            <% if (assignments != null && !assignments.isEmpty()) { %>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <% for (Assignment assignment : assignments) { %>
                <div class="bg-gray-100 p-6 shadow-lg rounded-lg">
                    <h4 class="text-lg font-semibold text-indigo-700"><%= assignment.getTitle() %></h4>
                    <p class="text-gray-700 mt-2"><%= assignment.getDescription() %></p>
                    <p class="text-red-500 font-bold mt-2">Deadline: <%= assignment.getDeadline() %></p>

                    <!-- Submit Assignment Form -->
                    <form action="submit_assignments" method="POST" class="mt-4" enctype="multipart/form-data">
                        <input type="hidden" name="assignmentId" value="<%= assignment.getId() %>">

                        <!-- File Upload Input -->
                        <input type="file" name="submissionFile" accept=".doc,.docx,.pdf,.zip" class="block w-full text-sm text-gray-700 border border-gray-300 rounded-lg p-2 mt-2">

                        <!-- Submit Button -->
                        <button type="submit" class="w-full bg-indigo-600 text-white py-2 rounded-lg hover:bg-indigo-700 mt-4">
                            📤 Submit Assignment
                        </button>
                    </form>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <p class="text-center text-gray-600">No assignments available at the moment.</p>
            <% } %>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-white text-gray-600 py-4 mt-8 text-center">
    <div class="text-sm">© 2025 Student Portal | All rights reserved</div>
</footer>

</body>
</html>
