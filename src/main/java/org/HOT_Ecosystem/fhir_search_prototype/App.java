package org.HOT_Ecosystem.fhir_search_prototype;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.HOT_Ecosystem.fhir_search_prototype.Student;
import org.HOT_Ecosystem.fhir_search_prototype.HibernateUtil;

public class App {

    private static void printSessionStatus(Session session) {
        System.out.println("connected? " + session.isConnected());
        System.out.println("open? " + session.isOpen());
        System.out.println("stats: " + session.getStatistics());
    }
    public static void main(String[] args) {

        Student student = new Student("Ramesh", "Fadatare", "rameshfadatare@javaguides.com");
        Student student1 = new Student("John", "Cena", "john@javaguides.com");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            printSessionStatus(session);

            // save the student objects
            System.out.println("put first");
            session.save(student);
            System.out.println("put second");
            session.save(student1);

            // commit transaction
            System.out.println("commit");
            transaction.commit();
        } catch (Exception e) {
        	System.out.println("App Exception putting student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Querying...");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("query"); 
            List < Student > students = session.createQuery("from Student", Student.class).list();

            System.out.println("list");
            students.forEach(s -> System.out.println(s.getFirstName()));
        } catch (Exception e) {
        	System.out.println("App Exception querying student objects");
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }

        System.out.println("BYE");
        HibernateUtil.shutdown();
    }
}