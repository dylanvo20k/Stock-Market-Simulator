package controller;


import model.AlphaVantageAPI;
import model.IModel;
import model.IPortfolio;
import model.IStockFetcher;
import model.IStockInfo;
import model.Portfolio;
import model.PortfolioManager;
import model.StockInfo;
import view.IView;
import view.ViewStocks;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class implements the controller logic for managing stock portfolios.
 * It allows creating portfolios, managing stocks within portfolios, calculating various metrics,
 * saving and loading portfolios from files, and interaction with the user.
 */
public class StockController implements IController {
  // Initially we used our concrete PortfolioManager class, but changed it to IModel for better
  // design and functionality.
  private IModel portfolioManager;
  // For our list, we originally used a Portfolio, however we changed it to IPortfolio for better
  // design and functionality.
  private List<IPortfolio> portfolios;
  private IView view;

  /**
   * Constructs a StockController object, with a brand new portfolioManager, new portfolios,
   * and sets up the API.
   */
  public StockController(IView view) {
    IStockFetcher stockFetcher = new AlphaVantageAPI();
    portfolioManager = new PortfolioManager(stockFetcher);
    portfolios = new ArrayList<>();
    this.view = new ViewStocks();
  }

  @Override
  public void start() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      // In assignment 5 we didn't call our displayMenu in view, so we changed our code to
      // call the displayMenu from view for assignment 6
      view.displayMenu();
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

  // new helper method to revamp the display
  private void managePortfolio(Scanner scanner) {
    while (true) {
      view.displayManagePortfolio();
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
    IPortfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter company name: ");
    String companyName = scanner.next();
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    int year;
    int month;
    int day;

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

      IStockInfo stockInfo = new StockInfo(companyName, tickerSymbol,
              stockDate.toString(), quantity);
      portfolio.addStock(stockInfo);
      System.out.println("Stock added successfully.");
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
    }
  }

  private void sellStockFromPortfolio(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    IPortfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();
    int year;
    int month;
    int day;

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
    IPortfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    int year;
    int month;
    int day;
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
    IPortfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    int year;
    int month;
    int day;

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
      composition.forEach((ticker, quantity) ->
              System.out.println(ticker + ": " + quantity + " shares"));
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
    }
  }

  private void getPortfolioValueDistribution(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    IPortfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    int year;
    int month;
    int day;
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
    IPortfolio portfolio = findPortfolioByClientName(clientName);
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

    int year;
    int month;
    int day;
    try {
      System.out.print("Enter end date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter end date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter end date day (DD): ");
      day = scanner.nextInt();
      LocalDate endDate = LocalDate.of(year, month, day);

      double movingAverage = portfolioManager
              .calculateMovingDayAverage(tickerSymbol, days, endDate);
      System.out.printf("Moving %d-day average for %s ending on %s: %.2f\n",
              days, tickerSymbol, endDate, movingAverage);
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

    int startYear;
    int startMonth;
    int startDay;
    int endYear;
    int endMonth;
    int endDay;
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

      List<LocalDate> crossovers = portfolioManager
              .detectCrossovers(tickerSymbol, days, startDate, endDate);
      System.out.println("Crossovers detected on: " + crossovers);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: Invalid date format or values.");
      scanner.nextLine();
    }
  }


  private void calculateGainOrLoss(Scanner scanner) {
    System.out.print("Enter ticker symbol: ");
    String tickerSymbol = scanner.next();

    int startYear;
    int startMonth;
    int startDay;
    int endYear;
    int endMonth;
    int endDay;

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
    IPortfolio portfolio = findPortfolioByClientName(clientName);
    if (portfolio == null) {
      System.out.println("Portfolio not found!");
      return;
    }

    int year;
    int month;
    int day;
    try {
      System.out.print("Enter rebalancing date year (YYYY): ");
      year = scanner.nextInt();
      System.out.print("Enter rebalancing date month (MM): ");
      month = scanner.nextInt();
      System.out.print("Enter rebalancing date day (DD): ");
      day = scanner.nextInt();
      LocalDate rebalanceDate = LocalDate.of(year, month, day);

      System.out.println("Enter the stock and weight percentages " +
              "for each stock followed by 'done' (i.e GOOG 20 done): ");
      Map<String, Double> targetAllocation = new HashMap<>();
      while (scanner.hasNext()) {
        String ticker = scanner.next();
        if (ticker.equals("done")) {
          break;
        }
        double percentage = scanner.nextDouble();
        if (percentage < 0 || percentage > 100) {
          System.out.println("Error: Each weight must be between 0 and 100.");
          return;
        }
        targetAllocation.put(ticker, percentage);
      }

      double totalPercentage = targetAllocation.values().stream()
              .mapToDouble(Double::doubleValue).sum();
      // We made a check to verify that weights inputted by a user are valid.
      if (totalPercentage != 100) {
        System.out.println("Error: Total weights must add up to 100%.");
        return;
      }
      Map<String, Integer> composition = portfolio.getComposition(rebalanceDate);
      Map<String, Double> valueDistribution = portfolio
              .getValueDistribution(rebalanceDate, portfolioManager);

      double totalValue = valueDistribution.values().stream()
              .mapToDouble(Double::doubleValue).sum();

      Map<String, Double> intendedValues = new HashMap<>();
      targetAllocation.forEach((ticker, percentage) ->
              intendedValues.put(ticker, totalValue * percentage / 100.0));

      for (Map.Entry<String, Double> entry : intendedValues.entrySet()) {
        String tickerSymbol = entry.getKey();
        double intendedValue = entry.getValue();
        int currentQuantity = composition.getOrDefault(tickerSymbol, 0);
        double currentValue = valueDistribution.getOrDefault(tickerSymbol, 0.0);

        if (currentValue > intendedValue) {
          int sellQuantity = (int) ((currentValue - intendedValue) /
                  portfolioManager.fetchStockPrice(tickerSymbol, rebalanceDate));
          IStockInfo sellingStock = new StockInfo("", tickerSymbol,
                  rebalanceDate.toString(), sellQuantity);
          portfolio.sellStock(sellingStock.getTickerSymbol(), rebalanceDate, sellQuantity);
          System.out.println("Sold " + sellQuantity + " shares of " +
                  tickerSymbol + " to rebalance the portfolio.");
        } else if (currentValue < intendedValue) {
          double stockPrice = portfolioManager.fetchStockPrice(tickerSymbol, rebalanceDate);
          int buyQuantity = (int) ((intendedValue - currentValue) / stockPrice);
          portfolio.addStock(new StockInfo("", tickerSymbol,
                  rebalanceDate.toString(), buyQuantity));
          System.out.println("Bought " + buyQuantity + " shares of "
                  + tickerSymbol + " to rebalance the portfolio.");
        }
      }

      System.out.println("Portfolio rebalanced successfully.");
    } catch (Exception e) {
      System.out.println("Error: Invalid date format or values.");
    }
  }

  private IPortfolio findPortfolioByClientName(String clientName) {
    for (IPortfolio portfolio : portfolios) {
      if (portfolio.getClientName().equals(clientName)) {
        return portfolio;
      }
    }
    return null;
  }

  private void viewPortfolioPerformanceChart(Scanner scanner) {
    System.out.print("Enter client name: ");
    String clientName = scanner.next();
    IPortfolio portfolio = findPortfolioByClientName(clientName);
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

    System.out.printf("Scale: Each asterisk represents %.2f dollars.\n", valuePerAsterisk);
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
