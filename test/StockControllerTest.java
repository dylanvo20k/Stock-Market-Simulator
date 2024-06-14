import controller.StockController;

import org.junit.Before;
import org.junit.Test;

import view.MockViewStocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * The StockControllerTest class contains unit tests for the StockController class.
 * It covers various scenarios related to creating portfolios and managing stocks.
 */
public class StockControllerTest {

  private ByteArrayOutputStream outContent;
  private StockController controller;
  private MockViewStocks mockView;

  @Before
  public void setUp() {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testCreatePortfolio() {
    String userInput = "1\nJohn Doe\n7\n";
    mockView = new MockViewStocks(userInput);
    controller = new StockController();
    controller.setView(mockView);

    ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(inContent);

    controller.start();

    String expectedOutput = "Welcome to the Stock Investment App\n"
            + "1. Create Portfolio\n"
            + "2. Manage Portfolio\n"
            + "3. Save Portfolio to File\n"
            + "4. Load Portfolio from File\n"
            + "5. Calculate Moving Day Average\n"
            + "6. Detect Crossovers\n"
            + "7. Calculate Gain or Loss\n"
            + "8. Rebalance Portfolio\n"
            + "9. View portfolio performance chart\n"
            + "10. Exit\n"
            + "Enter your choice: Enter client name: Portfolio created successfully.\n";
    assertEquals(expectedOutput, outContent.toString().replaceAll("\r", ""));
  }

  @Test
  public void testConstructPortfolio() {
    String userInput = "1\nJohn Doe\n2\nJohn Doe\nCompanyA\nAAPL\n2024\n06\n01\n10\n7\n";
    mockView = new MockViewStocks(userInput);
    controller = new StockController();
    controller.setView(mockView);

    ByteArrayInputStream inContent = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(inContent);

    controller.start();

    String expectedOutput = "Welcome to the Stock Investment App\n"
            + "1. Create Portfolio\n"
            + "2. Manage Portfolio\n"
            + "3. Save Portfolio to File\n"
            + "4. Load Portfolio from File\n"
            + "5. Calculate Moving Day Average\n"
            + "6. Detect Crossovers\n"
            + "7. Calculate Gain or Loss\n"
            + "8. Rebalance Portfolio\n"
            + "9. View portfolio performance chart\n"
            + "10. Exit\n"
            + "Enter your choice: Enter client name: Portfolio created successfully.\n"
            + "Manage Portfolio:\n"
            + "1. Add Stock to Portfolio\n"
            + "2. Sell Stock from Portfolio\n"
            + "3. Calculate Portfolio Value\n"
            + "4. Get Portfolio Composition\n"
            + "5. Get Portfolio Value Distribution\n"
            + "6. Calculate Moving Day Average\n"
            + "7. Detect Crossovers\n"
            + "8. Calculate Gain or Loss\n"
            + "9. Rebalance Portfolio\n"
            + "10. View portfolio performance chart\n"
            + "11. Back to main menu\n"
            + "Enter your choice: Enter client name: Enter company name: Enter ticker symbol: "
            + "Enter stock year (YYYY): Enter stock month (MM): Enter stock day (DD): "
            + "Enter quantity: "
            + "Stock added successfully.\n";
    assertEquals(expectedOutput, outContent.toString().replaceAll("\r", ""));
  }
}
