package io.github.wangster6.bank;

import org.mindrot.jbcrypt.BCrypt;

import io.github.wangster6.database.DatabaseConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * The Bank class represents an ATM application where users can log in, sign up,
 * and perform various account-related actions.
 * 
 * @author wangster612
 */
public class Bank {
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private Scanner scanner;

	private static final int ERROR_NO_MONEY = -1;
	private static final int RETURN_TO_MENU = -2;

	/**
	 * Constructs a new Bank instance, initializing the scanner for user input.
	 */
	public Bank() {
		scanner = new Scanner(System.in);
	}

	/**
	 * Runs the ATM application based on the given mode and input stream. Can be run
	 * in testing mode or in regular mode.
	 *
	 * @param testing Indicates whether the application is in testing mode (0) or
	 *                not.
	 * @param input   The input stream to read user input from.
	 */
	public void run(int testing, InputStream input) {
		if (testing == 0) { // 0 stands for testing mode
			scanner = new Scanner(input);
		}
		System.out.print(Messages.WELCOME_TO_ATM);
		menuPrompt(input);
		scanner.close();
	}

	/**
	 * Displays the main menu to the user and prompts for their choice.
	 *
	 * @param input The input stream to read user input from.
	 */
	private void menuPrompt(InputStream input) {
		boolean menuLoop = true;
		while (menuLoop) {
			System.out.println(Messages.MENU_PROMPT);
			System.out.print("> ");
			int choice = getMenuChoice();

			switch (choice) {
			case 1:
				login(input);
				break;
			case 2:
				signup();
				break;
			case 3:
				System.out.println(Messages.EXIT_MESSAGE);
				menuLoop = false;
				break;
			default:
				System.out.println(Messages.MENU_PROMPT_INVALID_CHOICE);
			}
		}
	}

	/**
	 * Helper method to validate and get user input for menu choice.
	 * 
	 * @return the integer input given by user
	 */
	private int getMenuChoice() {
		int choice = -1;
		while (true) {
			try {
				choice = Integer.parseInt(scanner.nextLine());
				if (choice < 1 || choice > 3) {
					throw new NumberFormatException();
				}
				break;
			} catch (NumberFormatException e) {
				break;
			}
		}
		return choice;
	}

	/**
	 * Displays the action menu to the user and prompts for their choice.
	 *
	 * @param username the username of the user that is logged in. used for action
	 *                 purposes.
	 * @param input    the input stream to read user input from.
	 */
	private void actionPrompt(String username, InputStream input) {
		double balance = getAccountBalanceByUsername(username);

		while (true) {
			System.out.println(
					"\n\u001B[1;4mWelcome " + getFirstNameByUsername(username) + "!\u001B[0m" + Messages.ACTION_PROMPT);
			System.out.print("> ");

			int choice = getActionChoice();

			switch (choice) {
			case 1:
				System.out.println(Messages.YOUR_CURRENT_BALANCE);
				System.out.println("\u001B[32m$" + df.format(balance) + "\u001B[0m");
				break;
			case 2:
				balance = simulateDeposit(balance, input);
				if (balance == -2) {
					balance = getAccountBalanceByUsername(username);
					break;
				} else {
					setAccountBalanceByUsername(username, balance);
				}
				break;
			case 3:
				balance = simulateWithdrawal(balance, input);
				if (balance == -1 || balance == -2) {
					balance = getAccountBalanceByUsername(username);
					break;
				} else {
					setAccountBalanceByUsername(username, balance);
				}
				break;
			case 4:
				return;
			default:
				System.out.println(Messages.ACTION_PROMPT_INVALID_CHOICE);
			}
		}
	}

	/**
	 * Helper method to validate and get user input for action choice.
	 * 
	 * @return the integer input given by user
	 */
	private int getActionChoice() {
		int choice = -1;
		while (true) {
			try {
				choice = Integer.parseInt(scanner.nextLine());
				if (choice < 1 || choice > 4) {
					throw new NumberFormatException();
				}
				break;
			} catch (NumberFormatException e) {
				break;
			}
		}
		return choice;
	}

	/**
	 * Handles the login process for the ATM application.
	 *
	 * @param input The input stream to read user input from.
	 */
	private void login(InputStream input) {
		boolean loggedIn = false;
		boolean usernameExists = false;
		String username = null;
		String password = null;

		while (!usernameExists) {
			System.out.println(Messages.ENTER_USERNAME_LOGIN);
			System.out.print("> ");
			username = scanner.nextLine();
			if ("0".equals(username)) {
				return;
			}

			if (doesUsernameExists(username)) {
				usernameExists = true;
			} else {
				System.out.println(Messages.USERNAME_DNE);
			}
		}

		while (!loggedIn) {
			System.out.println(Messages.ENTER_PASSWORD_LOGIN);
			System.out.print("> ");
			password = scanner.nextLine();
			if ("0".equals(password)) {
				return;
			}

			if (authenticateUser(username, password)) {
				loggedIn = true;
				System.out.println(Messages.LOGIN_SUCCESS);
			} else {
				System.out.println(Messages.INVALID_PASSWORD);
			}
		}

		actionPrompt(username, input);
	}

