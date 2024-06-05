package model;

import java.time.LocalDate;

public class StockPrice {
  private double openingPrice;
  private double closingPrice;
  private double lowPrice;
  private double highPrice;
  private LocalDate date;

  public StockPrice(double openingPrice, double closingPrice, double lowPrice, double highPrice, LocalDate date) {
    this.openingPrice = openingPrice;
    this.closingPrice = closingPrice;
    this.lowPrice = lowPrice;
    this.highPrice = highPrice;
    this.date = date;
  }

  public double getOpeningPrice() {
    return openingPrice;
  }

  public double getClosingPrice() {
    return closingPrice;
  }

  public double getLowPrice() {
    return lowPrice;
  }

  public double getHighPrice() {
    return highPrice;
  }

  public LocalDate getDate() {
    return date;
  }
}
