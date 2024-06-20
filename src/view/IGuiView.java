package view;

import java.awt.event.ActionListener;

/**
 * This interface defines the contract for the graphical user interface of the portfolio
 * management application. It includes methods for displaying different panels, retrieving user
 * input, and setting result text.
 */
public interface IGuiView {
  /**
   * Shows the action panel in the GUI using CardLayout.
   * This panel contains main buttons for various actions.
   */
  void showActionPanel();

  /**
   * Shows the add stock panel in the GUI using CardLayout.
   * This panel allows users to input details for adding a stock.
   */
  void showAddStockPanel();

  /**
   * Shows the sell stock panel in the GUI using CardLayout.
   * This panel allows users to input details for selling a stock.
   */
  void showSellStockPanel();

  /**
   * Shows the query value panel in the GUI using CardLayout.
   * This panel allows users to input a date to query the portfolio value.
   */
  void showQueryValuePanel();

  /**
   * Shows the query composition panel in the GUI using CardLayout.
   * This panel allows users to input a date to query the portfolio composition.
   */
  void showQueryCompositionPanel();

  /**
   * Retrieves the text entered in the add stock symbol field.
   *
   * @return The text entered in the add stock symbol field.
   */
  String getAddStockSymbol();

  /**
   * Retrieves the integer value entered in the add stock quantity field.
   *
   * @return The integer value entered in the add stock quantity field.
   * @throws NumberFormatException if the text in the field is not a valid integer.
   */
  int getAddStockQuantity();

  /**
   * Retrieves the text entered in the add stock year field.
   *
   * @return The text entered in the add stock year field.
   */
  String getAddStockYear();

  /**
   * Retrieves the text entered in the add stock month field.
   *
   * @return The text entered in the add stock month field.
   */
  String getAddStockMonth();

  /**
   * Retrieves the text entered in the add stock day field.
   *
   * @return The text entered in the add stock day field.
   */
  String getAddStockDay();

  /**
   * Retrieves the text entered in the sell stock symbol field.
   *
   * @return The text entered in the sell stock symbol field.
   */
  String getSellStockSymbol();

  /**
   * Retrieves the integer value entered in the sell stock quantity field.
   *
   * @return The integer value entered in the sell stock quantity field.
   * @throws NumberFormatException if the text in the field is not a valid integer.
   */
  int getSellStockQuantity();

  /**
   * Retrieves the text entered in the sell stock year field.
   *
   * @return The text entered in the sell stock year field.
   */
  String getSellStockYear();

  /**
   * Retrieves the text entered in the sell stock month field.
   *
   * @return The text entered in the sell stock month field.
   */
  String getSellStockMonth();

  /**
   * Retrieves the text entered in the sell stock day field.
   *
   * @return The text entered in the sell stock day field.
   */
  String getSellStockDay();

  /**
   * Retrieves the text entered in the query value year field.
   *
   * @return The text entered in the query value year field.
   */
  String getQueryValueYear();

  /**
   * Retrieves the text entered in the query value month field.
   *
   * @return The text entered in the query value month field.
   */
  String getQueryValueMonth();

  /**
   * Retrieves the text entered in the query value day field.
   *
   * @return The text entered in the query value day field.
   */
  String getQueryValueDay();

  /**
   * Retrieves the text entered in the query composition year field.
   *
   * @return The text entered in the query composition year field.
   */
  String getQueryCompositionYear();

  /**
   * Retrieves the text entered in the query composition month field.
   *
   * @return The text entered in the query composition month field.
   */
  String getQueryCompositionMonth();

  /**
   * Retrieves the text entered in the query composition day field.
   *
   * @return The text entered in the query composition day field.
   */
  String getQueryCompositionDay();

  /**
   * Sets the text to be displayed in the result area.
   *
   * @param text The text to be displayed.
   */
  void setResultArea(String text);

  /**
   * Adds an ActionListener to the create portfolio button.
   *
   * @param listener The ActionListener to be added.
   */
  void addCreatePortfolioListener(ActionListener listener);

  /**
   * Adds ActionListeners to the add stock button and confirm add stock button.
   *
   * @param listener The ActionListener to be added.
   */
  void addAddStockListener(ActionListener listener);

  /**
   * Adds ActionListeners to the sell stock button and confirm sell stock button.
   *
   * @param listener The ActionListener to be added.
   */
  void addSellStockListener(ActionListener listener);

  /**
   * Adds ActionListeners to the query value button and confirm query value button.
   *
   * @param listener The ActionListener to be added.
   */
  void addQueryValueListener(ActionListener listener);

  /**
   * Adds ActionListeners to the query composition button and confirm query composition button.
   *
   * @param listener The ActionListener to be added.
   */
  void addQueryCompositionListener(ActionListener listener);

  /**
   * Adds an ActionListener to the save portfolio button.
   *
   * @param listener The ActionListener to be added.
   */
  void addSavePortfolioListener(ActionListener listener);

  /**
   * Adds an ActionListener to the load portfolio button.
   *
   * @param listener The ActionListener to be added.
   */
  void addLoadPortfolioListener(ActionListener listener);

  /**
   * Clears the add stock input fields by setting their text to an empty string.
   */
  void clearAddStockFields();

  /**
   * Clears the sell stock input fields by setting their text to an empty string.
   */
  void clearSellStockFields();

  /**
   * Clears the query value input fields by setting their text to an empty string.
   */
  void clearQueryValueFields();

  /**
   * Clears the query composition input fields by setting their text to an empty string.
   */
  void clearQueryCompositionFields();
}
