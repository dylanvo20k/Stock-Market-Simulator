1. Since this assignment was based on creating a GUI of our program, we didn’t make any substantial changes to our existing code. However, we did make some changes based on our manual feedback for assignment 5. 

2. To start off, we made a lot of changes to our StockController class to improve our design and functionality. For our portfolioManager instance, we initially used our concrete PortfolioManager class which we realized wasn’t the best decision for design purposes. To fix this, we changed it to be an instance of IModel. 

3. Similarly, for our portfolios instance, we changed from a List of Portfolios to a List of IPortfolio. We did this because we realized that using the interface was a better option for our design rather than relying on the main Portfolio class. 

4. Next, we changed how we displayed our menu in the start method of our StockController. For assignment 5, we hardcoded the menu that was displayed to the user when running our program. In other words, we were essentially not using our displayMenu method from our view component. To fix this, we called the displayMenu method from our ViewStocks class in our StocksController class. This ensured that we were using the view in our controller and made us not rely on the hard code we had for assignment 5. 

5. Next, in our rebalancePortfolio method in the StockController class we added a check that made sure that weights inputted by users are valid before the portfolio is recalculated. We did this because we realized that for assignment 5, users could enter invalid weights that weren’t in the range of 1-99 percent. Therefore, by adding this check, we ensure that weights entered by users are valid. 

6. Next, in our Portfolio class, we added a check for our addStock method. The check was added to verify that stocks entered by a user were valid before they were added to the Portfolio. Similar to #5, we did this in order to prevent users from inputting invalid stocks. 

7. Lastly, in our Portfolio class, we added a check for our getComposition method. The check was added to verify that a date entered by a user was valid before the the composition of our portfolio was retrieved. We did this in order to prevent users from inputting invalid dates when trying to retrieve the composition of their portfolio. 