	/**
	 * Handles the signup process for the ATM application.
	 */
	private void signup() {
		boolean firstCheck = false;
		boolean lastCheck = false;
		boolean usernameCheck = false;
		boolean passwordCheck = false;
		boolean confirmPasswordCheck = false;

		String firstName = null, lastName = null, username = null, password = null, confirmPassword = null;

		System.out.println(Messages.RETURN_TO_MENU);

		while (!firstCheck) {
			System.out.println(Messages.ENTER_FIRST_NAME);
			System.out.print("> ");
			firstName = scanner.nextLine();

			if ("0".equals(firstName)) {
				return;
			} else if (!PasswordUtils.containsOnlyLetters(firstName)) {
				System.out.println(Messages.INVALID_FIRST_NAME);
			} else {
				firstCheck = true;
				firstName = firstName.toUpperCase();
			}
		}

		while (!lastCheck) {
			System.out.println(Messages.ENTER_LAST_NAME);
			System.out.print("> ");
			lastName = scanner.nextLine();

			if ("0".equals(lastName)) {
				return;
			} else if (!PasswordUtils.containsOnlyLetters(lastName)) {
				System.out.println(Messages.INVALID_LAST_NAME);
			} else {
				lastCheck = true;
				lastName = lastName.toUpperCase();
			}
		}

		while (!usernameCheck) {
			System.out.println(Messages.ENTER_USERNAME_SIGNUP);
			System.out.print("> ");
			username = scanner.nextLine();

			if ("0".equals(username)) {
				return;
			} else if (doesUsernameExists(username)) {
				System.out.println(Messages.USERNAME_ALREADY_USED);
			} else if (username.length() < 3 || username.length() > 15) {
				System.out.println(Messages.INVALID_USERNAME);
			} else {
				usernameCheck = true;
			}
		}

		while (!passwordCheck) {
			System.out.println(Messages.ENTER_PASSWORD_SIGNUP);
			System.out.print("> ");
			password = scanner.nextLine();

			if ("0".equals(password)) {
				return;
			} else if (PasswordUtils.isValidPassword(password)) {
				while (!confirmPasswordCheck) {
					System.out.println(Messages.CONFIRM_PASSWORD);
					System.out.print("> ");
					confirmPassword = scanner.nextLine();

					if ("0".equals(confirmPassword)) {
						return;
					} else if ("back".equals(confirmPassword)) {
						break;
					}

					if (confirmPassword.equals(password)) {
						passwordCheck = true;
						confirmPasswordCheck = true;
					} else {
						System.out.println(Messages.PASSWORDS_DONT_MATCH);
					}
				}
			} else {
				System.out.println(Messages.INVALID_PASSWORD);
			}
		}

		// Hash the password using BCrypt
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		// Create a new user in the database
		boolean signupSuccessful = createUser(firstName, lastName, username, hashedPassword);
		if (signupSuccessful) {
			System.out.println(Messages.SIGNUP_SUCCESS);
		} else {
			System.out.println(Messages.SIGNUP_FAILURE);
		}
	}

	/**
	 * Simulates the entire deposit process. Calculates the new balance after
	 * depositing the inputted amount by user.
	 * 
	 * @param startBal the starting balance before the deposit
	 * @return the end balance after the deposit
	 */
	private double simulateDeposit(double startBal, InputStream input) {
		double endBal;
		double depositAmt = promptDepositInput(startBal, input); // Prompt user for input

		if (depositAmt == RETURN_TO_MENU) {
			return RETURN_TO_MENU;// Return a code indicating user wants to go back
		}

		endBal = startBal + depositAmt; // Calculate new balance after deposit
		Messages.depositSuccess(depositAmt, endBal); // Print success message

		return endBal; // Return the new balance after deposit
	}

	/**
	 * Helper method to handle prompting the user for a deposit amount input and
	 * validating the inputted values.
	 *
	 * @param startBal the starting balance before the deposit
	 * @return the deposit amount entered by the user, or a code indicating a return
	 *         to the menu
	 */
	private double promptDepositInput(double startBal, InputStream input) {
		double depositAmt = 0;
		boolean validInput = false;

		while (!validInput) {
			Messages.depositAmountPrompt(startBal); // Prompt user for deposit amount
			String depositString = scanner.nextLine();

			if ("back".equals(depositString)) {
				return RETURN_TO_MENU; // Return a code indicating user wants to go back
			}

			if (validateInput(depositString)) { // Validate the input
				depositAmt = Double.parseDouble(depositString);
				validInput = true; // Input is valid, exit the loop
			}
		}

		return depositAmt;
	}

