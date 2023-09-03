package _05Ex.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Credit Card")
public class CreditCard extends BillingDetailsImpl{
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "expiration_month")
    private String expirationMonth;
    @Column(name = "expiration_year")
    private String expirationYear;

    public CreditCard() {
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }
}
