<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-r from-indigo-500 to-purple-600 min-h-screen text-white">
<%
    HttpSession sessionObj = request.getSession(false);
    String successMessage = (sessionObj != null) ? (String) sessionObj.getAttribute("successMessage") : null;
    if (successMessage != null) {
%>
<div id="successMessage" class="fixed top-4 left-1/2 transform -translate-x-1/2 bg-green-500 text-white py-3 px-6 rounded-lg shadow-lg">
    🎉 <%= successMessage %>
</div>
<script>
    setTimeout(() => document.getElementById("successMessage").style.display = "none", 3000);
</script>
<% sessionObj.removeAttribute("successMessage"); %>
<% } %>

<div class="flex h-screen">
    <!-- Sidebar -->
    <div class="w-72 bg-white text-gray-800 shadow-lg flex flex-col items-center py-6">
        <h2 class="text-3xl font-bold text-indigo-700">📚 My Portal</h2>
        <ul class="mt-6 w-full">
            <li class="flex items-center gap-3 py-3 px-6 hover:bg-indigo-100 rounded-lg cursor-pointer">
                🏠 Dashboard
            </li>
            <li class="flex items-center gap-3 py-3 px-6 hover:bg-indigo-100 rounded-lg cursor-pointer">
                📝 My Assignments
            </li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="flex-1 p-8">
        <!-- Navbar -->
        <%
            String studentName = (sessionObj != null) ? (String) sessionObj.getAttribute("studentName") : "Student";
        %>
        <div class="flex justify-between items-center bg-white p-4 shadow-lg rounded-lg">
            <h2 class="text-2xl font-semibold text-gray-800">Welcome Back! 🎉</h2>
            <div class="text-gray-600">👋 Hey, <%= studentName %></div>
        </div>

        <!-- Dashboard Stats -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
            <div class="bg-white p-6 shadow-lg rounded-lg flex flex-col items-center text-gray-800">
                <h3 class="text-lg font-semibold">📂 Total Assignments</h3>
                <p class="text-3xl font-bold mt-2">5</p>
            </div>
            <div class="bg-white p-6 shadow-lg rounded-lg flex flex-col items-center text-gray-800">
                <h3 class="text-lg font-semibold">⏳ Pending Assignments</h3>
                <p class="text-3xl font-bold mt-2">2</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
