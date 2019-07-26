package Section6.AutoboxingAndUnboxingChallenge;

import java.util.ArrayList;

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
    private ArrayList<Customer> listOfCustomers;

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
     * Get the current list of customers
     *
     * @return The current list of customers
     */
    ArrayList<Customer> getListOfCustomers() {
        return listOfCustomers;
    }
}