package view;

import model.Portfolio;
import model.StockInfo;

public interface IView {
  void displayMenu();
  void displayMessage(String message);
  String getUserInput(String prompt);
  void displayPortfolioValue(Portfolio portfolio, String date, double value);
  void displayStockInfo(StockInfo stock, String startDate, String endDate);
}
