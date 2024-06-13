package controller;

import model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

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
      System.out.println("2. Manage Portfolio");
      System.out.println("3. Save Portfolio to File");
      System.out.println("4. Load Portfolio from File");
      System.out.println("5. Calculate Moving Day Average");
      System.out.println("6. Detect Crossovers");
      System.out.println("7. Calculate Gain or Loss");
      System.out.println("8. Rebalance Portfolio");
      System.out.println("9. View portfolio performance chart");
      System.out.println("10. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          createPortfolio(scanner);
          break;
        case 2:
          managePortfolio(scanner);
          break;
        case 3:
          savePortfolioToFile(scanner);
          break;
        case 4:
          loadPortfolioFromFile(scanner);
          break;
        case 5:
          calculateMovingDayAverage(scanner);
          break;
        case 6:
          detectCrossovers(scanner);
          break;
        case 7:
          calculateGainOrLoss(scanner);
          break;
        case 8:
          rebalancePortfolio(scanner);
          break;
        case 9:
          viewPortfolioPerformanceChart(scanner);
          break;
        case 10:
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

  private void managePortfolio(Scanner scanner) {
    while (true) {
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
      int choice = scanner.nextInt();

      switch (choice) {
        case 1:
          addStockToPortfolio(scanner);
          break;
        case 2:
          sellStockFromPortfolio(scanner);
          break;
        case 3:
          calculatePortfolioValue(scanner);
          break;
        case 4:
          getPortfolioComposition(scanner);
          break;
        case 5:
          getPortfolioValueDistribution(scanner);
          break;
        case 6:
          calculateMovingDayAverage(scanner);
          break;
        case 7:
          detectCrossovers(scanner);
          break;
        case 8:
          calculateGainOrLoss(scanner);
          break;
        case 9:
          rebalancePortfolio(scanner);
          break;
        case 10:
          viewPortfolioPerformanceChart(scanner);
          break;
        case 11:
          return;
        default:
          System.out.println("Invalid choice! Please try again.");
      }
    }
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
    int year, month, day;

    try {
      System.out.print("Enter stock year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter stock month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter stock day (DD): ");
      day = scanner.nextInt();
      LocalDate stockDate = LocalDate.of(year, month, day);
      System.out.print("Enter quantity: ");
      int quantity = scanner.nextInt();

      if (!isWholeNumber(quantity)) {
        return;
      }

      StockInfo stockInfo = new StockInfo(companyName, tickerSymbol, stockDate.toString(), quantity);
      portfolio.addStock(stockInfo);
      System.out.println("Stock added successfully.");
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
    }
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
    int year, month, day;

    try {
      System.out.print("Enter stock year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter stock month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter stock day (DD): ");
      day = scanner.nextInt();
      LocalDate stockDate = LocalDate.of(year, month, day);
      System.out.print("Enter quantity: ");
      int quantity = scanner.nextInt();

      if (!isWholeNumber(quantity)) {
        return;
      }

      portfolio.sellStock(tickerSymbol, stockDate, quantity);
      System.out.println("Stock sold successfully.");
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
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

    int year, month, day;
    try {
      System.out.print("Enter date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter date day (DD): ");
      day = scanner.nextInt();
      LocalDate date = LocalDate.of(year, month, day);

      double totalValue = portfolio.calculatePortfolioValue(date, portfolioManager);
      System.out.println("Total portfolio value on " + date + ": " + "$" + totalValue);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: Invalid date format or values.");
      scanner.nextLine();
    }
  }

  private void getPortfolioComposition(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    int year, month, day;
    try {
      System.out.print("Enter date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter date day (DD): ");
      day = scanner.nextInt();
      LocalDate date = LocalDate.of(year, month, day);
      Map<String, Integer> composition = portfolio.getComposition(date);
      System.out.println("Portfolio composition on " + date + ":");
      composition.forEach((ticker, quantity) -> System.out.println(ticker + ": " + quantity + " shares"));
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
    }
  }

  private void getPortfolioValueDistribution(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    int year, month, day;
    try {
      System.out.print("Enter date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter date day (DD): ");
      day = scanner.nextInt();
      LocalDate date = LocalDate.of(year, month, day);
      Map<String, Double> distribution = portfolio.getValueDistribution(date, portfolioManager);
      System.out.println("Portfolio value distribution on " + date + ":");
      distribution.forEach((ticker, value) -> System.out.println(ticker + ": " + value));
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
    }
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

    int days;
    try {
      System.out.print("Enter number of days: ");
      days = scanner.nextInt();
      if (days <= 0) {
        System.out.println("Error: Number of days must be greater than zero.");
        return;
      }
    } catch (InputMismatchException e) {
      System.out.println("Error: Invalid input format. Please enter a valid number for days.");
      scanner.nextLine(); // Clear the scanner buffer
      return;
    }

    int year, month, day;
    try {
      System.out.print("Enter end date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter end date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter end date day (DD): ");
      day = scanner.nextInt();
      LocalDate endDate = LocalDate.of(year, month, day);

      double movingAverage = portfolioManager.calculateMovingDayAverage(tickerSymbol, days, endDate);
      System.out.printf("Moving %d-day average for %s ending on %s: %.2f\n", days, tickerSymbol, endDate, movingAverage);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: Invalid date format or values.");
      scanner.nextLine();
    }
  }

  private void detectCrossovers(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();

    int days;
    try {
      System.out.print("Enter number of days: ");
      days = scanner.nextInt();
      if (days <= 0) {
        System.out.println("Error: Number of days must be greater than zero.");
        return;
      }
    } catch (InputMismatchException e) {
      System.out.println("Error: Invalid input format. Please enter a valid number for days.");
      scanner.nextLine();
      return;
    }

    int startYear, startMonth, startDay, endYear, endMonth, endDay;
    try {
      System.out.print("Enter start date year (YYYY): ");
      startYear = scanner.nextInt();
      System.out.print("Enter start date month (MM): ");
      startMonth = scanner.nextInt();
      System.out.print("Enter start date day (DD): ");
      startDay = scanner.nextInt();
      LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

      System.out.print("Enter end date year (YYYY): ");
      endYear = scanner.nextInt();
      System.out.print("Enter end date month (MM): ");
      endMonth = scanner.nextInt();
      System.out.print("Enter end date day (DD): ");
      endDay = scanner.nextInt();
      LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

      if (startDate.isAfter(endDate)) {
        System.out.println("Error: Start date must be before end date.");
        return;
      }

      List<LocalDate> crossovers = portfolioManager.detectCrossovers(tickerSymbol, days, startDate, endDate);
      System.out.println("Crossovers detected on: " + crossovers);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: Invalid date format or values.");
      scanner.nextLine();
    }
  }


  private void calculateGainOrLoss(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    int startYear, startMonth, startDay, endYear, endMonth, endDay;
    try {
      System.out.print("Enter start date year (YYYY): ");
      startYear = scanner.nextInt();
      System.out.print("Enter start date month (MM): ");
      startMonth = scanner.nextInt();
      System.out.print("Enter start date day (DD): ");
      startDay = scanner.nextInt();
      System.out.print("Enter end date year (YYYY): ");
      endYear = scanner.nextInt();
      System.out.print("Enter end date month (MM): ");
      endMonth = scanner.nextInt();
      System.out.print("Enter end date day (DD): ");
      endDay = scanner.nextInt();
      LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
      LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
      double gainOrLoss = portfolioManager.calculateGainOrLoss(tickerSymbol, startDate, endDate);
      System.out.printf("Gain or Loss from %s to %s: %.2f%%\n", startDate, endDate, gainOrLoss);
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
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

    int year, month, day;
    try {
      System.out.print("Enter rebalancing date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter rebalancing date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter rebalancing date day (DD): ");
      day = scanner.nextInt();
      LocalDate rebalanceDate = LocalDate.of(year, month, day);

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
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
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

  private void viewPortfolioPerformanceChart(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    Portfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter start year: ");
    int startYear = scanner.nextInt();
    System.out.print("Enter start month: ");
    int startMonth = scanner.nextInt();
    System.out.print("Enter start day: ");
    int startDay = scanner.nextInt();
    LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

    System.out.print("Enter end year: ");
    int endYear = scanner.nextInt();
    System.out.print("Enter end month: ");
    int endMonth = scanner.nextInt();
    System.out.print("Enter end day: ");
    int endDay = scanner.nextInt();
    LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

    IModel model = portfolioManager;

    List<LocalDate> timestamps = generateTimestamps(startDate, endDate);

    List<Double> values = new ArrayList<>();
    for (LocalDate date : timestamps) {
      double value = portfolio.calculatePortfolioValue(date, model);
      values.add(value);
    }

    double minValue = Collections.min(values);
    double maxValue = Collections.max(values);
    double range = maxValue - minValue;

    int maxAsterisks = 50;
    double valuePerAsterisk = range / maxAsterisks;

    System.out.println("Portfolio Performance Chart:");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (int i = 0; i < timestamps.size(); i++) {
      LocalDate date = timestamps.get(i);
      double value = values.get(i);
      int asterisksCount = (int) ((value - minValue) / valuePerAsterisk);
      String asterisks = "*".repeat(asterisksCount);
      System.out.printf("%s | %s\n", dateFormatter.format(date), asterisks);
    }

    System.out.printf("Scale: Each asterisk represents %.2f units of value.\n", valuePerAsterisk);
  }

  private List<LocalDate> generateTimestamps(LocalDate startDate, LocalDate endDate) {
    long daysBetween = DAYS.between(startDate, endDate);

    List<LocalDate> timestamps = new ArrayList<>();

    if (daysBetween <= 30) {
      // Daily intervals
      LocalDate currentDate = startDate;
      while (!currentDate.isAfter(endDate)) {
        timestamps.add(currentDate);
        currentDate = currentDate.plusDays(1);
      }
    } else if (daysBetween <= 210) {
      // Weekly intervals
      LocalDate currentDate = startDate;
      while (!currentDate.isAfter(endDate)) {
        timestamps.add(currentDate);
        currentDate = currentDate.plusWeeks(1);
      }
    } else if (daysBetween <= 900) {
      // Monthly intervals
      LocalDate currentDate = startDate.withDayOfMonth(1);
      while (!currentDate.isAfter(endDate)) {
        timestamps.add(currentDate);
        currentDate = currentDate.plusMonths(1);
      }
    } else {
      // Yearly intervals
      LocalDate currentDate = startDate.withDayOfYear(1);
      while (!currentDate.isAfter(endDate)) {
        timestamps.add(currentDate);
        currentDate = currentDate.plusYears(1);
      }
    }

    return timestamps;
  }

  private boolean isWholeNumber(int amount) {
    if (amount % 1 != 0 || amount < 1) {
      System.out.println("Error: Quantity must be a whole number greater than 0.");
      return false;
    }
    return true;
  }
}
