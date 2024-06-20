package view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * This class represents the graphical user interface for our portfolio management application. It
 * provides a main menu window with different panels of functionalities for user actions like
 * creating a portfolio, buying/selling stocks, querying portfolio value, querying portfolio
 * composition, and saving/loading portfolio files.
 */
public class GuiView extends JFrame {
  private JPanel mainPanel;
  private CardLayout cardLayout;

  // Input fields for add stock
  private JTextField addStockSymbolField;
  private JTextField addStockQuantityField;
  private JTextField addStockYearField;
  private JTextField addStockMonthField;
  private JTextField addStockDayField;


  // Input fields for sell stock
  private JTextField sellStockSymbolField;
  private JTextField sellStockQuantityField;
  private JTextField sellStockYearField;

  private JTextField sellStockMonthField;

  private JTextField sellStockDayField;


  // Input fields for query value
  private JTextField queryValueYearField;
  private JTextField queryValueMonthField;
  private JTextField queryValueDayField;

  // Input fields for query composition
  private JTextField queryCompositionYearField;
  private JTextField queryCompositionMonthField;
  private JTextField queryCompositionDayField;


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

  /**
   * Constructs a GuiView object and initializes the main frame with all its components for the GUI
   * view.
   */
  public GuiView() {
    setTitle("Portfolio Management");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    add(mainPanel, BorderLayout.CENTER);

    // Action panel with main buttons
    // Panels for different actions
    JPanel actionPanel = new JPanel(new GridLayout(7, 2));
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
    JPanel addStockPanel = new JPanel(new GridLayout(6, 2));
    mainPanel.add(addStockPanel, "addStockPanel");

    addStockPanel.add(new JLabel("Enter the ticker symbol:"));
    addStockSymbolField = new JTextField();
    addStockPanel.add(addStockSymbolField);

    addStockPanel.add(new JLabel("Enter the quantity:"));
    addStockQuantityField = new JTextField();
    addStockPanel.add(addStockQuantityField);

    addStockPanel.add(new JLabel("Enter the year (YYYY):"));
    addStockYearField = new JTextField();
    addStockPanel.add(addStockYearField);

    addStockPanel.add(new JLabel("Enter the month (MM):"));
    addStockMonthField = new JTextField();
    addStockPanel.add(addStockMonthField);

    addStockPanel.add(new JLabel("Enter the day (DD):"));
    addStockDayField = new JTextField();
    addStockPanel.add(addStockDayField);

    confirmAddStockButton = new JButton("Add Stock");
    addStockPanel.add(confirmAddStockButton);
    addStockPanel.add(new JLabel(""));

    // Sell stock panel
    JPanel sellStockPanel = new JPanel(new GridLayout(6, 2));
    mainPanel.add(sellStockPanel, "sellStockPanel");

    sellStockPanel.add(new JLabel("Enter the ticker symbol:"));
    sellStockSymbolField = new JTextField();
    sellStockPanel.add(sellStockSymbolField);

    sellStockPanel.add(new JLabel("Enter the quantity:"));
    sellStockQuantityField = new JTextField();
    sellStockPanel.add(sellStockQuantityField);

    sellStockPanel.add(new JLabel("Enter the year (YYYY):"));
    sellStockYearField = new JTextField();
    sellStockPanel.add(sellStockYearField);

    sellStockPanel.add(new JLabel("Enter the month (MM):"));
    sellStockMonthField = new JTextField();
    sellStockPanel.add(sellStockMonthField);

    sellStockPanel.add(new JLabel("Enter the day (DD):"));
    sellStockDayField = new JTextField();
    sellStockPanel.add(sellStockDayField);

    confirmSellStockButton = new JButton("Sell Stock");
    sellStockPanel.add(confirmSellStockButton);
    sellStockPanel.add(new JLabel(""));


    // Query value panel
    JPanel queryValuePanel = new JPanel(new GridLayout(4, 2));
    mainPanel.add(queryValuePanel, "queryValuePanel");

    queryValuePanel.add(new JLabel("Enter the year (YYYY):"));
    queryValueYearField = new JTextField();
    queryValuePanel.add(queryValueYearField);

    queryValuePanel.add(new JLabel("Enter the month (MM):"));
    queryValueMonthField = new JTextField();
    queryValuePanel.add(queryValueMonthField);

    queryValuePanel.add(new JLabel("Enter the day (DD):"));
    queryValueDayField = new JTextField();
    queryValuePanel.add(queryValueDayField);

    confirmQueryValueButton = new JButton("Query Value");
    queryValuePanel.add(confirmQueryValueButton);
    queryValuePanel.add(new JLabel(""));


    // Query composition panel
    JPanel queryCompositionPanel = new JPanel(new GridLayout(4, 2));
    mainPanel.add(queryCompositionPanel, "queryCompositionPanel");

    queryCompositionPanel.add(new JLabel("Enter the year (YYYY):"));
    queryCompositionYearField = new JTextField();
    queryCompositionPanel.add(queryCompositionYearField);

    queryCompositionPanel.add(new JLabel("Enter the month (MM):"));
    queryCompositionMonthField = new JTextField();
    queryCompositionPanel.add(queryCompositionMonthField);

    queryCompositionPanel.add(new JLabel("Enter the day (DD):"));
    queryCompositionDayField = new JTextField();
    queryCompositionPanel.add(queryCompositionDayField);

    confirmQueryCompositionButton = new JButton("Query Composition");
    queryCompositionPanel.add(confirmQueryCompositionButton);
    queryCompositionPanel.add(new JLabel(""));
  }

