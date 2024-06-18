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

  class CreatePortfolioListener implements ActionListener {
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
          String date = view.getAddStockDate();
          LocalDate localDate = parseDate(date);

          System.out.println("Adding stock - Symbol: " + symbol + ", Quantity: " + quantity + ", Date: " + date);
          double price = model.fetchStockPrice(symbol, localDate);
          System.out.println("Fetched price for " + symbol + " on " + date + ": " + price);
          IStockInfo stock = new StockInfo(symbol, symbol, date, quantity);
          portfolio.addStock(stock);
          view.setResultArea("Stock added: " + symbol + ", Quantity: " + quantity + ", Date: " + date);
          view.showActionPanel();
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date format. Please use YYYY-MM-DD.");
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
          String date = view.getSellStockDate();
          LocalDate localDate = parseDate(date);

          System.out.println("Selling stock - Symbol: " + symbol + ", Quantity: " + quantity + ", Date: " + date);
          portfolio.sellStock(symbol, localDate, quantity);
          view.setResultArea("Stock sold: " + symbol + ", Quantity: " + quantity + ", Date: " + date);
          view.showActionPanel();
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date format. Please use YYYY-MM-DD.");
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
          String date = view.getQueryValueDate();
          LocalDate localDate = parseDate(date);

          System.out.println("Querying portfolio value - Date: " + date);
          double value = portfolio.calculatePortfolioValue(localDate, model);
          view.setResultArea("Portfolio value on " + date + ": " + value);
          view.showActionPanel();
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date format. Please use YYYY-MM-DD.");
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
          String date = view.getQueryCompositionDate();
          LocalDate localDate = parseDate(date);

          System.out.println("Querying portfolio composition - Date: " + date);
          Map<String, Integer> composition = portfolio.getComposition(localDate);
          StringBuilder result = new StringBuilder("Portfolio composition on " + date + ":\n");
          for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
          }
          view.setResultArea(result.toString());
          view.showActionPanel();
        } catch (DateTimeParseException ex) {
          view.setResultArea("Error: Invalid date format. Please use YYYY-MM-DD.");
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

  private LocalDate parseDate(String dateString) throws DateTimeParseException {
    return LocalDate.parse(dateString);
  }
}
