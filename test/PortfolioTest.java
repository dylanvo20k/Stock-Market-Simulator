import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import model.IStockInfo;
import model.MockStockInfo;
import model.Portfolio;
import model.PortfolioManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PortfolioTest {
  private Portfolio portfolio;
  private MockStockInfo stock1;
  private MockStockInfo stock2;
  private MockStockInfo stock3;
  private PortfolioManager portfolioManager;

  @Before
  public void setUp() {
    stock1 = new MockStockInfo("Apple", "AAPL", "2023-06-01", 10, 150.0);
    stock2 = new MockStockInfo("Google", "GOOG", "2023-06-02", 5, 2800.0);
    stock3 = new MockStockInfo("Tesla", "TSLA", "2023-06-03", 20, 700.0);

    List<IStockInfo> initialStocks = Arrays.asList(stock1, stock2);
    portfolio = new Portfolio("John Doe", initialStocks);

    portfolioManager = new PortfolioManager();
  }

  @Test
  public void testPortfolioCreation() {
    assertEquals("John Doe", portfolio.getClientName());
    assertEquals(2, portfolio.getStockList().size());
    assertTrue(portfolio.getStockList().contains(stock1));
    assertTrue(portfolio.getStockList().contains(stock2));
  }

  @Test
  public void testGetStockList() {
    List<IStockInfo> stockList = portfolio.getStockList();
    assertEquals(2, stockList.size());
    assertTrue(stockList.contains(stock1));
    assertTrue(stockList.contains(stock2));
  }

  @Test
  public void testAddStock() {
    portfolio.addStock(stock3);
    assertEquals(3, portfolio.getStockList().size());
    assertTrue(portfolio.getStockList().contains(stock3));
  }

  @Test
  public void testGetPortfolioValue() {
    // mock prices for stocks
    double applePrice = 150.0;
    double googlePrice = 2800.0;
    LocalDate date = LocalDate.now();

    double expectedValue = (stock1.getQuantity() * applePrice) + (stock2.getQuantity() * googlePrice);

    assertEquals(expectedValue, portfolio.calculatePortfolioValue(date, portfolioManager), 0.01);
  }
}
