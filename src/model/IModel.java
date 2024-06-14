package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface holds all the methods required for the implementation of the model component.
 * These methods allow users to manage portfolios and analyze stock data using various techniques.
 * This interface is implemented by multiple classes for Portfolio, StockInfo, and Mocks.
 */
// new interface
public interface IModel {

  /**
   * Calculates the moving day average for a specified stock over a given number of days ending at the provided date.
   *
   * @param tickerSymbol the ticker symbol of the stock.
   * @param days the number of days over which to calculate the moving average.
   * @param endDate the end date for the moving average calculation.
   * @return the moving day average.
   */
  double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate);

  /**
   * Detects crossovers for a specified stock within the given date range.
   *
   * @param tickerSymbol the ticker symbol of the stock.
   * @param days the number of days to consider for the crossover detection.
   * @param startDate the start date for the crossover detection period.
   * @param endDate the end date for the crossover detection period.
   * @return a list of dates where crossovers were detected.
   */
  List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate);

  /**
   * Calculates the gain or loss for a specified stock over the given date range.
   *
   * @param tickerSymbol the ticker symbol of the stock.
   * @param startDate the start date for the gain/loss calculation.
   * @param endDate the end date for the gain/loss calculation.
   * @return the calculated gain or loss.
   */
  double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate);

  /**
   * Fetches the stock price for a specified stock on a given date.
   *
   * @param tickerSymbol the ticker symbol of the stock.
   * @param date the date for which to fetch the stock price.
   * @return the stock price on the specified date.
   */
  double fetchStockPrice(String tickerSymbol, LocalDate date);

  /**
   * Fetches the closing prices for a specified stock within the given date range.
   *
   * @param tickerSymbol the ticker symbol of the stock.
   * @param startDate the start date for fetching closing prices.
   * @param endDate the end date for fetching closing prices.
   * @return a list of closing prices.
   * @throws IOException if an I/O error occurs.
   */
  List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException;
}
