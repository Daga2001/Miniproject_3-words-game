/*
 * Programación Interactiva
 * Author: David Alberto Guzmán - 201942789
 * Mini-Project-3: Juego de Palabras.
 */
package JuegoDePalabras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class MySqlDbs. It works when you want to retrieve any data from a mysql database.
 */
public class Database {
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//attributes
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/** The statement. */
	private Statement statement;
	
	/** The connection. */
	private Connection connection;
	
	/** The result. */
	private ResultSet result;
	
	/** The rsmd. */
	private ResultSetMetaData rsmd;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//constructor
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Instantiates a new Database.
	 *
	 * @param url paste here your url where is stored the database with the following syntax: jdbc:mysql://url or localhost:port/database's name.
	 * @param username the username of the database.
	 * @param password the password established for database.
	 */
	public Database(String url, String username, String password) {
		try {
			//After Installing the driver to identify the mysql database i must connect with it and create a space for statements
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	};
	
	//----------------------------------------------------------------------------------------------------------------------------------
	//methods
	//----------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Gets the table value.
	 *
	 * @param table the table's name.
	 * @param column the column where is located the value.
	 * @param condition the condition WHERE to filter data.
	 * @return the table value.
	 */
	public String getTableValue(String table, String column, String condition) {
		String found = "not found!";
		
		try {
			String query = String.format("SELECT * FROM %s WHERE %s", table, condition);
			result = statement.executeQuery(query);
			while(result.next()) {
				String cell = result.getString(column);
				found = cell;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return found;
	};
	
	/**
	 * Checks if is in table.
	 *
	 * @param table the table's name.
	 * @param value the value to check.
	 * @param column the column where is located the value (optional, if you don't want to set this value, then write null).
	 * @return boolean.
	 */
	public boolean isInTable(String table, String value, String column) {
		boolean found = false;
		
		try {
			String query = String.format("SELECT * FROM %s", table);
			result = statement.executeQuery(query);
			if(column == null) {
				rsmd = result.getMetaData();
				while(result.next()) {
					for(int i = 1; i <= rsmd.getColumnCount(); i++) {
						String cell = result.getString(i);
						if(value.equals(cell)) {
							found = true;
							break;
						}
					}
				}
			}
			else {
				while(result.next()) {
					String cell = result.getString(column);
					if(value.equals(cell)) {
						found = true;
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return found;
	}
	
	/**
	 * Updates table.
	 *
	 * @param table the table's name.
	 * @param value the value to change, it should follow this syntax: column 1 = value 1, column 2 = value 2, ..., column n = value n.
	 * @param conditionFilter the condition to filter data.
	 */
	public void updateTable(String table, String value, String conditionFilter) {
		try {
			String query = String.format("UPDATE %s SET %s WHERE %s", table, value, conditionFilter);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
	}
	
	/**
	 * Inserts values in table.
	 *
	 * @param table the table's name.
	 * @param columns the columns where you'll set the values, it follow this syntax: column 1, column 2, ..., column n.
	 * @param values the values to insert, they must follow this syntax: (value 1, value2, ..., value n), (value 1, value2, ..., value n), ..., (value 1, value2, ..., value n).
	 */
	public void insertValuesInTable(String table, String columns, String values) {
		try {
			String query = String.format("INSERT INTO %s (%s) VALUES %s", table, columns, values);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	};
	
	/**
	 * Deletes values in table.
	 *
	 * @param table the table's name.
	 * @param conditionFilter the condition to filter data.
	 */
	public void deleteValuesInTable(String table, String conditionFilter) {
		try {
			String query = String.format("DELETE FROM %s WHERE %s", table, conditionFilter);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	};
	
	/**
	 * Resets table id.
	 *
	 * @param table the table's name.
	 */
	public void resetTableId(String table) {
		try {
			String query = String.format("ALTER TABLE %s AUTO_INCREMENT = 0", table);
			statement.executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Releases the connection with the database.
	 */
	public void killConnections() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The connection with database failed!", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
}
