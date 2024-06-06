import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.time.LocalDate;

import model.StockInfo;

import static org.junit.Assert.assertEquals;

public class StockInfoTest {
  private StockInfo stockInfo;
  private LocalDate date;
  @Before
  public void setUp() {
    stockInfo = new StockInfo("Apple", "AAPL", "2023-06-01", 10);
    date = LocalDate.of(2023, 6, 1);
  }

  @Test
  public void testConstructorAndGetters() {
    assertEquals("Apple", stockInfo.getCompanyName() );
    assertEquals("AAPL", stockInfo.getTickerSymbol());
    assertEquals(LocalDate.of(2023, 6, 1), stockInfo.getStockDate());
    assertEquals(10, stockInfo.getQuantity());
  }
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidTickerSymbol() {
    new StockInfo("INVALID", "INVLD", "2023-06-01", 10);
  }

  @Test
  public void testGetStockValueOnDate() throws Exception {
    // mocks the url and BufferedReader to simulate an API response
    String apiResponse = "timestamp,open,high,low,close,volume\n" +
            "2023-06-01,150.00,155.00,149.00,154.00,1000000\n" +
            "2023-05-31,148.00,152.00,147.00,150.00,900000";
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(apiResponse.getBytes());
    BufferedReader mockBufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));

    // uses reflectiont o replace actual URL connection with mock
    URL originalUrl = new URL(String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv", "AAPL", "1NWYKFC079957SBS"));
    HttpURLConnection mockHttpURLConnection = (HttpURLConnection) originalUrl.openConnection();
    mockHttpURLConnection.setDoInput(true);
    mockHttpURLConnection.getInputStream();
    stockInfo.getClass().getDeclaredField("API_URL_TEMPLATE").setAccessible(true);
    stockInfo.getClass().getDeclaredField("API_URL_TEMPLATE").set(stockInfo, mockHttpURLConnection.getURL());

    // fetch stock value on a given date
    double expectedValue = 154.00 * 10;
    double actualValue = stockInfo.getStockValueOnDate(date);
    assertEquals(expectedValue, actualValue, 0.01);
  }

}