package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PortfolioManager {
  public double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate) {
    List<StockPrice> stockPrices = getStockPricesForDataRange(tickerSymbol, startDate, endDate);
    double sum = 0.0;
    int count = 0;

    for (StockPrice price : stockPrices) {
      sum += price.getClosingPrice();
      count++;
    }
    return sum / count;
  }

  // implement this  method
//  public List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate) {
//
//  }
  private List<StockPrice> getStockPricesForDataRange(String tickerSymbol, LocalDate startDate, LocalDate endDate) {
    return new ArrayList<>();
    // replace return array list with an implementation of fetching historical stock prices
    // This method should essentially query a database or API to get stock prices for a given range
  }
}
