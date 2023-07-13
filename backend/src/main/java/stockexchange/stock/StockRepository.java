package stockexchange.stock;

import org.springframework.stereotype.Repository;
import stockexchange.history.stock.HistoryStock;
import stockexchange.stock.IRepository;
import stockexchange.stock.Stock;

@Repository
public class StockRepository {

    IRepository repo;
    public StockRepository(IRepository repo){
        this.repo = repo;
    }

    public Stock saveStock(Stock stock) {
        return repo.save(stock);
    }

    public Stock findStockBySymbol(String symbol) {
        return repo.getStockBySymbol(symbol);
    }

}
