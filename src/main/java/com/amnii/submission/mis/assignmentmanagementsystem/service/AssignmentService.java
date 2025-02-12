package com.amnii.submission.mis.assignmentmanagementsystem.service;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Assignment;
import com.amnii.submission.mis.assignmentmanagementsystem.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AssignmentService {
    protected static SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
    protected static Session session;
    protected static AssignmentService assignmentService;

    public static AssignmentService getInstance() {
        if (assignmentService == null) {
            assignmentService = new AssignmentService();
        }
        return assignmentService;
    }

    private AssignmentService() {}

    public boolean createAssignment(Assignment assignment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(assignment);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public List<Assignment> getAssignmentsByTeacherId(int teacherId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Assignment> query = session.createQuery("FROM Assignment a WHERE a.teacher.id = :teacherId", Assignment.class);
            query.setParameter("teacherId", teacherId);
            return query.getResultList();
        }
    }
}
