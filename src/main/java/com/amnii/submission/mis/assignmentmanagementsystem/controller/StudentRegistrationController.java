package com.amnii.submission.mis.assignmentmanagementsystem.controller;


import com.amnii.submission.mis.assignmentmanagementsystem.model.Student;
import com.amnii.submission.mis.assignmentmanagementsystem.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;

public class StudentRegistrationController extends HttpServlet {
    StudentService service = new StudentService();
    String message;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String category = req.getParameter("category");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        LocalDate dob;
        if ("teacher".equals(category)) {
            req.getRequestDispatcher("/teacher-register").forward(req,resp);
            return;
        }

        try {
            dob = LocalDate.parse(req.getParameter("dob"));
        } catch (Exception e) {
            req.setAttribute("message","<p  style='color:red;'>Invalid date format.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
            return;
        }

        // Check if the date of birth is in the future
        if (dob.isAfter(LocalDate.now())) {
            req.setAttribute("message","<p  style='color:red;'>Invalid date of birth. Please enter a valid past date.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
            return;
        }

        // Calculate age
        int age = Period.between(dob, LocalDate.now()).getYears();

        // Check if the user is under 15
        if (age < 15) {
            req.setAttribute("message","<p  style='color:red;'>You must be at least 15 years old to register.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
            return;
        }

        // Redirect teachers to the teacher registration page


        // Hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Check if email already exists
        if (service.emailExists(email)) {
            req.setAttribute("message","<p  style='color:red;'>Email already exists or is in use.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
            return;
        }

        // Create a student object and save it
        Student student = new Student(fname, lname, email, dob, age, hashedPassword);
        service.addStudent(student);
        req.setAttribute("message","<p  style='color:green;'>Registration successful.</p>");

        // Set session attributes
        HttpSession session = req.getSession();
        session.setAttribute("student", student);
        session.setMaxInactiveInterval(30 * 60);

        // Redirect to a confirmation page
        req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
    }
}
