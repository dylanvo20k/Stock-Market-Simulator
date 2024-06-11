package model;

import java.io.IOException;
import java.sql.Array;
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

  public double calculatePortfolioValue(LocalDate date) {
    double totalValue = 0.0;
    for (StockInfo stock : stockList) {
      try {
        totalValue += stock.getStockValueOnDate(date);
      } catch (Exception e) {
        System.err.println("Error fetching stock value for " + stock.getTickerSymbol() + " on " + date + ": " + e.getMessage());
      }
    }
    return totalValue;
  }

  public void purchaseStock(StockInfo stock) {
    stockList.add(stock);
  }

  public void sellStock(StockInfo stock) {
    stockList.remove(stock);
  }

  public List<StockInfo> getComposition(LocalDate date) {
    List<StockInfo> composition = new ArrayList<>();
    for (StockInfo stock : stockList) {
      if (!stock.getStockDate().isAfter(date)) {
        composition.add(stock);
      }
    }
    return composition;
  }
}
