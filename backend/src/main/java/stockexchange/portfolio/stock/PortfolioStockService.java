package stockexchange.portfolio.stock;

import org.springframework.stereotype.Service;
import stockexchange.stock.Stock;
import stockexchange.stock.StockRepository;

import java.util.List;

@Service
public class PortfolioStockService {

    PortfolioStockRepo repo;

    public PortfolioStockService(PortfolioStockRepo repo) {
        this.repo = repo;
    }

    public PortfolioStock saveStock(PortfolioStock stock) {
        return repo.savePortfolioStock(stock);
    }

    public PortfolioStock findPortfolioStock(String symbol) {
        return repo.findPortfolioStock(symbol);
    }

    public void deleteStockFromPortfolio(PortfolioStock stock) {
        repo.deleteStockFromPortfolio(stock);
    }

    public List<PortfolioStock> showAllPortfolioStock() {
        return repo.showAllPortfolioStocks();
    }
 }
