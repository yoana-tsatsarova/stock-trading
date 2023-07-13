package stockexchange.history.stock;


public record HistoryStockDTO(String symbol, Double price, Long quantity, String transactionType) {

}
