package controller;

import model.Portfolio;
import model.PortfolioManager;
import model.StockInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StockController implements IController {
  private PortfolioManager portfolioManager;
  private List<Portfolio> portfolios;

  public StockController() {
    portfolioManager = new PortfolioManager();
    portfolios = new ArrayList<>();
  }

  @Override
  public void start() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Welcome to the Stock Investment App");
      System.out.println("1. Create Portfolio");
      System.out.println("2. Add Stock to Portfolio");
      System.out.println("3. Sell Stock from Portfolio");
      System.out.println("4. Calculate Portfolio Value");
      System.out.println("5. Get Portfolio Composition");
      System.out.println("6. Get Portfolio Value Distribution");
      System.out.println("7. Save Portfolio to File");
      System.out.println("8. Load Portfolio from File");
      System.out.println("9. Exit");
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
          sellStockFromPortfolio(scanner);
          break;
        case 4:
          calculatePortfolioValue(scanner);
          break;
        case 5:
          getPortfolioComposition(scanner);
          break;
        case 6:
          getPortfolioValueDistribution(scanner);
          break;
        case 7:
          savePortfolioToFile(scanner);
          break;
        case 8:
          loadPortfolioFromFile(scanner);
          break;
        case 9:
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
    Portfolio portfolio = new Portfolio(clientName);
    portfolios.add(portfolio);
    System.out.println("Portfolio created successfully.");
  }

  private void addStockToPortfolio(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

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
    System.out.println("Stock added successfully.");
  }

  private void sellStockFromPortfolio(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    System.out.print("Enter stock date (YYYY-MM-DD): ");
    String stockDate = scanner.next();
    System.out.print("Enter quantity: ");
    int quantity = scanner.nextInt();

    StockInfo stockInfo = new StockInfo("", tickerSymbol, stockDate, -quantity);
    portfolio.sellStock(stockInfo);
    System.out.println("Stock sold successfully.");
  }

  private void calculatePortfolioValue(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter date (YYYY-MM-DD): ");
    String dateStr = scanner.next();
    LocalDate date = LocalDate.parse(dateStr);

    double totalValue = portfolio.calculatePortfolioValue(date, portfolioManager);
    System.out.println("Total portfolio value on " + date + ": " + totalValue);
  }

  private void getPortfolioComposition(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter date (YYYY-MM-DD): ");
    String dateStr = scanner.next();
    LocalDate date = LocalDate.parse(dateStr);

    Map<String, Integer> composition = portfolio.getComposition(date);
    System.out.println("Portfolio composition on " + date + ":");
    composition.forEach((ticker, quantity) -> System.out.println(ticker + ": " + quantity + " shares"));
  }

  private void getPortfolioValueDistribution(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter date (YYYY-MM-DD): ");
    String dateStr = scanner.next();
    LocalDate date = LocalDate.parse(dateStr);

    Map<String, Double> distribution = portfolio.getValueDistribution(date, portfolioManager);
    System.out.println("Portfolio value distribution on " + date + ":");
    distribution.forEach((ticker, value) -> System.out.println(ticker + ": " + value));
  }

  private void savePortfolioToFile(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter file name: ");
    String fileName = scanner.next();
    portfolio.saveToFile(fileName);
    System.out.println("Portfolio saved to " + fileName);
  }

  private void loadPortfolioFromFile(Scanner scanner) {
    System.out.print("Enter file name: ");
    String fileName = scanner.next();
    Portfolio portfolio = Portfolio.loadFromFile(fileName);
    if (portfolio == null) {
      System.out.println("Failed to load portfolio from " + fileName);
    } else {
      portfolios.add(portfolio);
      System.out.println("Portfolio loaded from " + fileName);
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
