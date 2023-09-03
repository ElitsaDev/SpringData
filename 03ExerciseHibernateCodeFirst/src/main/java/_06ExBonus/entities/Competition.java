package _06ExBonus.entities;

import _06ExBonus.enums.CompetitionType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "competitions")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @OneToOne
    private CompetitionType type;

    @OneToMany(mappedBy = "competition")
    private Set<Game> games;

    public Competition() {
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

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
