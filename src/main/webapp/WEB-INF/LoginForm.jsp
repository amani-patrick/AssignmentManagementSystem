<%--
  Created by IntelliJ IDEA.
  User: amnii
  Date: 2/11/2025
  Time: 5:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Welcome! 🎉 Login</title>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      background: linear-gradient(135deg, #ff9a9e, #fad0c4);
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }
    .container {
      background: white;
      padding: 25px;
      border-radius: 15px;
      box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2);
      width: 350px;
      text-align: center;
    }
    h2 {
      margin-bottom: 15px;
      font-size: 22px;
    }
    .role-selection {
      display: flex;
      justify-content: space-around;
      margin-bottom: 20px;
    }
    .role {
      padding: 10px 20px;
      border: 2px solid #007bff;
      border-radius: 8px;
      cursor: pointer;
      transition: 0.3s;
      font-size: 16px;
    }
    .role:hover, .selected {
      background: #007bff;
      color: white;
    }
    input {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 16px;
    }
    button {
      width: 100%;
      padding: 10px;
      background: #28a745;
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 18px;
      cursor: pointer;
      margin-top: 15px;
      transition: 0.3s;
    }
    button:hover {
      background: #218838;
    }
    .forgot {
      display: block;
      margin-top: 10px;
      color: #007bff;
      text-decoration: none;
      font-size: 14px;
    }
    .forgot:hover {
      text-decoration: underline;
    }
  </style>
  <script>
    function selectRole(role) {
      document.getElementById("role").value = role;
      document.querySelectorAll(".role").forEach(el => el.classList.remove("selected"));
      document.getElementById(role).classList.add("selected");
    }
  </script>
</head>
<body>
<div class="container">
  <h2>Who are you logging in as? 😃</h2>
  <div class="role-selection">
    <div id="student" class="role" onclick="selectRole('student')">🎓 Student</div>
    <div id="teacher" class="role" onclick="selectRole('teacher')">👩‍🏫 Teacher</div>
  </div>
  <form action="login" method="post">
    <input type="hidden" name="category" id="role" required>
    <input type="email" name="email" placeholder="📧 Enter your email" required>
    <input type="password" name="password" placeholder="🔒 Enter your password" required>
    <button type="submit">🚀 Login</button>
    <a href="#" class="forgot">Forgot Password? 🤔</a>
  </form>
</div>
</body>
</html>
