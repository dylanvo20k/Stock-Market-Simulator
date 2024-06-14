import controller.StockController;
import model.PortfolioManager;
import view.ViewStocks;

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
    app.start();
  }
}
