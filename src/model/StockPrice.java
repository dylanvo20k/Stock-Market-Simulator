package model;

import java.time.LocalDate;

/**
 * The StockPrice class represents the prices of a stock on a specific date, including opening
 * price, closing price, low price, high price, and the date of the prices.
 */
public class StockPrice {
  private double openingPrice;
  private double closingPrice;
  private double lowPrice;
  private double highPrice;
  private LocalDate date;

  /**
   * Consturcts a StockPrice object with its specified parameters.
   * @param openingPrice  the opening price of the stock
   * @param closingPrice  the closing price of the stock
   * @param lowPrice  the lowest price of the stock during the day
   * @param highPrice  the highest price of the stock during the day
   * @param date  the date associated with the stock prices
   */
  public StockPrice(double openingPrice, double closingPrice,
                    double lowPrice, double highPrice, LocalDate date) {
    this.openingPrice = openingPrice;
    this.closingPrice = closingPrice;
    this.lowPrice = lowPrice;
    this.highPrice = highPrice;
    this.date = date;
  }

  /**
   * Retrieves the opening price of the stock.
   * @return the opening price
   */
  public double getOpeningPrice() {
    return openingPrice;
  }

  /**
   * Retrieves the closing price of the stock.
   * @return  the closing price.
   */
  public double getClosingPrice() {
    return closingPrice;
  }

  /**
   * Retrieves the lowest price of the stock during the day.
   * @return  the low price
   */
  public double getLowPrice() {
    return lowPrice;
  }

  /**
   * Retrieves the highest price of the stock during the day.
   * @return  the highest price
   */
  public double getHighPrice() {
    return highPrice;
  }

  /**
   * Retrieves the date associated with these stock prices.
   * @return  the date of the stock prices
   */
  public LocalDate getDate() {
    return date;
  }
  
}
