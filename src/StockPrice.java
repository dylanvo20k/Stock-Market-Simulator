public class StockPrice {
  private double openingPrice;
  private double closingPrice;
  private double lowPrice;
  private double highPrice;

  public StockPrice(double openingPrice, double closingPrice, double lowPrice, double highPrice) {
    this.openingPrice = openingPrice;
    this.closingPrice = closingPrice;
    this.lowPrice = lowPrice;
    this.highPrice = highPrice;
  }

  public double getOpeningPrice() {
    return openingPrice;
  }

  public double getClosingPrice() {
    return closingPrice;
  }

  public double getLowPrice() {
    return lowPrice;
  }

  public double getHighPrice() {
    return highPrice;
  }

}


