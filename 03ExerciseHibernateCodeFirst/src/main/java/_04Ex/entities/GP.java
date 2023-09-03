package _04Ex.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gp")
public class GP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "gp", targetEntity = Patient.class, cascade = CascadeType.PERSIST)
    private List<Patient> patients;

    public GP() {
    }

    public GP(String name, List<Patient> patients) {
        this.name = name;
        this.patients = patients;
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

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
