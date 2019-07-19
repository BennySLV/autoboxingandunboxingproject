package Section6.AutoboxingAndUnboxingChallenge;

import java.io.*;
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
     * Save the list of customers for
     * each branch to a text file.
     * Specified by the branch name.
     *
     * @param branchName The name of the branch
     * @return The file containing the list of customers for that branch
     */
    private File saveListOfCustomers(String branchName) {
        File file = new File(branchName + "_customers.txt");
        try {
            if(branchIsOnFile(branchName)) {
                if(!this.getBranch().getListOfCustomers().isEmpty()) {
                    PrintWriter output = new PrintWriter(file);
                    for(int i = 0; i < this.getBranch().getListOfCustomers().size(); i++) {
                        output.println(this.getBranch().getListOfCustomers().get(i));
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
    public File loadListOfCustomers(String branchName) {
        File file = saveListOfCustomers(branchName);
        try {
            if(branchIsOnFile(branchName)) {
                if(!this.getBranch().getListOfCustomers().isEmpty()) {
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
     * Display all customers for a particular branch
     */
    void displayAllCustomers() {
        System.out.print("Please type in a branch name for searching: ");
        String branchName = SCANNER.next();
        if(branchIsOnFile(branchName)) {
            loadListOfCustomers(branchName);
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
                    System.out.println("Branch: " + this.getBranch().getBranchName());
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