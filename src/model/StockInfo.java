package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * The StockInfo class implements the IStockInfo interface to represent information about a
 * specific stock, including its company name, ticker symbol, stock date, quantity, and methods
 * to interact with its data, such as fetching its value on a specific date using the Alpha Vantage
 * API.
 */
public class StockInfo implements IStockInfo {
  private String companyName;
  private String tickerSymbol;
  private LocalDate stockDate;
  private int quantity;
  private static final String API_KEY = "MYWEKXDOJ1DOGTIH"; // Replace with your own API key
  private static final String API_URL_TEMPLATE = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv";

  /**
   * Constructs a StockInfo object with the provided company name, ticker symbol, stock date, and
   * quantity.
   *
   * @param companyName  the name of the company
   * @param tickerSymbol the ticker symbol of the stock
   * @param stockDate    the date of the stock
   * @param quantity     the quantity of the stock
   * @throws IllegalArgumentException if the ticker symbol is invalid
   */
  public StockInfo(String companyName, String tickerSymbol, String stockDate, int quantity) {
    if (!isValidTicker(tickerSymbol)) {
      throw new IllegalArgumentException("Invalid ticker symbol: " + tickerSymbol);
    }
    this.companyName = companyName;
    this.tickerSymbol = tickerSymbol;
    this.stockDate = LocalDate.parse(stockDate);
    this.quantity = quantity;
  }

  @Override
  public String getCompanyName() {
    return companyName;
  }

  @Override
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public LocalDate getStockDate() {
    return stockDate;
  }

  @Override
  public int getQuantity() {
    return quantity;
  }

  @Override
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Calculates the value of this stock on a specific date based on its quantity and the closing
   * price.
   *
   * @param date the date for which to calculate the stock value
   * @return the value fo the stock on a given date
   * @throws RuntimeException if there is an error fetching stock prices
   */
  public double getStockValueOnDate(LocalDate date) {
    try {
      double priceOnDate = fetchStockPriceOnDate(date);
      return priceOnDate * quantity;
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock price: " + e.getMessage());
    }
  }

  private double fetchStockPriceOnDate(LocalDate date) throws IOException {
    String apiUrl = String.format(API_URL_TEMPLATE, tickerSymbol, getApiKey());
    URL url = new URL(apiUrl);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
    Map<LocalDate, Double> stockPrices = new HashMap<>();

    // Read the CSV data
    while ((line = reader.readLine()) != null) {
      String[] values = line.split(",");
      if (values.length < 5 || values[0].equals("timestamp")) {
        continue;
      }
      LocalDate dataDate = LocalDate.parse(values[0],
              DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Update date format here
      double closingPrice = Double.parseDouble(values[4]);
      stockPrices.put(dataDate, closingPrice);
    }
    reader.close();

    // Return the stock price on the requested date
    Double price = stockPrices.get(date);
    if (price == null) {
      throw new IllegalArgumentException("No price data found for " + tickerSymbol + " on " + date);
    }
    return price;
  }

  private boolean isValidTicker(String tickerSymbol) {
    return tickerSymbol != null && !tickerSymbol.trim().isEmpty()
            && tickerSymbol.matches("[A-Z]{4}");
  }

  public static String getApiKey() {
    return API_KEY;
  }

  @Override
  public boolean isValid() {
    return companyName != null
            && !companyName.trim().isEmpty()
            && tickerSymbol != null
            && !tickerSymbol.trim().isEmpty()
            && stockDate != null
            && quantity > 0;
  }
}
