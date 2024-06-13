package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IModel {
  double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate);

  List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate);
  double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate);
  double fetchStockPrice(String tickerSymbol, LocalDate date);
  List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException;
}