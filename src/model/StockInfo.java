package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StockInfo {
  private String companyName;
  private String tickerSymbol;
  private LocalDate stockDate;
  private int quantity;
  private static final String API_KEY = "1NWYKFC079957SBS"; // Replace with your own API key

  public StockInfo(String companyName, String tickerSymbol, String stockDate, int quantity) {
    if (!isValidTicker(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid ticker symbol: " + tickerSymbol);
    }
    this.companyName = companyName;
    this.tickerSymbol = tickerSymbol;
    this.stockDate = LocalDate.parse(stockDate);
    this.quantity = quantity;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public LocalDate getStockDate() {
    return stockDate;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getStockValueOnDate(LocalDate date) {
    try {
      double priceOnDate = fetchStockPriceOnDate(date);
      return priceOnDate * quantity;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock price: " + e.getMessage());
    }
  }

  private double fetchStockPriceOnDate(LocalDate date) throws IOException {
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
      throw new IllegalArgumentException("No price data found for " + tickerSymbol);
    }

    String[] lines = output.toString().split("\n");
    Map<LocalDate, Double> stockPrices = new HashMap<>();
    for (String line : lines) {
      String[] values = line.split(",");
      if (values.length < 5 || values[0].equals("timestamp")) {
        continue;
      }
      LocalDate dataDate = LocalDate.parse(values[0], DateTimeFormatter.ISO_DATE);
      double closingPrice = Double.parseDouble(values[4]);
      stockPrices.put(dataDate, closingPrice);
    }

    Double price = stockPrices.get(date);
    if (price == null) {
      throw new IllegalArgumentException("No price data found for " + tickerSymbol + " on " + date);
    }
    return price;
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

    return !output.toString().contains("Error Message");
  }
}
