import java.io.IOException;
import java.util.Scanner;

public class ATM {

	private Scanner in;
	private BankAccount activeAccount;
	private Bank bank;
	private BankAccount transferAccount;

	public static final int VIEW = 1;
	public static final int DEPOSIT = 2;
	public static final int WITHDRAW = 3;
	public static final int TRANSFER = 4;
	public static final int LOGOUT = 5;

	public static final int INVALID = 0;
	public static final int INSUFFICIENT = 1;
	public static final int SUCCESS = 2;

	// sets restrictions on acceptable inputs
	public static final int FIRST_NAME_MIN_WIDTH = 1;
	public static final int FIRST_NAME_WIDTH = 20;
	public static final int LAST_NAME_MIN_WIDTH = 1;
	public static final int LAST_NAME_WIDTH = 30;

	public static final int PIN_WIDTH = 4;
	public static final int PIN_MIN = 1000;
	public static final int PIN_MAX = 9999;

	public static final int ACCOUNT_NO_WIDTH = 9;
	public static final long ACCOUNT_NO_MIN = 100000001;
	public static final long ACCOUNT_NO_MAX = 999999999;

	public static final int BALANCE_WIDTH = 15;
	public static final double BALANCE_MIN = 0.00;
	public static final double BALANCE_MAX = 999999999999.99;

	public ATM() {
		this.in = new Scanner(System.in);

		try {
			this.bank = new Bank();
		} catch (IOException e) {
			// cleanup any resources (i.e., the Scanner) and exit
			in.close();
		}
	}

	public void startup() {
		System.out.println("Welcome to the AIT ATM!\n");
		long accountNo;
		int pin;

		boolean creatingAccount = true;

		System.out.print("Account No.: ");
		String accountNoString = in.next();
		// define and add condition for valid acct. no; consolidate
		while (!isNumeric(accountNoString)) {
			System.out.print("\nInvalid entry.\n\nAccount No.: ");
			accountNoString = in.next();
		}
		System.out.print("Pin: ");
		pin = in.nextInt();
		while (!isValidPin(1000, 9999, pin)) {
			System.out.print("\nInvalid entry.\n\nPin: ");
			pin = in.nextInt();
		}

		while (creatingAccount) {
			if (accountNoString.equals("+")) {
				createAccount();
				System.out.print("\nAccount No.: ");
				accountNoString = in.next();
			} else if (!(accountNoString.equals("+")) && isNumeric(accountNoString)) {
				creatingAccount = false;
			}
		}

		accountNo = Long.parseLong(accountNoString);

		while (true) {

			if (accountNo == -1 && pin == -1) {
				System.out.print("\nGoodbye!");
				return;
			}

			activeAccount = bank.getAccount(accountNo);

			if (isValidLogin(accountNo, pin)) {
				System.out.println("\nHello, again, " + activeAccount.getAccountHolder().getFirstName() + "!\n");

				boolean validLogin = true;
				while (validLogin) {
					switch (getSelection()) {
					case VIEW:
						showBalance();
						bank.save();
						break;
					case DEPOSIT:
						deposit();
						bank.save();
						break;
					case WITHDRAW:
						withdraw();
						bank.save();
						break;
					case TRANSFER:
						transfer();
						bank.save();
					case LOGOUT:
						validLogin = false;
						break;
					default:
						System.out.println("\nInvalid selection.\n");
						break;
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

	// for the error, the getter is the issue
	public boolean isValidLogin(long accountNo, int pin) {
		return accountNo == activeAccount.getAccountNo() && pin == activeAccount.getPin();
	}

	public int getSelection() {
		System.out.println("[1] View balance");
		System.out.println("[2] Deposit money");
		System.out.println("[3] Withdraw money");
		System.out.println("[4] Transfer money");
		System.out.println("[5] Logout");

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

	public void transfer() {
		System.out.print("\nEnter destination account number: ");
		long toAccountNo = in.nextLong();

		System.out.print("Enter transfer amount: ");
		double transferAmount = in.nextDouble();

		// code to make sure transfer amount is valid
		if (transferAmount <= 0) {
			System.out.println("\nTransfer rejected. Amount must be greater than $0.00.");
		} else if (activeAccount.getDoubleBalance() < transferAmount) {
			System.out.println("\nTransfer rejected. Insufficient funds.");
		} else {
			System.out.println("\nTransfer accepted.");
			activeAccount.withdraw(transferAmount);
			transferAccount = bank.getAccount(toAccountNo);
			transferAccount.deposit(transferAmount);
		}

	}

	public void shutdown() {
		if (in != null) {
			in.close();
		}
		System.out.println("\nGoodbye!");
		System.exit(0);
	}

	public void createAccount() {
		System.out.print("\nFirst Name: ");
		String firstName = in.next();
		while (!isValidFirst(1, 20, firstName)) {
			System.out.print("Invalid entry. First Name: ");
			firstName = in.next();
		}

		System.out.print("Last Name: ");
		String lastName = in.next();
		while (!isValidLast(1, 30, lastName)) {
			System.out.print("Invalid entry. Last Name: ");
			lastName = in.next();
		}

		System.out.print("Pin: ");
		int pin = in.nextInt();
		while (!isValidPin(1000, 9999, pin)) {
			System.out.print("Invalid entry. Last Name: ");
			pin = in.nextInt();
		}

		// User newUser = new User(firstName, lastName);
		//
		// BankAccount newAccount = bank.createAccount(pin, newUser);
		//
		// long newAccountNo = newAccount.getAccountNo();

		activeAccount = bank.createAccount(pin, new User(firstName, lastName));

		System.out.print("\nThank you. Your account number is " + activeAccount.getAccountNo()
				+ ". Please login to access your newly created account.\n");

		bank.save();
	}

	public boolean isValidFirst(int min, int max, String firstName) {
		if (firstName != null && firstName.length() >= min && firstName.length() <= max) {
			return true;
		}
		return false;
	}

	public boolean isValidLast(int min, int max, String lastName) {
		if (lastName != null && lastName.length() >= min && lastName.length() <= max) {
			return true;
		}
		return false;
	}

	public boolean isValidPin(int min, int max, int pin) {
		if (pin >= min && pin <= max) {
			return true;
		}
		return false;
	}

	public boolean isNumeric(String testStr) {
		try {
			int integer = Integer.parseInt(testStr);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		ATM atm = new ATM();

		atm.startup();
	}
}