package stockexchange.history.stock;

import org.springframework.data.repository.CrudRepository;

public interface IHistoryRepo extends CrudRepository <HistoryStock, String> {
}
