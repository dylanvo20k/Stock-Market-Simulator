package model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

<<<<<<< Updated upstream
public class Portfolio implements  IPortfolio {
=======
/**
 * This class represents a collection of stocks held by a client. It contains methods to manage
 * and analyze the portfolio, including adding and selling stocks, calculating portfolio value,
 * retrieving composition, saving to and loading from files, and generating a performance chart.
 */
public class Portfolio {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Retrieves the client name associated with the portfolio.
   *
   * @return the client name
   */
>>>>>>> Stashed changes
  public String getClientName() {
    return clientName;
  }

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Adds a stock to the portfolio.
   *
   * @param stock the stock to be added
   */
>>>>>>> Stashed changes
  public void addStock(IStockInfo stock) {
    stockList.add(stock);
  }

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Sells a specified quantity of a stock from the portfolio on a specific date.
   *
   * @param tickerSymbol the ticker symbol of the stock to be sold
   * @param date         the date of the sale
   * @param quantity     the quantity of shares to sell
   */
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Retrieves the composition of the portfolio on a specific date, represented as a map of ticker
   * symbols to quantities.
   *
   * @param date the date for which the composition is requested
   * @return a map representing the composition of the portfolio
   */
>>>>>>> Stashed changes
  public Map<String, Integer> getComposition(LocalDate date) {
    return stockList.stream()
            .filter(stock -> !stock.getStockDate().isAfter(date))
            .collect(Collectors.groupingBy(
                    IStockInfo::getTickerSymbol,
                    Collectors.summingInt(IStockInfo::getQuantity)
            ));
  }

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Calculates the total value of the portfolio on a specific date using a provided stock price
   * mode.
   *
   * @param date  the date for which the portfolio value is calculated
   * @param model the model used to fetch stock prices
   * @return the total value of the portfolio on a given date
   */
>>>>>>> Stashed changes
  public double calculatePortfolioValue(LocalDate date, IModel model) {
    return getComposition(date).entrySet().stream()
            .mapToDouble(entry -> model.fetchStockPrice(entry.getKey(), date) * entry.getValue())
            .sum();
  }

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Retrieves the value distribution of the portfolio as of a specific date, represented as a map
   * of ticker symbols to values.
   *
   * @param date  the date for which the value distribution is requested
   * @param model the model used to fetch stock prices
   * @return a map representing the value distribution of the portfolio
   */
>>>>>>> Stashed changes
  public Map<String, Double> getValueDistribution(LocalDate date, IModel model) {
    return getComposition(date).entrySet().stream()
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> model.fetchStockPrice(entry.getKey(), date) * entry.getValue()
            ));
  }

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Saves the portfolio information to a specified file.
   *
   * @param fileName the name of the file to be saved
   */
>>>>>>> Stashed changes
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

  /**
   * Loads portfolio information from a specified file and returns a Portfolio object.
   *
   * @param fileName the name of the file to be loaded
   * @return a Portfolio object contained the loaded portfolio information
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

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Generates a performance bar chart for the portfolio from a specified start date and end date.
   *
   * @param startDate te start date of the performance chart
   * @param endDate   the end date of the performance chart
   * @param model     the model used to fetch stock price
   */
>>>>>>> Stashed changes
  public void generatePerformanceChart(LocalDate startDate, LocalDate endDate, IModel model) {
    System.out.println("Performance of portfolio " + clientName + " from " + startDate + " to " + endDate);

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

<<<<<<< Updated upstream
  @Override
=======
  /**
   * Retrieves a copy of the list of stocks in the portfolio.
   *
   * @return a listing containing copies of the stocks in the portfolio to prevent modification
   */
>>>>>>> Stashed changes
  public List<IStockInfo> getStockList() {
    return new ArrayList<>(stockList);

  }
}
