# Bank Of Ray
This is a simple Java project that simulates a bank ATM system. Users can create accounts, log in, perform various banking actions like depositing and withdrawing money, and manage their account balances. The project uses Java, JDBC for database connectivity, MySQL for database storage and data management, JBCrypt for hashing, and JUnit 5 for testing.
<br>
<br>
## Features
- **User Signup and Login:** Users can create new accounts by providing their first name, last name, username, and password. Users can then log in using their credentials.
- **Account Actions:** Upon logging in, users can check their balance, deposit money, or withdraw money.
- **Database Integration:** User information and account balances are stored in a MySQL database using JDBC.
- **Input Validation:** The program includes input validation to ensure that users provide valid data during signup and login.
- **Enhanced Security:** This program employs JBCrypt hashing for all passwords, fortifying the protection of sensitive information.
- **JUnit Testing:** The program includes JUnit test cases to validate the functionality of the classes and their methods.
<br>

## Prerequisites
- Java Development Kit (JDK)
- JUnit 5
- MySQL Server (https://dev.mysql.com/downloads/installer/)
- MySQL Connector/J (https://dev.mysql.com/downloads/connector/j/)
- JBCrypt (https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4)
<br>

## Getting Started
1. Clone this repository to your local machine.
2. Set up your MySQL database and configure the connection details in the app.properties file and the DatabaseConnection class.
3. Compile the project using a Java compiler.
4. Run the project by executing the main class, Bank.
5. Follow the on-screen prompts to create accounts, log in, and perform account actions.
<br>

## Contributing
Contributions to this project are welcome! Feel free to submit issues or pull requests.
<br>
<br>
## Contact
For questions or feedback, please contact me on [my profile](https://github.com/wangster6).
