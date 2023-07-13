import { Button } from "react-bootstrap";
import axios from 'axios';
import { useEffect, useState } from "react";
import StockDetails from "./StockDetails.ts";
import { Link, useHistory } from "react-router-dom";
import "./Search.css";

type SearchProps = {
    displayStock: boolean;
    setDisplayStock: (display: boolean) => void;
    displayError: boolean;
    setDisplayError: (display: boolean) => void;
    fetchPortfolio: () => void; // Add fetchPortfolio function prop
    setInvalidStocks: (display: boolean) => void;
}

const Search = (props: SearchProps) => {
    const [userInput, setUserInput] = useState("");
    const [stock, setStock] = useState<StockDetails | undefined>();
    const [numStocks, setNumStocks] = useState(0);// Access history object from react-router-dom

    const onSubmit = () => {
        const url = `http://localhost:8080/search/${userInput}`;
        axios.get(url)
            .then(response => {
                setStock(response.data);
                props.setDisplayStock(true);
                props.setDisplayError(false);
            })
            .catch(error => {
                props.setDisplayError(true);
                props.setDisplayStock(false);
                console.error(error);
            });
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === "Enter") {
            onSubmit();
        }
    };

    const buyStock = () => {
        axios.post('http://localhost:8080/buy', {
            "id": "f691fa0f-21df-4358-a60f-fa9081f03de7",
            "symbol": userInput,
            "quantity": numStocks
        }).then((response) => {
            console.log(response.data);
            props.setInvalidStocks(false);
            props.fetchPortfolio(); // Fetch portfolio data after successful buy// Navigate to the portfolio route
        })
            .catch(error => {
                console.error(error);
                props.setInvalidStocks(true);
            });
    }

    return (
            <div className="whole-search">
            <div className="search">
                <input
                    className="input-symbol"
                    type="text"
                    placeholder="Stock"
                    value={userInput}
                    onChange={(e) => setUserInput(e.target.value)}
                    onKeyDown={handleKeyDown}
                />
            </div>
            <Button className="search-button" onClick={onSubmit}>
                Search Stock
            </Button>
            {props.displayError && <div className="error-message">Invalid stock symbol... Try again</div>}
            {props.displayStock && stock &&
                <div className="show-stock">
                    <h3>{stock.symbol}</h3>
                    <h3>$ {stock.price}</h3>
                    <input
                        className="input-quantity"
                        type="number"
                        placeholder="0"
                        onChange={(e) => setNumStocks(Number(e.target.value))}/>
                    <Link to="/portfolio"><Button className="buy-button" onClick={buyStock} >
                        Buy
                    </Button> </Link>
                </div> }
            </div>
    )
};

export default Search;
