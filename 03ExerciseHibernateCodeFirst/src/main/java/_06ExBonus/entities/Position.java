package _06ExBonus.entities;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @Column(name = "id",length = 2)
    private String id;

    @Column(name = "position_description", columnDefinition = "TEXT")
    private String positionDescription;

    public Position() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionDescription() {
        return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }
}
