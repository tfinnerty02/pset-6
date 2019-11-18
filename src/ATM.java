import java.util.Scanner;

public class ATM {

    private Scanner in;
    private BankAccount activeAccount;
    private Bank bank;

    public static final int VIEW = 1;
    public static final int DEPOSIT = 2;
    public static final int WITHDRAW = 3;
    public static final int LOGOUT = 4;

    public static final int INVALID = 0;
    public static final int INSUFFICIENT = 1;
    public static final int SUCCESS = 2;

    public ATM() {
        in = new Scanner(System.in);

        activeAccount = new BankAccount(1234, new User("Ryan", "Wilson"));
    }

    public void startup() {
        System.out.println("Welcome to the AIT ATM!\n");

        while (true) {
            System.out.print("Account No.: ");
            String accountNoString = in.nextLine();

            // try {
            // 	accountNo = Long.parseLong(accountNoString);
            // } catch (NumberFormatException | NullPointerException nfe) {
            // 	System.out.println("Implement starting a new account here.");
            // }

           if(accountNoString.equals("+")){
              System.out.print("\nFirst Name: ");
              String firstName = in.nextLine();
              System.out.print("Last Name: ");
              String lastName = in.nextLine();
              System.out.print("Pin: ");
              int pin = in.nextInt();

      		  User newUser =  new User(firstName, lastName);

      		  BankAccount newAccount = bank.createAccount(pin, newUser);
              
              long newAccountNo = newAccount.getAccountNo();
              
      			System.out.println("\nThank you. Your account number is " + newAccountNo
      			+ ". Please login to access your newly created account");

            }else{
            	
              long accountNo = Long.parseLong(accountNoString);
              System.out.print("Pin: ");
              int pin = in.nextInt();
              
              if (isValidLogin(accountNo, pin)) {
                  System.out.println("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");

                  boolean validLogin = true;
                  while (validLogin) {
                      switch (getSelection()) {
                          case VIEW: showBalance(); break;
                          case DEPOSIT: deposit(); break;
                          case WITHDRAW: withdraw(); break;
                          case LOGOUT: validLogin = false; break;
                          default: System.out.println("\nInvalid selection.\n"); break;
                      }
                  }
              } else {
                  if (accountNo == -1 && pin == -1) {
                      shutdown();
                  } else {
                      System.out.println("\nInvalid account number and/or PIN.\n");
                  }
              }
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
        System.out.println("[4] Logout");

        return in.nextInt();
    }

    public void showBalance() {
        System.out.println("\nCurrent balance: " + activeAccount.getBalance());
    }

    public void deposit() {
        System.out.print("\nEnter amount: ");
        double amount = in.nextDouble();

        int status = activeAccount.deposit(amount);
        if (status == ATM.INVALID) {
            System.out.println("\nDeposit rejected. Amount must be greater than $0.00.\n");
        } else if (status == ATM.SUCCESS) {
            System.out.println("\nDeposit accepted.\n");
        }
    }

    public void withdraw() {
        System.out.print("\nEnter amount: ");
        double amount = in.nextDouble();

        int status = activeAccount.withdraw(amount);
        if (status == ATM.INVALID) {
            System.out.println("\nWithdrawal rejected. Amount must be greater than $0.00.\n");
        } else if (status == ATM.INSUFFICIENT) {
            System.out.println("\nWithdrawal rejected. Insufficient funds.\n");
        } else if (status == ATM.SUCCESS) {
            System.out.println("\nWithdrawal accepted.\n");
        }
    }

    public void shutdown() {
        if (in != null) {
            in.close();
        }

        System.out.println("\nGoodbye!");
        System.exit(0);
    }

    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.startup();
    }
}
