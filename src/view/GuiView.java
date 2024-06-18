package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.IPortfolio;

public class GuiView {
  private JFrame frame;
  private JPanel panel;
  private JButton createButton;
  private JButton buyButton;
  private JButton sellButton;
  private JButton queryButton;
  private JButton saveButton;
  private JButton loadButton;
  private JTextField stockNameField;
  private JTextField sharesField;
  private JTextField dateField;
  private JTextField portfolioNameField;
  private JTextArea outputArea;
  private IModel model;

  public GuiView(IModel model) {
    this.model = model;
    createGUI();
  }

  private void createGUI() {
    frame = new JFrame("Portfolio Manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);

    panel = new JPanel();
    panel.setLayout(new GridLayout(8, 2));

    createButton = new JButton("Create Portfolio");
    createButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String portfolioName = portfolioNameField.getText();
        model.createPortfolio(portfolioName);
        outputArea.setText("Portfolio created: " + portfolioName);
      }
    });
    panel.add(createButton);

    buyButton = new JButton("Buy Stocks");
    buyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String stockName = stockNameField.getText();
        int shares = Integer.parseInt(sharesField.getText());
        String date = dateField.getText();
        model.buyStock(stockName, shares, date);
        outputArea.setText("Stocks bought: " + stockName + ", " + shares + " shares, on " + date);
      }
    });
    panel.add(buyButton);

    sellButton = new JButton("Sell Stocks");
    sellButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String stockName = stockNameField.getText();
        int shares = Integer.parseInt(sharesField.getText());
        String date = dateField.getText();
        model.sellStock(stockName, shares, date);
        outputArea.setText("Stocks sold: " + stockName + ", " + shares + " shares, on " + date);
      }
    });
    panel.add(sellButton);

    queryButton = new JButton("Query Portfolio");
    queryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String date = dateField.getText();
        String portfolioName = portfolioNameField.getText();
        double value = model.queryPortfolioValue(portfolioName, date);
        String composition = model.queryPortfolioComposition(portfolioName, date);
        outputArea.setText("Portfolio value on " + date + ": " + value + "\nPortfolio composition on " + date + ": " + composition);
      }
    });
    panel.add(queryButton);

    saveButton = new JButton("Save Portfolio");
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String portfolioName = portfolioNameField.getText();
        String fileName = JOptionPane.showInputDialog("Enter the file name to save the portfolio:");
        model.savePortfolio(portfolioName, fileName);
        outputArea.setText("Portfolio saved: " + portfolioName + ", file name: " + fileName);
      }
    });
    panel.add(saveButton);

    loadButton = new JButton("Load Portfolio");
    loadButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog("Enter the file name to load the portfolio:");
        String portfolioName = model.loadPortfolio(fileName);
        outputArea.setText("Portfolio loaded: " + portfolioName + ", file name: " + fileName);
      }
    });
    panel.add(loadButton);

    stockNameField = new JTextField(10);
    panel.add(new JLabel("Stock Name:"));
    panel.add(stockNameField);

    sharesField = new JTextField(10);
    panel.add(new JLabel("Shares:"));
    panel.add(sharesField);

    dateField = new JTextField(10);
    panel.add(new JLabel("Date (YYYY-MM-DD):"));
    panel.add(dateField);

    portfolioNameField = new JTextField(10);
    panel.add(new JLabel("Portfolio Name:"));
    panel.add(portfolioNameField);

    outputArea = new JTextArea(10, 20);
    outputArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(outputArea);
    panel.add(scrollPane);

    frame.add(panel);
    frame.setVisible(true);
  }

  public void displayOutput(String output) {
    outputArea.setText(output);
  }
}