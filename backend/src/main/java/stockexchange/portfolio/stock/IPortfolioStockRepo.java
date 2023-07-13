package stockexchange.portfolio.stock;

import org.springframework.data.repository.CrudRepository;

public interface IPortfolioStockRepo extends CrudRepository<PortfolioStock, String> {

    public PortfolioStock getPortfolioStockBySymbol(String symbol);

}
