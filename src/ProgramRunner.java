import javax.swing.SwingUtilities;
import controller.GuiController;
import controller.StockController;
import view.GuiView;
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
      launchGuiMode();
    } else if (args.length == 1 && args[0].equals("-text")) {
      // Launch text-based interface
      launchTextMode();
    } else {
      // Invalid command-line arguments
      System.out.println("Invalid command-line arguments. Usage:");
      System.out.println("To run in text mode: java -jar assignment-4-OOD.jar -text");
      System.out.println("To run in GUI mode: java -jar assignment-4-OOD.jar");
    }
  }

  /**
   * Method to launch the text-based interface.
   */
  private static void launchTextMode() {
    ViewStocks view = new ViewStocks();
    StockController textApp = new StockController(view);
    textApp.start();
    System.exit(0);
  }


  /**
   * Method to launch the GUI interface.
   */
  private static void launchGuiMode() {
    SwingUtilities.invokeLater(() -> {
      GuiView guiView = new GuiView();
      new GuiController(guiView);
      guiView.setVisible(true);
    });
  }
}
