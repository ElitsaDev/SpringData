package _02Ex;

import _02Ex.entities.Customer;
import _02Ex.entities.Product;
import _02Ex.entities.Sale;
import _02Ex.entities.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("codeFirstEx02");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Product product = new Product("product", 2.0, BigDecimal.valueOf(100));
        Customer customer = new Customer("customer", "customer@mail.bg", "asd");
        StoreLocation location = new StoreLocation("location");

        Sale sale = new Sale(product, customer, location);

        entityManager.persist(product);
        entityManager.persist(customer);
        entityManager.persist(location);

        entityManager.persist(sale);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
