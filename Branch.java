package Section6.AutoboxingAndUnboxingChallenge;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Java Programming Masterclass for Software Developers
 *
 * Section 6: Arrays, Java in-built lists, autoboxing
 * and unboxing
 *
 * This class will represent a Branch object
 * for the Autoboxing and Unboxing challenge
 *
 * The class will contain the data and functionality
 * for each branch and its list of customers and their respective
 * transactions.
 *
 * @author Ben Silveston
 */
public class Branch {
    private String branchName;
    private Customer customer;
    private ArrayList<Customer> listOfCustomers;

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Empty constructor
     *
     * This is needed to ensure that
     * all object data can be ultimately
     * stored and accessed by other classes
     * when the program is running.
     * It will therefore be overridden
     *
     * by the default constructor whenever
     * a new branch will be created in the appropriate methods.
     */
    Branch() {}

    /**
     * Default constructor
     *
     * @param branchName The name of the branch
     */
    Branch(String branchName) {
        this.branchName = branchName;
        this.customer = new Customer();
        this.listOfCustomers = new ArrayList<>();
    }

    /**
     * Get the current branch name for the bank
     *
     * @return The current branch name
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Set the new branch name for the bank
     *
     * @param branchName The new branch name
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * Get the current customer data
     *
     * @return The current customer data
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Get the current list of customers
     *
     * @return The current list of customers
     */
    private ArrayList<Customer> getListOfCustomers() {
        return listOfCustomers;
    }

    /**
     * Add a new customer
     * to the branch
     *
     * This method also performs validation
     * checks to ensure that there are no duplicate customers
     */
    void addNewCustomer() {
        System.out.print("Please enter the number of customers that you wish to add: ");
        int numberOfCustomers = SCANNER.nextInt();
        String customerName;

        for(int i = 0; i < numberOfCustomers; i++) {
            System.out.println("\nPlease enter the following details for the new customer");
            System.out.print("\nCustomer name: ");
            customerName = SCANNER.next();
            System.out.print("Transaction amount: ");
            double transactionAmount = SCANNER.nextDouble();

            for(int j = 0; j < this.listOfCustomers.size(); j++) {
                if(this.customerIsOnFile(customerName)) {
                    System.out.println("Error - customer already exists.");
                }
            }
            this.customer = new Customer(customerName, transactionAmount);
            this.listOfCustomers.add(this.customer);
            System.out.println("Customer has been added successfully.");
            this.displayListOfCustomers();
        }
        this.addAdditionalTransaction();
    }

    /**
     * Close an account by removing
     * an existing customer
     */
    void removeCustomer() {
        System.out.print("Please select the customer that you wish to remove (using the name): ");
        String customerName = SCANNER.nextLine();
        for(int i = 0; i < this.listOfCustomers.size(); i++) {
            if(this.customerIsOnFile(customerName)) {
                this.listOfCustomers.remove(i);
            }
            else {
                System.out.println("Error - customer not found.");
            }
        }
    }

    /**
     * Search for a specific customer
     * in the list of customer
     *
     * If found, the customer data will
     * be printed (i.e. the name and the list
     * of transactions).
     */
    void searchForCustomer() {
        System.out.print("Please type in a customer name for searching: ");
        String customerName = SCANNER.nextLine();
        if(customerIsOnFile(customerName)) {
            System.out.println("Name: " + customerName);
            System.out.println("Transactions: ");
            displayListOfTransactions(customerName);
        }
        else {
            System.out.println("Error - customer not found!");
        }
    }

    /**
     * Determine if a specific customer
     * is found on the system
     *
     * @param customerName The name of the customer to be found
     * @return The result
     */
    private boolean customerIsOnFile(String customerName) {
        boolean customerFound = false;
        if(this.listOfCustomers.isEmpty()) {
            System.out.println("Error - there are no customers found for this branch.");
        }
        else {
            for(int i = 0; i < this.listOfCustomers.size(); i++) {
                if(this.listOfCustomers.get(i).getName().equalsIgnoreCase(customerName)) {
                    customerFound = true;
                }
                else {
                    customerFound = false;
                }
            }
        }
        return customerFound;
    }

    /**
     * Add an additional transaction to an account for an existing
     * customer
     *
     * Only add additional transactions if the customer's
     * list of transactions is not empty (i.e. an initial
     * transaction has been added). As well as that customer
     * existing by name in the first place.
     *
     * If the customer does not wish to add an additional
     * transaction then the branch will be given the option
     * to either remove an existing transaction
     * or be redirected back to the main menu.
     */
    private void addAdditionalTransaction() {
        System.out.print("Would you like to add a new transaction for this customer? (Y / N): ");
        String answer = SCANNER.next();

        if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("Yes")) {
            System.out.print("Please select the customer's name to add the additional transaction: ");
            String customerName = SCANNER.next();

            if(customerIsOnFile(customerName)) {
                if(!this.customer.getListOfTransactions().isEmpty()) {
                    System.out.print("Please enter a new transaction amount for the customer: ");
                    double transactionAmount = SCANNER.nextDouble();
                    this.customer.addNewTransaction(transactionAmount);
                    System.out.println("New transaction added successfully.");
                    displayListOfTransactions(customerName);
                    System.out.print("Continue adding further transactions? (Y / N): ");
                }
                else {
                    System.out.println("Error - there must be an initial transaction before adding a new one.");
                }
            }
            else {
                System.out.println("Error - customer not found.");
            }

        }
        else if(answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("No")) {
            System.out.print("Would you like to remove the current transaction from this customer (Y / N): ");
            answer = SCANNER.next();
            if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("Yes")) {
                removeAdditionalTransaction();
            }
            else if(answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("No")) {
                System.out.println("Redirected back to the main menu...");
                BankMain.runApplication();
            }
        }
    }

    /**
     * Remove an additional transaction from
     * a customer's existing current account
     */
    private void removeAdditionalTransaction() {
        displayListOfCustomers();
        System.out.println("Please select the customer's transaction that you wish to remove: ");
        String customerName = SCANNER.nextLine();
        if(customerIsOnFile(customerName)) {
            for(int i = 0; i < this.customer.getListOfTransactions().size(); i++) {
                this.customer.getListOfTransactions().remove(i);
            }
        }
        else {
            System.out.println("Error - customer not found.");
        }
    }

    /**
     * Format the given transaction
     * amount to the currency of
     * Great British Pound (GBP).
     *
     * @param amount The transaction amount to format
     * @return The formatted transaction amount in the GBP currency
     */
    private String formatCurrencyToGbp(double amount) {
        Locale locale = new Locale("en", "GB");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(amount);
    }

    /**
     * Display a list of customers for that branch
     */
    private void displayListOfCustomers() {
        for(int i = 0; i < this.listOfCustomers.size(); i++) {
            System.out.println("Name: " + this.getListOfCustomers().get(i).getName());
            this.displayListOfTransactions(this.getListOfCustomers().get(i).getName());
        }
    }

    /**
     * Print the list of transactions for a specific customer
     *
     * @param customerName The name of the customer
     */
    private void displayListOfTransactions(String customerName) {
        System.out.println("List of transactions for '" + customerName + "':");
        for(int i = 0; i < this.getCustomer().getListOfTransactions().size(); i++) {
            System.out.println(formatCurrencyToGbp(this.getCustomer().getListOfTransactions().get(i)));
        }
    }
}