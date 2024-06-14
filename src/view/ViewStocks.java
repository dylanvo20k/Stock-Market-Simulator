package view;

import java.util.Scanner;

import model.Portfolio;
import model.StockInfo;

/**
 * The ViewStocks class implements the IView interface and provides methods to interact with the
 * user through the console for managing stocks and portfolios.
 */
public class ViewStocks implements IView {
  private Scanner scanner;
  /**
   * Constructs a ViewStocks object initializing the scanner to read user input from the console.
   */
  public ViewStocks() {
    this.scanner = new Scanner(System.in);
  }
  /**
   * Displays the main menu options for managing stocks and portfolios.
   */
  @Override
  public void displayMenu() {
    System.out.println("Stocks Menu");
    System.out.println("1. Create a portfolio.");
    System.out.println("2. Add stock to a portfolio.");
    System.out.println("3. Calculate portfolio value.");
    System.out.println("4. Calculate moving day average.");
    System.out.println("5. Detect x-day crossovers.");
    System.out.println("6. Calculate gain or loss.");
    System.out.println("7. Exit.");
  }
  /**
   * Displays a message to the user.
   *
   * @param message the message to display
   */
  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }
  /**
   * Prompts the user for input and returns the user's input as a string.
   *
   * @param prompt the prompt message for the user
   * @return the user's input as a string
   */
  @Override
  public String getUserInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }
  /**
   * Displays the value of a portfolio on a specific date.
   *
   * @param portfolio the portfolio to display
   * @param date      the date for which the value is displayed
   * @param value     the value of the portfolio
   */
  @Override
  public void displayPortfolioValue(Portfolio portfolio, String date, double value) {
    System.out.println("The value of portfolio " + portfolio.getClientName() + " on " + date + " is $" + value);
  }
  /**
   * Displays stock information for a specific stock within a date range.
   *
   * @param stock     the stock information to display
   * @param startDate the start date of the date range
   * @param endDate   the end date of the date range
   */
  @Override
  public void displayStockInfo(StockInfo stock, String startDate, String endDate) {
    System.out.println("Stock information for " + stock.getTickerSymbol() + " (" + stock.getCompanyName() + ") from " + startDate + " to " + endDate);
  }
}
