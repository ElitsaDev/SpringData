package _04Ex;

import _04Ex.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, ParseException {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("codeFirstEx04");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        int input;
        System.out.print("Please enter 1 for insert data for a new patient, or 2 for finding patient in DateBase: ");
        try {
            input = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Wrong format of input! Try again!");
            return;
        }

        switch (input) {
            case 1:
                System.out.print("Please enter number of patients, you want to insert in database: ");
                int numberOfPatients;
                try {
                    numberOfPatients = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("You do not enter any number! Try again!");
                    break;
                }

                Patient patient;
                List<Patient> patients = new ArrayList<>();

                while (numberOfPatients-- > 0) {
                    patient = insertPatientData();
                    if (patient != null) {
                        entityManager.persist(patient);
                    } else {
                        return;
                    }

                    Visitation visitation = new Visitation();
                    visitation.setDate(LocalDate.now());
                    visitation.setPatient(patient);

                    entityManager.persist(visitation);

                    Comment commentV = insertVisitationComment(visitation);
                    entityManager.persist(commentV);

                    Diagnose diagnose = insertDiagnose(patient);
                    entityManager.persist(diagnose);

                    Comment commentD = insertDiagnoseComment(diagnose);
                    entityManager.persist(commentD);

                    System.out.print("Please enter number of medicaments you want to prescribe: ");
                    int numberOfMedicament;
                    try {
                        numberOfMedicament = Integer.parseInt(reader.readLine());
                    } catch (NumberFormatException e) {
                        numberOfMedicament = 0;
                    }
                    while (numberOfMedicament-- > 0) {
                        Medicament medicament = prescribeMedicament(patient);

                        entityManager.persist(medicament);
                    }
                    patients.add(patient);
                }

                GP gpDoctor = insertGPInfo(patients);
                System.out.println("*** Thanks for the entered information! ***");
                System.out.println("              *** END ***");
                patients.forEach(p -> p.setGp(gpDoctor));
                entityManager.persist(gpDoctor);

                entityManager.getTransaction().commit();
                entityManager.close();
                break;
            case 2:
                System.out.print("Please enter first name, last name of patient you want to find in DateBase: ");
                try {
                    String fullName = reader.readLine().replace(" ", "");
                    containsPatientFirstAndLastName(entityManager, fullName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                entityManager.close();
                break;
            default:
                System.out.println("Try again!");
        }
    }

    private static Patient insertPatientData() throws IOException, ParseException {
        System.out.print("Please enter first name, last name, address, email (optional) of new patient on one line ");
        String[] patientData = reader.readLine().split("\\s+");
        if (patientData.length < 3) {
            System.out.println("Try again! You have to enter et least first name, last name and address!");
            return null;
        }
        System.out.print("Please enter date of birth in format \"dd/MM/yyyy\":");
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(reader.readLine());
        } catch (ParseException e) {
            System.out.println("Try again! You have to enter correct date format!");
            return null;
        }
        System.out.print("Please insert picture file (optional):");
        File pictureFile = new File(reader.readLine());

        System.out.print("Please enter true or false for medical insurance. Default value is false: ");
        boolean hasInsurance = Boolean.parseBoolean(reader.readLine());

        return new Patient(patientData[0], patientData[1], patientData[2], patientData.length > 3 ? patientData[3] : null, date, pictureFile, hasInsurance);
    }

    private static Comment insertVisitationComment(Visitation visitation) throws IOException {
        Comment commentV = new Comment();
        System.out.print("Please enter some comment about visitation (optional): ");

        commentV.setText(reader.readLine());
        commentV.setVisitation(visitation);
        return commentV;
    }

    private static Comment insertDiagnoseComment(Diagnose diagnose) throws IOException {
        Comment commentD = new Comment();
        System.out.print("Please enter some comment about diagnose (optional): ");

        commentD.setText(reader.readLine());
        commentD.setDiagnose(diagnose);
        return commentD;
    }

    private static Diagnose insertDiagnose(Patient patient) throws IOException {
        Diagnose diagnose = new Diagnose();
        System.out.print("Please enter diagnose name: ");

        diagnose.setName(reader.readLine());
        diagnose.setPatient(patient);
        return diagnose;
    }

    private static Medicament prescribeMedicament(Patient patient) throws IOException {
        Medicament medicament = new Medicament();
        System.out.print("Please enter medicament: ");
        medicament.setName(reader.readLine());
        patient.setMedicaments(new HashSet<>());
        patient.getMedicaments().add(medicament);
        medicament.setPatients(new HashSet<>());
        medicament.getPatients().add(patient);
        return medicament;
    }

    private static void containsPatientFirstAndLastName(EntityManager entityManager, String fullName) {
        List<Patient> patients = entityManager
                .createQuery("SELECT p FROM Patient AS p" +
                                " WHERE concat_ws('',p.firstName,p.lastName) = :name"
                        , Patient.class)
                .setParameter("name", fullName)
                .getResultList();
        if (patients.isEmpty()) {
            System.out.println("There is no such patient in the DataBase!");
        }
        patients.forEach(p -> {
            System.out.println(p.toString());
            p.getVisitations().forEach(v -> {
                System.out.println(v.toString());
                v.getComments().forEach(c -> System.out.println("___" + c));
            });
            if (p.getDiagnose() != null) {
                p.getDiagnose().forEach(d -> {
                    System.out.println(d.toString());
                    d.getComments().forEach(c -> System.out.println("___" + c));
                });
            }
            if (p.getMedicaments() != null) {
                p.getMedicaments().forEach(m -> System.out.println(m.toString()));
            }
        });
    }

    private static GP insertGPInfo(List<Patient> patients) throws IOException {
        GP gpDoctor = new GP();
        System.out.print("Doctor, please enter your name in the system: ");

        gpDoctor.setName(reader.readLine());
        gpDoctor.setPatients(patients);

        return gpDoctor;
    }
}
