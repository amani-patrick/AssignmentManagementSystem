package com.amnii.submission.mis.assignmentmanagementsystem.service;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Assignment;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Student;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Submission;
import com.amnii.submission.mis.assignmentmanagementsystem.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SubmissionService {
    private static SubmissionService submissionService;

    public static synchronized SubmissionService getInstance() {
        if (submissionService == null) {
            submissionService = new SubmissionService();
        }
        return submissionService;
    }

    private SubmissionService() {}

    /**
     * Submits an assignment by saving submission details in the database.
     * If the student doesn't exist, it will add a new student.
     */
    public boolean submitAssignment(Submission submission, String studentEmail, String firstName, String lastName, LocalDate birthDate, int age, String password) {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // Check if the student exists
                Student student = getStudentByEmail(studentEmail);

                // If student doesn't exist, create a new student and persist it
                if (student == null) {
                    student = new Student();
                    student.setEmail(studentEmail);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setBirthDate(birthDate);  // Set LocalDate directly
                    student.setAge(age);
                    student.setPassword(password);  // Be sure to hash the password before storing in real-world use cases.
                    session.persist(student);
                }

                // Set the student in the submission
                submission.setStudent(student);

                // Persist the submission
                session.persist(submission);

                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Checks if a student already exists based on email.
     */
    private Student getStudentByEmail(String email) {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery(
                    "FROM Student s WHERE s.email = :email", Student.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        }
    }

    /**
     * Gets all submissions made by a specific student.
     */
    public List<Submission> getSubmissionsByStudent(Student student) {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Query<Submission> query = session.createQuery(
                    "FROM Submission s WHERE s.student = :student", Submission.class);
            query.setParameter("student", student);
            return query.getResultList();
        }
    }

    /**
     * Gets all submissions for a specific assignment.
     */
    public List<Submission> getSubmissionsByAssignment(Assignment assignment) {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Query<Submission> query = session.createQuery(
                    "FROM Submission s WHERE s.assignment = :assignment", Submission.class);
            query.setParameter("assignment", assignment);
            return query.getResultList();
        }
    }

    /**
     * Checks if a student has already submitted an assignment.
     */
    public boolean hasStudentSubmittedAssignment(Student student, Assignment assignment) {
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(s) FROM Submission s WHERE s.student = :student AND s.assignment = :assignment",
                    Long.class
            );
            query.setParameter("student", student);
            query.setParameter("assignment", assignment);
            return query.getSingleResult() > 0;
        }
    }
}
