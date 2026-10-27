package banking;

// Inheritance: SavingsAccount extends BankAccount
// Interface Implementation: Implements Loanable interface
// Final class: No intention for further extension
public final class SavingsAccount extends BankAccount implements Loanable {
    
    // Encapsulation: private fields specific to SavingsAccount
    private static final double MINIMUM_BALANCE = 100.00;
    private static final double ANNUAL_INTEREST_RATE = 0.05; // 5%
    private static final double MAX_LOAN_AMOUNT = 10000.00;

    // Constructor
    public SavingsAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    // Method Overriding: SavingsAccount-specific deposit
    @Override
    public void deposit(double amount) {
        // Exception handling: Validate deposit amount
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            logTransaction("Deposit", amount, "FAILED - Invalid amount");
            return;
        }

        double newBalance = getBalance() + amount;
        setBalance(newBalance);
        System.out.println("Successfully deposited M" + String.format("%.2f", amount));
        System.out.println("New balance: M" + String.format("%.2f", newBalance));
        logTransaction("Deposit", amount, "SUCCESS - New balance: M" + String.format("%.2f", newBalance));
    }

    // Method Overriding: SavingsAccount-specific withdrawal with minimum balance rule
    @Override
    public void withdraw(double amount) {
        // Exception handling: Validate withdrawal amount
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            logTransaction("Withdrawal", amount, "FAILED - Invalid amount");
            return;
        }

        double newBalance = getBalance() - amount;

        // Business Rule: Must maintain minimum balance of M100.00
        if (newBalance < MINIMUM_BALANCE) {
            System.out.println("Withdrawal denied. Must maintain minimum balance of M" + MINIMUM_BALANCE);
            System.out.println("Current balance: M" + String.format("%.2f", getBalance()));
            System.out.println("Maximum withdrawal allowed: M" + String.format("%.2f", getBalance() - MINIMUM_BALANCE));
            logTransaction("Withdrawal", amount, "FAILED - Insufficient funds (minimum balance rule)");
            return;
        }

        setBalance(newBalance);
        System.out.println("Successfully withdrew M" + String.format("%.2f", amount));
        System.out.println("New balance: M" + String.format("%.2f", newBalance));
        logTransaction("Withdrawal", amount, "SUCCESS - New balance: M" + String.format("%.2f", newBalance));
    }

    // Method Overriding: SavingsAccount earns 5% annual interest
    @Override
    public void calculateInterest() {
        double interest = getBalance() * ANNUAL_INTEREST_RATE;
        double newBalance = getBalance() + interest;
        setBalance(newBalance);
        System.out.println("Interest calculated at 5% annual rate.");
        System.out.println("Interest earned: M" + String.format("%.2f", interest));
        System.out.println("New balance: M" + String.format("%.2f", newBalance));
        logTransaction("Interest Calculation", interest, "SUCCESS - 5% interest applied. New balance: M" + String.format("%.2f", newBalance));
    }

    // Interface Implementation: Loan request for SavingsAccount
    @Override
    public void requestLoan(double amount) {
        // Exception handling: Validate loan amount
        if (amount <= 0) {
            System.out.println("Loan amount must be positive.");
            logTransaction("Loan Request", amount, "FAILED - Invalid amount");
            return;
        }

        if (amount > MAX_LOAN_AMOUNT) {
            System.out.println("Loan request denied. Maximum loan limit for Savings Account is M" + MAX_LOAN_AMOUNT);
            logTransaction("Loan Request", amount, "FAILED - Exceeds maximum loan limit");
            return;
        }

        // Successful loan increases account balance
        double newBalance = getBalance() + amount;
        setBalance(newBalance);
        System.out.println("Loan of M" + String.format("%.2f", amount) + " approved!");
        System.out.println("New balance: M" + String.format("%.2f", newBalance));
        logTransaction("Loan Request", amount, "SUCCESS - Loan approved. New balance: M" + String.format("%.2f", newBalance));
    }
}