package controller;

import model.IModel;
import model.IStockInfo;
import model.Portfolio;
import model.PortfolioManager;
import model.StockInfo;

import java.time.LocalDate;
import java.util.*;

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
      System.out.println("9. Calculate Moving Day Average");
      System.out.println("10. Detect Crossovers");
      System.out.println("11. Calculate Gain or Loss");
      System.out.println("12. Rebalance Portfolio");
      System.out.println("13. View portfolio performance chart");
      System.out.println("14. Exit");
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
          calculateMovingDayAverage(scanner);
          break;
        case 10:
          detectCrossovers(scanner);
          break;
        case 11:
          calculateGainOrLoss(scanner);
          break;
        case 12:
          rebalancePortfolio(scanner);
          break;
        case 13:
          viewPortfolioPerformanceChart(scanner);
          break;
        case 14:
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
    List<IStockInfo> initialStocks = new ArrayList<>();
    Portfolio portfolio = new Portfolio(clientName, initialStocks);
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

    LocalDate date = LocalDate.parse(stockDate);

    try {
      portfolio.sellStock(tickerSymbol, date, quantity);
      System.out.println("Stock sold successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
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

  private void calculateMovingDayAverage(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    System.out.print("Enter number of days: ");
    int days = scanner.nextInt();
    System.out.print("Enter end date (YYYY-MM-DD): ");
    String endDateStr = scanner.next();
    LocalDate endDate = LocalDate.parse(endDateStr);
    double movingAverage = portfolioManager.calculateMovingDayAverage(tickerSymbol, days, endDate);
    System.out.println("Moving day average: " + movingAverage);
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
    List<LocalDate> crossovers = portfolioManager.detectCrossovers(tickerSymbol, days, startDate, endDate);
    System.out.println("Crossovers detected on: " + crossovers);
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

  private void rebalancePortfolio(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter rebalancing date (YYYY-MM-DD): ");
    String rebalanceDateStr = scanner.next();
    LocalDate rebalanceDate = LocalDate.parse(rebalanceDateStr);

    System.out.println("Enter the target allocation percentages for each stock (ticker symbol and percentage, separated by spaces): ");
    Map<String, Double> targetAllocation = new HashMap<>();
    while (scanner.hasNext()) {
      String ticker = scanner.next();
      if (ticker.equals("done")) break;
      double percentage = scanner.nextDouble();
      targetAllocation.put(ticker, percentage);
    }

    Map<String, Integer> composition = portfolio.getComposition(rebalanceDate);
    Map<String, Double> valueDistribution = portfolio.getValueDistribution(rebalanceDate, portfolioManager);

    double totalValue = valueDistribution.values().stream().mapToDouble(Double::doubleValue).sum();

    Map<String, Double> intendedValues = new HashMap<>();
    targetAllocation.forEach((ticker, percentage) -> intendedValues.put(ticker, totalValue * percentage / 100.0));

    for (Map.Entry<String, Double> entry : intendedValues.entrySet()) {
      String tickerSymbol = entry.getKey();
      double intendedValue = entry.getValue();
      int currentQuantity = composition.getOrDefault(tickerSymbol, 0);
      double currentValue = valueDistribution.getOrDefault(tickerSymbol, 0.0);

      if (currentValue > intendedValue) {
        int sellQuantity = (int) ((currentValue - intendedValue) / portfolioManager.fetchStockPrice(tickerSymbol, rebalanceDate));
        IStockInfo sellingStock = new StockInfo("", tickerSymbol, rebalanceDate.toString(), sellQuantity);
        portfolio.sellStock(sellingStock.getTickerSymbol(), rebalanceDate, sellQuantity);
        System.out.println("Sold " + sellQuantity + " shares of " + tickerSymbol + " to rebalance the portfolio.");
      } else if (currentValue < intendedValue) {
        double stockPrice = portfolioManager.fetchStockPrice(tickerSymbol, rebalanceDate);
        int buyQuantity = (int) ((intendedValue - currentValue) / stockPrice);
        portfolio.addStock(new StockInfo("", tickerSymbol, rebalanceDate.toString(), buyQuantity));
        System.out.println("Bought " + buyQuantity + " shares of " + tickerSymbol + " to rebalance the portfolio.");
      }
    }

    System.out.println("Portfolio rebalanced successfully.");
  }

  private Portfolio findPortfolioByClientName(String clientName) {
    for (Portfolio portfolio : portfolios) {
      if (portfolio.getClientName().equals(clientName)) {
        return portfolio;
      }
    }
    return null;
  }

  private void viewPortfolioPerformanceChart(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter start date (YYYY-MM-DD): ");
    String startDateStr = scanner.next();
    LocalDate startDate = LocalDate.parse(startDateStr);

    System.out.print("Enter end date (YYYY-MM-DD): ");
    String endDateStr = scanner.next();
    LocalDate endDate = LocalDate.parse(endDateStr);

    IModel model = portfolioManager;

    portfolio.generatePerformanceChart(startDate, endDate, model);
  }
}
