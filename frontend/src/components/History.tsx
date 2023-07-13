import axios from "axios";
import HistoryDetails from "./HistoryDetails.ts";
import {useEffect, useState} from "react";
import "./History.css";

const History = () => {

    const [history, setHistory] = useState<HistoryDetails[]>();

    const fetchHistory = () => {
        const url = `http://localhost:8080/history`;
        axios
            .get(url)
            .then((response) => {
                setHistory(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    useEffect(() => {
        fetchHistory()
    }, []);

    return (
        <>
            <h2 className="history">History</h2>
            <div className="transaction-div">
            <div className="buy">
                <h2>Buy</h2>
            {history?.filter(stock => stock.transactionType == "buy")
                .map((stock) => (
                    <div className="history-stock">
                        <div>{stock.symbol}</div>
                        <div>$ {stock.price}</div>
                        <div>{stock.quantity} stocks</div>
                    </div>
                ))}
            </div>
            <div className="sell">
                <h2>Sell</h2>
            {history?.filter(stock => stock.transactionType == "sell")
                .map((stock) => (
                    <div className="history-stock">
                        <div>{stock.symbol}</div>
                        <div>$ {stock.price}</div>
                        <div>{stock.quantity} stocks</div>
                    </div>
                ))}
            </div>
            </div>
            </>
    )
}

export default History;