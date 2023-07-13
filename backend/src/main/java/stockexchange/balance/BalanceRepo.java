package stockexchange.balance;

import org.springframework.stereotype.Repository;
import stockexchange.history.stock.HistoryStock;
import stockexchange.history.stock.IHistoryRepo;

import java.util.List;

@Repository
public class BalanceRepo {

    IBalanceRepo repo;

    public BalanceRepo(IBalanceRepo repo) {

        this.repo = repo;
    }

    public Balance saveBalance(Balance balance) {
        return repo.save(balance);
    }

    public Balance getBalanceById(String id) {
        return repo.findBalanceById(id);
    }

}
