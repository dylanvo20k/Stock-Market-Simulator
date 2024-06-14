package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * AN interface which represents a portfolio. Has methods which can
 * retrieve data related to portfolio(s).
 */
public interface IPortfolio {

  /**
   * Retrieves the client name associated with the portfolio.
   *
   * @return the client name
   */
  // new interface
  String getClientName();

  /**
   * Adds a stock to the portfolio.
   *
   * @param stock the stock to be added
   */
  void addStock(IStockInfo stock);

  /**
   * Sells a specified quantity of a stock from the portfolio on a specific date.
   *
   * @param tickerSymbol the ticker symbol of the stock to be sold
   * @param date         the date of the sale
   * @param quantity     the quantity of shares to sell
   */
  void sellStock(String tickerSymbol, LocalDate date, int quantity);

  /**
   * Retrieves the composition of the portfolio on a specific date, represented as a map of ticker
   * symbols to quantities.
   *
   * @param date the date for which the composition is requested
   * @return a map representing the composition of the portfolio
   */
  Map<String, Integer> getComposition(LocalDate date);

  /**
   * Calculates the total value of the portfolio on a specific date using a provided stock price
   * mode.
   *
   * @param date  the date for which the portfolio value is calculated
   * @param model the model used to fetch stock prices
   * @return the total value of the portfolio on a given date
   */
  double calculatePortfolioValue(LocalDate date, IModel model);


  /**
   * Retrieves the value distribution of the portfolio as of a specific date, represented as a map
   * of ticker symbols to values.
   *
   * @param date  the date for which the value distribution is requested
   * @param model the model used to fetch stock prices
   * @return a map representing the value distribution of the portfolio
   */
  Map<String, Double> getValueDistribution(LocalDate date, IModel model);

  /**
   * Saves the portfolio information to a specified file.
   *
   * @param fileName the name of the file to be saved
   */
  void saveToFile(String fileName);

  /**
   * Loads portfolio information from a specified file and returns a Portfolio object.
   *
   * @param fileName the name of the file to be loaded
   * @return a Portfolio object contained the loaded portfolio information
   */
  static IPortfolio loadFromFile(String fileName) {
    return null;
  }

  /**
   * Generates a performance bar chart for the portfolio from a specified start date and end date.
   *
   * @param startDate te start date of the performance chart
   * @param endDate   the end date of the performance chart
   * @param model     the model used to fetch stock price
   */
  void generatePerformanceChart(LocalDate startDate, LocalDate endDate, IModel model);



  /**
   * Retrieves a copy of the list of stocks in the portfolio.
   *
   * @return a listing containing copies of the stocks in the portfolio to prevent modification
   */
  List<IStockInfo> getStockList();
}
