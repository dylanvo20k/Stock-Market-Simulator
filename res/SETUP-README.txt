Instructions for how to operate our program:

Navigating to the folder the jar file is in:
1. Open up the command line terminal on your Windows Laptop or MacBook
2. Navigate to the folder that the jar file of our program is in (should be in the res folder we submitted)
3. Confirm this by looking at the folder you are in from the terminal

How to run the our text-based interface:
1. Before moving on please follow the 3 steps above
2. To open our text-based interface, please type “java -jar assignment-4-OOD.jar -text” in the terminal —> the text menu should now pop up
3. To create a new portfolio, type 1
4. Then type in your client name (this will be the name of your new portfolio)
5. After pressing enter, you should be brought back to the main menu
6. To add stocks, type 2, in order to manage your portfolio
7. You should now see the ManagePortfolio options
8. To add a new stock to your portfolio, type 1 
9. Type in your client name, a desired company name, (ex. Google), the companies ticker symbol (ex. GOOG), a stock year, (ex. YYYY = 2021, MM = 05, DD = 26), a quantity (ex. 15). 
10. You should now have successfully added your first stock! To add two more stocks follow steps seven and eight (examples of two other stocks can be (AMZN, 2023, 04, 11, 15 and AAPL, 2020, 07, 14, 15. <-- in the order you should type them into the program.
11. Once you have added two more stocks you should be back at the main menu
12. To query the values of the stocks on specific dates, type 5 (Get Portfolio Value distribution)
13. Enter your client name and a date (ex. 2024, 05, 22)
14. You should now see your portfolio value distribution for all your stocks
15. Now we are going to save the portfolio you have just create
16. First type 11 to go back to the main menu
17. Next, choose the “Save Portfolio to File” by typing 3
18. Enter your client name (should be the name you created from step 4)
19. Next, enter a file name for you new portfolio —> the portfolio should now be stored in your files
20. To load your portfolio, restart the program, and type 4 to choose the option “Load Portfolio from File”
21. Enter your file name that you previously saved
22. You should now have loaded in the portfolio


How to manage your portfolio
1. Before managing your portfolio, make sure you have loaded in the portfolio you have created in the above steps
2. To manage your portfolio, type 2 to choose the “Manage Portfolio” option
3. Here there are 10 different functionalities to manage your portfolio
4. For the purpose of testing assignment 5 functionalities, let’s rebalance our portfolio and create a bar performance chart from our portfolio
To rebalance your portfolio type 9
5. To set your weights please follow this format:

TICKER SYMBOL, percentage (ex. GOOG, 25)

** Do this with all the stocks you added to your portfolio

6. Once you type “done” and click enter, you should have successfully rebalanced your portfolio
7. Now to create a bar performance chart, please type 10
8. Enter your client name
9. Enter a start date and an end date, you can use this as an example:

Start date:  2024 —> 01 —> 10                 End date: 2024 —> 06 —> 04

10. After a few seconds, you should see a bar chart that represents the stock data performance of your portfolio from the start to end date
11. To test the other functionalities, follow the commands from the program (entering client name, date, etc.)


How to run our GUI:
1. To open our GUI interface, please type “java -jar assignment-4-OOD.jar” in the terminal. —> the GUI should pop up
2. To create a portfolio, click on the “Create Portfolio” button on the top and enter a client name
3. After entering your client name you should be brought back to the main menu
4. To add a stock, simply click the “Add Stock” button
5. Fill in the following text boxes to add a stock (ex. GOOG, 15, 2021, 05, 26) <— in the order to be typed in
6. After filling in the text boxes, click the “Add Stock” button on the bottom of the GUI.
7. To sell a stock, click on the “Sell Stock” button in the main menu
8. Fill in the text boxes (same process as adding a stock but we are removing stocks this time), and click the SellStock button at the bottom after you’ve filled out the text boxes
9. To query a value click on the “Query Portfolio Value” button in the main menu, fill in the textbooks and click the “Query Value” button at the bottom
10. To query the composition, click on the “Query Portfolio Composition” button in the main menu, fill in textbooks and click the “Query Composition” button at the bottom
11. Now let’s save your new portfolio, click on the “Save Portfolio” button on the main menu, navigate to the folder you want to save your file in (for instance, the res folder) 
12. Name your folder in the “Save as” textbook and then click save on the bottom right of the popup —> You should now have successfully saved your new portfolio to a file
13. To load your file into the program, first restart the program.
14. Once you’ve restarted the program click on the “Load Portfolio” button and then navigate to the folder you saved your portfolio to
15. Click on the file and make sure it’s highlighted, then click open on the bottom right of the popup —> You should now have successfully loaded in your portfolio

To test our GUI, you can play around with the different functionalities like first creating a portfolio, add a stock, check the portfolio composition and value, then sell a stock and then recheck the portfolio composition and value to make sure your portfolio changed when a stock was removed.


**Please let me know if you have any trouble or difficulties running the program. I tried my best to be as detailed as possible so that the TA’s are able to run the program with no problems.


