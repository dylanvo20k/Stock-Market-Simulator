import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import model.StockPrice;

import static org.junit.Assert.assertEquals;

/**
 * This test class tests all the scenarios and edge cases for the StockPrice class.
 * This class tests all the getters for the prices of the stocks and also tests the stock date.
 */
public class StockPriceTest {
  private double openingPrice;
  private double closingPrice;
  private double lowPrice;
  private double highPrice;
  private LocalDate date;
  private StockPrice stockPrice;

  @Before
  public void setUp() {
    openingPrice = 100.0;
    closingPrice = 110.0;
    lowPrice = 90.0;
    highPrice = 120.0;
    date = LocalDate.of(2024, 6, 5);
    stockPrice = new StockPrice(openingPrice, closingPrice, lowPrice, highPrice, date);
  }

  @Test
  public void testGetOpeningPrice() {
    assertEquals(openingPrice, stockPrice.getOpeningPrice(), 0.01);
  }

  @Test
  public void testGetClosingPrice() {
    assertEquals(closingPrice, stockPrice.getClosingPrice(), 0.01);
  }

  @Test
  public void testGetLowPrice() {
    assertEquals(lowPrice, stockPrice.getLowPrice(), 0.01);
  }

  @Test
  public void testGetHighPrice() {
    assertEquals(highPrice, stockPrice.getHighPrice(), 0.01);
  }

  @Test
  public void testGetDate() {
    assertEquals(date, stockPrice.getDate());
  }

}