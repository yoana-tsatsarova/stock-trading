package stockexchange.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public record StockDTO(String symbol, Double price) {
}
