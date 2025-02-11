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
    if (successMessage != null) {
%>
<div id="successMessage" class="fixed top-4 left-1/2 transform -translate-x-1/2 bg-green-500 text-white py-3 px-6 rounded-lg shadow-lg">
    🎉 <%= successMessage %>
</div>
<script>
    setTimeout(() => document.getElementById("successMessage").style.display = "none", 3000);
</script>
<%
        sessionObj.removeAttribute("successMessage");
    }
%>

<!-- Navbar -->
<div class="bg-white text-gray-800 p-4 shadow-lg flex justify-between items-center">
    <h2 class="text-3xl font-bold text-indigo-700">📚 My Portal</h2>
    <div class="text-gray-600">👋 Hey, <%= sessionObj != null ? sessionObj.getAttribute("studentName") : "Student" %></div>
</div>

<!-- Main Content -->
<div class="flex justify-center items-center mt-8 px-4 sm:px-6 md:px-8 flex-grow">
    <div class="w-full max-w-4xl bg-white p-8 rounded-lg shadow-lg text-gray-800">
        <h3 class="text-2xl font-semibold mb-6 text-center">Welcome to Your Portal</h3>

        <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
            <div class="bg-indigo-100 p-6 shadow-lg rounded-lg text-center">
                <h4 class="text-lg font-semibold">📂 Total Assignments</h4>
                <p class="text-3xl font-bold mt-2">5</p>
            </div>
            <div class="bg-indigo-100 p-6 shadow-lg rounded-lg text-center">
                <h4 class="text-lg font-semibold">⏳ Pending Assignments</h4>
                <p class="text-3xl font-bold mt-2">2</p>
            </div>
        </div>

        <!-- My Assignments Link -->
        <div class="mt-6 text-center">
            <a href="#" class="text-indigo-600 hover:underline text-lg font-semibold">📝 View My Assignments</a>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-white text-gray-600 py-4 mt-8">
    <div class="text-center text-sm">© 2025 My Portal | All rights reserved</div>
</footer>

</body>
</html>
