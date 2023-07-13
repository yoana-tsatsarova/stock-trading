package stockexchange.stock;

import org.springframework.data.repository.CrudRepository;

public interface IRepository extends CrudRepository<Stock, String>{

    public Stock getStockBySymbol(String symbol);

}
