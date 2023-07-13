package stockexchange.balance;

import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    BalanceRepo repo;

    public BalanceService(BalanceRepo repo) {
        this.repo = repo;
    }

    public Balance saveBalance(Balance balance) {
        return repo.saveBalance(balance);
    }

    public Balance findBalanceById(String id) {
        return repo.getBalanceById(id);
    }


}
