<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Assignment</title>
    <style>
        /* General Styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 100%;
            max-width: 600px;
            margin: 20px;
        }

        .form-title {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 20px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-size: 16px;
            color: #555;
            margin-bottom: 8px;
        }

        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            border-color: #007bff;
            outline: none;
        }

        .form-group textarea {
            resize: vertical;
            min-height: 120px;
        }

        .message {
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-size: 16px;
            text-align: center;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        button {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }

            .form-title {
                font-size: 22px;
            }

            .form-group input,
            .form-group textarea {
                font-size: 14px;
                padding: 10px;
            }

            button {
                font-size: 14px;
                padding: 10px;
            }
        }

        @media (max-width: 480px) {
            .container {
                margin: 10px;
                padding: 15px;
            }

            .form-title {
                font-size: 20px;
            }

            .form-group label {
                font-size: 14px;
            }

            .form-group input,
            .form-group textarea {
                font-size: 13px;
                padding: 8px;
            }

            button {
                font-size: 13px;
                padding: 8px;
            }
        }
    </style>
</head>

<body>
<div class="container">
    <h2 class="form-title">Create a New Assignment</h2>

    <!-- Display success/error messages -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <div class="message <%= message.contains("successful") ? "success" : "error" %>">
        <%= message %>
    </div>
    <% } %>

    <form action="create_assignment" method="post">
        <div class="form-group">
            <label for="title">Assignment Title:</label>
            <input type="text" id="title" name="title" placeholder="Enter assignment title" required />
        </div>

        <div class="form-group">
            <label for="description">Assignment Description:</label>
            <textarea id="description" name="description" rows="4"
                      placeholder="Provide a description for the assignment" required></textarea>
        </div>

        <div class="form-group">
            <label for="deadline">Deadline:</label>
            <input type="datetime-local" id="deadline" name="deadline" required />
        </div>

        <button type="submit">Create Assignment</button>
    </form>
</div>
</body>

</html>