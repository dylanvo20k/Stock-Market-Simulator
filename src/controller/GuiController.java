package controller;

import model.*;
import view.GuiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuiController {
  private final GuiView view;
  private IPortfolio portfolio;
  private final IModel model;

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

          System.out.println("Adding stock - Symbol: " + symbol + ", Quantity: " + quantity + ", Date: " + localDate);
          double price = model.fetchStockPrice(symbol, localDate);
          System.out.println("Fetched price for " + symbol + " on " + localDate + ": " + price);
          IStockInfo stock = new StockInfo(symbol, symbol, localDate.toString(), quantity);
          portfolio.addStock(stock);
          view.setResultArea("Stock added: " + symbol + ", Quantity: " + quantity + ", Date: " + localDate);
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

          System.out.println("Selling stock - Symbol: " + symbol + ", Quantity: " + quantity + ", Date: " + localDate);
          portfolio.sellStock(symbol, localDate, quantity);
          view.setResultArea("Stock sold: " + symbol + ", Quantity: " + quantity + ", Date: " + localDate);
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
          StringBuilder result = new StringBuilder("Portfolio composition on " + localDate + ":\n");
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

  private LocalDate parseDate(int year, int month, int day) throws DateTimeParseException {
    return LocalDate.of(year, month, day);
  }
}
