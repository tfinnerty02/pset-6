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

	public static final int FIRST_NAME_MIN_WIDTH = 1;
	public static final int FIRST_NAME_WIDTH = 20;
	public static final int LAST_NAME_MIN_WIDTH = 1;
	public static final int LAST_NAME_WIDTH = 30;

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
		while (!accountNoString.contentEquals("+") && !isValidAcctNo(100000001, 999999999, accountNoString)) {
			System.out.print("\nInvalid entry.\n\nAccount No.: ");
			accountNoString = in.next();
		}

		while (creatingAccount) {
			if (accountNoString.equals("+")) {
				createAccount();
				System.out.print("\nAccount No.: ");
				while (!accountNoString.contentEquals("+") && !isValidAcctNo(100000001, 999999999, accountNoString)) {
					System.out.print("\nInvalid entry.\n\nAccount No.: ");
					accountNoString = in.next();
				}
				accountNoString = in.next();
			} else if (!(accountNoString.equals("+")) && isNumeric(accountNoString)) {
				creatingAccount = false;
			}
		}

		System.out.print("Pin: ");
		pin = in.nextInt();
		while (!isValidPin(1000, 9999, pin)) {
			System.out.print("\nInvalid entry.\n\nPin: ");
			pin = in.nextInt();
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
						break;
					case LOGOUT:
						validLogin = false;
						startup();
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
		} else if (activeAccount.getDoubleBalance() + amount > 999999999999.99) {
			System.out.println("\nDeposit rejected. Amount would cause balance to exceed $999,999,999,999.99.\n");
			activeAccount.withdraw(amount);
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
		try {
			transferAccount = bank.getAccount(toAccountNo);
		} catch (NumberFormatException | NullPointerException nfe) {
			System.out.println("\nTransfer rejected. Destination account not found.");
			return;
		}

		System.out.print("Enter transfer amount: ");
		double transferAmount = in.nextDouble();

		// code to make sure transfer amount is valid
		if (transferAmount <= 0) {
			System.out.println("\nTransfer rejected. Amount must be greater than $0.00.");
		} else if (activeAccount.getDoubleBalance() < transferAmount) {
			System.out.println("\nTransfer rejected. Insufficient funds.");
		} else if (transferAccount.getDoubleBalance() + transferAmount > 999999999999.99) {
			System.out.println(
					"\nTransfer rejected. Amount would cause destination balance to exceed $999,999,999,999.99.");
		} else {
			System.out.println("\nTransfer accepted.\n");
			activeAccount.withdraw(transferAmount);
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
		// prompts for first name and checks if it is valid
		System.out.print("\nFirst Name: ");
		String firstName = in.next();
		while (!isValidFirst(1, 20, firstName)) {
			System.out.print("Invalid entry. First Name: ");
			firstName = in.next();
		}

		// prompts for last name and checks if it is valid
		System.out.print("Last Name: ");
		String lastName = in.next();
		while (!isValidLast(1, 30, lastName)) {
			System.out.print("Invalid entry. Last Name: ");
			lastName = in.next();
		}

		// prompts for pin and checks if it is valid
		System.out.print("Pin: ");
		int pin = in.nextInt();
		while (!isValidPin(1000, 9999, pin)) {
			System.out.print("Invalid entry. Last Name: ");
			pin = in.nextInt();
		}

		activeAccount = bank.createAccount(pin, new User(firstName, lastName));

		System.out.print("\nThank you. Your account number is " + activeAccount.getAccountNo()
				+ ".\nPlease login to access your newly created account.\n");
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

	public boolean isValidAcctNo(long min, long max, String accountNo) {
		try {
			if (Long.parseLong(accountNo) >= min && Long.parseLong(accountNo) <= max && isNumeric(accountNo)) {
				return true;
			}
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
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