package _06ExBonus.CompositeKeys;

import _06ExBonus.entities.Game;
import _06ExBonus.entities.Player;

import java.io.Serializable;
import java.util.Objects;

public class PlayerStatsCK implements Serializable {
    private Game game;
    private Player player;

    public PlayerStatsCK(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public PlayerStatsCK() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatsCK that = (PlayerStatsCK) o;
        return Objects.equals(game, that.game) && Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, player);
    }
}
