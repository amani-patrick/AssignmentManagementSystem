package com.amnii.submission.mis.assignmentmanagementsystem.controller;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Assignment;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Student;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Submission;
import com.amnii.submission.mis.assignmentmanagementsystem.service.AssignmentService;
import com.amnii.submission.mis.assignmentmanagementsystem.service.SubmissionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
@WebServlet("/student-assignments")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class StudentAssignmentController extends HttpServlet {
    private final AssignmentService assignmentService = AssignmentService.getInstance();
    private final SubmissionService submissionService = SubmissionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        request.setAttribute("assignments", assignments);
        request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("StudentName") == null) {
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
            return;
        }

        Student student = (Student) session.getAttribute("loggedStudent");
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);

        if (assignment == null) {
            request.setAttribute("errorMessage", "Assignment not found!");
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
            return;
        }

        // Check if student already submitted
        if (submissionService.hasStudentSubmittedAssignment(student, assignment)) {
            request.setAttribute("errorMessage", "You have already submitted this assignment.");
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
            return;
        }

        // Handle file upload
        Part filePart = request.getPart("submissionFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + "uploads";

        // Save file to server
        filePart.write(uploadPath + "/" + fileName);

        // Create and set submission details
        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setFilePath("uploads/" + fileName);

        // Save submission to database
        String studentEmail = student.getEmail(); // Assuming email is available
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        LocalDate birthDate = student.getBirthDate();
        int age = student.getAge();
        String password = student.getPassword();  // Be sure to handle this carefully in a real-world scenario

        // Call the submitAssignment method with additional details
        submissionService.submitAssignment(submission, studentEmail, firstName, lastName, birthDate, age, password);

        request.setAttribute("successMessage", "Assignment submitted successfully!");
        request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
    }
}
