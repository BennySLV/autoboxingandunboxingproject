package Section6.AutoboxingAndUnboxingChallenge;

import java.io.*;
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
     * Get the current bank brand name
     *
     * @return The current name
     */
    public String getBankBrandName() {
        return bankBrandName;
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
    Branch getBranch() {
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
    void addNewCustomer() {
        System.out.print("Please enter the customer's name: ");
        String customerName = SCANNER.next();
        System.out.print("Please enter the initial transaction amount for that customer: ");
        double transactionAmount = SCANNER.nextDouble();

        if(!customerName.isEmpty()) {
            this.customer = new Customer(customerName, transactionAmount);
            this.getBranch().getListOfCustomers().add(this.customer);
            this.customer.addNewTransaction(transactionAmount);
            System.out.println("Customer has been added successfully.");
            this.saveListOfCustomers(this.getBranch().getBranchName());
            this.saveListOfTransactions(customerName);
        }
        else {
            System.out.println("Customer not added - no name entered.");
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
     * Save the list of customers for
     * each branch to a text file.
     * Specified by the branch name.
     *
     * @param branchName The name of the branch
     * @return The file containing the list of customers for that branch
     */
    private File saveListOfCustomers(String branchName) {
        File file = new File("/home/ben/NetBeansProjects/CompleteJavaMasterclassProjects/src/" +
            "Section6/AutoboxingAndUnboxingChallenge/branch_customer_files/" + branchName + "_customers.txt");
        try {
            if(branchIsOnFile(branchName)) {
                if(!this.getBranch().getListOfCustomers().isEmpty()) {
                    PrintWriter output = new PrintWriter(file);
                    for(int i = 0; i < this.getBranch().getListOfCustomers().size(); i++) {
                        output.println(this.getBranch().getListOfCustomers().get(i).getName());
                    }
                    output.close();
                }
                else {
                    System.out.println("Error - the customer list for this branch is empty. Aborting.");
                }
            }
            else {
                System.out.println("Error - branch not found.");
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    /**
     * Load the list of customers
     * for a given branch.
     *
     * Specified by the given branch name.
     *
     * @param branchName The name of the branch
     * @return The file containing the customers for that specific branch
     */
    private File loadListOfCustomers(String branchName) {
        File file = saveListOfCustomers(branchName);
        try {
            if(branchIsOnFile(branchName)) {
                if(!this.getBranch().getListOfCustomers().isEmpty()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    bufferedReader.close();
                }
                else {
                    System.out.println("Error - transaction list is empty.");
                }
            }
            else {
                System.out.println("Error - customer not found.");
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return file;
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
    void addAdditionalTransaction() {
        System.out.print("Please select the customer's name to add the additional transaction: ");
        String customerName = SCANNER.next();

        if(customerIsOnFile(customerName)) {
            if(!this.getCustomer().getListOfTransactions().isEmpty()) {
                System.out.print("Please enter a new transaction amount for the customer: ");
                double transactionAmount = SCANNER.nextDouble();
                this.getCustomer().addNewTransaction(transactionAmount);
                System.out.println("New transaction added successfully.");
                this.saveListOfTransactions(customerName);
            }
            else {
                System.out.println("Error - you must add a new customer with an initial transaction " +
                        "first before adding a new transaction.");
                this.addNewCustomer();
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
     * Save the list of transactions to
     * a text file.
     *
     * This text file will contain a list
     * of transactions for a specific customer.
     * Situated by a given customer name.
     *
     * @param customerName The name of the customer
     * @return The saved file containing the specific customer's transactions
     */
    File saveListOfTransactions(String customerName) {
        File file = new File("/home/ben/NetBeansProjects/CompleteJavaMasterclassProjects/src/" +
                "Section6/AutoboxingAndUnboxingChallenge/transactions_files/" +
                customerName + "_transactions.txt");
        try {
            if(customerIsOnFile(customerName)) {
                if(!this.getCustomer().getListOfTransactions().isEmpty()) {
                    PrintWriter output = new PrintWriter(file);
                    for(int i = 0; i < this.getCustomer().getListOfTransactions().size(); i++) {
                        output.println(formatCurrencyToGbp(
                                this.getCustomer().getListOfTransactions().get(i)));
                    }
                    output.close();
                }
                else {
                    System.out.println("Error - the transaction list for this customer is empty. Aborting.");
                }
            }
            else {
                System.out.println("Error - customer not found.");
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    /**
     * Load the text file containing the list of transactions
     * for a specific customer.
     *
     * Specified by the customer's name.
     *
     * @param customerName The name of the customer
     */
    File loadListOfTransactions(String customerName) {
        File file = saveListOfTransactions(customerName);
        try {
            if(customerIsOnFile(customerName)) {
                if(!this.getCustomer().getListOfTransactions().isEmpty()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    bufferedReader.close();
                }
                else {
                    System.out.println("Error - transaction list is empty.");
                }
            }
            else {
                System.out.println("Error - customer not found.");
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return file;
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
     * is found on the system
     *
     * @param customerName The name of the customer to be found
     * @return The search result
     */
    boolean customerIsOnFile(String customerName) {
        boolean customerFound = false;
        if(this.getBranch().getListOfCustomers().isEmpty()) {
            System.out.println("Error - there are no customers found for this branch.");
        }
        else {
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
            loadListOfCustomers(branchName);
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
    void displayListOfTransactions(String customerName) {
        loadListOfTransactions(customerName);
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