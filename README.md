# Problem Set 6

It's time to put your skills to the test. Rather than solving a disjointed set of exercises, this problem set focuses on expanding upon the [Simple ATM](https://apcs.gitbook.io/java/objects-and-classes/tutorials/simple-atm) tutorial. Portions of the application are already implemented for you. It's your job to fill in the pieces.

## Getting Started

To get started, create a [GitHub](https://github.com/) repository to store your code. When you're finished, clone my skeleton to get all of the starter code and instructions. Setup a remote to push your code to your repository instead of mine.

### Setup

1. Login to your GitHub account and create a new repository named `pset-6`.
2. In the terminal, navigate to your `APCSA` directory on the `Desktop`.
```
$ cd ~/Desktop/APCSA
```
3. Clone my skeleton repository into a directory named `pset-6`.
```
$ git clone git@github.com:ap-java-ucvts/pset-6-skeleton.git pset-6
```
4. Change into your newly created `pset-6` directory.
```
$ cd pset-6
```
5. Overwrite the remote, which originally points at my skeleton repository.
```
$ git remote rename origin upstream
```
6. Add a new remote that points at your `pset-6` repository. Replace `YOUR-USERNAME` with your actual username.
```
$ git remote add origin git@github.com:YOUR-USERNAME/pset-6.git
```
7. Open Eclipse. Click `File > Import...`.
8. Choose `Git > Projects from Git` and click `Next >`.
9. Click `Existing local repository` and click `Next >`.
10. Click `Add`, and then `Browse`. Locate your repository.
11. Check the repository when it appears in the `Search results`, and click `Finish`.
12. Highlight the newly added repository, click `Next >`, `Next >`, and `Finish`.

## Use Cases

Many of these features should be reminiscnent of the [Simple ATM](https://apcs.gitbook.io/java/objects-and-classes/tutorials/simple-atm) tutorial. Others are brand new, so please read through these use cases carefully.

Your ATM application must support the following use cases.

* Login to an account
* Create a new account
* View account balance
* Deposit funds into an account
* Withdraw funds from an account
* Transfer funds to another account
* Logout of an account
* Exit the application

### Main Menu

After starting the ATM, a welcome message is displayed and users are presented with a single prompt.

![Advanced ATM Login 0](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-login-0.png)

The ATM waits indefinitely for the user to respond in one of three possible ways. Let's walk through each possibility.

#### Login to an Existing Account

If the user already has an account, he or she can enter credentials (i.e., account number and PIN) to login.

If the credentials are valid, the user is logged into the ATM and given access to his or her account.

![Advanced ATM Login 1](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-login-1.png)

Otherwise, if the credentials are invalid, restart the login prompt cycle.

![Advanced ATM Login 2](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-login-2.png)

#### Create an Account

If the user does not have an account, he or she can enter `+` to create a new one.

![Advanced ATM Create Account](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-new-account.png)

Account numbers are assigned automatically. Take a look at the `Bank` class to see how this is done. Check out the [Requirements]() section for details regarding valid names and PINs, and what to do if a user enters an invalid one.

#### Exit Application

To manually exit the application, enter `-1` for both the `Account No.: ` and `PIN: ` prompts.

![Advanced ATM Exit](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-exit.png)

### Account Menu

After successfully logging into an account, users are greeted with a welcome message and presented with a five-option menu.

![Advanced ATM Account Menu](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-account-menu.png)

The ATM waits indefinitely for the user to select one of the options. Let's walk through each possibility, plus the scenario in which a user chooses an invalid option.

#### View Balance

If a user chooses option 1, display his or her current account balance.

![Advanced ATM View Balance](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-view-balance.png)

Display the balance, and restart the menu cycle.

#### Deposit Money

If a user chooses option 2, prompt him or her to enter the amount of money to be deposited.

![Advanced ATM Deposit](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-deposit.png)

Display a transaction status message, and restart the menu cycle. Check out the [Requirements]() section for details on status codes, messages, and the validity of transactions.

#### Withdraw Money

If a user chooses option 3, prompt him or her to enter the amount of money to be withdrawn.

![Advanced ATM Withdraw](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-withdraw.png)

Display a transaction status message, and restart the menu cycle. Check out the [Requirements]() section for details on status codes, messages, and the validity of transactions.

#### Transfer Money

If a user chooses option 4, prompt him or her to enter the destination account and the amount of money to be transferred to that account.

![Advanced ATM Transfer](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-transfer.png)

Display a transaction status message, and restart the menu cycle. Check out the [Requirements]() section for details on status codes, messages, and the validity of transactions.

#### Logout

If a user chooses option 5, logout and return to the login prompt cycle.

![Advanced ATM Logout](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-logout.png)

#### Invalid Option

If a user chooses something other then 1, 2, 3, 4, or 5, display an error message and restart the menu cycle.

![Advanced ATM Invalid Option](https://github.com/ap-java-ucvts/pset-6-skeleton/blob/master/images/atm-invalid-option.png)

## Requirements

Whenever we deal with user input, we need to determine what is acceptable and what is not.

### Fields

Let's walk through the fields defined in each class.

#### User

The `User` class defines two fields: `firstName` and `lastName`. They must meet the following requirements.

| Field       | Allow `null` | Min. Length | Max. Length |
| ----------- | ------------ | ----------- | ----------- |
| `firstName` | No           | 1           | 20          |
| `lastName`  | No           | 1           | 30          |

#### BankAccount

The `BankAccount` class defines four fields: `pin`, `accountNo`, `balance`, and `accountHolder`. The `accountHolder` is a `User` object, which we've already covered. The other three must meet the following requirements.

| Field       | Mininum   | Maximum         |
| ----------- | --------- | --------------- |
| `pin`       | 1000      | 9999            |
| `accountNo` | 100000001 | 999999999       |
| `balance`   | 0.00      | 999999999999.99 |

#### ATM

The `ATM` class defines three fields: `in`, `activeAccount`, and `bank`. These are all objects, so we'll be dealing with the validity of `null` value.


| Field           | Type          | Allow `null` |
| --------------- | ------------- | ------------ |
| `in`            | `Scanner`     | No           |
| `activeAccount` | `BankAccount` | Yes          |
| `bank`          | `Bank`        | No           |

### Transactions

There are three possible transactions that our ATM needs to handle: deposits, withdrawals, and transfers. Before carrying out these transactions, we need to verify that such a transaction is allowed.

#### Deposits

Deposit requests must meet the following requirements.
* the amount must be greater than $0.00
* the amount must not case the account balance to exceed the field maximum

If a deposit request fails to meet these requirements, your ATM must print an applicable status message.
* Deposit rejected. Amount must be greater than $0.00.
* Deposit rejected. Amount would cause balance to exceed $999,999,999,999.99.

#### Withdrawals

Withdrawal requests must meet the following requirements.
* the amount must be greater than $0.00
* the amount must be less than or equal to account balance

If a withdrawal request fails to meet these requirements, your ATM must print an applicable status message.
* Withdrawal rejected. Amount must be greater than $0.00.
* Withdrawal rejected. Insufficient funds.

#### Transfers

Transfer requests must meet the following requirements.
* the destination account must exist
* the amount must be greater than $0.00
* the amount must not cause the destination account balance to exceed the field maximum
* the amount must be less than or equal to account balance

If a transfer request fails to meet these requirements, your ATM must print an applicable status message.
* Transfer rejected. Destination account not found.
* Transfer rejected. Amount must be greater than $0.00.
* Transfer rejected. Amount would cause destination balance to exceed $999,999,999,999.99.
* Transfer rejected. Insufficient funds.

### Data Persistence

Account changes (i.e., creating new accounts, or depositing, withdrawing, or transfering funds) must persist between:
* separate logins
* application shutdowns and startups

The file reading and writing to achieve this is already done for you in the `Bank` class. You just need to connect my code to yours to make it work together. **Do not change** the code provided for you in the `Bank.java` file.

## Deadline

Please read very carefully. Historically, most students lose points on problem sets for simply failing to read the instructions and requirements.

* November 24, 2019, at 11:59pm.

If you submit your problem set at midnight (i.e., November 25, 2019, at 12:00am), it is considered **late**!

### Submission Requirements

* Your code **must** compile. Code that fails to meet this minimum requirement will not be accepted.
* There must be **at least** 20 unique commits to your repository.
* Your code must meet each requirement outlined in the *Use Cases* and *Requirements* sections.
* Your code must adhere to the course style guidelines.

Happy coding!
