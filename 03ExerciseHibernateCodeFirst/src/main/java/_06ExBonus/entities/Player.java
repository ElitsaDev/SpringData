package _06ExBonus.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "squad_number")
    private int squadNumber;

    @ManyToOne
    @JoinColumn(name = "team_id",referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "position_id",referencedColumnName = "id")
    private Position position;

    @Column(name = "is_currently_injured")
    private boolean isCurrentlyInjured;

    @OneToMany(mappedBy = "player")
    private Set<PlayerStatistics> stats;

    public Player() {
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

    public int getSquadNumber() {
        return squadNumber;
    }

    public void setSquadNumber(int squadNumber) {
        this.squadNumber = squadNumber;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCurrentlyInjured() {
        return isCurrentlyInjured;
    }

    public void setCurrentlyInjured(boolean currentlyInjured) {
        isCurrentlyInjured = currentlyInjured;
    }

    public Set<PlayerStatistics> getStats() {
        return stats;
    }

    public void setStats(Set<PlayerStatistics> stats) {
        this.stats = stats;
    }
}
