import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import model.StockInfo;

import static org.junit.Assert.assertEquals;

/**
 * This test class tests all the scenarios and edge cases for the StockInfo class.
 * This class also tests invalid ticker symbols and exceptions that are thrown in the StockInfo
 * class.
 */
public class StockInfoTest {
  private StockInfo stockInfo;

  @Before
  public void setUp() throws NoSuchFieldException, IllegalAccessException {
    stockInfo = new StockInfo("Apple", "AAPL", "2023-06-01", 10);
    LocalDate date = LocalDate.of(2023, 6, 1);
  }

  @Test
  public void testConstructorAndGetters() {
    assertEquals("Apple", stockInfo.getCompanyName());
    assertEquals("AAPL", stockInfo.getTickerSymbol());
    assertEquals(LocalDate.of(2023, 6, 1), stockInfo.getStockDate());
    assertEquals(10, stockInfo.getQuantity());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTickerSymbolTwoLetters() {
    new StockInfo("Alcoa Corporation", "AA", "2023-06-01", 12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTickerSymbolThreeLetters() {
    new StockInfo("Federal Realty Investment Trust", "FRT", "2018-04-10", 8);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTickerSymbolFiveLetters() {
    new StockInfo("Over-the-Counter Bulletin Board", "OTCBB", "2023-06-01", 4);
  }

  @Test
  public void testValidTickerSymbol() {
    new StockInfo("Apple", "AAPL", "2023-06-01", 2);
    assertEquals("AAPL", stockInfo.getTickerSymbol());
  }

  @Test
  public void testGetStockValueOnDate() {
    StockInfo stockInfo = new StockInfo("Apple Inc.", "AAPL", "2002-05-28", 10);
    LocalDate testDate = LocalDate.of(2002, 5, 28);

    // the total price of Apple's stock value with 10 shares
    double expectedPrice = 239.8;
    double actualPrice = stockInfo.getStockValueOnDate(testDate);

    assertEquals(expectedPrice, actualPrice, 0.001); // Tolerance for floating-point comparison
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoPriceDataFound() {
    StockInfo stockInfo = new StockInfo("Apple Inc.", "AAPL", "2002-05-26", 10);
    LocalDate testDate = LocalDate.of(2002, 5, 26);

    stockInfo.getStockValueOnDate(testDate);
  }

  @Test
  public void testSetQuantity() {
    stockInfo.setQuantity(3);
    assertEquals(3, stockInfo.getQuantity());
  }
}
