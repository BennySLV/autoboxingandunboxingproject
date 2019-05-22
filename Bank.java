package Section6.AutoboxingAndUnboxingChallenge;

import java.util.ArrayList;
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
    private Branch branch;
    private ArrayList<Branch> listOfBranches;

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor
     *
     * @param bankBrandName The brand name for the bank
     */
    public Bank(String bankBrandName) {
        this.bankBrandName = bankBrandName;
        this.listOfBranches = new ArrayList<>();
    }

    /**
     * Get the branch object data
     *
     * @return The branch object data
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Get the current bank brand name
     *
     * @return The current brand name
     */
    public String getBankBrandName() {
        return bankBrandName;
    }

    /**
     * Set the new bank brand name
     *
     * @param bankBrandName The new brand name
     */
    public void setBankBrandName(String bankBrandName) {
        this.bankBrandName = bankBrandName;
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
        System.out.println("\nList of added branches: ");
        displayListOfBranches();
        this.branch.addNewCustomer();
    }

    /**
     * Remove a branch from the bank's
     * branch list
     *
     * Using the name as a reference
     */
    void removeBranch() {
        displayListOfBranches();
        System.out.print("Please remove an existing branch, using the branch name as a reference: ");
        String branchName = SCANNER.next();
        if(!this.listOfBranches.isEmpty()) {
            for(int i = 0; i < this.listOfBranches.size(); i++) {
                if(branchName.equalsIgnoreCase(this.listOfBranches.get(i).getBranchName())) {
                    this.listOfBranches.remove(i);
                    System.out.println("Branch has been removed successfully.");
                }
            }
        }
        else {
            System.out.println("Error - branch list is empty!");
        }
    }

    /**
     * Display the list
     * of branches for a particular bank
     */
    private void displayListOfBranches() {
        if(this.listOfBranches.isEmpty()) {
            System.out.println("Branch list is empty.");
        }
        else {
            for(int i = 0; i < this.listOfBranches.size(); i++) {
                System.out.println(this.listOfBranches.get(i).getBranchName());
            }
        }
    }
}

