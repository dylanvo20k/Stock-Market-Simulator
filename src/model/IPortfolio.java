package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IPortfolio {
  String getClientName();
  void addStock(IStockInfo stock);
  void sellStock(String tickerSymbol, LocalDate date, int quantity);
  Map<String, Integer> getComposition(LocalDate date);
  double calculatePortfolioValue(LocalDate date, IModel model);
  Map<String, Double> getValueDistribution(LocalDate date, IModel model);
  void saveToFile(String fileName);
  static IPortfolio loadFromFile(String fileName) {
    return null;
  }
  void generatePerformanceChart(LocalDate startDate, LocalDate endDate, IModel model);
  List<IStockInfo> getStockList();
}
