package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IStockFetcher {
  List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException;
  double fetchStockPrice(String tickerSymbol, LocalDate date) throws IOException;
}
