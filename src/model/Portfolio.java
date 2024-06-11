package model;

import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
  private String clientName;
  private List<IStockInfo> stockList;

  public Portfolio(String clientName, List<IStockInfo> stockList) {
    this.clientName = clientName;
    this.stockList = new ArrayList<>(stockList);
  }

  public String getClientName() {
    return clientName;
  }

  public List<IStockInfo> getStockList() {
    return stockList;
  }

  public void addStock(IStockInfo stock) {
    stockList.add(stock);
  }

  public double calculatePortfolioValue(LocalDate date, IModel model) {
    double totalValue = 0.0;
    for (IStockInfo stock : stockList) {
      totalValue += stock.getQuantity() * model.fetchStockPrice(stock.getTickerSymbol(), date);
    }
    return totalValue;
  }

  public void purchaseStock(IStockInfo stock) {
    stockList.add(stock);
  }

  public void sellStock(StockInfo stock) {
    stockList.remove(stock);
  }

  public List<IStockInfo> getComposition(LocalDate date) {
    List<IStockInfo> composition = new ArrayList<>();
    for (IStockInfo stock : stockList) {
      if (!stock.getStockDate().isAfter(date)) {
        composition.add(stock);
      }
    }
    return composition;
  }
}
