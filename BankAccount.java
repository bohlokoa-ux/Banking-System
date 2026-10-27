package banking;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Abstraction: BankAccount is an abstract superclass
public abstract class BankAccount {
    
    // Encapsulation: private fields
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        // Exception handling & Business Rule: Opening balance must not be negative
        if (balance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
        this.balance = balance;
    }

    // Getters (Encapsulation)
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Protected setter for balance (used internally by subclasses)
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    // Abstract methods (Abstraction) - Subclasses MUST implement these
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract void calculateInterest();

    // File Handling: Method to log transactions to a text file
    protected void logTransaction(String transactionType, double amount, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = String.format("Date/Time: %s | Account: %s | Type: %s | Amount: %.2f | Balance: %.2f | Result: %s%n",
                timestamp, accountNumber, transactionType, amount, balance, message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.out.println("Error writing to transactions.txt: " + e.getMessage());
        }
    }
    
    // Helper method to display basic account info
    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: M" + String.format("%.2f", balance));
        System.out.println("-------------------------");
    }
}