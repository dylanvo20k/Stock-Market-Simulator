package view;

import java.util.Scanner;

/**
 * The MockViewStocks class extends the ViewStocks class and provides mock functionality
 * for user input and message display.
 *
 * <p>It allows for testing user interaction without requiring actual user input/output.
 */
public class MockViewStocks extends ViewStocks {
  private final Scanner scanner;
  private final StringBuilder output;

  public MockViewStocks(String input) {
    this.scanner = new Scanner(input);
    this.output = new StringBuilder();
  }

  @Override
  public String getUserInput(String prompt) {
    output.append(prompt);
    return scanner.nextLine();
  }

  @Override
  public void displayMessage(String message) {
    output.append(message).append("\n");
  }

  public String getOutput() {
    return output.toString();
  }
}
