package com.amnii.submission.mis.assignmentmanagementsystem.controller;


import com.amnii.submission.mis.assignmentmanagementsystem.model.Teacher;
import com.amnii.submission.mis.assignmentmanagementsystem.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class TeacherLoginController extends HttpServlet {

    TeacherService service=new TeacherService();
    Teacher teacher=new Teacher();
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(req,res);

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String email=req.getParameter("email");
        String password=req.getParameter("password");
        String category=req.getParameter("category");
        if(category.equals("student")){
            req.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(req,res);
        }
        if(service.login(email,password)){
            HttpSession session = req.getSession();
            session.setAttribute("email",email);
            session.setAttribute("teacherName", teacher.getFirstName() + " " + teacher.getLastName());
            session.setAttribute("successMessage","Login successful welcome back ");
            session.setMaxInactiveInterval(30 * 60);
            req.getRequestDispatcher("/WEB-INF/TeacherDashboard.jsp").forward(req,res);
        }
        req.setAttribute("message","<p  style='color:red;'>Invalid Credentials</p>");
        req.getRequestDispatcher("/WEB-INF/LoginForm.jsp").forward(req,res);

    }
}
