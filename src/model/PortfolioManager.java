package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioManager {
  private static final String API_KEY = "1NWYKFC079957SBS"; // Replace with your own API key

  public double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate) {
    if (!isValidTicker(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid ticker symbol: " + tickerSymbol);
    }
    try {
      List<Double> closingPrices = fetchClosingPrices(tickerSymbol, endDate.minusDays(days - 1), endDate);
      double sum = 0.0;
      for (double price : closingPrices) {
        sum += price;
      }
      return sum / days;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  public List<LocalDate> detectCrossovers(String tickerSymbol, int days, LocalDate startDate, LocalDate endDate) {
    if (!isValidTicker(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid ticker symbol: " + tickerSymbol);
    }
    try {
      List<LocalDate> crossovers = new ArrayList<>();
      List<Double> closingPrices = fetchClosingPrices(tickerSymbol, startDate, endDate);

      for (int i = days; i < closingPrices.size(); i++) {
        double movingAverage = calculateMovingDayAverage(tickerSymbol, days, startDate.plusDays(i));
        if (closingPrices.get(i) > movingAverage) {
          crossovers.add(startDate.plusDays(i));
        }
      }
      return crossovers;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  private List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException {
    StringBuilder output = new StringBuilder();
    URL url = null;

    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + tickerSymbol
              + "&apikey=" + API_KEY
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("The Alpha Vantage API has either changed or no longer works");
    }

    try ( InputStream in = url.openStream()) {
      int b;
      while ((b = in.read()) != - 1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + tickerSymbol);
    }

    System.out.println("CSV Date " + output.toString());

    String[] lines = output.toString().split("\n");
    Map<LocalDate, Double> stockPrices = new HashMap<>();
    for (String line : lines) {
      String[] values = line.split(",");
      if (values.length < 5 || values[0].equals("timestamp")) {
        continue;
      }
      LocalDate dataDate = LocalDate.parse(values[0], DateTimeFormatter.ISO_DATE);
      if (!dataDate.isBefore(startDate) && !dataDate.isAfter(endDate)) {
        double closingPrice = Double.parseDouble(values[4]);
        stockPrices.put(dataDate, closingPrice);
      }
    }

    List<Double> closingPrices = new ArrayList<>();
    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      Double price = stockPrices.get(date);
      if (price != null) {
        closingPrices.add(price);
      }
    }
    return closingPrices;
  }

  private boolean isValidTicker(String tickerSymbol) {
    StringBuilder output = new StringBuilder();
    URL url = null;

    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + tickerSymbol
              + "&apikey=" + API_KEY
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("The Alpha Vantage API has either changed or no longer works");
    }

    try (InputStream in = url.openStream()) {
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      return false;
    }
    System.out.println(output.toString());
    return !output.toString().contains("Error Message");
  }
}