	/**
	 * Simulates the entire withdrawal process. Calculates the new balance after
	 * withdrawing the inputted amount by user.
	 * 
	 * @param startBal the starting balance before the withdrawal
	 * @return the end balance after the withdrawal
	 */
	private double simulateWithdrawal(double startBal, InputStream input) {
		// Check if the starting balance is zero
		if (startBal == 0) {
			Messages.withdrawNoMoney(); // Print error message
			return ERROR_NO_MONEY; // Return a code indicating error
		}

		double endBal;
		double withdrawAmt = promptWithdrawalInput(startBal, input); // Prompt user for withdrawal amount

		if (withdrawAmt == RETURN_TO_MENU) {
			return RETURN_TO_MENU;// Return a code indicating user wants to go back
		}

		endBal = startBal - withdrawAmt; // Calculate new balance after withdrawal
		Messages.withdrawSuccess(withdrawAmt, endBal); // Print success message

		return endBal; // Return the new balance
	}

	/**
	 * Helper method to handle prompting the user for a withdrawal amount input and
	 * validating the inputted values.
	 * 
	 * @param startBal the starting balance before the withdrawal
	 * @return the withdrawal amount entered by the user, or a code indicating a
	 *         return to the menu
	 */
	private double promptWithdrawalInput(double startBal, InputStream input) {
		double withdrawAmt = 0;
		boolean validInput = false;

		while (!validInput) {
			Messages.withdrawAmountPrompt(startBal); // Prompt user for withdrawal amount
			String withdrawString = scanner.nextLine();

			if ("back".equals(withdrawString)) {
				return RETURN_TO_MENU; // Return a code indicating user wants to go back
			}

			if (validateInput(withdrawString)) { // Validate the input
				withdrawAmt = Double.parseDouble(withdrawString);
				if (withdrawAmt > startBal) {
					Messages.withdrawMoreThanBalance(); // Print error message indicating user is attempting to withdraw
														// more than their balance
				} else {
					validInput = true; // Input is valid, exit the while loop
				}
			}
		}

		return withdrawAmt;
	}

	/**
	 * Validate the inputted String by checking if it is a double value. If so, this
	 * method delegates to the validateInputPositive method to continue checking if
	 * the parsed double is greater than zero.
	 * 
	 * @param inputString the String input to validate.
	 * @return true if the input is valid, false if invalid.
	 */
	private boolean validateInput(String inputString) {
		// Check if input is a double. If not, catch NumberFormatException and print
		// error message
		try {
			double inputAmt = Double.parseDouble(inputString);
			// If input is a double, check if greater than zero
			return validateInputPositive(inputAmt);
		} catch (NumberFormatException e) {
			Messages.inputNotDouble();
			return false;
		}
	}

