import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import controller.StockController;

public class StockControllerTest {
  private StockController stockController;
  private InputStream sysInBackup;
  private ByteArrayInputStream input;

  @Before
  public void setUp() {
    stockController = new StockController();
    sysInBackup = System.in;
  }

  @After
  public void tearDown() {
    System.setIn(sysInBackup);
  }


}