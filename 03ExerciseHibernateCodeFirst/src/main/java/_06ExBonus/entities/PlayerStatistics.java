package _06ExBonus.entities;

import _06ExBonus.CompositeKeys.PlayerStatsCK;

import javax.persistence.*;

@Entity
@IdClass(PlayerStatsCK.class)
@Table(name = "player_statistics")
public class PlayerStatistics {
    //Game, Player, Scored Goals, Player Assists,
    // Played Minutes During Game, (PK = Game + Player)
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id",referencedColumnName = "id")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id",referencedColumnName = "id")
    private Player player;

    @Column(name = "scored_goals")
    private short scoredGoals;

    @Column(name = "player_assists")
    private short playerAssists;

    @Column(name = "minutes_played")
    private short minutesPlayed;
}
