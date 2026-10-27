package banking;

// Inheritance: CurrentAccount extends BankAccount
// Interface Implementation: Implements Loanable interface
// Final class: No intention for further extension
public final class CurrentAccount extends BankAccount implements Loanable {
    
    // Encapsulation: private fields specific to CurrentAccount
    private static final double OVERDRAFT_LIMIT = 2000.00;
    private static final double MAX_LOAN_AMOUNT = 20000.00; // Permitted loan limit for Current Account

    // Constructor
    public CurrentAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    // Method Overriding: CurrentAccount-specific deposit
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

    // Method Overriding: CurrentAccount-specific withdrawal with overdraft rule
    @Override
    public void withdraw(double amount) {
        // Exception handling: Validate withdrawal amount
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            logTransaction("Withdrawal", amount, "FAILED - Invalid amount");
            return;
        }

        double newBalance = getBalance() - amount;

        // Business Rule: Must not exceed overdraft limit of M2,000.00
        if (newBalance < -OVERDRAFT_LIMIT) {
            System.out.println("Withdrawal denied. Exceeds overdraft limit of M" + OVERDRAFT_LIMIT);
            System.out.println("Current balance: M" + String.format("%.2f", getBalance()));
            logTransaction("Withdrawal", amount, "FAILED - Exceeds overdraft limit");
            return;
        }

        setBalance(newBalance);
        System.out.println("Successfully withdrew M" + String.format("%.2f", amount));
        System.out.println("New balance: M" + String.format("%.2f", newBalance));
        logTransaction("Withdrawal", amount, "SUCCESS - New balance: M" + String.format("%.2f", newBalance));
    }

    // Method Overriding: CurrentAccount earns no interest
    @Override
    public void calculateInterest() {
        System.out.println("Current Accounts do not earn interest.");
        logTransaction("Interest Calculation", 0.00, "INFO - No interest applied for Current Account");
    }

    // Interface Implementation: Loan request for CurrentAccount
    @Override
    public void requestLoan(double amount) {
        // Exception handling: Validate loan amount
        if (amount <= 0) {
            System.out.println("Loan amount must be positive.");
            logTransaction("Loan Request", amount, "FAILED - Invalid amount");
            return;
        }

        if (amount > MAX_LOAN_AMOUNT) {
            System.out.println("Loan request denied. Maximum loan limit for Current Account is M" + MAX_LOAN_AMOUNT);
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