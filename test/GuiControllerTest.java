//import controller.GuiController;
//import org.junit.Before;
//import org.junit.Test;
//import view.MockGuiView;
//
//import static org.junit.Assert.assertEquals;
//
//public class GuiControllerTest {
//
//  private MockGuiView mockView;
//  private GuiController controller;
//
//  @Before
//  public void setUp() {
//    mockView = new MockGuiView();
//    controller = new GuiController(mockView);
//  }
//
//  @Test
//  public void testCreatePortfolio() {
//    // Simulate user input
//    mockView.setInput("Test Client");
//    String expectedOutput = "Portfolio created for client: Test Client";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//
//  @Test
//  public void testAddStock() {
//    // Set mock view inputs
//    mockView.setInput("AAPL");
//    mockView.setInput("10");
//    mockView.setInput("2020");
//    mockView.setInput("01");
//    mockView.setInput("01");
//    String expectedOutput = "Stock added: AAPL, Quantity: 10, Date: 2020-01-01";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//
//  @Test
//  public void testSellStock() {
//    // Set mock view inputs
//    mockView.setInput("AAPL");
//    mockView.setInput("5");
//    mockView.setInput("2020");
//    mockView.setInput("01");
//    mockView.setInput("02");
//    String expectedOutput = "Stock sold: AAPL, Quantity: 5, Date: 2020-01-02";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//
//  @Test
//  public void testQueryValue() {
//    // Set mock view inputs
//    mockView.setInput("2020");
//    mockView.setInput("01");
//    mockView.setInput("03");
//    String expectedOutput = "Portfolio value on 2020-01-03: 10000.0";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//
//  @Test
//  public void testQueryComposition() {
//    // Set mock view inputs
//    mockView.setInput("2020");
//    mockView.setInput("01");
//    mockView.setInput("03");
//    String expectedOutput = "Portfolio composition on 2020-01-03:\nAAPL: 10\n";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//
//  @Test
//  public void testSavePortfolio() {
//    // Simulate file chooser action
//    mockView.setInput("test_portfolio.txt");
//    String expectedOutput = "Portfolio saved to: test_portfolio.txt";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//
//  @Test
//  public void testLoadPortfolio() {
//    // Simulate file chooser action
//    mockView.setInput("test_portfolio.txt");
//    String expectedOutput = "Portfolio loaded from: test_portfolio.txt";
//
//    assertEquals(expectedOutput, mockView.getOutput());
//  }
//}


// I tried my best to test the GUIController using a mock of GUIView and the action listeners
// however, I didn't have enough time to finish it. BUT I do see how testing the GUI Controller
// works now which is very helpful.