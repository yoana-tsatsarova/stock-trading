package stockexchange.stock;
import org.springframework.stereotype.Service;
import stockexchange.stock.Stock;
import stockexchange.stock.StockRepository;

@Service
public class StockService {

    StockRepository repo;

    public StockService(StockRepository repo) {
        this.repo = repo;
    }

    public Stock saveStock(Stock stock) {
        return repo.saveStock(stock);
    }

    public Stock findStockBySymbol(String symbol) {
        return repo.findStockBySymbol(symbol);
    }

    public void seachForStock(String symbol, Double price) {
        Stock existingStock = findStockBySymbol(symbol);
        if (existingStock == null) {
            Stock stock = new Stock(symbol, price);
            saveStock(stock);
        } else {
            existingStock.setCurrentPrice(price);
            saveStock(existingStock);
        }
    }
}
