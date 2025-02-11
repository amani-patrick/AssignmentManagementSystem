<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Sign Up</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        function toggleFields() {
            let role = document.getElementById("role").value;
            let dobField = document.getElementById("dob-group");
            let courseField = document.getElementById("course-group");

            if (role === "teacher") {
                dobField.classList.add("hidden");
                courseField.classList.remove("hidden");
            } else {
                dobField.classList.remove("hidden");
                courseField.classList.add("hidden");
            }
        }
    </script>
</head>
<body class="flex justify-center items-center min-h-screen bg-gray-100">

<div class="bg-white p-8 rounded-xl shadow-md w-full max-w-md">
    <h2 class="text-2xl font-bold text-center text-indigo-700 mb-4">Create an Account</h2>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <p class="text-red-600 text-center mb-4"><%= message %></p>
    <% } %>

    <form action="student_register" method="post">
        <label class="block font-medium text-gray-700">Register as:</label>
        <select id="role" name="category" onchange="toggleFields()" required
                class="w-full p-3 border rounded-lg mt-2 bg-gray-50">
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
        </select>

        <label class="block font-medium text-gray-700 mt-4">First Name</label>
        <input type="text" id="fname" name="fname" required
               class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <label class="block font-medium text-gray-700 mt-4">Last Name</label>
        <input type="text" id="lname" name="lname" required
               class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <label class="block font-medium text-gray-700 mt-4">Email</label>
        <input type="email" id="email" name="email" required
               class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <div id="dob-group">
            <label class="block font-medium text-gray-700 mt-4">Date of Birth</label>
            <input type="date" id="dob" name="dob" required
                   class="w-full p-3 border rounded-lg mt-2 bg-gray-50">
        </div>

        <div id="course-group" class="hidden">
            <label class="block font-medium text-gray-700 mt-4">Course</label>
            <input type="text" id="course" name="course"
                   class="w-full p-3 border rounded-lg mt-2 bg-gray-50">
        </div>

        <label class="block font-medium text-gray-700 mt-4">Password</label>
        <input type="password" id="password" name="password" required
               class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <button type="submit"
                class="w-full bg-indigo-600 text-white py-3 rounded-lg mt-6 hover:bg-indigo-700 transition">
            Sign Up 🚀
        </button>
    </form>
</div>

</body>
</html>
