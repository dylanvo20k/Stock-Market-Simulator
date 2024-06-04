package view;

import java.util.Scanner;
public class ViewStocks {
  private Scanner scanner;

  public ViewStocks() {
    this.scanner = new Scanner(System.in);
  }

  public void menu() {
    System.out.println("Stocks Menu");
    System.out.println("1. Examine gain or loss of a stock over a specified period.");
    System.out.println("2. Examine x-day moving average of a stock for a specified date.");
    System.out.println("3. Determine x-day crossovers for a specified stock.");
    System.out.println("4. Create a portfolio and find its value on a specific date.");
    System.out.println("5. Exit.");
  }

  public void displayMessage(String message) {
    System.out.println(message);
  }

  public String getUserInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  public void displayPortfolioValue(Portfolio portfolio, String date, double value) {
    System.out.println("The value of portfolio " + portfolio.getName() + " on " + date + " is $" + value);
  }

  public void displayStockInfo(StockInfo stock, String startDate, String endDate) {
    System.out.println("Stock information for " + stock.getTickerSymbol() + " (" + stock.getCompanyName() + ") from " + startDate + " to " + endDate);
  }
}
