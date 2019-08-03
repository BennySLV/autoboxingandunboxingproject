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
 * This class will represent a Bank object
 * for the Autoboxing and Unboxing challenge
 *
 * The class will contain the functionality associated
 * with the data and functionality from both the Branch
 * and Customer classes.
 *
 * @author Ben Silveston
 */
public class Bank {
    private String bankBrandName;
    private Customer customer;
    private Branch branch;
    private ArrayList<Branch> listOfBranches;

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor
     *
     * @param bankBrandName The brand name for the bank
     */
    Bank(String bankBrandName) {
        this.bankBrandName = bankBrandName;
        this.listOfBranches = new ArrayList<>();
    }

    /**
     * Get the customer object data.
     *
     * @return The customer object data
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Get the branch object data
     *
     * @return The branch object data
     */
    private Branch getBranch() {
        return branch;
    }

    /**
     * Add a branch to the bank's branch list,
     * using the name as a reference
     */
    void addBranch() {
        System.out.print("Please add a new branch, using the branch name as a reference: ");
        String branchName = SCANNER.next();
        if(!this.listOfBranches.isEmpty()) {
            for(int i = 0; i < this.listOfBranches.size(); i++) {
                if(branchName.equalsIgnoreCase(this.listOfBranches.get(i).getBranchName())) {
                    System.out.println("Error - branch name already exists!");
                }
            }
        }
        this.branch = new Branch(branchName);
        this.listOfBranches.add(this.branch);
        System.out.println("Branch has been added successfully.");
    }

    /**
     * Remove a single branch from the bank's
     * branch list
     *
     * Using the name as a reference
     */
    void removeBranch() {
        if(this.listOfBranches.isEmpty()) {
            System.out.println("Error - branch list is empty!");
        }
        else {
            displayListOfBranches();
            System.out.print("Please remove an existing branch, using the branch name as a reference: ");
            String branchName = SCANNER.next();

            for(int i = 0; i < this.listOfBranches.size(); i++) {
                if(branchName.equalsIgnoreCase(this.listOfBranches.get(i).getBranchName())) {
                    this.listOfBranches.remove(i);
                    System.out.println("Branch has been removed successfully.");
                }
            }
        }
    }

    /**
     * Add a new customer
     * to the branch.
     *
     * This method also performs validation
     * checks to ensure that there are no duplicate customers.
     */
    void addCustomer() {
        System.out.println("Please select the appropriate branch: ");
        String branchName = SCANNER.next();
        System.out.print("Please enter the customer's name: ");
        String customerName = SCANNER.next();
        System.out.print("Please enter the initial transaction amount for that customer: ");
        double transactionAmount = SCANNER.nextDouble();

        if(!branchName.isEmpty()) {
            if(!customerName.isEmpty()) {
                if(!customerIsOnFile(customerName) && branchIsOnFile(branchName)) {
                    this.customer = new Customer(customerName, transactionAmount);
                    this.getBranch().getListOfCustomers().add(this.customer);
                    this.customer.addNewTransaction(transactionAmount);
                    System.out.println("Customer has been added successfully.");
                }
                else {
                    System.out.println("Error - customer already exists.");
                }
            }
            else {
                System.out.println("Error - customer name not entered.");
            }
        }
        else {
            System.out.println("Error - branch name not entered.");
        }
    }

    /**
     * Close an account by removing
     * an existing customer
     */
    void removeCustomer() {
        System.out.print("Please select the customer that you wish to remove (using their name): ");
        String customerName = SCANNER.nextLine();
        for(int i = 0; i < this.getBranch().getListOfCustomers().size(); i++) {
            if(customerIsOnFile(customerName)) {
                this.getBranch().getListOfCustomers().remove(i);
                break;
            }
            else {
                System.out.println("Error - customer not found.");
            }
        }
    }

