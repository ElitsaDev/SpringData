import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Engine implements Runnable {
    private EntityManager entityManager;
    private BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.print("Please select the task number you want to check: ");
        int taskNumber = 0;

        try {
            taskNumber = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println("Sorry, but you must enter the numerical value.Try again!");
        }
        System.out.print("Task: ");

        switch (taskNumber) {
            case 1:
                System.out.println("01.Setup - Change the port, username and password accordingly to your settings");
                break;
            case 2:
                System.out.println("02.Change casing.");
                changeCasing();
                break;
            case 3:
                System.out.println("03.Contains Employee.");
                System.out.print("Enter employee first and last name: ");
                try {
                    String fullName = reader.readLine().replace(" ", "");
                    containsEmployeeFirstAndLastName(fullName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

              /* Друго Решение на задачата за по-общият случай,
               когато се търси по пълното име. Него не съм го пращала за проверка
                System.out.println("Enter employee full name: ");
                try {
                    String[] fullName = reader.readLine().split("\\s+");
                    containsEmployeeFullName(fullName);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
            case 4:
                System.out.println("04.Employees with Salary Over 50 000.");
                employeesWithSalaryOver50000();
                break;
            case 5:
                System.out.println("05.Employees from Department.");
                employeesFromDepartment();
                break;
            case 6:
                System.out.println("06.Adding a New Address and Updating Employee.");
                System.out.print("Enter employee's last name: ");
                try {
                    String lastName = reader.readLine();
                    addingNewAddressAndUpdatingEmployee(lastName.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                System.out.println("07.Addresses with Employee Count.");
                addressesWithEmployeeCount();
                break;
            case 8:
                System.out.println("08.Get Employee with Project.");
                System.out.print("Enter employee's id: ");
                try {
                    int id = Integer.parseInt(reader.readLine());
                    getEmployeeWithProject(id);
                } catch (IOException | NullPointerException e) {
                   System.out.println("Employee with this id does not exist in DataBase!");
                }
                break;
            case 9:
                System.out.println("09. Find Latest 10 Projects");
                findLatestTenProjects();
                break;
            case 10:
                System.out.println("10. Increase Salaries");
                increaseSalaries();
                break;
            case 11:
                System.out.println("Find Employees by First Name");
                System.out.print("Enter the staring letters of the Employee's first name: ");
                try {
                    String pattern = reader.readLine();
                    findEmployeesByFirstName(pattern.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 12:
                System.out.println("Employees Maximum Salaries");
                employeesMaximumSalaries();
                break;
            case 13:
                System.out.println("Remove Towns");
                System.out.print("Enter the name of the town you want to remove: ");
                try {
                    String townName = reader.readLine();
                    removeTowns(townName.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Sorry, but you have to enter task number between 1 and 10.Try again!");
        }
    }

    private void changeCasing() {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("" +
                        " UPDATE Town t" +
                        " SET t.name = upper(t.name)" +
                        " WHERE char_length(t.name) <= 5")
                .executeUpdate();

        this.entityManager.getTransaction().commit();
    }

    private void containsEmployeeFirstAndLastName(String readLine) {
        Employee fullName = this.entityManager
                .createQuery("SELECT e FROM Employee AS e" +
                                " WHERE concat_ws('',e.firstName,e.lastName) = :name"
                        , Employee.class)
                .setParameter("name", readLine)
                .getResultStream().findFirst().orElse(null);

        System.out.println(fullName != null ? "Yes" : "No");
    }

    private void containsEmployeeFullName(String[] fullName) {
     /*  Employee employeeFullName = this.entityManager
                .createQuery("SELECT e FROM Employee AS e" +
                                " WHERE concat_ws('',e.firstName,e.middleName,e.lastName) = :name"
                        , Employee.class)
                .setParameter("name", fullName)
                .getSingleResult();
        System.out.println(employeeFullName);

        System.out.println(employeeFullName!= null ? "Yes" : "No");
     */

        String firstNameCheck;
        String middleNameCheck;
        String lastNameCheck;

        if (fullName.length == 2) {
            firstNameCheck = fullName[0];
            middleNameCheck = "";
            lastNameCheck = fullName[1];
        } else {
            firstNameCheck = fullName[0];
            middleNameCheck = fullName[1];
            lastNameCheck = fullName[2];
        }
        Long employeeName = this.entityManager
                .createQuery("SELECT COUNT(e) FROM Employee e" +
                        " WHERE e.firstName = :first_name AND" +
                        (fullName.length > 2 ? " e.middleName = :middle_name AND" : " :middle_name = :middle_name AND") +
                        " e.lastName = :last_name", Long.class)
                .setParameter("first_name", firstNameCheck)
                .setParameter("middle_name", middleNameCheck)
                .setParameter("last_name", lastNameCheck).getSingleResult();

        System.out.println(employeeName != 0 ? "Yes" : "No");
    }

    private void employeesWithSalaryOver50000() {
        this.entityManager
                .createQuery("" +
                        " SELECT e.firstName FROM Employee AS e" +
                        " WHERE e.salary > 50000", String.class)
                .getResultStream()
                .forEach(System.out::println);
    }

    private void employeesFromDepartment() {
        this.entityManager.createQuery("" +
                " SELECT e FROM Employee AS e" +
                " WHERE e.department.name = 'Research and Development'" +
                " ORDER BY e.salary, e.id", Employee.class)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getDepartment().getName(),
                        e.getSalary()));
    }

    private void addingNewAddressAndUpdatingEmployee(String lastName) {
        String newAddress = "Vitoshka 15";
        Address address = new Address();
        address.setText(newAddress);

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);

        this.entityManager.createQuery("" +
                " UPDATE Employee e" +
                " SET e.address = :address" +
                " WHERE e.lastName = : name")
                .setParameter("address", address)
                .setParameter("name", lastName)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void addressesWithEmployeeCount() {
        this.entityManager.createQuery("" +
                " SELECT a FROM Address AS a" +
                " ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultStream().forEach(a -> System.out.printf("%s, %s - %d employees%n",
                a.getText(),
                a.getTown() != null ? a.getTown().getName() : "",
                a.getEmployees().size()));
        entityManager.close();
    }

    private void getEmployeeWithProject(int id) {
        Employee employee = this.entityManager.find(Employee.class, id);
        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        List<String> projectNames = new ArrayList<>();
        if(employee.getProjects().isEmpty()){
            System.out.println("  This employee does not have any project in DataBase!");
        }
        for (Project project : employee.getProjects()) {
            String name = project.getName();
            projectNames.add(name);
        }

        Arrays.stream(projectNames.toArray())
                .sorted()
                .forEach(p -> System.out.printf("\t%s%n", p));
    }

    private void findLatestTenProjects() {
        List<Project> projects = this.entityManager
                .createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC, p.name ASC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.sort(Comparator.comparing(Project::getName));

        for (Project project : projects) {
            System.out.printf("Project name: %s%n" +
                    " \tProject Description: %s%n", project.getName(), project.getDescription());

            if (project.getStartDate() != null) {
                System.out.printf("\tProject Start Date:%s%n", project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                System.out.println("\tProject Start Date:null");
            }

            if (project.getEndDate() != null) {
                System.out.printf("\tProject End Date:%s%n", project.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                System.out.println("\tProject End Date:null");
            }
        }
    }

    private void increaseSalaries() {
        this.entityManager.getTransaction().begin();

        var resultList = this.entityManager.createQuery("" +
                " SELECT id FROM Department AS d" +
                " WHERE d.name IN(:names)")
                .setParameter("names", Set.of("Engineering", "Tool Design", "Marketing", "Information Services"))
                .getResultList();

        int updatedSalaries = this.entityManager
                .createQuery("" +
                        " UPDATE Employee AS e" +
                        " SET e.salary = e.salary * 1.12" +
                        " WHERE e.department.id IN(:ids)")
                .setParameter("ids", resultList)
                .executeUpdate();
        System.out.printf("Affected %d rows:%n", updatedSalaries);

        this.entityManager.createQuery("SELECT e FROM Employee AS e" +
                " WHERE e.department.id IN(:ids)", Employee.class)
                .setParameter("ids", resultList)
                .getResultStream()
                .forEach(employee -> System.out.printf("%s %s ($%.2f)%n",
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary()));

        this.entityManager.getTransaction().commit();
    }

    private void findEmployeesByFirstName(String pattern) {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee AS e" +
                        " WHERE lower(e.firstName) LIKE ?1", Employee.class)
                .setParameter(1, pattern.toLowerCase() + "%")
                .getResultList();

        employees.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(),
                e.getLastName(),
                e.getJobTitle(),
                e.getSalary()));
    }

    private void employeesMaximumSalaries() {
        Stream<Department> resultStream = this.entityManager.createQuery("" +
                " SELECT DISTINCT d FROM Department AS d" +
                " JOIN Employee AS e" +
                " ON d.id = e.department.id" +
                " WHERE e.salary NOT BETWEEN 30000 AND 70000" +
                " ORDER BY d.id ASC, e.salary DESC", Department.class).getResultStream();

        resultStream
                .map(e -> e.getEmployees().stream()
                        .max(Comparator.comparingDouble(d -> d.getSalary().doubleValue()))
                        .orElseThrow(IllegalStateException::new))
                .filter(e -> e.getSalary().compareTo(new BigDecimal("30000")) < 0 || e.getSalary().compareTo(new BigDecimal("70000")) > 0)
                .forEach(e -> System.out.printf("%s %.2f%n", e.getDepartment().getName(), e.getSalary()));
    }

    private void removeTowns(String townName) {
        this.entityManager.getTransaction().begin();

        Optional<Town> townToDelete = entityManager.createQuery("FROM Town t WHERE t.name = :town_param", Town.class).setParameter("town_param", townName).getResultStream().findFirst();

        if (townToDelete.isEmpty()) {
            System.out.println("In DataBase there is no such town.");
            return;
        }

        Integer townIdToDelete = townToDelete.get().getId();

        List<Integer> employeeIds = entityManager.createQuery(" SELECT e.id FROM Employee AS e WHERE e.address.town.id = :town_id", Integer.class)
                .setParameter("town_id", townIdToDelete)
                .getResultList();

        entityManager.createQuery("" +
                " UPDATE Employee AS e" +
                " SET e.address.id = null" +
                " WHERE e.id IN(:ids)")
                .setParameter("ids", employeeIds).executeUpdate();

        int addressesToDelete = entityManager.createQuery("" +
                " DELETE FROM Address AS a" +
                " WHERE a.town.id = :town_id")
                .setParameter("town_id", townIdToDelete)
                .executeUpdate();

        entityManager.createQuery("" +
                " DELETE FROM Town AS t" +
                " WHERE t.name = :town_name")
                .setParameter("town_name", townName)
                .executeUpdate();

        if (addressesToDelete == 1) {
            System.out.printf("1 address in %s deleted%n", townName);
        } else if (addressesToDelete > 1) {
            System.out.printf("%d addresses in %s deleted%n", addressesToDelete, townName);
        }
        this.entityManager.getTransaction().commit();
    }
}

