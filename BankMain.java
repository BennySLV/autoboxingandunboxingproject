package Section6.AutoboxingAndUnboxingChallenge;

import java.util.Scanner;

/**
 * Java Programming Masterclass for Software Developers
 *
 * Section 6: Arrays, Java in-built lists, autoboxing
 * and unboxing
 *
 * This class will run the banking application. Which
 * have all been defined in the other classes in this package.
 *
 * @author Ben Silveston
 */
public class BankMain {
    private Bank bank;
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor
     *
     * @param bank The bank object data
     */
    private BankMain(Bank bank) {
        this.bank = bank;
    }

    /**
     * Run the application
     */
    private void runApplication() {
        boolean applicationIsRunning = true;
        while(applicationIsRunning) {
            System.out.println("Please select from the following eight options: " +
                    "\n\t 1 - Create a new branch" +
                    "\n\t 2 - Remove an existing branch" +
                    "\n\t 3 - Add a new customer" +
                    "\n\t 4 - Remove an existing customer" +
                    "\n\t 5 - Add a new transaction" +
                    "\n\t 6 - Remove an existing transaction" +
                    "\n\t 7 - Search for an existing customer" +
                    "\n\t 8 - Display all customers for a particular branch " +
                    "\n\t 9 - Close the application");

            byte selection = SCANNER.nextByte();
            switch(selection) {
                case 1:
                    bank.addBranch();
                    runApplication();
                    break;
                case 2:
                    bank.removeBranch();
                    runApplication();
                    break;
                case 3:
                    bank.getBranch().addNewCustomer();
                    runApplication();
                    break;
                case 4:
                    bank.getBranch().removeCustomer();
                    runApplication();
                    break;
                case 5:
                    bank.getBranch().addAdditionalTransaction();
                    runApplication();
                    break;
                case 6:
                    bank.getBranch().removeTransaction();
                    runApplication();
                    break;
                case 7:
                    bank.getBranch().searchForCustomer();
                    runApplication();
                    break;
                case 8:
                    bank.displayAllCustomers();
                    runApplication();
                case 9:
                    closeApplication();
                    break;
            }
            applicationIsRunning = false;
        }
    }

    /**
     * Close the application
     */
    private static void closeApplication() {
        System.out.println("Application closed.");
        System.exit(0);
    }

    /**
     * Main method
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        BankMain bankMain = new BankMain(new Bank("Lloyds Bank"));
        bankMain.runApplication();
    }
}