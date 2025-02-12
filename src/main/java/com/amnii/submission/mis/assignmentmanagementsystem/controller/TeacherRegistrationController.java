package com.amnii.submission.mis.assignmentmanagementsystem.controller;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Teacher;
import com.amnii.submission.mis.assignmentmanagementsystem.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;


public class TeacherRegistrationController extends HttpServlet {
    private final TeacherService service = TeacherService.getInstance(); // Singleton instance

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String course = req.getParameter("course");

        // Check if email already exists
        if (service.emailExists(email)) {
            req.setAttribute("message", "<p style='color:red;'>Email already exists or is in use.</p>");
            req.getRequestDispatcher("/WEB-INF/TeacherRegistration.jsp").forward(req, resp);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Teacher teacher = new Teacher(fname, lname, email, course, hashedPassword);
        boolean isAdded = service.addTeacher(teacher);

        if (isAdded) {
            HttpSession session = req.getSession();
            session.setAttribute("teacher", teacher);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            req.setAttribute("message", "<p style='color:green;'>Registration successful.</p>");
            req.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "<p style='color:red;'>Registration failed. Please try again.</p>");
            req.getRequestDispatcher("/WEB-INF/TeacherRegistration.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/TeacherRegistration.jsp").forward(req, resp);
    }
}
