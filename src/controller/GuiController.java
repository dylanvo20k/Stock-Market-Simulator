package controller;

import model.AlphaVantageAPI;
import model.IModel;
import model.IPortfolio;
import model.IStockInfo;
import model.Portfolio;
import model.PortfolioManager;
import model.StockInfo;
import view.GuiView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * The GUI controller class is responsible for handing user interactions from the GUI interface,
 * performing operations on the portfolio, and updating the view with results inputted by the user.
 */
public class GuiController {
  private final GuiView view;
  private IPortfolio portfolio;
  private final IModel model;

  /**
   * Constructs a GuiController with the specified view and parameters.
   *
   * @param view the view our GUI interacts with
   */
  public GuiController(GuiView view) {
    this.view = view;
    this.model = new PortfolioManager(new AlphaVantageAPI());
    this.view.addCreatePortfolioListener(new CreatePortfolioListener());
    this.view.addAddStockListener(new AddStockListener());
    this.view.addSellStockListener(new SellStockListener());
    this.view.addQueryValueListener(new QueryValueListener());
    this.view.addQueryCompositionListener(new QueryCompositionListener());
    this.view.addSavePortfolioListener(new SavePortfolioListener());
    this.view.addLoadPortfolioListener(new LoadPortfolioListener());
  }

  /**
   * Listener for creating a new portfolio. Prompts the user to enter a client name and creates a
   * new portfolio for that client.
   */
  public class CreatePortfolioListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String clientName = JOptionPane.showInputDialog("Enter Client Name:");
      if (clientName != null && !clientName.isEmpty()) {
        portfolio = new Portfolio(clientName, new ArrayList<>());
        view.setResultArea("Portfolio created for client: " + clientName);
      }
    }
  }

  /**
   * Listener for adding a stock to the portfolio. Prompts the user to enter the stock symbol,
   * quantity, and purchase date. Validates the input and adds the stock to the portfolio.
   * Updates the view with the result.
   */
  class AddStockListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == view.addStockButton) {
        view.showAddStockPanel();
      } else {
        try {
          String symbol = view.getAddStockSymbol();
          int quantity = view.getAddStockQuantity();
          int year = Integer.parseInt(view.getAddStockYear());
          int month = Integer.parseInt(view.getAddStockMonth());
          int day = Integer.parseInt(view.getAddStockDay());
          LocalDate localDate = LocalDate.of(year, month, day);

          System.out.println("Adding stock - Symbol: " + symbol + ", Quantity: " + quantity
                  + ", Date: " + localDate);
          double price = model.fetchStockPrice(symbol, localDate);
          System.out.println("Fetched price for " + symbol + " on " + localDate + ": " + price);
          IStockInfo stock = new StockInfo(symbol, symbol, localDate.toString(), quantity);
          portfolio.addStock(stock);
          view.setResultArea("Stock added: " + symbol + ", Quantity: " + quantity + ", Date: "
                  + localDate);
          view.clearAddStockFields();
          view.showActionPanel();
        } catch (NumberFormatException ex) {
          view.setResultArea("Error: Please enter valid numbers for year, month, and day");
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date. Please ensure the date is correct");
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    }
  }

  /**
   * Listener for selling a stock from the portfolio. Prompts the user to enter the stock symbol,
   * quantity, and sale date. Validates the input and removes the stock from the portfolio.
   * Updates the view with the result.
   */
  class SellStockListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == view.sellStockButton) {
        view.showSellStockPanel();
      } else {
        try {
          String symbol = view.getSellStockSymbol();
          int quantity = view.getSellStockQuantity();
          int year = Integer.parseInt(view.getSellStockYear());
          int month = Integer.parseInt(view.getSellStockMonth());
          int day = Integer.parseInt(view.getSellStockDay());
          LocalDate localDate = LocalDate.of(year, month, day);

          System.out.println("Selling stock - Symbol: " + symbol + ", Quantity: " + quantity
                  + ", Date: " + localDate);
          portfolio.sellStock(symbol, localDate, quantity);
          view.setResultArea("Stock sold: " + symbol + ", Quantity: " + quantity + ", Date: "
                  + localDate);
          view.clearSellStockFields();
          view.showActionPanel();
        } catch (NumberFormatException ex) {
          view.setResultArea("Error: Please enter valid numbers for year, month, and day.");
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date. Please ensure the date is correct.");
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    }
  }

  /**
   * Listener for querying the value of the portfolio on a specific date. Prompts the user to
   * enter the date for which the portfolio value is to be calculated. Validates the input,
   * calculates the portfolio value, and updates the view with the result.
   */
  class QueryValueListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == view.queryValueButton) {
        view.showQueryValuePanel();
      } else {
        try {
          int year = Integer.parseInt(view.getQueryValueYear());
          int month = Integer.parseInt(view.getQueryValueMonth());
          int day = Integer.parseInt(view.getQueryValueDay());
          LocalDate localDate = LocalDate.of(year, month, day);

          System.out.println("Querying portfolio value - Date: " + localDate);
          double value = portfolio.calculatePortfolioValue(localDate, model);
          view.setResultArea("Portfolio value on " + localDate + ": " + value);
          view.clearQueryValueFields();
          view.showActionPanel();
        } catch (NumberFormatException ex) {
          view.setResultArea("Error: Please enter valid numbers for year, month, and day.");
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date. Please ensure the date is correct.");
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    }
  }

  /**
   * Listener for querying the composition of the portfolio on a specific date. Prompts the user
   * to enter the date for which the portfolio composition is to be retrieved. Validates the input,
   * retrieves the portfolio composition, and updates the view with the result.
   */
  class QueryCompositionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == view.queryCompositionButton) {
        view.showQueryCompositionPanel();
      } else {
        try {
          int year = Integer.parseInt(view.getQueryCompositionYear());
          int month = Integer.parseInt(view.getQueryCompositionMonth());
          int day = Integer.parseInt(view.getQueryCompositionDay());
          LocalDate localDate = LocalDate.of(year, month, day);

          System.out.println("Querying portfolio composition - Date: " + localDate);
          Map<String, Integer> composition = portfolio.getComposition(localDate);
          StringBuilder result = new StringBuilder("Portfolio composition on " + localDate
                  + ":\n");
          for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
          }
          view.setResultArea(result.toString());
          view.clearQueryCompositionFields();
          view.showActionPanel();
        } catch (NumberFormatException ex) {
          view.setResultArea("Error: Please enter valid numbers for year, month, and day.");
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date. Please ensure the date is correct.");
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
          ex.printStackTrace();
        }
      }
    }
  }

  /**
   * Listener for saving the portfolio to a file. Prompts the user to choose a file location
   * and saves the portfolio to the selected file.
   */
  class SavePortfolioListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      int option = fileChooser.showSaveDialog(view);
      if (option == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        portfolio.saveToFile(file.getAbsolutePath());
        view.setResultArea("Portfolio saved to: " + file.getAbsolutePath());
      }
    }
  }

  /**
   * Listener for loading the portfolio from a file. Prompts the user to choose a file location
   * and loads the portfolio to the text-based interface or GUI.
   */
  class LoadPortfolioListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JFileChooser fileChooser = new JFileChooser();
      int option = fileChooser.showOpenDialog(view);
      if (option == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        portfolio = Portfolio.loadFromFile(file.getAbsolutePath());
        view.setResultArea("Portfolio loaded from: " + file.getAbsolutePath());
      }
    }
  }
}
