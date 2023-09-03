package _06ExBonus.CompositeKeys;

import _06ExBonus.entities.Bet;
import _06ExBonus.entities.Game;

import java.io.Serializable;
import java.util.Objects;

public class BetGameCK implements Serializable {

    private Game game;
    private Bet bet;

    public BetGameCK(Game game, Bet bet) {
        this.game = game;
        this.bet = bet;
    }

    public BetGameCK() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetGameCK betGameCK = (BetGameCK) o;
        return Objects.equals(game, betGameCK.game) && Objects.equals(bet, betGameCK.bet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, bet);
    }
}
