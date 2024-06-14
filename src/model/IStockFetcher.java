package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IStockFetcher {

  /**
   * Fetches the closing prices for a given stock symbol between the specified start and end dates.
   *
   * @param tickerSymbol the stock symbol for which to fetch the closing prices.
   * @param startDate the start date to fetch.
   * @param endDate the end date to fetch.
   * @return a list of closing prices in the specified date range.
   * @throws IOException if an I/O error occurs when fetching data from the Alpha Vantage API.
   */
  // new interface
  List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException;

  /**
   * Fetches the stock price for a given stock symbol on a specific date.
   *
   * @param tickerSymbol the stock symbol for which to fetch the stock price.
   * @param date the date for which to fetch the stock price.
   * @return the stock price on the specified date.
   * @throws IOException if an I/O error occurs when fetching data.
   */
  double fetchStockPrice(String tickerSymbol, LocalDate date) throws IOException;
}
