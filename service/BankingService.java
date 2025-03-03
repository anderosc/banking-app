package service;

import model.User;
import model.InvestmentAccount;
import model.SavingsAccount;
import model.Fund;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class BankingService {

    private ArrayList<String> users = new ArrayList<String>(Arrays.asList("Alice", "Bob", "Charlie", "Diana"));

    private Scanner _sc = new Scanner(System.in);

    private User user;
    private SavingsAccount savingsAccount;
    private InvestmentAccount investmentAccount;
    private String LastUsername = "";


    public boolean Login(String input) {
        boolean isLoggedin = false;
        if(input.equals("Alice") || input.equals("Bob") || input.equals("Charlie") || input.equals("Diana")) {
            isLoggedin = true;
            /*if(LastUsername.isEmpty()) {
                LastUsername = input;
                user = new User(input, 1000.00);
                savingsAccount = new SavingsAccount();
                investmentAccount = new InvestmentAccount();
            }else{
                if(!LastUsername.equals(input)) {
                    user = new User(input, 1000.00);
                    savingsAccount = new SavingsAccount();
                    investmentAccount = new InvestmentAccount();
                }
            }*/
            
        }else{
            isLoggedin = false;
            System.out.println("User not found. Please try again.");
        }
        return isLoggedin;
    }

    public void DisplayBankingMenu() {
        System.out.println(" --- Banking App Menu ---");
        System.out.println("1. Show balance");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Send money to a person");
        System.out.println("5. Invest in funds");
        System.out.println("6. Transfer between accounts");
        System.out.println("7. Withdraw all investments");
        System.out.println("8. Logout");
        System.out.println("9. Exit");
        System.out.print("Enter your choice:");
    }

    public void ShowBalance() {
        savingsAccount.addInterest();
        System.out.println("Savings account balance: $" + savingsAccount.getBalance().setScale(2, RoundingMode.HALF_UP));
        System.out.println("Investment account balance:");
        System.out.println("* Not Invested: $" + investmentAccount.getBalance().setScale(2, RoundingMode.HALF_UP));
        investmentAccount.addInterest();
        System.out.println("* LOW_RISK: $" + investmentAccount.getLOWBalance().setScale(2, RoundingMode.HALF_UP));
        System.out.println("* MEDIUM_RISK: $" + investmentAccount.getMEDBalance().setScale(2, RoundingMode.HALF_UP));
        System.out.println("* HIGH_RISK: $" + investmentAccount.getHIGHBalance().setScale(2, RoundingMode.HALF_UP));
    }

    public void DepositMoney() {
        try{
            System.out.print("Enter amount to deposit to savings account: $");
            String input = _sc.nextLine().trim();
            if(Double.parseDouble(input) <= 0) {
                System.out.println("Deposit failed: amount must be positive.");
            }else{
                double userBalance = user.getUserCashBalance();
                if(userBalance >= 0 && Double.parseDouble(input) <= userBalance) {
                    user.setuserCashBalance(user.getUserCashBalance() - Double.parseDouble(input));
                    savingsAccount.deposit(new BigDecimal(input));
                    System.out.println("Deposit successful.");
                }else {
                    System.out.println("Insufficient cash on hand.");
                }
            }
        }catch(Exception e) {
            //System.out.println(e);
            //System.out.println("Error Occured While Processing Your Request...");
        }
    }

    boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
    return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    public void WithdrawMoneyFromSavingsAccount() {
        try{
            System.out.print("Enter amount to withdraw from savings account: $");
            String input = _sc.nextLine().trim();
            if(Double.parseDouble(input) <= 0) {
                System.out.println("Withdrawal failed: amount must be positive.");
            }else{
                if (isBetween(new BigDecimal(input), new BigDecimal(0), savingsAccount.getBalance())) {
                    savingsAccount.withdraw(new BigDecimal(input));
                    user.setuserCashBalance(Double.parseDouble(input));
                }else {
                    System.out.println("Withdrawal failed: Insufficient funds.");
                }
            }
        }catch(Exception e) {
            //System.out.println(e);
            //System.out.println("Error Occured While Processing Your Request...");
        }
        
    }

    public void SendMoneyToUser() {
        try{
            System.out.println("Available recipients: ");
            for(String user1 : users) {
                if(!user.getUserName().contains(user1)) {
                    System.out.println(user1);
                }
            }
            System.out.print("Enter recipient's name: ");
            String input = _sc.nextLine().trim();
            if(!input.equals(user.getUserName())) {
                if(users.contains(input)) {
                    System.out.print("Enter amount to send: $");
                    String input2 = _sc.nextLine().trim();
                    if(Double.parseDouble(input2) <= 0){
                        System.out.println("Failed to send money: amount must be positive.");
                    }else {
                        if(Integer.parseInt(input2) >= 0) {
                            if (isBetween(new BigDecimal(input2), new BigDecimal(0), savingsAccount.getBalance())) {
                                savingsAccount.deleteBalance(new BigDecimal(input2));
                                System.out.println("Sent $" + Integer.parseInt(input2) + " to " + input);
                            }else {
                                System.out.println("Insufficient funds.");
                            }
                        }
                    }
                    
                }else {
                    System.out.println("Invalid recipient");
                }
            }
        }catch(Exception e) {
            //System.out.println(e);
            //System.out.println("Error Occured While Processing Your Request...");
        }
    }

    public void TransferBetweenAccounts() {
        System.out.println("1. Transfer from savings to investment");
        System.out.println("2. Transfer from investment to savings");
        System.out.print("Enter your choice: ");
        String input = _sc.nextLine().trim();
        if(input.equals("1")) {
            System.out.print("Enter amount to transfer: $");
            String amount = _sc.nextLine().trim();
            if(Double.parseDouble(amount) <= 0){
                System.out.println("Transfer failed: amount must be positive");
            }else {
                if(Double.parseDouble(amount) >= 0) {
                    if (isBetween(new BigDecimal(amount), new BigDecimal(0), savingsAccount.getBalance())) {
                        savingsAccount.deleteBalance(new BigDecimal(amount));
                        investmentAccount.deposit(new BigDecimal(amount));
                        System.out.println("Successfully transferred $" + amount + " to investment account.");
                    }else {
                        System.out.println("Transfer failed: Insufficient funds");
                    }
                }
            }
        }else if(input.equals("2")) {
            System.out.print("Enter amount to transfer: $");
            String amount = _sc.nextLine().trim();
            if(Double.parseDouble(amount) <= 0){
                System.out.println("Transfer failed: amount must be positive");
            }else {
                if(Double.parseDouble(amount) >= 0) {
                    if (isBetween(new BigDecimal(amount), new BigDecimal(0), investmentAccount.getBalance())) {
                        investmentAccount.deleteBalance(new BigDecimal(amount));
                        savingsAccount.deposit(new BigDecimal(amount));
                        System.out.println("Successfully transferred $" + amount +" to savings account");
                    }else {
                        System.out.println("Transfer failed: Insufficient funds");
                    }
                }
            }
        }
    }

    public void InvestInFunds() {
        System.out.println("Available funds:");
        for (Fund fund : Fund.values()) {
            System.out.println(fund);
        }
        System.out.print("Enter fund to invest in: ");
        String input = _sc.nextLine().trim();
        if(input.equals("LOW_RISK")) {
            System.out.print("Enter amount to invest: $");
            String amount = _sc.nextLine().trim();
            if(Double.parseDouble(amount) <= 0) {
                System.out.println("Invalid amount. Must be greater than zero.");
            }else {
                if(isBetween(new BigDecimal(amount), new BigDecimal(0), investmentAccount.getBalance())) {
                    investmentAccount.deleteBalance(new BigDecimal(amount));
                    investmentAccount.addLOWBalance(new BigDecimal(amount));
                    System.out.println("Successfully invested $" + amount + " in LOW_RISK fund");
                }else {
                    System.out.println("Insufficient funds in investment account.");
                }
            }
        }else if(input.equals("MEDIUM_RISK")) {
            System.out.print("Enter amount to invest: $");
            String amount = _sc.nextLine().trim();
            if(Double.parseDouble(amount) <= 0) {
                System.out.println("Invalid amount. Must be greater than zero.");
            }else {
                if(isBetween(new BigDecimal(amount), new BigDecimal(0), investmentAccount.getBalance())) {
                    investmentAccount.deleteBalance(new BigDecimal(amount));
                    investmentAccount.addMEDBalance(new BigDecimal(amount));
                    System.out.println("Successfully invested $" + amount + " in MEDIUM_RISK fund");
                }else {
                    System.out.println("Insufficient funds in investment account.");
                }
            }
        }else if(input.equals("HIGH_RISK")) {
            System.out.print("Enter amount to invest: $");
            String amount = _sc.nextLine().trim();
            if(Double.parseDouble(amount) <= 0) {
                System.out.println("Invalid amount. Must be greater than zero.");
            }else {
                if(isBetween(new BigDecimal(amount), new BigDecimal(0), investmentAccount.getBalance())) {
                    investmentAccount.deleteBalance(new BigDecimal(amount));
                    investmentAccount.addHIGHBalance(new BigDecimal(amount));
                    System.out.println("Successfully invested $" + amount + " in HIGH_RISK fund");
                }else {
                    System.out.println("Insufficient funds in investment account.");
                }
            }
        }else {
            System.out.println("Invalid fund");
        }
    }

    public void WithdrawAllInvestments() {
        System.out.println("All investments have been withdrawn and added to your investment account balance.");
        investmentAccount.WithdrawAllInvestments();
    }

    public String CheckBankingMenuChoice(String input) {
        String result = "";
        switch(input) {
            case "1":
                ShowBalance();
                result = "1";
                break;
            case "2":
                DepositMoney();
                result = "2";
                break;
            case "3":
                WithdrawMoneyFromSavingsAccount();
                result = "3";
                break;
            case "4":
                SendMoneyToUser();
                result = "4";
                break;
            case "5":
                InvestInFunds();
                result = "5";
                break;
            case "6":
                TransferBetweenAccounts();
                result = "6";
                break;
            case "7":
                WithdrawAllInvestments();
                result = "7";
                break;
            case "8":
                System.out.println("You have been logged out.");
                result = "8";
                break;
            case "9":
                System.out.println("Thank you for using our banking app. Goodbye!");
                result = "9";
                break;
            default:
                System.out.println("Invalid Case");
                break;
        }
        return result;
    }

    public void LaunchApp() {
        boolean shouldExit = false;
        while(shouldExit == false) {
            System.out.print("Enter your name to login: ");
            String username = _sc.nextLine().trim();
            boolean isLoggedin = Login(username);
            if(isLoggedin) {
                user = new User(username, 1000.00);
                savingsAccount = new SavingsAccount();
                investmentAccount = new InvestmentAccount();
                System.out.println("Welcome, " + user.getUserName() + "!");
                while(true) {
                    DisplayBankingMenu();
                    String choice = _sc.nextLine().trim();
                    String result = CheckBankingMenuChoice(choice);
                    if(result == "8") {
                        user = null;
                        savingsAccount = null;
                        investmentAccount = null;
                        break;
                    }
                    if(result == "9") {
                        shouldExit = true;
                        break;
                    }
                }
            }
        }
    }
}