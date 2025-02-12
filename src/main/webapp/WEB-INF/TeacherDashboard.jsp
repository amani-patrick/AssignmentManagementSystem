<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50">
<%
    HttpSession sessionObj = request.getSession(false);
    String successMessage = (sessionObj != null) ? (String) sessionObj.getAttribute("successMessage") : null;
    if (successMessage != null) {
%>
<div id="successMessage" class="fixed top-4 left-1/2 transform -translate-x-1/2 bg-green-600 text-white py-2 px-6 rounded-lg shadow-lg">
    ✅ <%= successMessage %>
</div>
<script>
    setTimeout(() => document.getElementById("successMessage").style.display = "none", 3000);
</script>
<% sessionObj.removeAttribute("successMessage"); %>
<% } %>

<div class="flex h-screen">
    <!-- Sidebar -->
    <div class="w-72 bg-gray-900 text-gray-300 p-6 flex flex-col">
        <h2 class="text-2xl font-bold text-white">🎓 Teacher's Panel</h2>
        <ul class="mt-6">
            <li class="flex items-center gap-3 py-3 px-4 hover:bg-gray-700 rounded cursor-pointer">
                🏡 Dashboard
            </li>
            <li class="flex items-center gap-3 py-3 px-4 hover:bg-gray-700 rounded cursor-pointer">
                📚 My Assignments
            </li>
            <li class="flex items-center gap-3 py-3 px-4 hover:bg-gray-700 rounded cursor-pointer">
                📩 Submitted Work
            </li>
            <li class="flex items-center gap-3 py-3 px-4 hover:bg-gray-700 rounded cursor-pointer">
                <a href="create_assignment" > ➕ New Assignment</a>

            </li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="flex-1 p-8">
        <!-- Navbar -->
        <%
            String teacherName = (sessionObj != null) ? (String) sessionObj.getAttribute("teacherName") : "Teacher";
        %>
        <div class="flex justify-between items-center bg-white p-5 shadow-md rounded-lg">
            <h2 class="text-2xl font-semibold text-gray-800">Welcome, Instructor 🎉</h2>
            <div class="text-gray-700">👨‍🏫 <%= teacherName %></div>
        </div>

        <!-- Dashboard Stats -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
            <div class="bg-white p-6 shadow-md rounded-xl flex flex-col items-center text-gray-800">
                <h3 class="text-lg font-semibold">📂 Total Assignments</h3>
                <p class="text-3xl font-bold mt-2">10</p>
            </div>
            <div class="bg-white p-6 shadow-md rounded-xl flex flex-col items-center text-gray-800">
                <h3 class="text-lg font-semibold">📬 Submitted Assignments</h3>
                <p class="text-3xl font-bold mt-2">7</p>
            </div>
            <div class="bg-white p-6 shadow-md rounded-xl flex flex-col items-center text-gray-800">
                <h3 class="text-lg font-semibold">⌛ Pending Submissions</h3>
                <p class="text-3xl font-bold mt-2">3</p>
            </div>
        </div>

        <!-- Create Assignment Section -->
        <div class="mt-8 bg-white p-6 shadow-md rounded-xl">
            <h3 class="text-lg font-semibold">📝 Create a New Assignment</h3>
            <form class="mt-5 space-y-4">
                <div>
                    <label class="block text-gray-700 font-medium">Title</label>
                    <input type="text" class="w-full p-3 border rounded-lg mt-1 focus:ring focus:ring-blue-400">
                </div>

                <div>
                    <label class="block text-gray-700 font-medium">Description</label>
                    <textarea class="w-full p-3 border rounded-lg mt-1 focus:ring focus:ring-blue-400"></textarea>
                </div>

                <button class="w-full bg-indigo-600 text-white py-3 rounded-lg hover:bg-indigo-700 transition">
                    🚀 Publish Assignment
                </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
