import model.MockPortfolioManager;
import model.PortfolioManager;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * This class test scenarios and cases such as calculating and error handling for the Portfolio
 * Manager class. This test class uses a mock implementation of PortfolioManager.
 */
public class PortfolioManagerTest {
  private PortfolioManager portfolioManager;
  private PortfolioManager mockPortfolioManager;

  @Before
  public void setUp() {
    portfolioManager = new PortfolioManager();
    mockPortfolioManager = new MockPortfolioManager();
  }

  @Test
  public void testCalculateMovingDayAverage() {
    LocalDate endDate = LocalDate.of(2023, 6, 1);
    double movingAverage = portfolioManager.calculateMovingDayAverage("AAPL", 5, endDate);
    assertEquals(106.928, movingAverage, 0.1);
  }

  @Test
  public void testDetectCrossovers() {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 1, 31);
    List<LocalDate> crossovers = portfolioManager.detectCrossovers("AAPL", 5, startDate, endDate);
    assertEquals(15, crossovers.size());
  }

  @Test
  public void testCalculateGainOrLoss() {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 6, 1);
    double gainOrLoss = portfolioManager.calculateGainOrLoss("AAPL", startDate, endDate);
    assertEquals(43.99, gainOrLoss, 0.1);
  }

  @Test
  public void testFetchStockPrice() {
    LocalDate date = LocalDate.of(2023, 6, 1);
    double stockPrice = portfolioManager.fetchStockPrice("AAPL", date);
    assertEquals(180.09, stockPrice, 0.1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFetchStockPriceWithInvalidDate() {
    LocalDate date = LocalDate.of(2025, 6, 1);
    portfolioManager.fetchStockPrice("AAPL", date);
  }

  @Test
  public void testFetchClosingPrices() throws IOException {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 6, 1);
    List<Double> closingPrices = portfolioManager.fetchClosingPrices("AAPL", startDate, endDate);
    assertEquals(104, closingPrices.size());
  }

  @Test
  public void testCalculateMovingDayAverageWithIOException() {
    LocalDate endDate = LocalDate.of(2023, 6, 1);
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      mockPortfolioManager.calculateMovingDayAverage("AAPL", 5, endDate);
    });
    assertEquals("Error fetching stock prices: Mock IO Exception", exception.getMessage());
  }

  @Test
  public void testDetectCrossoversWithIOException() {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 1, 31);
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      mockPortfolioManager.detectCrossovers("AAPL", 5, startDate, endDate);
    });
    assertEquals("Error fetching stock prices: Mock IO Exception", exception.getMessage());
  }

  @Test
  public void testCalculateGainOrLossWithIOException() {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 6, 1);
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      mockPortfolioManager.calculateGainOrLoss("AAPL", startDate, endDate);
    });
    assertEquals("Error fetching stock prices: Mock IO Exception", exception.getMessage());
  }

  @Test
  public void testFetchStockPriceWithIOException() {
    LocalDate date = LocalDate.of(2023, 6, 1);
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      mockPortfolioManager.fetchStockPrice("AAPL", date);
    });
    assertEquals("Error fetching stock prices: Mock IO Exception", exception.getMessage());
  }

  @Test
  public void testFetchClosingPricesWithIOException() throws IOException {
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 6, 1);
    IOException exception = assertThrows(IOException.class, () -> {
      mockPortfolioManager.fetchClosingPrices("AAPL", startDate, endDate);
    });
    assertEquals("Mock IO Exception", exception.getMessage());
  }
}
