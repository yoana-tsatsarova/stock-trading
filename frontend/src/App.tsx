import './App.css'
import { Routes, Route, BrowserRouter, Link } from 'react-router-dom';
import Search from "./components/Search.tsx";
import Portfolio from "./components/Portfolio.tsx";
import History from "./components/History.tsx";
import { Nav } from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";
import PortfolioDetails from "./components/PortfolioDetails.ts";

function App() {
    const [displayStock, setDisplayStock] = useState(false);
    const [displayError, setDisplayError] = useState(false);
    const [portfolio, setPortfolio] = useState<PortfolioDetails[]>();
    const [invalidStocks, setInvalidStocks] = useState(false);
    const setDisplay = () => {
        setDisplayStock(false);
    }

    const fetchPortfolio = () => {
        const url = `http://localhost:8080/portfolio`;
        axios
            .get(url)
            .then((response) => {
                setPortfolio(response.data);
                console.log(portfolio);
            })
            .catch((error) => {
                console.log(error);
            });
    };


    return (
        <>
            <div className="container">
            <BrowserRouter>
                <Nav className="navigation-bar">
                            <Link to="/search" onClick={setDisplay}><div className="navigation-item">Search</div></Link>{" "}
                    <Link to="/portfolio"><div className="navigation-item">Portfolio
                        </div></Link>
                    <Link to="/history"><div className="navigation-item">
                            History
                        </div></Link>
                </Nav>
                    <Routes>
                        <Route
                            path="/search"
                            element={
                                <Search
                                    setInvalidStocks={setInvalidStocks}
                                    fetchPortfolio={fetchPortfolio}
                                    displayStock={displayStock}
                                    setDisplayStock={setDisplayStock}
                                    displayError={displayError}
                                    setDisplayError={setDisplayError}
                                />
                            }
                        ></Route>
                        <Route
                            path="/portfolio"
                            element={
                                <Portfolio
                                    setInvalidStocks={setInvalidStocks}
                                    invalidStocks={invalidStocks}
                                    portfolio={portfolio}
                                    fetchPortfolio={fetchPortfolio}
                                />
                            }
                        ></Route>
                        <Route path="/history" element={<History />} />
                    </Routes>
            </BrowserRouter>
            </div>
        </>
    );
}

export default App;
