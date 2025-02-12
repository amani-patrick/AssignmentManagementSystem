package com.amnii.submission.mis.assignmentmanagementsystem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.amnii.submission.mis.assignmentmanagementsystem.service.StudentService;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Student;

import java.io.IOException;

public class StudentLoginController extends HttpServlet {
    private String message;
    StudentService service=new StudentService();


    public  StudentLoginController(){}
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category=request.getParameter("category");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        if(category.equals("teacher")){
            request.getRequestDispatcher("teacher_login").forward(request,response);
        }
        if (service.login(email, password)) {
            HttpSession session = request.getSession();
            Student student = service.getStudentByEmail(email);

            session.setAttribute("email", email);
            session.setAttribute("studentName", student.getFirstName() + " " + student.getLastName());
            session.setAttribute("successMessage", "Login successful, welcome back " + student.getFirstName());
            session.setMaxInactiveInterval(30 * 60);

            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
        }
        request.setAttribute("message","<p  style='color:red;'>Invalid Credentials</p>");
        request.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(request,response);



    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(request,response);

    }


    public void destroy() {
    }
}
