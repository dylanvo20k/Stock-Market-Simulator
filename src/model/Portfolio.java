package model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private String clientName;
  private List<StockInfo> stockList;

  public Portfolio(String clientName, List<StockInfo> stockList) {
    this.clientName = clientName;
    this.stockList = new ArrayList<>();
  }

  public String getClientName() {
    return clientName;
  }

  public List<StockInfo> getStockList() {
    return stockList;
  }

  public void addStock(StockInfo stock) {
    stockList.add(stock);
  }

//  public double getPortfolioValue() {
//
//  }
}

