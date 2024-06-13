import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.IModel;
import model.IStockInfo;
import model.MockModel;
import model.MockStockInfo;
import model.Portfolio;
import static org.junit.Assert.assertEquals;

public class PortfolioTest {
  private Portfolio portfolio;
  private MockStockInfo stock1;
  private MockStockInfo stock2;
  private MockStockInfo stock3;
  private IModel model;

  @Before
  public void setUp() {
    stock1 = new MockStockInfo("Apple", "AAPL", "2023-06-01", 10, 150.0);
    stock2 = new MockStockInfo("Google", "GOOG", "2023-06-02", 5, 2800.0);
    stock3 = new MockStockInfo("Tesla", "TSLA", "2023-06-03", 20, 700.0);

    List<IStockInfo> initialStocks = new ArrayList<>();
    initialStocks.add(stock1);
    initialStocks.add(stock2);
    initialStocks.add(stock3);

    portfolio = new Portfolio("John Doe", initialStocks);
    model = new MockModel();

  }
  @Test
  public void testGetClientname() {
    assertEquals("John Doe", portfolio.getClientName());
  }
  @Test
  public void testAddStock() {
    portfolio.addStock(stock3);
    assertEquals(3, portfolio.getComposition(LocalDate.now()).size());
  }
  @Test
  public void testSellStockSufficientQuantity() {
    portfolio.sellStock("AAPL", LocalDate.parse("2023-06-01"), 6);
    assertEquals(4, portfolio.getComposition(LocalDate.now()).get("AAPL").intValue());
  }
  @Test (expected = IllegalArgumentException.class)
  public void testSellStockInsufficientQuantity() {
    portfolio.sellStock("AAPL", LocalDate.parse("2023-06-01"), 15);
  }
  @Test
  public void testGetComposition() {
    Map<String, Integer> composition = portfolio.getComposition(LocalDate.now());
    assertEquals(3, composition.size());
    assertEquals(10, composition.get("AAPL").intValue());
    assertEquals(5, composition.get("GOOG").intValue());
  }

  @Test
  public void testSaveToFile() throws IOException {
    String tempFileName = "tempPortfolio.txt";
    portfolio.saveToFile(tempFileName);

    Path path = Paths.get(tempFileName);
    List<String> lines = Files.readAllLines(path);

    assertEquals("clientName:John Doe", lines.get(0));
    assertEquals("AAPL,Apple,2023-06-01,10", lines.get(1));
    assertEquals("GOOG,Google,2023-06-02,5", lines.get(2));
    assertEquals("TSLA,Tesla,2023-06-03,20", lines.get(3));

    Files.delete(path);
  }
  @Test
  public void testLoadFromFile() throws IOException {
    String tempFileName = "tempPortfolio.txt";
    List<String> lines = new ArrayList<>();
    lines.add("clientName:John Doe");
    lines.add("AAPL,Apple,2023-06-01,10");
    lines.add("GOOG,Google,2023-06-02,5");
    lines.add("TSLA,Tesla,2023-06-03,20");

    Path path = Paths.get(tempFileName);
    Files.write(path, lines);

    Portfolio loadedPortfolio = Portfolio.loadFromFile(tempFileName);
    assertEquals("John Doe", loadedPortfolio.getClientName());

    List<IStockInfo> stockList = loadedPortfolio.getStockList();
    assertEquals(3, stockList.size());
    assertEquals("AAPL", stockList.get(0).getTickerSymbol());
    assertEquals("Apple", stockList.get(0).getCompanyName());
    assertEquals("2023-06-01", stockList.get(0).getStockDate().toString());
    assertEquals(10, stockList.get(0).getQuantity());

    assertEquals("GOOG", stockList.get(1).getTickerSymbol());
    assertEquals("Google", stockList.get(1).getCompanyName());
    assertEquals("2023-06-02", stockList.get(1).getStockDate().toString());
    assertEquals(5, stockList.get(1).getQuantity());

    assertEquals("TSLA", stockList.get(2).getTickerSymbol());
    assertEquals("Tesla", stockList.get(2).getCompanyName());
    assertEquals("2023-06-03", stockList.get(2).getStockDate().toString());
    assertEquals(20, stockList.get(2).getQuantity());

    Files.delete(path); // Clean up the temporary file
  }

  @Test
  public void testGeneratePerformanceChart() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    LocalDate startDate = LocalDate.of(2023, 6, 1);
    LocalDate endDate = LocalDate.of(2023, 12, 31);

    portfolio.generatePerformanceChart(startDate, endDate, model);

    String expectedOutput = "Performance of portfolio John Doe from 2023-06-01 to 2023-12-31\n"
            + "Jul 2023: \n"
            + "Aug 2023: \n"
            + "Sep 2023: \n"
            + "Oct 2023: \n"
            + "Nov 2023: \n"
            + "Dec 2023: \n"
            + "\nScale: * = 0.00\n";

    assertEquals(expectedOutput, outContent.toString());
    System.setOut(System.out);
  }
}