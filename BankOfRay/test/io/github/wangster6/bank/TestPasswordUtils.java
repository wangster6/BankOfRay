/**
 * 
 */
package io.github.wangster6.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases to test the functionality of the PasswordUtils Class and its methods.
 * 
 * @author wangster6
 */
class TestPasswordUtils {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for {@link io.github.wangster6.bank.PasswordUtils#containsOnlyLetters(java.lang.String)}.
	 */
	@Test
	void testContainsOnlyLetters() {
		assertTrue(PasswordUtils.containsOnlyLetters("TEST"));
		assertTrue(PasswordUtils.containsOnlyLetters("test"));
		assertTrue(PasswordUtils.containsOnlyLetters("TesT"));
		assertFalse(PasswordUtils.containsOnlyLetters("TEST123"));
		assertFalse(PasswordUtils.containsOnlyLetters("test123"));
		assertFalse(PasswordUtils.containsOnlyLetters("TesT123"));
		assertFalse(PasswordUtils.containsOnlyLetters("test 123"));
		assertFalse(PasswordUtils.containsOnlyLetters("test!"));
	}

	/**
	 * Test method for {@link io.github.wangster6.bank.PasswordUtils#isValidPassword(java.lang.String)}.
	 */
	@Test
	void testIsValidPassword() {
		assertTrue(PasswordUtils.isValidPassword("test123"));
		assertTrue(PasswordUtils.isValidPassword("TEST1"));
		assertTrue(PasswordUtils.isValidPassword("testING123456!"));
		assertFalse(PasswordUtils.isValidPassword("t1"));
		assertFalse(PasswordUtils.isValidPassword("testingapasswordthatistoolong123"));
		assertFalse(PasswordUtils.isValidPassword("testingnonumber"));
		assertFalse(PasswordUtils.isValidPassword("123456"));
	}

}
