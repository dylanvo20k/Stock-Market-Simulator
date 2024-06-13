package model;

import java.time.LocalDate;

public class MockStockInfo extends StockInfo {

  private double mockPrice;

  public MockStockInfo(String companyName, String tickerSymbol, String stockDate, int quantity, double mockPrice) {
    super(companyName, tickerSymbol, stockDate, quantity);
    this.mockPrice = mockPrice;
  }

  @Override
  public double getStockValueOnDate(LocalDate date) {
    return mockPrice * getQuantity();
  }
}
