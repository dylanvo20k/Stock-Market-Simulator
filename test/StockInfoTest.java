import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.time.LocalDate;

import model.StockInfo;

import static org.junit.Assert.assertEquals;

public class StockInfoTest {
  private StockInfo stockInfo;
  private LocalDate date;
  @Before
  public void setUp() {
    stockInfo = new StockInfo("Apple", "AAPL", "2023-06-01", 10);
    date = LocalDate.of(2023, 6, 1);
  }

  @Test
  public void testConstructorAndGetters() {
    assertEquals("Apple", stockInfo.getCompanyName() );
    assertEquals("AAPL", stockInfo.getTickerSymbol());
    assertEquals(LocalDate.of(2023, 6, 1), stockInfo.getStockDate());
    assertEquals(10, stockInfo.getQuantity());
  }
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTickerSymbol() {
    new StockInfo("INVALID", "INVLD", "2023-06-01", 10);
  }

  @Test
  public void testGetStockValueOnDate() {
    // Create a StockInfo instance for testing
    StockInfo stockInfo = new StockInfo("Apple Inc.", "AAPL", "2024-06-01", 10);

    // Define a test date for which we expect to get a stock value
    LocalDate testDate = LocalDate.of(2024, 1, 10);

    // Expected stock price (replace with the expected value for the test date)
    double expectedPrice = 123.45; // Example value, replace with actual expected price

    // Fetch the stock value for the test date
    double actualPrice = stockInfo.getStockValueOnDate(testDate);

    // Assert that the fetched price matches the expected price
    assertEquals(expectedPrice, actualPrice, 0.001); // Tolerance for floating-point comparison
  }
}