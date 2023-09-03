package _06ExBonus.entities;

import _06ExBonus.CompositeKeys.BetGameCK;
import _06ExBonus.enums.ResultPrediction;

import javax.persistence.*;

@Entity
@IdClass(BetGameCK.class)
@Table(name = "bet_games")
public class BetGame {
    @Id
    @ManyToOne
    @JoinColumn(name = "bet_id",referencedColumnName = "id")
    private Bet bet;

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id",referencedColumnName = "id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "result_prediction_id", referencedColumnName = "id")
    @Enumerated(value = EnumType.STRING)
    private ResultPrediction resultPrediction;

    public BetGame() {
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ResultPrediction getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
    }
}
