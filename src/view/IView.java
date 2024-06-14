package view;

import model.Portfolio;
import model.StockInfo;
/**
 * This interface holds all the methods for the view implementation for our application program.
 * The ViewStocks class implements this interface and utilizes its methods for the program.
 */
public interface IView {
  void displayMenu();
  void displayMessage(String message);
  String getUserInput(String prompt);
  void displayPortfolioValue(Portfolio portfolio, String date, double value);
  void displayStockInfo(StockInfo stock, String startDate, String endDate);
}
