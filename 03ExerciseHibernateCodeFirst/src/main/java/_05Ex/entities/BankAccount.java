package _05Ex.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Bank Account")
public class BankAccount extends BillingDetailsImpl {

    @Column(name = "name")
    private String name;
    @Column(name = "swift_code")
    private String swiftCode;

    public BankAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
