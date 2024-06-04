package model;

import java.time.LocalDate;

public class StockInfo {
  private String companyName;
  private String tickerSymbol;
  private LocalDate stockDate;

  public StockInfo(String companyName, String tickerSymbol, String stockDate) {
    this.companyName = companyName;
    this.tickerSymbol = tickerSymbol;
    this.stockDate = LocalDate.parse(stockDate);
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public LocalDate getStockDate() {
    return stockDate;
  }
}

// all different types of variables
// company name, ticker symbol, stock date, opening price, closing price, low price, high price,
/// x-day moving average
// x-day crossovers
