package stockexchange.portfolio.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortfolioStockDTO( String id,  String symbol,  Long quantity) {
}

