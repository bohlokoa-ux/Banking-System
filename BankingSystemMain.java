import banking.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Polymorphism: BankAccount references are used to hold subclass objects
public class BankingSystemMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Creating sample accounts using BankAccount references (Polymorphism)
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount("SA-1001", "Bohlokoa Leche", 500.00));
        accounts.add(new CurrentAccount("CA-1002", "Sekhola Leche", 1500.00));

        int choice = 0;

        // Console Menu Loop
        do {
            
            System.out.println("       BANKING SYSTEM MENU       ");
          
            System.out.println("1. Display all accounts");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Transfer money");
            System.out.println("5. Calculate interest");
            System.out.println("6. Request bank loan");
            System.out.println("7. Display account details");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            // Exception handling for invalid numeric input
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        displayAllAccounts(accounts);
                        break;
                    case 2:
                        handleDeposit(scanner, accounts);
                        break;
                    case 3:
                        handleWithdraw(scanner, accounts);
                        break;
                    case 4:
                        handleTransfer(scanner, accounts);
                        break;
                    case 5:
                        handleInterest(scanner, accounts);
                        break;
                    case 6:
                        handleLoan(scanner, accounts);
                        break;
                    case 7:
                        handleDisplayDetails(scanner, accounts);
                        break;
                    case 8:
                        System.out.println("Thank you for using the Banking System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid menu choice. Please enter a number between 1 and 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input from scanner
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

        } while (choice != 8);

        scanner.close();
    }

    // Helper method to find an account by number
    private static BankAccount findAccount(List<BankAccount> accounts, String accountNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    // Helper method to get a valid positive double amount
    private static double getValidAmount(Scanner scanner, String prompt) {
        double amount = -1;
        while (amount <= 0) {
            System.out.print(prompt);
            try {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                scanner.nextLine();
            }
        }
        return amount;
    }

    private static void displayAllAccounts(List<BankAccount> accounts) {
        System.out.println("\n--- All Accounts ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            for (BankAccount acc : accounts) {
                acc.displayAccountInfo();
            }
        }
    }

    private static void handleDeposit(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = findAccount(accounts, accNum);
        
        if (acc != null) {
            double amount = getValidAmount(scanner, "Enter deposit amount: ");
            acc.deposit(amount); // Method Overriding
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void handleWithdraw(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = findAccount(accounts, accNum);
        
        if (acc != null) {
            double amount = getValidAmount(scanner, "Enter withdrawal amount: ");
            acc.withdraw(amount); // Method Overriding
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void handleTransfer(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Enter source account number: ");
        String sourceNum = scanner.nextLine();
        BankAccount source = findAccount(accounts, sourceNum);

        System.out.print("Enter destination account number: ");
        String destNum = scanner.nextLine();
        BankAccount dest = findAccount(accounts, destNum);

        if (source != null && dest != null) {
            double amount = getValidAmount(scanner, "Enter transfer amount: ");
            
            // Check sufficient funds before transferring
            double balanceBefore = source.getBalance();
            source.withdraw(amount); // Uses specific subclass rules (overdraft/min balance)
            
            // If withdrawal was successful (balance decreased)
            if (source.getBalance() < balanceBefore) {
                dest.deposit(amount);
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Transfer failed due to insufficient funds or account rules.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    private static void handleInterest(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = findAccount(accounts, accNum);
        
        if (acc != null) {
            acc.calculateInterest(); // Method Overriding
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void handleLoan(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = findAccount(accounts, accNum);
        
        if (acc != null) {
            // Polymorphism & Interface Implementation
            if (acc instanceof Loanable) {
                double amount = getValidAmount(scanner, "Enter loan amount: ");
                ((Loanable) acc).requestLoan(amount);
            } else {
                System.out.println("This account type does not support loans.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void handleDisplayDetails(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = findAccount(accounts, accNum);
        
        if (acc != null) {
            acc.displayAccountInfo();
        } else {
            System.out.println("Account not found.");
        }
    }
}