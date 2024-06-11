package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Portfolio {
  private String clientName;
  private List<IStockInfo> stockList;

  public Portfolio(String clientName) {
    this.clientName = clientName;
    this.stockList = new ArrayList<>();
  }

  public String getClientName() {
    return clientName;
  }

  public void addStock(IStockInfo stock) {
    stockList.add(stock);
  }

  public void sellStock(IStockInfo stock) {
   stockList.remove(stock);
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
      Portfolio portfolio = new Portfolio(clientName);
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        IStockInfo stock = new StockInfo(parts[1], parts[0], parts[2], Integer.parseInt(parts[3]));
        portfolio.addStock(stock);
      }
      return portfolio;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
