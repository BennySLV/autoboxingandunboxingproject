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
    private Bank bank;

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor
     *
     * @param branchName The name of the branch
     */
    Branch(String branchName) {
        this.branchName = branchName;
        this.listOfCustomers = new ArrayList<>();
    }

    /**
     * Get the current branch name for the bank
     *
     * @return The current branch name
     */
    String getBranchName() {
        return branchName;
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
    ArrayList<Customer> getListOfCustomers() {
        return listOfCustomers;
    }

    /**
     * Get the data for each Bank object that has been created.
     *
     * @return The Bank data
     */
    public Bank getBank() {
        return bank;
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
            this.listOfCustomers.add(this.customer);
            this.customer.addNewTransaction(transactionAmount);
            System.out.println("Customer has been added successfully.");
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
        for(int i = 0; i < this.listOfCustomers.size(); i++) {
            if(customerIsOnFile(customerName)) {
                this.listOfCustomers.remove(i);
                break;
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
        String customerName = SCANNER.next();

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
     * @return The search result
     */
    private boolean customerIsOnFile(String customerName) {
        boolean customerFound = false;
        if(this.getBank().loadListOfCustomers(this.getBranchName()).exists()) {
            customerFound = true;
        }
        else if(this.listOfCustomers.isEmpty()) {
            System.out.println("Error - there are no customers found for this branch.");
        }
        else {
            for(int i = 0; i < this.listOfCustomers.size(); i++) {
                if(this.listOfCustomers.get(i).getName().equalsIgnoreCase(customerName)) {
                    customerFound = true;
                    break;
                }
            }
        }
        return customerFound;
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
    private File saveListOfTransactions(String customerName) {
        File file = new File(customerName + "_transactions.txt");
        try {
            if(customerIsOnFile(customerName)) {
                if(!this.getCustomer().getListOfTransactions().isEmpty()) {
                    PrintWriter output = new PrintWriter(file);
                    for(int i = 0; i < this.getCustomer().getListOfTransactions().size(); i++) {
                        output.println(formatCurrencyToGbp(this.getCustomer().getListOfTransactions().get(i)));
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
     * @return The file containing the list of transactions for that specific customer
     */
    private File loadListOfTransactions(String customerName) {
        File file = saveListOfTransactions(customerName);
        try {
            if(customerIsOnFile(customerName)) {
                if(!this.getCustomer().getListOfTransactions().isEmpty()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
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
        if(customerIsOnFile(customerName)) {
            loadListOfTransactions(customerName);
        }
    }
}