package stockexchange.history.stock;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryRepo {

    IHistoryRepo repo;

    public HistoryRepo(IHistoryRepo repo) {
        this.repo = repo;
    }

    public HistoryStock saveHistoryStock(HistoryStock stock) {
        return repo.save(stock);
    }

    public List<HistoryStock> showAllHistoryStocks() {
        return (List<HistoryStock>) repo.findAll();
    }
}
