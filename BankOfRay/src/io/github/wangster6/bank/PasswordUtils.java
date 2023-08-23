/**
 * 
 */
package io.github.wangster6.bank;

import java.util.regex.Pattern;

/**
 * @author raywa
 *
 */
public class PasswordUtils {
	/**
     * Checks if the inputted String consists of only letters.
     * 
     * @param input the inputted string to check
     * @return true if the input contains only letters, false if it does not
     */
	public static boolean containsOnlyLetters(String input) {
		return Pattern.matches("[a-zA-Z]+", input);
	}

	/**
	 * Checks if the inputted String meets all the requirements for a password.
	 * 
	 * @param input the inputted String to check
	 * @return true if the input is a valid password, false if it is not
	 */
	public static boolean isValidPassword(String input) {
		// Check if the input contains at least one letter
		if (!input.matches(".*[a-zA-Z].*")) {
			return false;
		}

		// Check if the input contains at least one number
		if (!input.matches(".*[0-9].*")) {
			return false;
		}

		// Check if the input is between 5 and 15 characters in length
		int length = input.length();
		return length >= 5 && length <= 15;
	}
}
