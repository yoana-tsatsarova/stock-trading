package stockexchange.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name="stocks")
public class Stock {

    @Id
    private String id;

    @Column(name="stock_symbol", nullable = false)
    private String symbol;

    @Column(name="current_price", nullable = false)
    private Double price;

    public Stock() {
    }

    public Stock(String symbol,Double currentPrice) {
        this.id = UUID.randomUUID().toString();
        this.symbol = symbol;
        this.price = currentPrice;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String stockName) {
        this.symbol = stockName;
    }

    public Double getCurrentPrice() {
        return price;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.price = currentPrice;
    }
}
