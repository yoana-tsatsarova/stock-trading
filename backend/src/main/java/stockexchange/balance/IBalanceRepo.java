package stockexchange.balance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stockexchange.balance.Balance;


public interface IBalanceRepo extends CrudRepository<Balance, String> {

    public Balance findBalanceById(String id);

}
