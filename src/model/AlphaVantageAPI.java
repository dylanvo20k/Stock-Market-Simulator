package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link IStockFetcher} interface that retrieves stock data from the Alpha Vantage API.
 * This class provides methods to fetch closing prices for a given stock symbol over a specified date range,
 * as well as fetching the stock price for a specific date.
 */
public class AlphaVantageAPI implements IStockFetcher {
  private static final String API_KEY = "MYWEKXDOJ1DOGTIH"; // Replace with your own API key
  private static final String API_URL_TEMPLATE = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv";

  @Override
  public List<Double> fetchClosingPrices(String tickerSymbol, LocalDate startDate, LocalDate endDate) throws IOException {
    String apiUrl = String.format(API_URL_TEMPLATE, tickerSymbol, API_KEY);
    URL url = new URL(apiUrl);
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line;
    StringBuilder output = new StringBuilder();

    while ((line = reader.readLine()) != null) {
      output.append(line).append("\n");
    }
    reader.close();

    String[] lines = output.toString().split("\n");
    Map<LocalDate, Double> stockPrices = new HashMap<>();

    for (String record : lines) {
      String[] values = record.split(",");
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

  @Override
  public double fetchStockPrice(String tickerSymbol, LocalDate date) throws IOException {
    List<Double> closingPrices = fetchClosingPrices(tickerSymbol, date, date);

    if (closingPrices.isEmpty()) {
      return -1;
    }
    return closingPrices.get(0);
  }
}
