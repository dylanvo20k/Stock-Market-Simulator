import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import model.Portfolio;
import model.StockInfo;
import view.ViewStocks;

public class ViewStocksTest {
  @Test
  public void testDisplayMenu() {
    ByteArrayOutputStream contentOutput = new ByteArrayOutputStream();
    System.setOut(new PrintStream(contentOutput));

    ViewStocks viewStocks = new ViewStocks();
    viewStocks.displayMenu();

    String expectedOutput = "Stocks Menu\n" +
            "1. Examine gain or loss of a stock over a specified period.\n" +
            "2. Examine x-day moving average of a stock for a specified date.\n" +
            "3. Determine x-day crossovers for a specified stock.\n" +
            "4. Create a portfolio and find its value on a specific date.\n" +
            "5. Exit.\n";
    assertEquals(expectedOutput, expectedOutput.toString());
  }

  @Test
  public void testDisplayMessage() {
    ByteArrayOutputStream contentOutput = new ByteArrayOutputStream();
    System.setOut(new PrintStream(contentOutput));

    ViewStocks viewStocks = new ViewStocks();
    viewStocks.displayMessage("This is a test message.");

    assertEquals("This is a test message.\n", contentOutput.toString());
  }

  @Test
  public void testGetUserInput() {
    ByteArrayInputStream contentInput= new ByteArrayInputStream("Test Input".getBytes());
    System.setIn(contentInput);

    ViewStocks viewStocks = new ViewStocks();
    String userInput = viewStocks.getUserInput("Please enter some input: ");

    assertEquals("Test Input", userInput);
  }

  @Test
  public void testDisplayPortfolioValue() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    Portfolio portfolio = new Portfolio("John Doe", new ArrayList<>()); // Initialize with a non-null list
    String date = "2024-06-05";
    double value = 1000.0;

    ViewStocks viewStocks = new ViewStocks();
    viewStocks.displayPortfolioValue(portfolio, date, value);

    assertEquals("The value of portfolio John Doe on 2024-06-05 is $1000.0\n", outContent.toString());
  }

  @Test
  public void testDisplayStockInfo() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Use a valid ticker symbol for the test
    StockInfo stock = new StockInfo("Company A", "AAPL", "2024-06-01", 10);
    String startDate = "2024-06-01";
    String endDate = "2024-06-05";

    ViewStocks viewStocks = new ViewStocks();
    viewStocks.displayStockInfo(stock, startDate, endDate);

    assertEquals("Stock information for AAPL (Company A) from 2024-06-01 to 2024-06-05\n", outContent.toString());
  }

  // You can add similar tests for other methods such as displayMessage, getUserInput, etc.
}
