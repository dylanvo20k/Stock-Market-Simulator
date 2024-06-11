package model;

import java.time.LocalDate;

public interface IStockInfo {
  String getCompanyName();
  String getTickerSymbol();
  LocalDate getStockDate();
  int getQuantity();
}
