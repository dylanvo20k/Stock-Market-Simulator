//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Arrays;
//
//import model.PortfolioManager;
//
//import static org.junit.Assert.assertEquals;
//
//public class PortfolioManagerTest {
//
//  @Test
//  public void testCalculateMovingDayAverage() {
//    // Create a PortfolioManager instance
//    PortfolioManager portfolioManager = new PortfolioManager();
//
//    // Mock closing prices
//    List<Double> closingPrices = Arrays.asList(100.0, 110.0, 120.0, 130.0, 140.0);
//
//    // Testing calculateMovingDayAverage method
//    double result = portfolioManager.calculateMovingDayAverage("AAPL", 5, LocalDate.now(), closingPrices);
//    assertEquals(120.0, result, 0.01);
//  }
//
//  @Test
//  public void testDetectCrossovers() {
//    // Create a PortfolioManager instance
//    PortfolioManager portfolioManager = new PortfolioManager();
//
//    // Mock closing prices
//    List<Double> closingPrices = Arrays.asList(100.0, 110.0, 120.0, 130.0, 140.0);
//
//    // Testing detectCrossovers method
//    List<LocalDate> result = portfolioManager.detectCrossovers("AAPL", 5, LocalDate.now(), LocalDate.now(), closingPrices);
//    assertEquals(3, result.size());
//  }
//}
