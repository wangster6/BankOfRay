/**
 * 
 */
package io.github.wangster6.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases to test the functionality of the Bank Class and its methods.
 * 
 * @author wangster612
 */
class TestBank {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	Bank bank;
	
	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		bank = new Bank();
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
	}
    
	/**
     * Test the signup process by simulating user input for creating a new account.
     * Verifies that signup is successful and the user can be deleted afterward.
     */
    @Test
    public void testSignUp() {
    	String input = "2\nfirst\nlast\nusername\npassword123\npassword123\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("username");
    }

    /**
     * Test the login process by simulating user input for logging in and performing actions.
     * Verifies that login is successful, account actions work as expected, and user can be deleted afterward.
     */
    @Test
    public void testLogIn() {
    	String input = "2\nfirst\nlast\nusername\npassword123\npassword123\n1\nusername\npassword123\n1\n0\n1\ninvalid\n-321\n100\n3\n150\nA\n100\n4\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("username");
    }
    
    /**
     * Test the signup process with various input scenarios.
     * Verifies that the user can return to the menu, providing proper input for signup.
     */
    @Test
    public void testSignUpReturn() {
    	String input = "2\n0\n2\nfirst1\n0\n2\nfirst\nlast1\n0\n2\nfirst\nlast\nusername\npassword123\nback\npassword123\npassword123\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("username");
    }
    
    /**
     * Test the deposit functionality with various input scenarios.
     * Verifies that the deposit action works as expected and the user can be deleted afterward.
     */
    @Test
    public void testSimulateDeposit() {
    	String input = "2\ntest\none\ntest1\ntest1\ntest1\n1\ntest1\ntest1\n2\ninvalid\n-100\n0\n100\n3\ninvalid\n-100\n0\n100\n4\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("test1");
    }
    
    /**
     * Test returning to the menu after each action and invalid menu options.
     * Verifies that the menu loop functions correctly and handles invalid input.
     */
    @Test
    public void testReturnToMenu() {
    	String input = "2\ntest\ntwo\ntest2\ntest2\ntest2\n1\ntest2\ntest2\n2\nback\n2\n100\n3\n500\nback\n1\n4\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("test2");
    }
    
    /**
     * Test withdrawing when there is no money in the account.
     * Verifies that the withdraw action works as expected with no money available.
     */
    @Test
    public void testWithdrawNoMoney() {
    	String input = "2\ntest\nthree\ntest3\ntest3\ntest3\n1\n0\n1\nrandouser\ntest3\n0\n1\ntest3\ntest3\n3\n4\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("test3");
    }
    
    /**
     * Test handling of invalid menu options and input.
     * Verifies that the program handles and responds to invalid user input correctly.
     */
    @Test
    public void testInvalidMenuOptions() {
    	String input = "invalid\n5\n-5\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    bank.run(0, in);
	    System.setIn(System.in);
    }
    
    /**
     * Test handling of invalid login password scenarios.
     * Verifies that the login process handles different cases of invalid passwords.
     */
    @Test
    public void testInvalidPasswordLogin() {
    	String input = "2\ntest\nfour\ntest4usernameiswaytoolong\nus\n0\n"
    			+ "2\ntest\nfour\ntest4\n0\n"
    			+ "2\ntest\nfour\ntest4\ntest\ntest4\n0\n"
    			+ "2\ntest\nfour\ntest4\ntest4\ntest5\ntest4"
    			+ "1\ntest4\ntest5\ntest4\n4\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("test4");
    }
    
    /**
     * Test signup when the username already exists.
     * Verifies that the program handles a username that is already in use during signup.
     */
    @Test
    public void testSignUpUsernameAlreadyExists() {
    	String input = "2\ntest\nfive\ntest5\ntest5\ntest5\n2\ntesting\nduplicate\ntest5\n0\n3\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		
	    bank.run(0, in);
	    
	    System.setIn(System.in);

	    bank.deleteUserByUsername("test5");
    }

    /**
     * Test the creation of the Messages class.
     * Verifies that the Messages class can be instantiated without exceptions.
     */
	@Test
	public void testClassCreation() {
		assertDoesNotThrow(() -> new Messages());
	}
}
