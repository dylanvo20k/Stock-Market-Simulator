package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * A MockPortfolioManager class that serves as a mock test for testing our portfolio manager tests.
 */
public class MockPortfolioManager extends PortfolioManager {
  @Override
  public List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate,
                                         LocalDate endDate) throws IOException {
    throw new IOException("Mock IO Exception");
  }
}
