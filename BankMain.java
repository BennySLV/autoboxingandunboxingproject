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
    private static Branch branch;
    private static Bank bank;

    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Run the application
     */
    static void runApplication() {
        boolean applicationIsRunning = true;
        while(applicationIsRunning) {
            System.out.println("Please select from the following four options: " +
                    "\n\t 1 - Create a new branch" +
                    "\n\t 2 - Remove an existing branch" +
                    "\n\t 3 - Close the application");
            byte selection = SCANNER.nextByte();
            switch(selection) {
                case 1:
                    bank.addBranch();
                    break;
                case 2:
                    bank.removeBranch();
                    break;
                case 3:
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
        bank = new Bank("Lloyds Bank");
        branch = new Branch();
        runApplication();
    }
}
