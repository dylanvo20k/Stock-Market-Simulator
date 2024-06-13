package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

public class PortfolioManager implements IModel {
  private static final String API_KEY = "MYWEKXDOJ1DOGTIH"; // Replace with your own API key
  private static final String API_URL_TEMPLATE = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv";

  @Override
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

  @Override
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

  @Override
  public double calculateGainOrLoss(String tickerSymbol, LocalDate startDate, LocalDate endDate) {
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

  @Override
  public double fetchStockPrice(String tickerSymbol, LocalDate date) {
    try {
      List<Double> closingPrices = fetchClosingPrices(tickerSymbol, date, date);

      if (closingPrices.isEmpty()) {
        return -1; // Or any other special value to indicate no price found
      }

      return closingPrices.get(0); // Return the first (and only) element
    } catch (IOException e) {
      throw new RuntimeException("Error fetching stock prices: " + e.getMessage());
    }
  }

  @Override
  public List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException {
    String apiUrl = String.format(API_URL_TEMPLATE, tickerSymbol, API_KEY);
    URL url = new URL(apiUrl);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
    StringBuilder output = new StringBuilder();

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

  public List<StockPrice> fetchStockPrices(String tickerSymbol) throws IOException {
    List<StockPrice> stockPrices = new ArrayList<>();
    String apiUrl = String.format(API_URL_TEMPLATE, tickerSymbol, API_KEY);
    URL url = new URL(apiUrl);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
    reader.readLine(); // Skip the header

    while ((line = reader.readLine()) != null) {
      String[] values = line.split(",");
      LocalDate date = LocalDate.parse(values[0], DateTimeFormatter.ISO_DATE);
      double open = Double.parseDouble(values[1]);
      double high = Double.parseDouble(values[2]);
      double low = Double.parseDouble(values[3]);
      double close = Double.parseDouble(values[4]);

      StockPrice stockPrice = new StockPrice(open, close, low, high, date);
      stockPrices.add(stockPrice);
    }
    reader.close();
    return stockPrices;
  }

  // Method to save stock prices to a CSV file in the resources directory
  public void saveStockPricesToCSV(List<StockPrice> stockPrices, String fileName) {
    String resourcePath = "src/main/resources/" + fileName;
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(resourcePath))) {
      writer.write("date,open,high,low,close\n");
      for (StockPrice stockPrice : stockPrices) {
        writer.write(stockPrice.getDate() + "," + stockPrice.getOpeningPrice() + "," +
                stockPrice.getHighPrice() + "," + stockPrice.getLowPrice() + "," +
                stockPrice.getClosingPrice() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Method to load stock prices from a CSV file in the resources directory
  public List<StockPrice> loadStockPricesFromCSV(String fileName) {
    List<StockPrice> stockPrices = new ArrayList<>();
    String resourcePath = "src/res" + fileName;
    String line;

    try (BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
      br.readLine(); // Skip the header
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        LocalDate date = LocalDate.parse(values[0], DateTimeFormatter.ISO_DATE);
        double open = Double.parseDouble(values[1]);
        double high = Double.parseDouble(values[2]);
        double low = Double.parseDouble(values[3]);
        double close = Double.parseDouble(values[4]);

        StockPrice stockPrice = new StockPrice(open, close, low, high, date);
        stockPrices.add(stockPrice);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return stockPrices;

  }
}
