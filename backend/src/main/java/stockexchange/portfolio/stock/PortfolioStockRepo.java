package stockexchange.portfolio.stock;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PortfolioStockRepo {

    IPortfolioStockRepo repo;

    public PortfolioStockRepo(IPortfolioStockRepo repo) {
        this.repo = repo;
    }

    public PortfolioStock savePortfolioStock(PortfolioStock stock) {
        return repo.save(stock);
    }

    public PortfolioStock findPortfolioStock(String symbol) {
        return repo.getPortfolioStockBySymbol(symbol);
    }

    public void deleteStockFromPortfolio(PortfolioStock stock) {
        repo.delete(stock);
    }

    public List<PortfolioStock> showAllPortfolioStocks() {
        return (List<PortfolioStock>) repo.findAll();
    }

}
