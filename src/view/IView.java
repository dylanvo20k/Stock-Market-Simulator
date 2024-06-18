package view;

import model.Portfolio;
import model.StockInfo;
/**
 * This interface holds all the methods for the view implementation for our application program.
 * The ViewStocks class implements this interface and utilizes its methods for the program.
 */

//new interface
public interface IView {

  /**
   * Displays the main menu options for managing stocks and portfolios.
   */
  void displayMenu();

  /**
   * Displays a message to the user.
   *
   * @param message the message to display
   */
  void displayMessage(String message);

  /**
   * Prompts the user for input and returns the user's input as a string.
   *
   * @param prompt the prompt message for the user
   * @return the user's input as a string
   */
  String getUserInput(String prompt);

  /**
   * Displays the value of a portfolio on a specific date.
   *
   * @param portfolio the portfolio to display
   * @param date      the date for which the value is displayed
   * @param value     the value of the portfolio
   */
  void displayPortfolioValue(Portfolio portfolio, String date, double value);

  /**
   * Displays stock information for a specific stock within a date range.
   *
   * @param stock     the stock information to display
   * @param startDate the start date of the date range
   * @param endDate   the end date of the date range
   */
  void displayStockInfo(StockInfo stock, String startDate, String endDate);

  void displayManagePortfolio();
}
