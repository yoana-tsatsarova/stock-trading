import { useEffect, useState } from "react";
import axios from "axios";
import PortfolioDetails from "./PortfolioDetails.ts";
import { Button } from "react-bootstrap";
import "./Portfolio.css";

type PortfolioProps = {
    fetchPortfolio: () => void;
    portfolio: PortfolioDetails[];
    invalidStocks: boolean;
    setInvalidStocks: (yes: boolean) => void
}

const Portfolio = (props: PortfolioProps) => {
    const [stocksToSell, setStocksToSell] = useState(0);

    useEffect(() => {
        props.fetchPortfolio();
    }, [stocksToSell]);

    const sellStock = (userInput: string) => {
        axios
            .post("http://localhost:8080/sell", {
                id: "f691fa0f-21df-4358-a60f-fa9081f03de7",
                symbol: userInput,
                quantity: stocksToSell,
            })
            .then((response) => {
                console.log(response.data);
                props.fetchPortfolio();
                props.setInvalidStocks(false);// Fetch portfolio data after successful sell
            })
            .catch((error) => {
                console.error(error);
            });
    };

    return (
        <>
            <div>{props.balance}</div>
            {props.invalidStocks && <div className="error-message">Invalid amount of stocks</div>}
            {props.portfolio?.map((stock) => (
        <div className="portfolio-stock">
                    <div>{stock.symbol}</div>
                    <div>$ {stock.price}</div>
                    <div>{stock.quantity} stocks</div>
                    <input
                        className="input-quantity"
                        type="number"
                        placeholder="0"
                        onChange={(e) => setStocksToSell(Number(e.target.value))}
                    />
                    <Button
                        className="btn btn-primary"
                        onClick={() => sellStock(stock.symbol)}
                    >
                        Sell
                    </Button>
        </div>
            ))}
            </>
    );
};

export default Portfolio;
