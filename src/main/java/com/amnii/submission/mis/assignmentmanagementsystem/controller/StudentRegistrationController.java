package com.amnii.submission.mis.assignmentmanagementsystem.controller;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Student;
import com.amnii.submission.mis.assignmentmanagementsystem.service.StudentService;
import com.amnii.submission.mis.assignmentmanagementsystem.util.HibernateUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;


public class StudentRegistrationController extends HttpServlet {
    private final StudentService service = new StudentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String category = req.getParameter("category");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if ("teacher".equalsIgnoreCase(category)) {
            req.getRequestDispatcher("/teacher_register").forward(req, resp);
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(req.getParameter("dob"));
        } catch (Exception e) {
            req.setAttribute("message", "<p style='color:red;'>Invalid date format.</p>");
            req.getRequestDispatcher("/WEB-INF/StudentRegister.jsp").forward(req, resp);
            return;
        }

        if (dob.isAfter(LocalDate.now())) {
            req.setAttribute("message", "<p style='color:red;'>Invalid date of birth. Please enter a past date.</p>");
            req.getRequestDispatcher("/WEB-INF/StudentRegister.jsp").forward(req, resp);
            return;
        }

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 15) {
            req.setAttribute("message", "<p style='color:red;'>You must be at least 15 years old to register.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
            return;
        }

        // Check if email already exists
        if (service.emailExists(email)) {
            req.setAttribute("message", "<p style='color:red;'>Email already exists or is in use.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
            return;
        }

        // Hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Create a student object and save it
        Student student = new Student(fname, lname, email, dob, age, hashedPassword);

        // Add student to the database
        boolean isAdded = addStudent(student);

        if (isAdded) {
            HttpSession session = req.getSession();
            session.setAttribute("student", student);
            session.setMaxInactiveInterval(30 * 60);

            req.setAttribute("message", "<p style='color:green;'>Registration successful.</p>");
            req.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(req, resp);
        } else {
            req.setAttribute("message", "<p style='color:red;'>Registration failed. Please try again.</p>");
            req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
        }
    }

    private boolean addStudent(Student student) {
        try {
            Session session = HibernateUtility.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
            session.close();
            return true; // Success
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failure
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/RegisterForm.jsp").forward(req, resp);
    }
}
