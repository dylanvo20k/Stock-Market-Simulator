package controller;

import model.Portfolio;
import model.PortfolioManager;
import model.StockInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockController {
  private PortfolioManager portfolioManager;
  private List<Portfolio> portfolios;

  public StockController() {
    portfolioManager = new PortfolioManager();
    portfolios = new ArrayList<>();
  }

  public void start() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Welcome to the Stock Investment App");
      System.out.println("You have a total of 25 queries to use, please use them accordingly.");
      System.out.println("1. Create Portfolio");
      System.out.println("2. Add Stock to Portfolio");
      System.out.println("3. Calculate Portfolio Value");
      System.out.println("4. Calculate Moving Day Average");
      System.out.println("5. Detect Crossovers");
      System.out.println("6. Calculate Gain or Loss");
      System.out.println("7. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          createPortfolio(scanner);
          break;
        case 2:
          addStockToPortfolio(scanner);
          break;
        case 3:
          calculatePortfolioValue(scanner);
          break;
        case 4:
          calculateMovingDayAverage(scanner);
          break;
        case 5:
          detectCrossovers(scanner);
          break;
        case 6:
          calculateGainOrLoss(scanner);
          break;
        case 7:
          System.exit(0);
          break;
        default:
          System.out.println("Invalid choice! Please try again.");
      }
    }
  }

  private void createPortfolio(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = new Portfolio(clientName, new ArrayList<>());
    portfolios.add(portfolio);
    System.out.println("Portfolio created successfully.");
  }

  private void addStockToPortfolio(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found for client: " + clientName);
      return;
    }

    try {
      System.out.print("Enter company name: ");
      String companyName = scanner.next();
      System.out.print("Enter ticker symbol: ");
      String tickerSymbol = scanner.next();
      System.out.print("Enter stock date (YYYY-MM-DD): ");
      String stockDate = scanner.next();
      System.out.print("Enter quantity: ");
      int quantity = scanner.nextInt();

      StockInfo stockInfo = new StockInfo(companyName, tickerSymbol, stockDate, quantity);
      portfolio.addStock(stockInfo);
      System.out.println("Stock added to portfolio successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void calculatePortfolioValue(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found for client: " + clientName);
      return;
    }

    System.out.print("Enter date (YYYY-MM-DD): ");
    String dateStr = scanner.next();
    LocalDate date = LocalDate.parse(dateStr);

    try {
      double totalValue = portfolio.calculatePortfolioValue(date);
      System.out.println("Total portfolio value on " + date + ": " + totalValue);
    } catch (RuntimeException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void calculateMovingDayAverage(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    System.out.print("Enter number of days: ");
    int days = scanner.nextInt();
    System.out.print("Enter end date (YYYY-MM-DD): ");
    String endDateStr = scanner.next();
    LocalDate endDate = LocalDate.parse(endDateStr);

    try {
      double movingAverage = portfolioManager.calculateMovingDayAverage(tickerSymbol, days, endDate);
      System.out.println("Moving day average: " + movingAverage);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void detectCrossovers(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    System.out.print("Enter number of days: ");
    int days = scanner.nextInt();
    System.out.print("Enter start date (YYYY-MM-DD): ");
    String startDateStr = scanner.next();
    LocalDate startDate = LocalDate.parse(startDateStr);
    System.out.print("Enter end date (YYYY-MM-DD): ");
    String endDateStr = scanner.next();
    LocalDate endDate = LocalDate.parse(endDateStr);

    try {
      List<LocalDate> crossovers = portfolioManager.detectCrossovers(tickerSymbol, days, startDate, endDate);
      System.out.println("Crossovers detected on: " + crossovers);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void calculateGainOrLoss(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    System.out.print("Enter start date (YYYY-MM-DD): ");
    String startDateStr = scanner.next();
    LocalDate startDate = LocalDate.parse(startDateStr);
    System.out.print("Enter end date (YYYY-MM-DD): ");
    String endDateStr = scanner.next();
    LocalDate endDate = LocalDate.parse(endDateStr);

    try {
      double gainOrLoss = portfolioManager.calculateGainOrLoss(tickerSymbol, startDate, endDate);
      System.out.printf("Gain or Loss from %s to %s: %.2f%%\n", startDate, endDate, gainOrLoss);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
  private Portfolio findPortfolioByClientName(String clientName) {
    for (Portfolio portfolio : portfolios) {
      if (portfolio.getClientName().equals(clientName)) {
        return portfolio;
      }
    }
    return null;
  }
}
