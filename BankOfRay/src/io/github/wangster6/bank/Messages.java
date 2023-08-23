package io.github.wangster6.bank;

import java.text.DecimalFormat;

/**
 * The Messages class provides static methods for printing various messages
 * related to banking operations.
 */
public class Messages {
	/** DecimalFormat instance for formatting double values */
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public static final String DEPOSIT_AMOUNT_PROMPT = "\n\u001B[1;4mHow much money would you like to deposit?\u001B[0m"
			+ "\nIf you would like to return to the menu, type 'back'.";
	public static final String DEPOSIT_SUCCESS = "\n\u001B[1;4mYou have successfully deposited:\u001B[0m \u001B[32m$";
	public static final String NEW_BALANCE = "\nYour new balance is: \u001B[32m$";
	public static final String WITHDRAW_AMOUNT_PROMPT = "\n\u001B[1;4mHow much money would you like to withdraw?\u001B[0m"
			+ "\nIf you would like to return to the menu, type 'back'.";
	public static final String NO_MONEY = "\n\u001B[31mERROR: You do not have any money in your account. You cannot withdraw anything!\u001B[0m";
	public static final String MORE_THAN_BALANCE = "\n\u001B[31mERROR: You cannot withdraw more money than you have in your balance!\u001B[0m";
	public static final String WITHDRAWAL_SUCCESS = "\n\u001B[1;4mYou have successfully withdrawn:\u001B[0m \u001B[32m$";
	public static final String AMOUNT_NOT_POSITIVE = "\n\u001B[31mERROR: You must enter a positive amount!\u001B[0m";
	public static final String NOT_DOUBLE = "\n\u001B[31mERROR: You must enter a number! Please make sure you are not typing any letters or special characters.\u001B[0m";
	public static final String ENTER_USERNAME_SIGNUP = "\n\u001B[1;4mEnter your username:\u001B[0m"
			+ "\nYour username must be within 3 - 15 characters. Please note"
			+ "\nthat usernames are case sensitive!";
	public static final String USERNAME_ALREADY_USED = "\n\u001B[31mERROR: This username is already being used! If this is you,"
			+ "\nplease type 0 to return to menu and choose the Login option. If this"
			+ "\nis not you, please think of a more unique username to use!\u001B[0m";
	public static final String INVALID_USERNAME = "\n\u001B[31mERROR: Invalid username!\u001B[0m";
	public static final String WELCOME_TO_ATM = "\u001B[1;4mWelcome to the ATM!\u001B[0m\n";
	public static final String MENU_PROMPT = "\n\u001B[1;4mPlease choose an option:\u001B[0m"
			+ "\n1) Login"
			+ "\n2) Sign Up"
			+ "\n3) Exit";
	public static final String EXIT_MESSAGE = "\n\u001B[1;4mThank you for banking with us here at Bank Of Ray!\u001B[0m"
			+ "\nSee you next time!";
	public static final String MENU_PROMPT_INVALID_CHOICE = "\n\u001B[31mERROR: Invalid input. Please enter 1, 2, or 3.\u001B[0m";
	public static final String ACTION_PROMPT = "\nWhat would you like to do?"
			+ "\n1) Check Balance" 
			+ "\n2) Deposit Money"
			+ "\n3) Withdraw Money" 
			+ "\n4) Exit";
	public static final String YOUR_CURRENT_BALANCE = "\n\u001B[1;4mYour Current Balance Is:\u001B[0m";
	public static final String ACTION_PROMPT_INVALID_CHOICE = "\n\u001B[31mERROR: Invalid input. Please enter 1, 2, 3, or 4.\u001B[0m";
	public static final String ENTER_USERNAME_LOGIN = "\n\u001B[1;4mEnter your username:\u001B[0m"
			+ "\nIf you would like to return to the menu, please type 0.";
	public static final String USERNAME_DNE = "\n\u001B[31mERROR: That username does not exist!\u001B[0m";
	public static final String ENTER_PASSWORD_LOGIN = "\n\u001B[1;4mEnter your password:\u001B[0m"
			+ "\nIf you would like to return to the menu, please type 0.";
	public static final String LOGIN_SUCCESS = "\n\u001B[32mLogin successful!\u001B[0m";
	public static final String INVALID_PASSWORD = "\n\u001B[31mERROR: Invalid password!\u001B[0m";
	public static final String RETURN_TO_MENU = "\n\u001B[1;4mNOTICE: If at any point in this process you would like to\u001B[0m"
			+ "\n\u001B[1;4mreturn to the menu, please type 0.\u001B[0m";
	public static final String ENTER_FIRST_NAME = "\n\u001B[1;4mEnter your first name:\u001B[0m"
			+ "\nYour first name can only contain letters. Do not"
			+ "\nuse any numbers or special characters.";
	public static final String INVALID_FIRST_NAME = "\n\u001B[31mERROR: Invalid first name!\u001B[0m";
	public static final String ENTER_LAST_NAME = "\n\u001B[1;4mEnter your last name:\u001B[0m"
			+ "\nYour last name can only contain letters. Do not" 
			+ "\nuse any numbers or special characters.";
	public static final String INVALID_LAST_NAME = "\n\u001B[31mERROR: Invalid last name!\u001B[0m";
	public static final String ENTER_PASSWORD_SIGNUP = "\n\u001B[1;4mEnter your password:\u001B[0m"
			+ "\nIt must be between 5 - 15 characters, and contain at least one letter and one number.";
	public static final String CONFIRM_PASSWORD = "\n\u001B[1;4mConfirm your password:\u001B[0m"
			+ "\nIt must match the password you previously chose!"
			+ "\nIf you suspect you made a mistake, type 'back' to"
			+ "\ngo back and rechoose a password";
	public static final String PASSWORDS_DONT_MATCH = "\n\u001B[31mERROR: Your passwords do not match! Try again!\u001B[0m";
	public static final String SIGNUP_SUCCESS = "\n\u001B[32mSignup successful! You can now log in.\u001B[0m";
	public static final String SIGNUP_FAILURE = "\n\u001B[31mERROR: Signup failed. Please try again.\u001B[0m";
	
