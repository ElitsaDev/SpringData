package _06ExBonus.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String name;

    @Column(name = "logo", columnDefinition = "BLOB")
    private String Logo;

    @Column(name = "initials", length = 3)
    private String initials;

    @ManyToOne
    @JoinColumn(name = "primary_kit_color_id")
    private Color primaryKitColor;

    @ManyToOne
    @JoinColumn(name = "secondary_kit_color_id")
    private Color secondaryKitColor;

    @ManyToOne
    @JoinColumn(name = "town_id",
            referencedColumnName = "id")
    private Town town;

    @Column(name = "budget")
    private BigDecimal budget;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Color getPrimaryKitColor() {
        return primaryKitColor;
    }

    public void setPrimaryKitColor(Color primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
    }

    public Color getSecondaryKitColor() {
        return secondaryKitColor;
    }

    public void setSecondaryKitColor(Color secondaryKitColor) {
        this.secondaryKitColor = secondaryKitColor;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
