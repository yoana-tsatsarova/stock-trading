package stockexchange.history.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="history")
public class HistoryStock {

    @Id
    private String id;

    @Column(name="stock_symbol", nullable = false)
    private String symbol;

    @Column(name="price_at_time", nullable = false)
    private Double price;

    @Column(name="quantity", nullable = false)
    private Long quantity;

    @Column(name="transaction_type", nullable = false)
    private String transactionType;

    public HistoryStock(String symbol, Double price, Long quantity, String transactionType) {
        this.id = UUID.randomUUID().toString();
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }

    public HistoryStock(){
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
