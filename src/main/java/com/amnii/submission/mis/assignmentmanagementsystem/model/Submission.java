package com.amnii.submission.mis.assignmentmanagementsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;  // Import LocalDate

@Entity
@Table
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "submitted_at", nullable = false)
    private LocalDate submittedAt;  // Changed from Date to LocalDate

    public Submission() {
        this.submittedAt = LocalDate.now();  // Initialize with the current date
    }

    public Submission(Assignment assignment, Student student, String filePath) {
        this.assignment = assignment;
        this.student = student;
        this.filePath = filePath;
        this.submittedAt = LocalDate.now();  // Initialize with the current date
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDate getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDate submittedAt) { this.submittedAt = submittedAt; }
}
