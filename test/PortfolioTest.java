import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import model.Portfolio;
import model.StockInfo;

import static org.junit.Assert.*;

public class PortfolioTest {
  private Portfolio portfolio;
  private StockInfo stock1;
  private StockInfo stock2;
  private StockInfo stock3;

  @Before
  public void setUp() {
    stock1 = new StockInfo("Apple", "AAPL", "2023-06-01", 10 );
    stock2 = new StockInfo("Google", "GOOGL", "2023-06-02", 5);
    stock3 = new StockInfo("Tesla", "TSLA", "2023-06-03", 20);

    List<StockInfo> initialStocks = Arrays.asList(stock1, stock2);
    portfolio = new Portfolio("John Doe", initialStocks);
  }
  @Test
  public void testPortfolioCreation() {
    assertEquals("John Doe", portfolio.getClientName());
    assertEquals(2, portfolio.getStockList().size());
    assertTrue(portfolio.getStockList().contains(stock1));
    assertTrue(portfolio.getStockList().contains(stock2));
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
    double teslaPrice = 700.0;

    LocalDate date = LocalDate.now();

    double expectedValue = (stock1.getQuantity() * applePrice)  + (stock2.getQuantity() * googlePrice); ;

    List<StockInfo> initialStocks = Arrays.asList(stock1, stock2);
    Portfolio portfolio = new Portfolio("John Doe", initialStocks);

    assertEquals(expectedValue, portfolio.getPortfolioValue(date), 0.01);
  }
}