	/**
	 * Prints a prompt for depositing money.
	 *
	 * @param startBal The starting balance in the account.
	 */
	public static void depositAmountPrompt(double startBal) {
		System.out.println(DEPOSIT_AMOUNT_PROMPT);
		System.out.print("> $");
	}

	/**
	 * Prints a success message after a successful deposit.
	 *
	 * @param depositAmt The amount deposited.
	 * @param endBal     The new balance after the deposit.
	 */
	public static void depositSuccess(double depositAmt, double endBal) {
		System.out.println(DEPOSIT_SUCCESS + df.format(depositAmt) + "\u001B[0m" + NEW_BALANCE + df.format(endBal) + "\u001B[0m");
	}

	/**
	 * Prints a prompt for withdrawing money.
	 *
	 * @param startBal The starting balance in the account.
	 */
	public static void withdrawAmountPrompt(double startBal) {
		System.out.println(WITHDRAW_AMOUNT_PROMPT);
		System.out.print("> $");
	}

	/**
	 * Prints an error message when attempting to withdraw with no money in the
	 * account.
	 */
	public static void withdrawNoMoney() {
		System.out.println(NO_MONEY);
	}

	/**
	 * Prints an error message when attempting to withdraw more than the available
	 * balance.
	 */
	public static void withdrawMoreThanBalance() {
		System.out.println(MORE_THAN_BALANCE);
	}

	/**
	 * Prints a success message after a successful withdrawal.
	 *
	 * @param withdrawAmt The amount withdrawn.
	 * @param endBal      The new balance after the withdrawal.
	 */
	public static void withdrawSuccess(double withdrawAmt, double endBal) {
		System.out.println(WITHDRAWAL_SUCCESS + df.format(withdrawAmt) + "\u001B[0m" + NEW_BALANCE + df.format(endBal)
				+ "\u001B[0m");
	}

	/**
	 * Prints an error message when the user enters a non-positive amount.
	 */
	public static void inputAmountNotPositive() {
		System.out.println(AMOUNT_NOT_POSITIVE);
	}

	/**
	 * Prints an error message when the user enters a non-double input.
	 */
	public static void inputNotDouble() {
		System.out.println(NOT_DOUBLE);
	}
}
