package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiView extends JFrame {
  private JPanel mainPanel;
  private CardLayout cardLayout;

  // Panels for different actions
  private JPanel actionPanel;
  private JPanel addStockPanel;
  private JPanel sellStockPanel;
  private JPanel queryValuePanel;
  private JPanel queryCompositionPanel;

  // Input fields for add stock
  private JTextField addStockSymbolField;
  private JTextField addStockQuantityField;
  private JTextField addStockDateField;

  // Input fields for sell stock
  private JTextField sellStockSymbolField;
  private JTextField sellStockQuantityField;
  private JTextField sellStockDateField;

  // Input fields for query value
  private JTextField queryValueDateField;

  // Input fields for query composition
  private JTextField queryCompositionDateField;

  private JTextArea resultArea;
  private JButton createPortfolioButton;
  public JButton addStockButton;
  public JButton sellStockButton;
  public JButton queryValueButton;
  public JButton queryCompositionButton;
  private JButton savePortfolioButton;
  private JButton loadPortfolioButton;

  private JButton confirmAddStockButton;
  private JButton confirmSellStockButton;
  private JButton confirmQueryValueButton;
  private JButton confirmQueryCompositionButton;

  public GuiView() {
    setTitle("Portfolio Management");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    add(mainPanel, BorderLayout.CENTER);

    // Action panel with main buttons
    actionPanel = new JPanel(new GridLayout(7, 2));
    mainPanel.add(actionPanel, "actionPanel");

    createPortfolioButton = new JButton("Create Portfolio");
    actionPanel.add(createPortfolioButton);
    actionPanel.add(new JLabel(""));

    addStockButton = new JButton("Add Stock");
    actionPanel.add(addStockButton);

    sellStockButton = new JButton("Sell Stock");
    actionPanel.add(sellStockButton);

    queryValueButton = new JButton("Query Portfolio Value");
    actionPanel.add(queryValueButton);

    queryCompositionButton = new JButton("Query Portfolio Composition");
    actionPanel.add(queryCompositionButton);

    savePortfolioButton = new JButton("Save Portfolio");
    actionPanel.add(savePortfolioButton);

    loadPortfolioButton = new JButton("Load Portfolio");
    actionPanel.add(loadPortfolioButton);

    resultArea = new JTextArea();
    add(new JScrollPane(resultArea), BorderLayout.SOUTH);

    // Add stock panel
    addStockPanel = new JPanel(new GridLayout(5, 2));
    mainPanel.add(addStockPanel, "addStockPanel");

    addStockPanel.add(new JLabel("Stock Symbol:"));
    addStockSymbolField = new JTextField();
    addStockPanel.add(addStockSymbolField);

    addStockPanel.add(new JLabel("Quantity:"));
    addStockQuantityField = new JTextField();
    addStockPanel.add(addStockQuantityField);

    addStockPanel.add(new JLabel("Date (YYYY-MM-DD):"));
    addStockDateField = new JTextField();
    addStockPanel.add(addStockDateField);

    confirmAddStockButton = new JButton("Add Stock");
    addStockPanel.add(confirmAddStockButton);
    addStockPanel.add(new JLabel(""));

    // Sell stock panel
    sellStockPanel = new JPanel(new GridLayout(5, 2));
    mainPanel.add(sellStockPanel, "sellStockPanel");

    sellStockPanel.add(new JLabel("Stock Symbol:"));
    sellStockSymbolField = new JTextField();
    sellStockPanel.add(sellStockSymbolField);

    sellStockPanel.add(new JLabel("Quantity:"));
    sellStockQuantityField = new JTextField();
    sellStockPanel.add(sellStockQuantityField);

    sellStockPanel.add(new JLabel("Date (YYYY-MM-DD):"));
    sellStockDateField = new JTextField();
    sellStockPanel.add(sellStockDateField);

    confirmSellStockButton = new JButton("Sell Stock");
    sellStockPanel.add(confirmSellStockButton);
    sellStockPanel.add(new JLabel(""));

    // Query value panel
    queryValuePanel = new JPanel(new GridLayout(3, 2));
    mainPanel.add(queryValuePanel, "queryValuePanel");

    queryValuePanel.add(new JLabel("Query Date (YYYY-MM-DD):"));
    queryValueDateField = new JTextField();
    queryValuePanel.add(queryValueDateField);

    confirmQueryValueButton = new JButton("Query Value");
    queryValuePanel.add(confirmQueryValueButton);
    queryValuePanel.add(new JLabel(""));

    // Query composition panel
    queryCompositionPanel = new JPanel(new GridLayout(3, 2));
    mainPanel.add(queryCompositionPanel, "queryCompositionPanel");

    queryCompositionPanel.add(new JLabel("Query Date (YYYY-MM-DD):"));
    queryCompositionDateField = new JTextField();
    queryCompositionPanel.add(queryCompositionDateField);

    confirmQueryCompositionButton = new JButton("Query Composition");
    queryCompositionPanel.add(confirmQueryCompositionButton);
    queryCompositionPanel.add(new JLabel(""));
  }

  public void showActionPanel() {
    cardLayout.show(mainPanel, "actionPanel");
  }

  public void showAddStockPanel() {
    cardLayout.show(mainPanel, "addStockPanel");
  }

  public void showSellStockPanel() {
    cardLayout.show(mainPanel, "sellStockPanel");
  }

  public void showQueryValuePanel() {
    cardLayout.show(mainPanel, "queryValuePanel");
  }

  public void showQueryCompositionPanel() {
    cardLayout.show(mainPanel, "queryCompositionPanel");
  }

  public String getAddStockSymbol() {
    return addStockSymbolField.getText();
  }

  public int getAddStockQuantity() {
    return Integer.parseInt(addStockQuantityField.getText());
  }

  public String getAddStockDate() {
    return addStockDateField.getText();
  }

  public String getSellStockSymbol() {
    return sellStockSymbolField.getText();
  }

  public int getSellStockQuantity() {
    return Integer.parseInt(sellStockQuantityField.getText());
  }

  public String getSellStockDate() {
    return sellStockDateField.getText();
  }

  public String getQueryValueDate() {
    return queryValueDateField.getText();
  }

  public String getQueryCompositionDate() {
    return queryCompositionDateField.getText();
  }

  public void setResultArea(String text) {
    resultArea.setText(text);
  }

  public void addCreatePortfolioListener(ActionListener listener) {
    createPortfolioButton.addActionListener(listener);
  }

  public void addAddStockListener(ActionListener listener) {
    addStockButton.addActionListener(listener);
    confirmAddStockButton.addActionListener(listener);
  }

  public void addSellStockListener(ActionListener listener) {
    sellStockButton.addActionListener(listener);
    confirmSellStockButton.addActionListener(listener);
  }

  public void addQueryValueListener(ActionListener listener) {
    queryValueButton.addActionListener(listener);
    confirmQueryValueButton.addActionListener(listener);
  }

  public void addQueryCompositionListener(ActionListener listener) {
    queryCompositionButton.addActionListener(listener);
    confirmQueryCompositionButton.addActionListener(listener);
  }

  public void addSavePortfolioListener(ActionListener listener) {
    savePortfolioButton.addActionListener(listener);
  }

  public void addLoadPortfolioListener(ActionListener listener) {
    loadPortfolioButton.addActionListener(listener);
  }
}
