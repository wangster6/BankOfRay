package io.github.wangster6.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
	/**
	 * The 
	 */
	private static final String PROPERTIES_FILE = "resources/app.properties";

	/**
     * Establishes a database connection using the configuration provided in the app.properties file.
     *
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     * @throws IOException  If an I/O error occurs while loading properties.
     */
	public static Connection getConnection() throws SQLException, IOException {
		Properties properties = loadProperties();
        String dbUrl = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");
        
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	}

	/**
     * Closes the given database connection.
     *
     * @param connection The Connection object to be closed.
     */
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * Loads properties from the app.properties file.
     *
     * @return A Properties object containing the loaded properties.
     * @throws IOException If an I/O error occurs while loading properties.
     */
	private static Properties loadProperties() throws IOException {
		Properties prop = new Properties();
		try(InputStream input = new FileInputStream(PROPERTIES_FILE)) {
			if (input != null) {
				prop.load(input);
            }
		} catch (IOException e) {
			throw new IOException("Could not load properties file: " + PROPERTIES_FILE);
		}
		return prop;
	}
}