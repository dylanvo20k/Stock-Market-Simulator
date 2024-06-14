package model;

import java.time.LocalDate;

/**
 * This class is used to represent a mock implementation of stock information used for testing
 * purposes.
 */
public class MockStockInfo extends StockInfo {

  private double mockPrice;

  /**
   * Constructs a MockStockInfo object with specified parameters.
   * @param companyName  the name of the company
   * @param tickerSymbol  the ticker symbol of the company
   * @param stockDate  the date of the stock information
   * @param quantity  the quantity of the stock
   * @param mockPrice  the mock price of the stock
   */
  public MockStockInfo(String companyName, String tickerSymbol,
                       String stockDate, int quantity, double mockPrice) {
    super(companyName, tickerSymbol, stockDate, quantity);
    this.mockPrice = mockPrice;
  }

  @Override
  public double getStockValueOnDate(LocalDate date) {
    return mockPrice * getQuantity();
  }
}
