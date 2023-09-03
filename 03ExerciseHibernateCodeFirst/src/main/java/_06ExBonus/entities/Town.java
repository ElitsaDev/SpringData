package _06ExBonus.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(unique = true, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country country;

    @OneToMany(mappedBy = "town")
    private Set<Team> teams;

    public Town() {
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
