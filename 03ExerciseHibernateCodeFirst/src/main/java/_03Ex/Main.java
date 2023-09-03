package _03Ex;

import _03Ex.entities.Course;
import _03Ex.entities.Student;
import _03Ex.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("codeFirstEx03");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Course course = new Course("Programming","Advanced", LocalDate.now(), LocalDate.of(2022,3,25), 150);

        Teacher teacher = new Teacher("firstName","lastName","+359888888","some@mail.bg", 2000);
        Student student = new Student("firstName","lastName","+359222255", 6.00f,1);

        entityManager.persist(course);
        entityManager.persist(teacher);
        entityManager.persist(student);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
