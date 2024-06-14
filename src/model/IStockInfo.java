package model;

import java.time.LocalDate;

/**
 * This interface contains all the methods that are needed to retrieve information on a specific
 * stock.
 * These methods include a stock's company name, ticker symbol, stock data, and quantity.
 * This interface is implemented by the StockInfo class.
 */
public interface IStockInfo {
  String getCompanyName();

  String getTickerSymbol();

  LocalDate getStockDate();

  int getQuantity();

  void setQuantity(int quantity);
}
