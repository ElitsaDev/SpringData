package _06ExBonus.enums;

import javax.persistence.*;

//@Entity - enum не могат да бъдат entity!!!
@Table(name = "result_prediction")
public enum ResultPrediction {
    HOME_TEAM_WIN("HOME_TEAM_WIN"),
    DRAW_GAME("DRAW_GAME"),
    AWAY_TEAM_WIN("AWAY_TEAM_WIN");

    @Column(name = "prediction")
    private String value;

    @Id
    private int id;

    ResultPrediction() {
    }

    ResultPrediction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
