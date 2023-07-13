package stockexchange;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import stockexchange.balance.Balance;
import stockexchange.balance.BalanceDTO;
import stockexchange.balance.BalanceService;
import stockexchange.history.stock.HistoryService;
import stockexchange.history.stock.HistoryStock;
import stockexchange.history.stock.HistoryStockDTO;
import stockexchange.portfolio.stock.PortfolioResponseDTO;
import stockexchange.portfolio.stock.PortfolioStock;
import stockexchange.portfolio.stock.PortfolioStockDTO;
import stockexchange.portfolio.stock.PortfolioStockService;
import stockexchange.stock.Stock;
import stockexchange.stock.StockDTO;
import stockexchange.stock.StockService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    private static final String IEX_API_URL = "https://cloud.iexapis.com/stable";
    private static final String IEX_API_TOKEN = "";

    private final RestTemplate restTemplate = new RestTemplate();

    StockService stockService;

    PortfolioStockService portfolioService;

    HistoryService historyService;

    BalanceService balanceService;

    private Double balance = 10000.0;

    public Controller(BalanceService balanceService, StockService service, PortfolioStockService portfolioService, HistoryService historyService) {
        this.balanceService = balanceService;
        this.historyService = historyService;
        this.stockService = service;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceDTO> getCurrentBalance() {
        Balance balanceById =  balanceService.findBalanceById("balance");
        if (balanceById == null) {
            Balance newBalance = new Balance(balance);
            balanceById = newBalance;
        } else {
            balanceById.setBalance(balance);
        }
        balanceService.saveBalance(balanceById);
        return ResponseEntity.ok(new BalanceDTO(balanceById.getBalance()));
    }


    @GetMapping("/search/{symbol}")
    public ResponseEntity<StockDTO> getStockPrice(@PathVariable("symbol") String symbol) {
        try {
            symbol = symbol.toUpperCase();
            String url = IEX_API_URL + "/stock/{symbol}/quote/latestPrice?token=" + IEX_API_TOKEN;
            ResponseEntity<Double> response = restTemplate.getForEntity(url, Double.class, symbol);
            stockService.seachForStock(symbol, response.getBody());
            StockDTO stockDTO = new StockDTO(symbol, response.getBody());
            return ResponseEntity.ok().body(stockDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("buy")
    ResponseEntity<PortfolioResponseDTO> buyStock(@RequestBody PortfolioStockDTO portfolio, HttpServletRequest req) {
        portfolio = new PortfolioStockDTO(portfolio.id(), portfolio.symbol().toUpperCase(), portfolio.quantity());
        if (portfolio.quantity() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Stock stock = stockService.findStockBySymbol(portfolio.symbol());
        if (stock == null) {
            return null;
        }
        double priceToPay = portfolio.quantity() * stock.getCurrentPrice();
        if (priceToPay > balance) {
            return ResponseEntity.badRequest().build();
        } else {
            balance -= priceToPay;
        }
        PortfolioStock existingPortfolioStock = portfolioService.findPortfolioStock(portfolio.symbol());
        if (existingPortfolioStock != null) {
            existingPortfolioStock.setQuantity(existingPortfolioStock.getQuantity() + portfolio.quantity());
            portfolioService.saveStock(existingPortfolioStock);
            PortfolioResponseDTO dto = new PortfolioResponseDTO(portfolio.symbol(), existingPortfolioStock.getQuantity(), stock.getCurrentPrice());
            return ResponseEntity.ok().body(dto);
        }
        PortfolioStock portfolioStock = new PortfolioStock(portfolio.symbol(), portfolio.quantity());
        portfolioService.saveStock(portfolioStock);
        HistoryStock historyStock = new HistoryStock(stock.getSymbol(), stock.getCurrentPrice(), portfolio.quantity(), "buy");
        historyService.saveHistoryStock(historyStock);
        URI location = URI.create(req.getRequestURL() + "/" + portfolioStock.getId());
        PortfolioResponseDTO dto = new PortfolioResponseDTO(portfolio.symbol(), portfolio.quantity(), stock.getCurrentPrice());
        getCurrentBalance();
        return ResponseEntity.created(location).body(dto);
    }

    @PostMapping("sell")
    ResponseEntity<PortfolioResponseDTO> sellStock(@RequestBody PortfolioStockDTO portfolio, HttpServletRequest req) {
        PortfolioStock portfolioStock = portfolioService.findPortfolioStock(portfolio.symbol());
        if (portfolioStock == null) {
            return ResponseEntity.notFound().build();
        }
        if (portfolio.quantity() > portfolioStock.getQuantity()) {
            return ResponseEntity.badRequest().build();
        }
        Long quantity = portfolioStock.getQuantity() - portfolio.quantity();
        portfolioStock.setQuantity(quantity);
        Stock stock = stockService.findStockBySymbol(portfolio.symbol());
        HistoryStock historyStock = new HistoryStock(stock.getSymbol(), stock.getCurrentPrice(), portfolio.quantity(), "sell");
        historyService.saveHistoryStock(historyStock);
        balance += portfolio.quantity() * stock.getCurrentPrice();
        PortfolioResponseDTO dto = new PortfolioResponseDTO(portfolio.symbol(), portfolio.quantity(), stock.getCurrentPrice());
        if (quantity == 0) {
            portfolioService.deleteStockFromPortfolio(portfolioStock);
        } else {
            portfolioService.saveStock(portfolioStock);
        }
        getCurrentBalance();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("portfolio")
    ResponseEntity<List<PortfolioResponseDTO>> showPortfolio() {
        List<PortfolioStock> portfolioStocks = portfolioService.showAllPortfolioStock();
        return ResponseEntity.ok(portfolioStocks.stream()
                .map(stock -> {
                    Stock newStock = stockService.findStockBySymbol(stock.getSymbol());
                    return new PortfolioResponseDTO(stock.getSymbol(), stock.getQuantity(), newStock.getCurrentPrice());
                }).toList());
    }

    @GetMapping("history")
    ResponseEntity<List<HistoryStockDTO>> showHistoryStocks() {
        List<HistoryStock> historyStocks = historyService.showAllHistoryStocks();
        return ResponseEntity.ok(historyStocks.stream()
                .map(stock -> {
                    return new HistoryStockDTO(stock.getSymbol(), stock.getPrice(), stock.getQuantity(), stock.getTransactionType());
                }).toList());
    }
}
