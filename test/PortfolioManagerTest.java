import model.PortfolioManager;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PortfolioManagerTest {
  private PortfolioManager portfolioManager;

  @Before
  public void setUp() {
    portfolioManager = new PortfolioManager();
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

}
