package com.amnii.submission.mis.assignmentmanagementsystem.controller;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Assignment;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Teacher;
import com.amnii.submission.mis.assignmentmanagementsystem.service.AssignmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@WebServlet("/create-assignment")
public class AssignmentCreationController extends HttpServlet {
    private final AssignmentService assignmentService = AssignmentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("loggedTeacher");


        if (teacher == null) {
            request.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(request, response);
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String deadlineStr = request.getParameter("deadline");

        if (title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                deadlineStr == null || deadlineStr.trim().isEmpty()) {
            request.setAttribute("message", "<p style='color:red;'>All fields are required.</p>");
            request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
            return;
        }

        try {
            // Parse the deadline date
            LocalDateTime deadline = LocalDateTime.parse(deadlineStr);
            Assignment assignment = new Assignment(title, description, deadline, teacher);

            // Attempt to create the assignment using the service
            boolean success = assignmentService.createAssignment(assignment);
            if (success) {
                request.setAttribute("message", "<p style='color:green;'>Assignment created successfully!</p>");
                request.getRequestDispatcher("/WEB-INF/TeacherDashboard.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "<p style='color:red;'>Failed to create assignment.</p>");
                request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
            }
        } catch (DateTimeParseException e) {
            request.setAttribute("message", "<p style='color:red;'>Invalid date format. Please use the correct format (e.g. yyyy-MM-ddTHH:mm:ss).</p>");
            request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "<p style='color:red;'>An unexpected error occurred.</p>");
            request.getRequestDispatcher("/WEB-INF/CreateAssignment.jsp").forward(request, response);
        }
    }
}
