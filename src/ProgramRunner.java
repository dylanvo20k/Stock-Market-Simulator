import javax.swing.*;

import controller.GuiController;
import controller.StockController;
import view.GuiView;


/**
 * This class serves as the entry point for the stock portfolio application program.
 * It initializes the model, view, and controller, then starts the application program.
 */
public class ProgramRunner {
  /**
   * The main method that runs the program.
   * @param args  the arguments to run the program
   */
  public static void main(String[] args) {
    StockController app = new StockController();
    SwingUtilities.invokeLater(() -> {
      GuiView view = new GuiView();
      new GuiController(view);
      view.setVisible(true);
    });
    app.start();
  }
}
