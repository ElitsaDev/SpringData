package _06ExBonus.entities;

import _06ExBonus.enums.RoundPhase;

import javax.persistence.*;

@Entity
@Table(name = "rounds")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private RoundPhase roundPhase;

    public Round() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoundPhase getRoundPhase() {
        return roundPhase;
    }

    public void setRoundPhase(RoundPhase roundPhase) {
        this.roundPhase = roundPhase;
    }
}
