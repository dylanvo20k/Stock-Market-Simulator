package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioManager {
  private static final String API_KEY = "8AHXFIPX9SVKC4LN"; // Replace with your own API key
  private static final String API_URL_TEMPLATE = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv";

  public double calculateMovingDayAverage(String tickerSymbol, int days, LocalDate endDate) {
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

  public double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate) {
    if (!isValidTicker(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid ticker symbol: " + tickerSymbol);
    }
    try {
      List<Double> closingPrices = fetchClosingPrices(tickerSymbol, startDate, endDate);
      if (closingPrices.size() < 2) {
        throw new IllegalArgumentException("Insufficient data for the given data range, " +
                "please provide at least 2 closing prices.");
      }
      double startPrice = closingPrices.get(0);
      double endPrice = closingPrices.get(closingPrices.size() - 1);
      return (endPrice - startPrice) / startPrice * 100;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  private List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException {
    String apiUrl = String.format(API_URL_TEMPLATE, tickerSymbol, API_KEY);
    URL url = new URL(apiUrl);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
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
      while ((b = in.read()) != - 1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + tickerSymbol);
    }

    System.out.println("CSV Date " + output.toString());

    String[] lines = output.toString().split("\n");
    Map<LocalDate, Double> stockPrices = new HashMap<>();

    // Read the CSV data
    while ((line = reader.readLine()) != null) {
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
    reader.close();

    List<Double> closingPrices = new ArrayList<>();
    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      Double price = stockPrices.get(date);
      if (price != null) {
        closingPrices.add(price);
      }
    }
    return closingPrices;
  }
}
