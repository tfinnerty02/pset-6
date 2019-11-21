public class User {

	private String firstName;
	private String lastName;
	private String accountHolder;

	/**
	 * Formats the first and last name in preparation to be written to the data
	 * file.
	 * 
	 * @return a fixed-width string in line with the data file specifications.
	 */

	public String serialize() {
		return String.format("%1$-" + ATM.FIRST_NAME_WIDTH + "s", firstName)
				+ String.format("%1$-" + ATM.LAST_NAME_WIDTH + "s", lastName);
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountHolder = firstName + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAccountHolder() {
		return accountHolder;
	}
}