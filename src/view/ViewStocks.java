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

  @Override
  public void displayMenu() {
    System.out.println("Welcome to the Stock Investment App");
    System.out.println("1. Create Portfolio");
    System.out.println("2. Manage Portfolio");
    System.out.println("3. Save Portfolio to File");
    System.out.println("4. Load Portfolio from File");
    System.out.println("5. Exit");
    System.out.print("Enter your choice: ");
  }

  @Override
  public void displayManagePortfolio() {
    System.out.println("Manage Portfolio:");
    System.out.println("1. Add Stock to Portfolio");
    System.out.println("2. Sell Stock from Portfolio");
    System.out.println("3. Calculate Portfolio Value");
    System.out.println("4. Get Portfolio Composition");
    System.out.println("5. Get Portfolio Value Distribution");
    System.out.println("6. Calculate Moving Day Average");
    System.out.println("7. Detect Crossovers");
    System.out.println("8. Calculate Gain or Loss");
    System.out.println("9. Rebalance Portfolio");
    System.out.println("10. View portfolio performance chart");
    System.out.println("11. Back to main menu");
    System.out.print("Enter your choice: ");
  }
  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }

  @Override
  public String getUserInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  @Override
  public void displayPortfolioValue(Portfolio portfolio, String date, double value) {
    System.out.println("The value of portfolio "
            + portfolio.getClientName() + " on " + date + " is $" + value);
  }

  @Override
  public void displayStockInfo(StockInfo stock, String startDate, String endDate) {
    System.out.println("Stock information for "
            + stock.getTickerSymbol() + " ("
            + stock.getCompanyName() + ") from "
            + startDate + " to " + endDate);
  }
}
