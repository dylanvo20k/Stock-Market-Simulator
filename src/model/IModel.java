package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * This interface holds all the methods that are needed for the implementation of the model
 * component.
 * These methods allow users to manage portfolios and analyze stock data using a bar chart.
 * This interface implemented by multiple classes for the Portfolio, StockInfo, and Mocks.
 */
public interface IModel {
  double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate);

  List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate);

  double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate);

  double fetchStockPrice(String tickerSymbol, LocalDate date);

  List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException;
}