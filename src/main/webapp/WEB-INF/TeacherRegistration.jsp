<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher Sign Up</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="flex justify-center items-center min-h-screen bg-gray-100">

<div class="bg-white p-8 rounded-xl shadow-md w-full max-w-md">
    <h2 class="text-2xl font-bold text-center text-indigo-700 mb-4">Teacher Registration</h2>

    <% String message = (String) request.getAttribute("message");
        if (message != null) { %>
    <p class="text-red-600 text-center mb-4"><%= message %></p>
    <% } %>

    <form action="teacher_register" method="post">
        <label class="block font-medium text-gray-700">First Name</label>
        <input type="text" name="fname" required class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <label class="block font-medium text-gray-700 mt-4">Last Name</label>
        <input type="text" name="lname" required class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <label class="block font-medium text-gray-700 mt-4">Email</label>
        <input type="email" name="email" required class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <label class="block font-medium text-gray-700 mt-4">Course</label>
        <input type="text" name="course" required class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <label class="block font-medium text-gray-700 mt-4">Password</label>
        <input type="password" name="password" required class="w-full p-3 border rounded-lg mt-2 bg-gray-50">

        <button type="submit"
                class="w-full bg-indigo-600 text-white py-3 rounded-lg mt-6 hover:bg-indigo-700 transition">
            Sign Up
        </button>
    </form>
</div>

</body>
</html>
