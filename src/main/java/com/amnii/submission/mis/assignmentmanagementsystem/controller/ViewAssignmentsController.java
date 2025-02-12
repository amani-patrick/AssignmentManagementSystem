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
import java.util.List;

@WebServlet("/view-assignments")
public class ViewAssignmentsController extends HttpServlet {

    private final AssignmentService assignmentService = AssignmentService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve teacher from session
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("loggedTeacher");


        if (teacher == null) {
            request.getRequestDispatcher("/WEB-INF/TeacherDashboard.jsp").forward(request, response);
            return;
        }

        int teacherId = teacher.getId();
        System.out.println("Teacher id from session: " + teacherId);

        List<Assignment> assignments = assignmentService.getAssignmentsByTeacherId(teacherId);

        if (assignments == null || assignments.isEmpty()) {
            // If no assignments are found, set a message to inform the user
            request.setAttribute("message", "No assignments found.");
        }

        request.setAttribute("assignments", assignments);

        request.getRequestDispatcher("/WEB-INF/ViewAssignment.jsp").forward(request, response);
    }
}
