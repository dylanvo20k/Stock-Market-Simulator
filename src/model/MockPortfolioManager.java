package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * A MockPortfolioManager class that serves as a mock test for testing our portfolio manager tests.
 */
//new mock class
public class MockPortfolioManager extends PortfolioManager {
  public MockPortfolioManager(IStockFetcher stockDataFetcher) {
    super(stockDataFetcher);
  }

  @Override
  public List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate,
                                         LocalDate endDate) throws IOException {
    throw new IOException("Mock IO Exception");
  }
}
