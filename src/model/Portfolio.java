package model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a collection of stocks held by a client. It contains methods to manage
 * and analyze the portfolio, including adding and selling stocks, calculating portfolio value,
 * retrieving composition, saving to and loading from files, and generating a performance chart.
 */
public class Portfolio implements IPortfolio {
  private String clientName;
  private List<IStockInfo> stockList;


  /**
   * Constructs a Portfolio object with the specified client name and initializes stock list.
   *
   * @param clientName    the name of the client
   * @param initialStocks the initial list of stocks in the portfolio
   */
  public Portfolio(String clientName, List<IStockInfo> initialStocks) {
    this.clientName = clientName;
    this.stockList = initialStocks;
  }

  @Override
  public String getClientName() {
    return clientName;
  }

  @Override
  public void addStock(IStockInfo stock) {
    stockList.add(stock);
  }


  @Override
  public void sellStock(String tickerSymbol, LocalDate date, int quantity) {
    List<IStockInfo> stocksToSell = stockList.stream()
            .filter(stock -> stock.getTickerSymbol().equals(tickerSymbol)
                    && !stock.getStockDate().isAfter(date))
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

  @Override
  public Map<String, Integer> getComposition(LocalDate date) {
    return stockList.stream()
            .filter(stock -> !stock.getStockDate().isAfter(date))
            .collect(Collectors.groupingBy(
                    IStockInfo::getTickerSymbol,
                    Collectors.summingInt(IStockInfo::getQuantity)
            ));
  }

  @Override
  public double calculatePortfolioValue(LocalDate date, IModel model) {
    return getComposition(date).entrySet().stream()
            .mapToDouble(entry -> model.fetchStockPrice(entry.getKey(), date) * entry.getValue())
            .sum();
  }

  @Override
  public Map<String, Double> getValueDistribution(LocalDate date, IModel model) {
    return getComposition(date).entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> model.fetchStockPrice(entry.getKey(), date) * entry.getValue()
            ));
  }

  @Override
  public void saveToFile(String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      writer.write("clientName:" + clientName + "\n");
      for (IStockInfo stock : stockList) {
        writer.write(stock.getTickerSymbol() + "," + stock.getCompanyName()
                + "," + stock.getStockDate() + "," + stock.getQuantity() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a portfolio from a saved file.
   * @param fileName the name of the saved file.
   * @return a Portfolio.
   */
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

  @Override
  public void generatePerformanceChart(LocalDate startDate, LocalDate endDate, IModel model) {
    System.out.println("Performance of portfolio " + clientName
            + " from " + startDate + " to " + endDate);

    Map<LocalDate, Double> monthlyValues = new HashMap<>();

    LocalDate currentMonth = LocalDate.of(startDate.getYear(), startDate.getMonth(), 1);

    while (!currentMonth.isAfter(endDate)) {
      LocalDate lastDayOfMonth = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth());
      double portfolioValue = calculatePortfolioValue(lastDayOfMonth, model);
      monthlyValues.put(lastDayOfMonth, portfolioValue);
      currentMonth = currentMonth.plusMonths(1);
    }

    double maxPortfolioValue = 0.0;
    for (double value : monthlyValues.values()) {
      if (value > maxPortfolioValue) {
        maxPortfolioValue = value;
      }
    }

    int maxAsterisks = 50;

    for (Map.Entry<LocalDate, Double> entry : monthlyValues.entrySet()) {
      LocalDate month = entry.getKey();
      double value = entry.getValue();

      int numAsterisks = (int) (value / maxPortfolioValue * maxAsterisks);

      String formattedMonth = month.format(DateTimeFormatter.ofPattern("MMM yyyy"));

      System.out.printf("%s: %s\n", formattedMonth, "*".repeat(numAsterisks));
    }

    System.out.printf("\nScale: * = %.2f\n", maxPortfolioValue / maxAsterisks);
  }

  @Override
  public List<IStockInfo> getStockList() {
    return new ArrayList<>(stockList);

  }
}
