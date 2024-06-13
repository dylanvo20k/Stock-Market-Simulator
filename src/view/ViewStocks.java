package view;

import java.util.Scanner;

import model.Portfolio;
import model.StockInfo;

public class ViewStocks implements IView {
  private Scanner scanner;

  public ViewStocks() {
    this.scanner = new Scanner(System.in);
  }

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
    System.out.println("The value of portfolio " + portfolio.getClientName() + " on " + date + " is $" + value);
  }

  @Override
  public void displayStockInfo(StockInfo stock, String startDate, String endDate) {
    System.out.println("Stock information for " + stock.getTickerSymbol() + " (" + stock.getCompanyName() + ") from " + startDate + " to " + endDate);
  }
}
