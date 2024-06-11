import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import model.PortfolioManager;
import model.StockPrice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PortfolioManagerTest {

  @Test
  public void testCalculateMovingDayAverage() {
    PortfolioManager portfolioManager = new PortfolioManager();
    double movingAverage = portfolioManager.calculateMovingDayAverage("AAPL", 5, LocalDate.now());
    assertTrue(movingAverage >= 0); // Ensure non-negative result
  }

  @Test
  public void testDetectCrossovers() {
    PortfolioManager portfolioManager = new PortfolioManager();
    List<LocalDate> crossovers = portfolioManager.detectCrossovers("AAPL", 5, LocalDate.now().minusDays(10), LocalDate.now());
    assertTrue(crossovers.isEmpty()); // No crossovers expected in this short period
  }

  @Test
  public void testCalculateGainOrLoss() {
    PortfolioManager portfolioManager = new PortfolioManager();
    double gainOrLoss = portfolioManager.calculateGainOrLoss("AAPL", LocalDate.now().minusDays(10), LocalDate.now());
    assertTrue(gainOrLoss >= 0); // Ensure non-negative result
  }

  @Test(expected = RuntimeException.class)
  public void testIOExceptionInCalculateMovingDayAverage() {
    PortfolioManager portfolioManager = new PortfolioManager();
    // Pass invalid ticker symbol to simulate IOException
    portfolioManager.calculateMovingDayAverage("INVALID", 5, LocalDate.now());
  }

  @Test(expected = RuntimeException.class)
  public void testIOExceptionInDetectCrossovers() {
    PortfolioManager portfolioManager = new PortfolioManager();
    // Pass invalid ticker symbol to simulate IOException
    portfolioManager.detectCrossovers("INVALID", 5, LocalDate.now().minusDays(10), LocalDate.now());
  }

  @Test(expected = RuntimeException.class)
  public void testIOExceptionInCalculateGainOrLoss() {
    PortfolioManager portfolioManager = new PortfolioManager();
    // Pass invalid ticker symbol to simulate IOException
    portfolioManager.calculateGainOrLoss("INVALID", LocalDate.now().minusDays(10), LocalDate.now());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInsufficientDataInCalculateGainOrLoss() {
    PortfolioManager portfolioManager = new PortfolioManager();
    // Pass short date range to trigger IllegalArgumentException
    portfolioManager.calculateGainOrLoss("AAPL", LocalDate.now(), LocalDate.now());
  }



  @Test
  public void testFetchStockPrices() throws IOException {
    PortfolioManager portfolioManager = new PortfolioManager();
    List<StockPrice> stockPrices = portfolioManager.fetchStockPrices("AAPL");
    assertTrue(stockPrices.size() > 0); // Ensure some stock prices are fetched
  }

  @Test
  public void testSaveStockPricesToCSV() {
    PortfolioManager portfolioManager = new PortfolioManager();
    List<StockPrice> stockPrices = List.of(new StockPrice(100.0, 105.0, 98.0, 110.0, LocalDate.now()));
    portfolioManager.saveStockPricesToCSV(stockPrices, "test.csv");
    // Check if file is created
    assertTrue("File not created", new File("src/main/resources/test.csv").exists());
  }

  @Test
  public void testLoadStockPricesFromCSV() {
    PortfolioManager portfolioManager = new PortfolioManager();
    List<StockPrice> stockPrices = portfolioManager.loadStockPricesFromCSV("test.csv");
    assertEquals(1, stockPrices.size()); // Ensure correct number of stock prices loaded
  }
}