	/**
	 * Check if the input is greater than zero.
	 * 
	 * @param inputAmt the double input to validate.
	 * @return true of greater than zero, false if not.
	 */
	private boolean validateInputPositive(double inputAmt) {
		if (inputAmt <= 0) {
			Messages.inputAmountNotPositive(); // Print error message if inputted amount is not positive
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Helper method to authenticate the user using JDBC
	 * 
	 * @param username username to authenticate
	 * @param password password to authenticate
	 * @return true if user is successfully authenticated, false if not
	 */
	private boolean authenticateUser(String username, String password) {
		// Establish database connection
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DatabaseConnection.getConnection();
			// Prepare query to retrieve user information by username
			String selectQuery = "SELECT hashed_password FROM users WHERE username = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, username);

			// Execute query
			resultSet = preparedStatement.executeQuery();
			// Check if user exists
			if (resultSet.next()) {
				String hashedPassword = resultSet.getString("hashed_password");
				return BCrypt.checkpw(password, hashedPassword);
			}
			return false; // User not found
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return false; // Return false on error
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a new account with an initial balance of 0 and returns the generated
	 * account ID.
	 *
	 * @return The generated account ID if successful, or -1 if account creation
	 *         failed.
	 */
	private static int createAccount() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = DatabaseConnection.getConnection();
			// Prepare query to create a new account with initial balance of 0
			String insertQuery = "INSERT INTO accounts (balance) VALUES (0)";
			preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

			// Execute query
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				// Get auto-generated account ID
				generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}

			return -1; // Return -1 if the account creation failed
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return -1; // Return -1 on error
		} finally {
			// Close resources
			try {
				if (generatedKeys != null) {
					generatedKeys.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a new user with the provided information and associates them with an
	 * account.
	 *
	 * @param firstName      The user's first name.
	 * @param lastName       The user's last name.
	 * @param username       The user's username.
	 * @param hashedPassword The hashed password of the user.
	 * @return True if the user was successfully created, false otherwise.
	 */
	private boolean createUser(String firstName, String lastName, String username, String hashedPassword) {
		// Establish database connection
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DatabaseConnection.getConnection();
			// Create an account for user and get the account ID
			int accountId = createAccount();

			if (accountId == -1) {
				// Account creation failed
				return false;
			}

			// Prepare query to insert new user into the "users" table
			String insertQuery = "INSERT INTO users (first_name, last_name, username, hashed_password, account_id) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, hashedPassword);
			preparedStatement.setInt(5, accountId);

			// Execute query
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0; // Returns true if the user was successfully created
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			// Close resources
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Retrieves the first name of a user based on their username.
	 *
	 * @param username The username of the user.
	 * @return The first name of the user if found, or null if the user is not
	 *         found.
	 */
	private String getFirstNameByUsername(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseConnection.getConnection();
			// Prepare query to retrieve user's first name based on the username
			String selectQuery = "SELECT first_name FROM users WHERE username = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, username);

			// Execute query
			resultSet = preparedStatement.executeQuery();

			// Check if user with the provided username exists
			if (resultSet.next()) {
				return resultSet.getString("first_name");
			}
			return null; // User not found
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null; // Return null on error
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Retrieves the account balance of a user based on their username.
	 *
	 * @param username The username of the user.
	 * @return The account balance of the user if found, or -1 if the user or
	 *         account is not found.
	 */
	private double getAccountBalanceByUsername(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseConnection.getConnection();
			// Prepare query to retrieve account balance based on the username
			String selectQuery = "SELECT balance FROM accounts "
					+ "WHERE id = (SELECT account_id FROM users WHERE username = ?)";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, username);

			// Execute query
			resultSet = preparedStatement.executeQuery();

			// Check if user's account exists
			if (resultSet.next()) {
				return resultSet.getDouble("balance");
			}
			return -1; // Account not found
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return -1; // Return -1 on error
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Helper method to set the account balance based on the username
	 * 
	 * @param username associated with the account to alter
	 * @param newBal   the new balance to set account balance to
	 * @return true if the query was successful, false if error
	 */
	private boolean setAccountBalanceByUsername(String username, double newBal) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DatabaseConnection.getConnection();
			// Prepare query to update account balance based on the username
			String updateQuery = "UPDATE accounts " + "SET balance = ? "
					+ "WHERE id = (SELECT account_id FROM users WHERE username = ?)";
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setDouble(1, newBal);
			preparedStatement.setString(2, username);

			// Execute query
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0; // Returns true if the update was successful
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return false; // Return false on error
		} finally {
			// Close resources
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if a given username exists in the "users" table.
	 *
	 * @param username The username to be checked.
	 * @return True if the username exists, false otherwise.
	 */
	private boolean doesUsernameExists(String username) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DatabaseConnection.getConnection();
			// Prepare query to check if username exists in the "users" table
			String selectQuery = "SELECT COUNT(*) as count FROM users WHERE username = ?";
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setString(1, username);

			// Execute query
			resultSet = preparedStatement.executeQuery();

			// Check if username exists
			if (resultSet.next()) {
				int count = resultSet.getInt("count");
				return count > 0;
			}
			return false; // Return false if username not found
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return false; // Return false on error
		} finally {
			// Close resources
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Deletes a user and their associated account based on the provided username.
	 *
	 * @param username The username of the user to be deleted.
	 * @return True if the deletion was successful, false otherwise.
	 */
	boolean deleteUserByUsername(String username) {
		Connection connection = null;
		PreparedStatement deleteUserStatement = null;
		PreparedStatement deleteAccountStatement = null;

		try {
			connection = DatabaseConnection.getConnection();
			connection.setAutoCommit(false);

			// Prepare query to delete account by username
			String deleteAccountQuery = "DELETE FROM accounts WHERE id = (SELECT account_id FROM users WHERE username = ?)";
			deleteAccountStatement = connection.prepareStatement(deleteAccountQuery);
			deleteAccountStatement.setString(1, username);

			// Execute the query
			deleteAccountStatement.executeUpdate();
			connection.commit();

			// If account deletion successful, delete the user as well
			String deleteUserQuery = "DELETE FROM users WHERE username = ?";
			deleteUserStatement = connection.prepareStatement(deleteUserQuery);
			deleteUserStatement.setString(1, username);

			// Execute query
			deleteUserStatement.executeUpdate();

			connection.commit();
			return true;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (deleteAccountStatement != null) {
					deleteAccountStatement.close();
				}
				if (deleteUserStatement != null) {
					deleteUserStatement.close();
				}
				if (connection != null) {
					connection.setAutoCommit(true);
					DatabaseConnection.closeConnection(connection);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The main method for the ATM application that starts the application when run
	 * normally. This method is not used when testing.
	 *
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		Bank bank = new Bank();
		bank.run(1, System.in);
	}
}