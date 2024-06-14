package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PortfolioManager implements IModel {
  private final IStockFetcher stockDataFetcher;

  public PortfolioManager(IStockFetcher stockDataFetcher) {
    this.stockDataFetcher = stockDataFetcher;
  }

  @Override
  public double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate) {
    try {
      List<Double> closingPrices = stockDataFetcher.fetchClosingPrices(tickerSymbol, endDate.minusDays(days - 1), endDate);
      double sum = 0.0;
      for (double price : closingPrices) {
        sum += price;
      }
      return sum / days;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  @Override
  public List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate) {
    try {
      List<LocalDate> crossovers = new ArrayList<>();
      List<Double> closingPrices = stockDataFetcher.fetchClosingPrices(tickerSymbol, startDate, endDate);

      for (int i = days; i < closingPrices.size(); i++) {
        double movingAverage = calculateMovingDayAverage(tickerSymbol, days, startDate.plusDays(i));
        if (closingPrices.get(i) > movingAverage) {
          crossovers.add(startDate.plusDays(i));
        }
      }
      return crossovers;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  @Override
  public double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate) {
    try {
      List<Double> closingPrices = stockDataFetcher.fetchClosingPrices(tickerSymbol, startDate, endDate);
      if (closingPrices.size() < 2) {
        throw new IllegalArgumentException("Insufficient data for the given data range, " +
                "please provide at least 2 closing prices.");
      }
      double startPrice = closingPrices.get(0);
      double endPrice = closingPrices.get(closingPrices.size() - 1);
      return (endPrice - startPrice) / startPrice * 100;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  @Override
  public double fetchStockPrice(String tickerSymbol, LocalDate date) {
    try {
      return stockDataFetcher.fetchStockPrice(tickerSymbol, date);
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  @Override
  public List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException {
    return stockDataFetcher.fetchClosingPrices(tickerSymbol, startDate, endDate);
  }
}
