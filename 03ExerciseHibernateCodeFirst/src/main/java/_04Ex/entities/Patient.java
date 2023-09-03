package _04Ex.entities;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "patients")
@Inheritance(strategy = InheritanceType.JOINED)
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "picture", columnDefinition = "BLOB")
    private File picture;

    @Column(name = "has_medical_insurance", nullable = false)
    private boolean hasMedicalInsurance;

    @ManyToOne
    @JoinColumn(name = "gp_id", referencedColumnName = "id")
    private GP gp;

    @OneToMany(targetEntity = Visitation.class, mappedBy = "patient")
    private Set<Visitation> visitations;

    @OneToMany(targetEntity = Diagnose.class, mappedBy = "patient")
    private Set<Diagnose> diagnose;

    @ManyToMany(targetEntity = Medicament.class, mappedBy = "patients")
    private Set<Medicament> medicaments;

    public Patient() {
    }

    public Patient(String firstName, String lastName,
                   String address, String email,
                   Date dateOfBirth,
                   File picture,
                   boolean hasMedicalInsurance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.picture = picture;
        this.hasMedicalInsurance = hasMedicalInsurance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public boolean isHasMedicalInsurance() {
        return hasMedicalInsurance;
    }

    public void setHasMedicalInsurance(boolean hasMedicalInsurance) {
        this.hasMedicalInsurance = hasMedicalInsurance;
    }

    public GP getGp() {
        return gp;
    }

    public void setGp(GP gp) {
        this.gp = gp;
    }

    public Set<Diagnose> getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Set<Diagnose> diagnose) {
        this.diagnose = diagnose;
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(String.format("Patient with id=%d, firstName=%s, lastName=%s, address=%s, ", id, firstName, lastName, address )).append("email= ").append(email!=null?email:null)
                .append(", dateOfBirth=").append(dateOfBirth).append(", picture= ").append(picture!=null?picture:"none").append(", hasMedicalInsurance= ").append(hasMedicalInsurance)
                .toString();

    }
}
