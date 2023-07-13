package stockexchange.balance;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="balance")
public class Balance {

    @Id
    private final String id = "balance";

    @Column(name="balance", nullable = false)
    private Double balance;

    public Balance() {

    }

    public Balance(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
