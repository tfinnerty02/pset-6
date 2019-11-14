import java.io.IOException;
import java.util.Scanner;

public class ATM {

    private Scanner in ;
    private BankAccount activeAccount;
    private Bank bank;

    public static final int VIEW = 1;
    public static final int DEPOSIT = 2;
    public static final int WITHDRAW = 3;

    ////////////////////////////////////////////////////////////////////////////
    //                                                                        //
    // Refer to the Simple ATM tutorial to fill in the details of this class. //
    // You'll need to implement the new features yourself.                    //
    //                                                                        //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs a new instance of the ATM class.
     */

    public ATM() {
        this.in = new Scanner(System.in);

        activeAccount = new BankAccount(1234, 123456789, 0, new User("Ryan", "Wilson"));
        try {
            this.bank = new Bank();
        } catch (IOException e) {
            // cleanup any resources (i.e., the Scanner) and exit
        }
    }

    public void startup() {
        System.out.println("Welcome to the AIT ATM!\n");

        System.out.print("Account No.: ");
        long accountNo = in .nextLong();

        System.out.print("PIN        : ");
        int pin = in .nextInt();

        if (isValidLogin(accountNo, pin)) {
            System.out.println("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");

            switch (getSelection()) {
                case VIEW:
                    showBalance();
                    break;
                case DEPOSIT:
                    deposit();
                    break;
                case WITHDRAW:
                    withdraw();
                    break;
                default:
                    System.out.println("\nInvalid selection.\n");
                    break;
            }
        } else {
            System.out.println("\nInvalid account number and/or PIN.\n");
        }
    }
}

public boolean isValidLogin(long accountNo, int pin) {
    return accountNo == activeAccount.getAccountNo() && pin == activeAccount.getPin();
}

public int getSelection() {
    System.out.println("[1] View balance");
    System.out.println("[2] Deposit money");
    System.out.println("[3] Withdraw money");

    return in.nextInt();
}

public void showBalance() {
    System.out.println("\nCurrent balance: " + activeAccount.getBalance());
}

public void deposit() {
    System.out.print("\nEnter amount: ");
    double amount = in .nextDouble();

    activeAccount.deposit(amount);
    System.out.println();
}

public void withdraw() {
    System.out.print("\nEnter amount: ");
    double amount = in .nextDouble();

    activeAccount.withdraw(amount);
    System.out.println();
}

/*
 * Application execution begins here.
 */

public static void main(String[] args) {
    ATM atm = new ATM();

    atm.startup();
}
}
}