  /**
   * Shows the action panel in the GUI using CardLayout.
   * This panel contains main buttons for various actions.
   */
  public void showActionPanel() {
    cardLayout.show(mainPanel, "actionPanel");
  }

  /**
   * Shows the add stock panel in the GUI using CardLayout.
   * This panel allows users to input details for adding a stock.
   */
  public void showAddStockPanel() {
    cardLayout.show(mainPanel, "addStockPanel");
  }

  /**
   * Shows the sell stock panel in the GUI using CardLayout.
   * This panel allows users to input details for selling a stock.
   */
  public void showSellStockPanel() {
    cardLayout.show(mainPanel, "sellStockPanel");
  }

  /**
   * Shows the query value panel in the GUI using CardLayout.
   * This panel allows users to input a date to query the portfolio value.
   */
  public void showQueryValuePanel() {
    cardLayout.show(mainPanel, "queryValuePanel");
  }

  /**
   * Shows the query composition panel in the GUI using CardLayout.
   * This panel allows users to input a date to query the portfolio composition.
   */
  public void showQueryCompositionPanel() {
    cardLayout.show(mainPanel, "queryCompositionPanel");
  }

  /**
   * Retrieves the text entered in the add stock symbol field.
   *
   * @return The text entered in the add stock symbol field.
   */
  public String getAddStockSymbol() {
    return addStockSymbolField.getText();
  }

  /**
   * Retrieves the integer value entered in the add stock quantity field.
   *
   * @return The integer value entered in the add stock quantity field.
   * @throws NumberFormatException if the text in the field is not a valid integer.
   */
  public int getAddStockQuantity() {
    return Integer.parseInt(addStockQuantityField.getText());
  }

  /**
   * Retrieves the text entered in the add stock year field.
   *
   * @return The text entered in the add stock year field.
   */
  public String getAddStockYear() {
    return addStockYearField.getText();
  }

  /**
   * Retrieves the text entered in the add stock month field.
   *
   * @return The text entered in the add stock month field.
   */
  public String getAddStockMonth() {
    return addStockMonthField.getText();
  }

  /**
   * Retrieves the text entered in the add stock day field.
   *
   * @return The text entered in the add stock day field.
   */
  public String getAddStockDay() {
    return addStockDayField.getText();
  }

  /**
   * Retrieves the text entered in the sell stock symbol field.
   *
   * @return The text entered in the sell stock symbol field.
   */
  public String getSellStockSymbol() {
    return sellStockSymbolField.getText();
  }

  /**
   * Retrieves the integer value entered in the sell stock quantity field.
   *
   * @return The integer value entered in the sell stock quantity field.
   * @throws NumberFormatException if the text in the field is not a valid integer.
   */
  public int getSellStockQuantity() {
    return Integer.parseInt(sellStockQuantityField.getText());
  }

  /**
   * Retrieves the text entered in the sell stock year field.
   *
   * @return The text entered in the sell stock year field.
   */
  public String getSellStockYear() {
    return sellStockYearField.getText();
  }

