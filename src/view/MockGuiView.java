package view;

import java.awt.event.ActionListener;

/**
 * This is a mock GuiView class for unit testing purposes.
 * This class simulates user input and captures output for testing the behavior
 * of a GUI controller without requiring a graphical interface.
 */
public class MockGuiView extends GuiView {
  private StringBuilder output;
  private String input;

  public MockGuiView() {
    output = new StringBuilder();
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output.toString();
  }

  @Override
  public void setResultArea(String message) {
    output.append(message);
  }

  @Override
  public void showAddStockPanel() {
    // Simulate showing add stock panel
  }

  @Override
  public String getAddStockSymbol() {
    return input;
  }

  @Override
  public int getAddStockQuantity() {
    return Integer.parseInt(input);
  }

  @Override
  public String getAddStockYear() {
    return input;
  }

  @Override
  public String getAddStockMonth() {
    return input;
  }

  @Override
  public String getAddStockDay() {
    return input;
  }

  @Override
  public void addCreatePortfolioListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }

  @Override
  public void addAddStockListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }

  @Override
  public void addSellStockListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }

  @Override
  public void addQueryValueListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }

  @Override
  public void addQueryCompositionListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }

  @Override
  public void addSavePortfolioListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }

  @Override
  public void addLoadPortfolioListener(ActionListener listener) {
    // Simulate adding listener (no operation needed since this is for testing GUI controller)
  }
}
