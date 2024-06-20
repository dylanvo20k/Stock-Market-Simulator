import javax.swing.SwingUtilities;
import controller.GuiController;
import controller.StockController;
import view.GuiView;
import view.MockViewStocks;
import view.ViewStocks;

/**
 * This class serves as the entry point for the stock portfolio application program.
 * It initializes the model, view, and controller, then starts the application program.
 */
public class ProgramRunner {
  /**
   * The main method that runs the program.
   *
   * @param args the arguments to run the program
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      // No command line arguments provided, launch GUI by default
      launchGuiMode();
    } else if (args.length == 1 && args[0].equals("-text")) {
      // Launch text-based interface
      launchTextMode();
    } else {
      // Invalid command-line arguments
      System.out.println("Invalid command-line arguments. Usage:");
      System.out.println("To run in text mode: java -jar Program.jar -text");
      System.out.println("To run in GUI mode: java -jar Program.jar");
    }
  }

  /**
   * Method to launch the text-based interface.
   */
  private static void launchTextMode() {
    // Example test run using MockViewStocks
    String mockInput = "1\n2\n3\n"; // Example input for testing
    MockViewStocks mockView = new MockViewStocks(mockInput);
    StockController mockApp = new StockController(mockView);
    mockApp.start();
    System.out.println(mockView.getOutput());
  }

  /**
   * Method to launch the GUI interface.
   */
  private static void launchGuiMode() {
    // Run the Swing GUI
    SwingUtilities.invokeLater(() -> {
      GuiView guiView = new GuiView();
      new GuiController(guiView);
      guiView.setVisible(true);
    });

    // Run the console application with the original view
    StockController consoleApp = new StockController(new ViewStocks());
    consoleApp.start();
  }
}
