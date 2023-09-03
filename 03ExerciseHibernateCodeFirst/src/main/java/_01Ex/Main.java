package _01Ex;
/*
За всяка задача трябва да се променя в persistence.xml
<persistence-unit name="codeFirstEx01">
 и името на базата, която се създава.
 Другите папки трябва да са Mark Directory as Excluded, освен текущата задача.
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("codeFirstEx01");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        WizardDeposits wizardDeposits = new WizardDeposits( null, "last name", "some notes",
                30,"wandMagic", (short) 5, null, LocalDateTime.now(),
                BigDecimal.valueOf(50000), BigDecimal.valueOf(2), BigDecimal.valueOf(1.2), LocalDateTime.now(),false);

        entityManager.persist(wizardDeposits);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
