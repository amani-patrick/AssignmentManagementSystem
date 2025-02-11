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
import java.io.PrintWriter;

public class TeacherRegistrationController extends HttpServlet {
    String message;
    TeacherService service=new TeacherService();
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String course=req.getParameter("course");
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Check if email already exists
        if (service.emailExists(email)) {
            req.setAttribute("message","<p  style='color:red;'>Email already exists or is in use.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
        }

        // Create a student object and save it
        Teacher teacher=new Teacher(fname,lname,email,course,hashedPassword);
        service.addTeacher(teacher);
        req.setAttribute("message","<p  style='color:green;'>Registration successful.</p>");

        HttpSession session= req.getSession();
        session.setAttribute("teacher",teacher);
        session.setMaxInactiveInterval(30*60);
        // Redirect to a confirmation page
        req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("//WEB-INF/RegisterForm.jsp").forward(req,resp);
    }
}
