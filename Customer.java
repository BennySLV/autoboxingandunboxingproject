package Section6.AutoboxingAndUnboxingChallenge;

import java.util.ArrayList;

/**
 * Java Programming Masterclass for Software Developers
 *
 * Section 6: Arrays, Java in-built lists, autoboxing
 * and unboxing
 *
 * This class will represent a Customer object
 * for the Autoboxing and Unboxing challenge.
 *
 * The class will contain the customer data
 * for use in the branches and for transaction
 * purposes.
 *
 * Additionally this class will also
 * demonstrate the use of autoboxing and unboxing.
 *
 * @author Ben Silveston
 */
public class Customer {
    private String name;
    private double transactionAmount;
    private ArrayList<Double> listOfTransactions;

    /**
     * Constructor
     *
     * @param name The name of the customer
     */
    Customer(String name, double transactionAmount) {
        this.name = name;
        this.transactionAmount = transactionAmount;
        this.listOfTransactions = new ArrayList<>();
    }

    /**
     * Get the current name of the customer
     *
     * @return The current name
     */
    public String getName() {
        return name;
    }

    /**
     * Set a new name for the customer
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the initial transaction
     * amount for the customer
     *
     * @return The initial transaction amount
     */
    public double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Get the list of transactions
     * made for the specific customer
     *
     * @return The list of transactions
     */
    public ArrayList<Double> getListOfTransactions() {
        return listOfTransactions;
    }

    /**
     * Add newly created transaction to
     * the customer's transaction list
     *
     * @param transactionAmount The initial transaction amount
     */
    void addNewTransaction(double transactionAmount) {
        if(this.getTransactionAmount() > 0) {
            this.listOfTransactions.add(transactionAmount);
        }
        else {
            System.out.println("Error - cannot add transaction. The amount needs to be at least £0.01." );
        }
    }
}