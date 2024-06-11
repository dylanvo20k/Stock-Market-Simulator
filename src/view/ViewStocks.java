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
    System.out.println("1. Examine gain or loss of a stock over a specified period.");
    System.out.println("2. Examine x-day moving average of a stock for a specified date.");
    System.out.println("3. Determine x-day crossovers for a specified stock.");
    System.out.println("4. Create a portfolio and find its value on a specific date.");
    System.out.println("5. Exit.");
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
