package _04Ex.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "medicaments_patients",
    joinColumns = @JoinColumn(name = "medicament_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "patient_id",referencedColumnName = "id"))
    private Set<Patient> patients;

    public Medicament() {
    }

    public Medicament(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return String.format("Medicament id=%d, name=%s ",id, name) ;
    }
}
