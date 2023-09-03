package _06ExBonus;

import _06ExBonus.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("codeFirstEx06");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        Team team = new Team();
        Town town = new Town();
        Continent continent = new Continent();
        Country country = new Country();
        country.setId("BG");
        Color color = new Color();

        entityManager.persist(team);
        entityManager.persist(town);

        entityManager.persist(continent);
        entityManager.persist(country);

        entityManager.persist(color);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
