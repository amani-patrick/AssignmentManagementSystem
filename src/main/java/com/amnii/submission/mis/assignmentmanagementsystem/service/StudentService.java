package com.amnii.submission.mis.assignmentmanagementsystem.service;

import com.amnii.submission.mis.assignmentmanagementsystem.model.Student;
import com.amnii.submission.mis.assignmentmanagementsystem.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

public class StudentService {
    protected static SessionFactory sessionFactory= HibernateUtility.getSessionFactory();
    protected static Session session;
    protected static StudentService studentService;
    public static StudentService getInstance(){
        if(studentService==null){
            studentService=new StudentService();
            return studentService;
        }
        return studentService;
    }
    public StudentService(){}
    public void addStudent(Student student){
        session=sessionFactory.openSession();
        session.beginTransaction();
        session.persist(student);
        session.getTransaction().commit();
        session.close();
    }
    public boolean emailExists(String email){
        session=sessionFactory.openSession();
        session.beginTransaction();

        Query<Student> query=session.createQuery("FROM Student WHERE email= :email");
        query.setParameter("email", email);

        boolean exists=!query.getResultList().isEmpty();
        session.close();
        return exists;

    }
    public Student getStudentByEmail(String email) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Student> query = session.createQuery("FROM Student WHERE email= :email", Student.class);
        query.setParameter("email", email);
        Student student = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return student;
    }

    public  boolean login(String email,String password){
        session=sessionFactory.openSession();
        session.beginTransaction();
        Query<Student> query=session.createQuery("FROM Student WHERE email= :email",Student.class);
        query.setParameter("email", email);
        Student student=query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (student != null) {
            // Compare stored hashed password with provided password
            return BCrypt.checkpw(password, student.getPassword());
        }
        return false;
    }

}