package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Portfolio {
  private String clientName;
  private List<IStockInfo> stockList;

  public Portfolio(String clientName, List<IStockInfo> initialStocks) {
    this.clientName = clientName;
    this.stockList = initialStocks;
  }

  public String getClientName() {
    return clientName;
  }

  public void addStock(IStockInfo stock) {
    stockList.add(stock);
  }

  public void sellStock(String tickerSymbol, LocalDate date, int quantity) {
    List<IStockInfo> stocksToSell = stockList.stream()
            .filter(stock -> stock.getTickerSymbol().equals(tickerSymbol) && !stock.getStockDate().isAfter(date))
            .collect(Collectors.toList());

    int remainingQuantity = quantity;
    for (IStockInfo stock : stocksToSell) {
      int stockQuantity = stock.getQuantity();
      if (stockQuantity > remainingQuantity) {
        stock.setQuantity(stockQuantity - remainingQuantity);
        remainingQuantity = 0;
        break;
      } else {
        remainingQuantity -= stockQuantity;
        stockList.remove(stock);
      }
    }

    if (remainingQuantity > 0) {
      throw new IllegalArgumentException("Not enough shares to sell.");
    }
  }

  public Map<String, Integer> getComposition(LocalDate date) {
    return stockList.stream()
            .filter(stock -> !stock.getStockDate().isAfter(date))
            .collect(Collectors.groupingBy(
                    IStockInfo::getTickerSymbol,
                    Collectors.summingInt(IStockInfo::getQuantity)
            ));
  }

  public double calculatePortfolioValue(LocalDate date, IModel model) {
    return getComposition(date).entrySet().stream()
            .mapToDouble(entry -> model.fetchStockPrice(entry.getKey(), date) * entry.getValue())
            .sum();
  }

  public Map<String, Double> getValueDistribution(LocalDate date, IModel model) {
    return getComposition(date).entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> model.fetchStockPrice(entry.getKey(), date) * entry.getValue()
            ));
  }

  public void saveToFile(String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write("clientName:" + clientName + "\n");
      for (IStockInfo stock : stockList) {
        writer.write(stock.getTickerSymbol() + "," + stock.getCompanyName() + "," + stock.getStockDate() + "," + stock.getQuantity() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Portfolio loadFromFile(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String clientName = reader.readLine().split(":")[1];
      List<IStockInfo> initialStocks = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        IStockInfo stock = new StockInfo(parts[1], parts[0], parts[2], Integer.parseInt(parts[3]));
        initialStocks.add(stock);
      }
      Portfolio portfolio = new Portfolio(clientName, initialStocks);
      return portfolio;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