  /**
   * Retrieves the text entered in the sell stock month field.
   *
   * @return The text entered in the sell stock month field.
   */
  public String getSellStockMonth() {
    return sellStockMonthField.getText();
  }

  /**
   * Retrieves the text entered in the sell stock day field.
   *
   * @return The text entered in the sell stock day field.
   */
  public String getSellStockDay() {
    return sellStockDayField.getText();
  }

  /**
   * Retrieves the text entered in the query value year field.
   *
   * @return The text entered in the query value year field.
   */
  public String getQueryValueYear() {
    return queryValueYearField.getText();
  }

  /**
   * Retrieves the text entered in the query value month field.
   *
   * @return The text entered in the query value month field.
   */
  public String getQueryValueMonth() {
    return queryValueMonthField.getText();
  }

  /**
   * Retrieves the text entered in the query value day field.
   *
   * @return The text entered in the query value day field.
   */
  public String getQueryValueDay() {
    return queryValueDayField.getText();
  }

  /**
   * Retrieves the text entered in the query composition year field.
   *
   * @return The text entered in the query composition year field.
   */
  public String getQueryCompositionYear() {
    return queryCompositionYearField.getText();
  }

  /**
   * Retrieves the text entered in the query composition month field.
   *
   * @return The text entered in the query composition month field.
   */
  public String getQueryCompositionMonth() {
    return queryCompositionMonthField.getText();
  }

  /**
   * Retrieves the text entered in the query composition day field.
   *
   * @return The text entered in the query composition day field.
   */
  public String getQueryCompositionDay() {
    return queryCompositionDayField.getText();
  }

  /**
   * Sets the text to be displayed in the result area.
   *
   * @param text The text to be displayed.
   */
  public void setResultArea(String text) {
    resultArea.setText(text);
  }

  /**
   * Adds an ActionListener to the create portfolio button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addCreatePortfolioListener(ActionListener listener) {
    createPortfolioButton.addActionListener(listener);
  }

  /**
   * Adds ActionListeners to the add stock button and confirm add stock button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addAddStockListener(ActionListener listener) {
    addStockButton.addActionListener(listener);
    confirmAddStockButton.addActionListener(listener);
  }

  /**
   * Adds ActionListeners to the sell stock button and confirm sell stock button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addSellStockListener(ActionListener listener) {
    sellStockButton.addActionListener(listener);
    confirmSellStockButton.addActionListener(listener);
  }

  /**
   * Adds ActionListeners to the query value button and confirm query value button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addQueryValueListener(ActionListener listener) {
    queryValueButton.addActionListener(listener);
    confirmQueryValueButton.addActionListener(listener);
  }

  /**
   * Adds ActionListeners to the query composition button and confirm query composition button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addQueryCompositionListener(ActionListener listener) {
    queryCompositionButton.addActionListener(listener);
    confirmQueryCompositionButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the save portfolio button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addSavePortfolioListener(ActionListener listener) {
    savePortfolioButton.addActionListener(listener);
  }

  /**
   * Adds an ActionListener to the load portfolio button.
   *
   * @param listener The ActionListener to be added.
   */
  public void addLoadPortfolioListener(ActionListener listener) {
    loadPortfolioButton.addActionListener(listener);
  }

  /**
   * Clears the add stock input fields by setting their text to an empty string.
   */
  public void clearAddStockFields() {
    addStockSymbolField.setText("");
    addStockQuantityField.setText("");
    addStockYearField.setText("");
    addStockMonthField.setText("");
    addStockDayField.setText("");
  }

  /**
   * Clears the sell stock input fields by setting their text to an empty string.
   */
  public void clearSellStockFields() {
    sellStockSymbolField.setText("");
    sellStockQuantityField.setText("");
    sellStockYearField.setText("");
    sellStockMonthField.setText("");
    sellStockDayField.setText("");
  }

  /**
   * Clears the query value input fields by setting their text to an empty string.
   */
  public void clearQueryValueFields() {
    queryValueYearField.setText("");
    queryValueMonthField.setText("");
    queryValueDayField.setText("");
  }

  /**
   * Clears the query composition input fields by setting their text to an empty string.
   */
  public void clearQueryCompositionFields() {
    queryCompositionYearField.setText("");
    queryCompositionMonthField.setText("");
    queryCompositionDayField.setText("");
  }
}
