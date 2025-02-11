package com.amnii.submission.mis.assignmentmanagementsystem.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.amnii.submission.mis.assignmentmanagementsystem.util.HibernateUtility;

public class TeacherService {
    protected static SessionFactory sessionFactory= HibernateUtility.getSessionFactory();
    protected static Session session;
    protected static TeacherService teacherService;
    public static TeacherService getInstance(){
        if(teacherService==null){
            teacherService=new TeacherService();
            return teacherService;
        }
        return teacherService;
    }
    public TeacherService(){}
    public void addTeacher(Teacher teacher){
        session=sessionFactory.openSession();
        session.beginTransaction();
        session.persist(teacher);
        session.getTransaction().commit();
        session.close();
    }
    public boolean emailExists(String email){
        session=sessionFactory.openSession();
        session.beginTransaction();

        Query<Teacher> query=session.createQuery("FROM Teacher WHERE email= :email");
        query.setParameter("email", email);

        boolean exists=!query.getResultList().isEmpty();
        session.close();
        return exists;

    }
    public Teacher getTeacherByEmail(String email) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Teacher> query = session.createQuery("FROM Teacher WHERE email= :email", Teacher.class);
        query.setParameter("email", email);
        Teacher teacher = query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return teacher;
    }
    public boolean Login(String email,String password){
        session=sessionFactory.openSession();
        session.beginTransaction();

        Query<Teacher> query=session.createQuery("from Teacher WHERE email=:email");
        query.setParameter("email",email);
        Teacher teacher=query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        if(teacher !=null){
            return BCrypt.checkpw(password,teacher.getPassword());
        }
        return false;
    }

}
