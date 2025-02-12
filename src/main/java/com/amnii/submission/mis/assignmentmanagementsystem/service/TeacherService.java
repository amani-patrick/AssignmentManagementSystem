package com.amnii.submission.mis.assignmentmanagementsystem.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.amnii.submission.mis.assignmentmanagementsystem.util.HibernateUtility;
import com.amnii.submission.mis.assignmentmanagementsystem.model.Teacher;
import org.mindrot.jbcrypt.BCrypt;

public class TeacherService {
    private static final SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
    private static TeacherService teacherService;

    public static TeacherService getInstance() {
        if (teacherService == null) {
            teacherService = new TeacherService();
        }
        return teacherService;
    }

    public TeacherService() {}

    public boolean addTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(teacher);
            transaction.commit();
            return true; // Successfully added
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false; // Failed to add
        }
    }

    public boolean emailExists(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(t) FROM Teacher t WHERE t.email = :email", Long.class);
            query.setParameter("email", email);
            query.setMaxResults(1);
            return query.uniqueResult() > 0;
        }
    }

    public Teacher getTeacherByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<Teacher> query = session.createQuery("FROM Teacher WHERE email = :email", Teacher.class);
            query.setParameter("email", email);

            return query.uniqueResult();
        }
    }

    public boolean login(String email, String password) {
        Teacher teacher = getTeacherByEmail(email);

        if (teacher == null) {
            System.out.println("Teacher not found with email: " + email);
            return false;
        }

        // Compare hashed password with provided password
        return BCrypt.checkpw(password, teacher.getPassword());
    }
}
