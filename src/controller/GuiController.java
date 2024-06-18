package controller;

import model.IPortfolio;
import model.IStockInfo;
import model.Portfolio;
import model.StockInfo;
import model.IModel;
import model.PortfolioManager;
import model.AlphaVantageAPI;
import view.GuiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
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
        portfolio = new Portfolio(clientName, List.of());
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
          LocalDate date = LocalDate.parse(view.getAddStockDate());
          IStockInfo stock = new StockInfo(symbol, symbol, date.toString(), quantity);
          portfolio.addStock(stock);
          view.setResultArea("Stock added: " + symbol + ", Quantity: " + quantity + ", Date: " + date);
          view.showActionPanel();
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
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
          LocalDate date = LocalDate.parse(view.getSellStockDate());
          portfolio.sellStock(symbol, date, quantity);
          view.setResultArea("Stock sold: " + symbol + ", Quantity: " + quantity + ", Date: " + date);
          view.showActionPanel();
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
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
          LocalDate date = LocalDate.parse(view.getQueryValueDate());
          double value = portfolio.calculatePortfolioValue(date, model);
          view.setResultArea("Portfolio value on " + date + ": " + value);
          view.showActionPanel();
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
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
          LocalDate date = LocalDate.parse(view.getQueryCompositionDate());
          Map<String, Integer> composition = portfolio.getComposition(date);
          StringBuilder result = new StringBuilder("Portfolio composition on " + date + ":\n");
          for (Map.Entry<String, Integer> entry : composition.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
          }
          view.setResultArea(result.toString());
          view.showActionPanel();
        } catch (Exception ex) {
          view.setResultArea("Error: " + ex.getMessage());
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
}
