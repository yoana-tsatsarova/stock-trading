package stockexchange.portfolio.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import javax.sound.sampled.Port;
import java.util.UUID;

@Entity
@Table(name="portfolio")
public class PortfolioStock {

    @Id
    private String id;

    @Column(name="stock_symbol", nullable = false)
    private String symbol;

    @Column(name="quantity", nullable = false)
    private Long quantity;

    public PortfolioStock() {
    }

    public PortfolioStock(String symbol, Long quantity) {
        this.id = UUID.randomUUID().toString();
        this.symbol = symbol;
        this.quantity = quantity;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