    /**
     * Add an additional transaction to an account for an existing
     * customer.
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
    void addTransaction() {
        System.out.print("Please select the customer's name to add the additional transaction: ");
        String customerName = SCANNER.next();

        if(customerIsOnFile(customerName)) {
            if(!this.getCustomer().getListOfTransactions().isEmpty()) {
                System.out.print("Please enter a new transaction amount for the customer: ");
                double transactionAmount = SCANNER.nextDouble();
                this.getCustomer().addNewTransaction(transactionAmount);
                System.out.println("New transaction added successfully.");
            }
            else {
                System.out.println("Error - you must add a new customer with an initial transaction " +
                        "first before adding a new transaction.");
                this.addCustomer();
            }
        }
        else {
            System.out.println("Error - customer not found.");
        }
    }

    /**
     * Remove a transaction from
     * a customer's existing current account.
     *
     * In this case, the most recent transaction
     * will be removed from the system.
     */
    void removeTransaction() {
        System.out.println("Please enter the customer's name to remove the transaction: ");
        String customerName = SCANNER.next();
        if(customerIsOnFile(customerName)) {
            for(int i = 0; i < this.customer.getListOfTransactions().size(); i++) {
                this.customer.getListOfTransactions().remove(i);
            }
            System.out.println("Transaction removed successfully.");
        }
        else {
            System.out.println("Error - customer not found.");
        }
    }

    /**
     * Search for a specific customer
     * in the list of customer
     *
     * If a customer is found either as a text file
     * or during program execution then
     * the customer data will be printed (i.e. the name and the list
     * of transactions).
     */
    void searchCustomer() {
        System.out.print("Please type in a customer name for searching: ");
        String customerName = SCANNER.next();

        if(customerIsOnFile(customerName)) {
            System.out.println("Name: " + customerName);
            System.out.println("Transactions: ");
            this.displayListOfTransactions(customerName);
        }
        else {
            System.out.println("Error - customer not found!");
        }
    }

    /**
     * Determine if a specific customer
     * can be found on the system.
     *
     * This method only searches for customers
     * that are on the system during program
     * execution and therefore does not include
     * searching for any external text files relating
     * to that specific customer.
     *
     * @param customerName The name of the customer to be found
     * @return The search result
     */
    private boolean customerIsOnFile(String customerName) {
        boolean customerFound = false;
        if(branchIsOnFile(this.getBranch().getBranchName())) {
            for(int i = 0; i < this.getBranch().getListOfCustomers().size(); i++) {
                if(this.getBranch().getListOfCustomers().get(i).getName().equalsIgnoreCase(customerName)) {
                    customerFound = true;
                    break;
                }
            }
        }
        return customerFound;
    }

    /**
     * Display all customers for a particular branch
     */
    void displayAllCustomers() {
        System.out.print("Please type in a branch name for searching: ");
        String branchName = SCANNER.next();
        System.out.println("Customers at '" + branchName + "' branch: ");
        if(branchIsOnFile(branchName)) {
            for(int i = 0; i < this.getBranch().getListOfCustomers().size(); i++) {
                System.out.println(this.getBranch().getListOfCustomers().get(i).getName());
            }
        }
    }

    /**
     * Format the given transaction
     * amount to the currency of
     * Great British Pound (GBP).
     *
     * @param transactionAmount The transaction amount to format
     * @return The formatted transaction amount in the GBP currency
     */
    private String formatCurrencyToGbp(double transactionAmount) {
        Locale locale = new Locale("en", "GB");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(transactionAmount);
    }

    /**
     * Print the list of transactions for a specific customer
     *
     * @param customerName The name of the customer
     */
    private void displayListOfTransactions(String customerName) {
        for(int i = 0; i < this.getCustomer().getListOfTransactions().size(); i++) {
            if(customerName.equals(this.getCustomer().getName())) {
                System.out.println(formatCurrencyToGbp(this.getCustomer().getListOfTransactions().get(i)));
            }
        }
    }

    /**
     * Check if a specific branch has been added on the system
     *
     * @param branchName The name of the branch to be found
     * @return The search result
     */
    private boolean branchIsOnFile(String branchName) {
        boolean branchFound = false;
        if(this.listOfBranches.isEmpty()) {
            System.out.println("Error - branch list is empty.");
        }
        else {
            for(int i = 0; i < this.listOfBranches.size(); i++) {
                if(this.listOfBranches.get(i).getBranchName().equalsIgnoreCase(branchName)) {
                    branchFound = true;
                }
                else {
                    System.out.println("Error - branch not found.");
                }
            }
        }
        return branchFound;
    }

    /**
     * Display the list
     * of branches for a particular bank
     */
    private void displayListOfBranches() {
        for(int i = 0; i < this.listOfBranches.size(); i++) {
            System.out.println(this.listOfBranches.get(i).getBranchName());
        }
    }
}