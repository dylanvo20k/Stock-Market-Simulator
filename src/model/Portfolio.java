package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private String clientName;
  private List<StockInfo> stockList;

  public Portfolio(String clientName, List<StockInfo> stockList) {
    this.clientName = clientName;
    this.stockList = new ArrayList<>(stockList);
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

  public double getPortfolioValue(LocalDate date) {
    double totalValue = 0.0;
    for (StockInfo stock : stockList) {
      double stockValue = stock.getStockValueOnDate(date);
      totalValue += stockValue;
    }
    return totalValue;
  }
}
