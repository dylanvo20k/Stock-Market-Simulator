package model;

import java.time.LocalDate;

/**
 * This interface contains all the methods that are needed to retrieve information on a specific
 * stock.
 * These methods include a stock's company name, ticker symbol, stock data, and quantity.
 * This interface is implemented by the StockInfo class.
 */
public interface IStockInfo {

  /**
   * Gets the company name associated with the stock.
   *
   * @return the company name.
   */
  String getCompanyName();

  /**
   * Gets the ticker symbol of the stock.
   *
   * @return the ticker symbol.
   */
  String getTickerSymbol();

  /**
   * Gets the date associated with the stock data.
   *
   * @return the stock date.
   */
  LocalDate getStockDate();

  /**
   * Gets the quantity of the stock.
   *
   * @return the stock quantity.
   */
  int getQuantity();

  /**
   * Sets the quantity of the stock.
   *
   * @param quantity the new quantity to set.
   */
  void setQuantity(int quantity);
}
