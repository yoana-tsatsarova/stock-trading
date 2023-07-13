package stockexchange.history.stock;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    HistoryRepo repo;

    public HistoryService(HistoryRepo repo) {
        this.repo = repo;
    }

    public HistoryStock saveHistoryStock(HistoryStock stock) {
        return repo.saveHistoryStock(stock);
    }

    public List<HistoryStock> showAllHistoryStocks() {
        return repo.showAllHistoryStocks();
    }
}
