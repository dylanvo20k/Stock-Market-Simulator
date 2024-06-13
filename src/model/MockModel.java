package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockModel implements IModel {
  private Map<String, Map<LocalDate, Double>> stockPrices;

  public MockModel() {
    stockPrices = new HashMap<>();

    Map<LocalDate, Double> aaplPrices = new HashMap<>();
    Map<LocalDate, Double> googPrices = new HashMap<>();
    Map<LocalDate, Double> tslaPrices = new HashMap<>();

    for (int month = 6; month <= 12; month++) {
      LocalDate date = LocalDate.of(2023, month, 1);
      aaplPrices.put(date, 150.0);
      googPrices.put(date, 2800.0);
      tslaPrices.put(date, 700.0);
    }

    stockPrices.put("AAPL", aaplPrices);
    stockPrices.put("GOOG", googPrices);
    stockPrices.put("TSLA", tslaPrices);
  }

  @Override
  public double fetchStockPrice(String tickerSymbol, LocalDate date) {
    return stockPrices.getOrDefault(tickerSymbol, new HashMap<>()).getOrDefault(date, 0.0);
  }

  @Override
  public double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate) {
    return 0.0;
  }

  @Override
  public List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate) {
    return null;
  }

  @Override
  public double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate) {
    return 0.0;
  }

  @Override
  public List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) {
    return null;
  }
}
