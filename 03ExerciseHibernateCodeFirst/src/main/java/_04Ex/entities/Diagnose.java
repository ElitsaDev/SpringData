package _04Ex.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name",unique = true)
    private String name;

    @OneToMany(mappedBy = "diagnose", targetEntity = Comment.class)
    @Column(name = "comments", length = 1000)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;

    public Diagnose() {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return String.format("Diagnose id=%d, name=%s ",id,name);
    }
}
