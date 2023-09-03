package _06ExBonus.enums;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity - enum не могат да бъдат entity!!!
@Table(name = "competition_type")
public enum CompetitionType {
    LOCAL("LOCAL"),
    NATIONAL("NATIONAL"),
    INTERNATIONAL("INTERNATIONAL");

    @Column(name = "name")
    private String value;
    @Id
    private int id;

    CompetitionType() {
    }

    CompetitionType(String value) {
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
