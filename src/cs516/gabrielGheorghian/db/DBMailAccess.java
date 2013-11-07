package cs516.gabrielGheorghian.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import cs516.gabrielGheorghian.JEEPapp.JEEPapp;
import cs516.gabrielGheorghian.data.Contact;
import cs516.gabrielGheorghian.data.Folder;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.data.MailConfig;
import cs516.gabrielGheorghian.mail.MailProperties;

public class DBMailAccess {

	// variables
	private Logger logger  = Logger.getLogger(getClass().getName());
	static {
		final InputStream inputStream = JEEPapp.class
				.getResourceAsStream("/logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(inputStream);

		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe(
					"Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	private Connection connection = null;
	private MailConfig mailConfig = null;


	/**
	 * Close the connection
	 */
	private void closeDB() {
		// If an error occurs when closing the connection just ignore it
		try {
			connection.close();
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					"Unable to close the connection\nto the database.",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"Unable to close the connection to the database.", sqlex);
		}
	}

	/**
	 * Connect to the database
	 * 
	 */
	private void connectToDB() {
		try {
			mailConfig = new MailProperties().loadProperties();

			String url = mailConfig.getUrlMySql();
			String user = mailConfig.getUserMySql();
			String password = mailConfig.getPassMySql();
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception"), "JDBC Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.SEVERE, "Unable to connect to the database.",
					sqlex);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("exception"), "Exception",
					JOptionPane.ERROR_MESSAGE);
			logger.log(Level.SEVERE, "Unexpected error.", e);
			System.exit(1);
		}
	}

	// --------------- methods for Mail----------------//
	/**
	 * Delete mail using ID in the Where clause
	 * 
	 * @param mail
	 * @return success or failure
	 */
	public boolean deleteMail(Mail mail) {
		boolean retVal = true;
		PreparedStatement statement = null;
		if (mail != null && mail.getId() != -1) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection
						.prepareStatement("DELETE FROM mail WHERE ID = "
								+ mail.getId());

				int records = statement.executeUpdate();
				// Must be 1 because ID is unique
				if (records != 1)
					retVal = false;
			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						JEEPapp.bundle.getString("sqlexception2"),
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Problem updating a record.", sqlex);
				retVal = false;
			} finally {
				closeDB();
			}
		}
		return retVal;
	}

	/**
	 * Get a list of all records
	 * 
	 * @return the list of records
	 */
	public ArrayList<Mail> getAllMail() {

		ArrayList<Mail> mail = new ArrayList<Mail>();
		Statement statement = null;
		ResultSet resultSet = null;

		connectToDB();
		try {
			// Send the SQL statement
			statement = connection.createStatement();
			resultSet = statement.executeQuery("Select * from mail");

			while (resultSet.next()) {
				mail.add(new Mail(resultSet.getInt(1), resultSet.getString(2),
						stringToArray(resultSet.getString(3)),
						stringToArray(resultSet.getString(4)),
						stringToArray(resultSet.getString(5)), resultSet
								.getString(6), resultSet.getString(7),
						resultSet.getDate(8), resultSet.getInt(9)));
			}
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception3"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records.", sqlex);
		} finally {
			closeDB();
		}
		return mail;
	}

	/**
	 * Get a list of records that have the specified last name
	 * 
	 * @param email
	 * @return the list of records
	 */
	public ArrayList<Mail> getMailByEmail(String email) {
		ArrayList<Mail> mail = new ArrayList<Mail>();
		Statement statement = null;
		ResultSet resultSet = null;
		if (email != null && email.length() > 0) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection.createStatement();
				resultSet = statement
						.executeQuery("Select * from mail where Sender = '"
								+ email + "'");

				while (resultSet.next()) {
					mail.add(new Mail(resultSet.getInt(1), resultSet
							.getString(2),
							stringToArray(resultSet.getString(3)),
							stringToArray(resultSet.getString(4)),
							stringToArray(resultSet.getString(5)), resultSet
									.getString(6), resultSet.getString(7),
							resultSet.getDate(8), resultSet.getInt(9)));
				}
			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						JEEPapp.bundle.getString("sqlexception3"),
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Problem retrieving records.", sqlex);
			} finally {
				closeDB();
			}
		}
		return mail;
	}

	/**
	 * Get a list of records that have the specified folder
	 * 
	 * @param folder
	 * @return the list of records
	 */
	public ArrayList<Mail> getMailByFolder(int folder) {
		ArrayList<Mail> mail = new ArrayList<Mail>();
		Statement statement = null;
		ResultSet resultSet = null;
		if (folder > 0) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection.createStatement();
				resultSet = statement
						.executeQuery("Select * from mail where FolderId = '"
								+ folder + "'");

				while (resultSet.next()) {
					mail.add(new Mail(resultSet.getInt(1), resultSet
							.getString(2),
							stringToArray(resultSet.getString(3)),
							stringToArray(resultSet.getString(4)),
							stringToArray(resultSet.getString(5)), resultSet
									.getString(6), resultSet.getString(7),
							resultSet.getDate(8), resultSet.getInt(9)));
				}
			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						JEEPapp.bundle.getString("sqlexception3"),
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Problem retrieving records.", sqlex);
			} finally {
				closeDB();
			}
		}
		return mail;
	}

	/**
	 * Saves mail (insert/update)
	 * 
	 * @param mail
	 * @return success or failure
	 */
	public boolean saveMail(Mail mail) {
		boolean retVal = false;
		if (mail != null) {
			if (mail.getId() == -1) {
				retVal = insertMail(mail);
			} else {
				retVal = updateMail(mail);
			}
		}
		return retVal;
	}

	/**
	 * Turns array into strings seperated by ,
	 * 
	 * @param data
	 * @return
	 */
	private String arrayToString(ArrayList<String> data) {
		String x = "";
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				if (i == data.size() - 1)
					x += data.get(i);
				else
					x += data.get(i) + ";";
			}
		}
		return x;
	}

	/**
	 * Insert a new record
	 * 
	 * @param mail
	 * @return success or failure
	 */
	private boolean insertMail(Mail mail) {
		boolean retVal = true;
		PreparedStatement statement = null;
		connectToDB();
		try {
			// Send the SQL statement
			statement = connection
					.prepareStatement("INSERT INTO mail (Sender,ToRecipients, CCRecipients, BccRecipients, Subject, Content, MessageDate, FolderID) "
							+ "VALUES ('"
							+ mail.getSender()
							+ "','"
							+ arrayToString(mail.getToRecipients())
							+ "','"
							+ arrayToString(mail.getCcRecipients())
							+ "','"
							+ arrayToString(mail.getBccRecipients())
							+ "','"
							+ mail.getSubject()
							+ "','"
							+ mail.getContent()
							+ "','"
							+ mail.getMessageDate()
							+ "','"
							+ mail.getFolder() + "')");

			int records = statement.executeUpdate();
			// Must be 1 because ID is unique
			if (records != 1)
				retVal = false;
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception4"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem inserting a record.", sqlex);
			retVal = false;
		} finally {
			closeDB();
		}
		return retVal;
	}

	/**
	 * Turns string with , token into an arraylist
	 * 
	 * @param thing
	 * @return array list of strings
	 */
	private ArrayList<String> stringToArray(String thing) {
		ArrayList<String> newList = new ArrayList<String>();
		String[] data = thing.split(";");

		for (String person : data) {
			newList.add(person);
		}
		return newList;
	}

	/**
	 * Update an existing mail using ID in the Where clause
	 * 
	 * @param mail
	 * @return success or failure
	 */
	private boolean updateMail(Mail mail) {
		boolean retVal = true;
		PreparedStatement statement = null;
		connectToDB();
		try {
			// Send the SQL statement

			statement = connection
					.prepareStatement("UPDATE mail SET Sender = '"
							+ mail.getSender() + "', toRecipients = '"
							+ arrayToString(mail.getToRecipients())
							+ "', CCRecipients = '"
							+ arrayToString(mail.getCcRecipients())
							+ "', BccRecipients = '"
							+ arrayToString(mail.getBccRecipients())
							+ "', Subject = '" + mail.getSubject()
							+ "', Content = '" + mail.getContent()
							+ "', MessageDate = '" + mail.getMessageDate()
							+ "', FolderID = " + mail.getFolder()
							+ " WHERE ID = " + mail.getId());

			int records = statement.executeUpdate();
			// Must be 1 because ID is unique
			if (records != 1)
				retVal = false;
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception2"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem updating a record.", sqlex);
			retVal = false;
		} finally {
			closeDB();
		}
		return retVal;
	}

	// ----------------END methods for Mail ------------//

	// -------------------- methods for Folder--------//
	/**
	 * Returns all the folders in the database
	 * 
	 * @return list of folders
	 */
	public ArrayList<Folder> getAllFolders() {
		ArrayList<Folder> folder = new ArrayList<Folder>();
		Statement statement = null;
		ResultSet resultSet = null;

		connectToDB();
		try {
			// Send the SQL statement
			statement = connection.createStatement();
			resultSet = statement.executeQuery("Select * from folder");

			while (resultSet.next()) {
				folder.add(new Folder(resultSet.getInt(1), resultSet
						.getString(2)));
			}
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception3"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records.", sqlex);
		} finally {
			closeDB();
		}
		return folder;
	}
	
	/**
	 * Get the  that have the specified folder
	 * 
	 * @param folderName
	 * @return the list of records
	 */
	public ArrayList<Folder> getFolderByName(String name) {
		ArrayList<Folder> folder = new ArrayList<Folder>();
		Statement statement = null;
		ResultSet resultSet = null;
		if (name != null && !name.equals("")) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection.createStatement();
				resultSet = statement
						.executeQuery("Select * from Folder where Name = '"
								+ name + "'");

				while (resultSet.next()) {
					folder.add(new Folder(resultSet.getInt(1), resultSet
							.getString(2)));
				}
			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						JEEPapp.bundle.getString("sqlexception3"),
						"JDBC Exception", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING, "Problem retrieving records.", sqlex);
			} finally {
				closeDB();
			}
		}
		return folder;
	}

	/**
	 * Deletes folder in the database
	 * 
	 * @param folder
	 * @return boolean if it was successful
	 */
	public boolean deleteFolder(Folder folder) {
		boolean retVal = true;
		PreparedStatement statement = null;
		if (folder != null && folder.getId() != -1 && folder.getId() != 1
				&& folder.getId() != 2 && folder.getId() != 3) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection
						.prepareStatement("DELETE FROM folder WHERE ID = "
								+ folder.getId());

				int records = statement.executeUpdate();
				// Must be 1 because ID is unique
				if (records != 1)
					retVal = false;
			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						"Problem updating a record.", "JDBC Exception",
						JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING,
						JEEPapp.bundle.getString("sqlexception2"), sqlex);
				retVal = false;
			} finally {
				closeDB();
			}
		}
		return retVal;
	}

	/**
	 * Saves the Folder (insert)
	 * 
	 * @param folder
	 * @return boolean if it was successful
	 */
	public boolean saveFolder(Folder folder) {
		boolean retVal = false;
		if (folder != null) {
			if (folder.getId() == -1) {
				retVal = insertFolder(folder);
			}
		}
		return retVal;
	}

	/**
	 * Inserts new folder into the database
	 * 
	 * @param folder
	 * @return boolean if it was successful
	 */
	private boolean insertFolder(Folder folder) {
		boolean retVal = true;
		PreparedStatement statement = null;
		connectToDB();
		try {
			// Send the SQL statement
			statement = connection
					.prepareStatement("INSERT INTO folder (Name) "
							+ "VALUES ('" + folder.getName() + "')");

			int records = statement.executeUpdate();
			// Must be 1 because ID is unique
			if (records != 1)
				retVal = false;
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null, "Problem inserting a record.",
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					JEEPapp.bundle.getString("sqlexception4"), sqlex);
			retVal = false;
		} finally {
			closeDB();
		}
		return retVal;
	}

	// ---------------------------END methods for Folder-------//

	// --------------------------- methods for Contact-------------//

	/**
	 * Deletes contact in the database
	 * 
	 * @param contact
	 * @return boolean if it was successful
	 */
	public boolean deleteContact(Contact contact) {
		boolean retVal = true;
		PreparedStatement statement = null;
		if (contact != null && contact.getId() != -1) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection
						.prepareStatement("DELETE FROM contact WHERE ID = "
								+ contact.getId());

				int records = statement.executeUpdate();
				// Must be 1 because ID is unique
				if (records != 1)
					retVal = false;
			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						"Problem updating a record.", "JDBC Exception",
						JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING,
						JEEPapp.bundle.getString("sqlexception2"), sqlex);
				retVal = false;
			} finally {
				closeDB();
			}
		}
		return retVal;
	}

	/**
	 * Gets all the contacts
	 * 
	 * @return list of contacts
	 */
	public ArrayList<Contact> getAllContacts() {
		ArrayList<Contact> contact = new ArrayList<Contact>();
		Statement statement = null;
		ResultSet resultSet = null;

		connectToDB();
		try {
			// Send the SQL statement
			statement = connection.createStatement();
			resultSet = statement.executeQuery("Select * from Contact");

			while (resultSet.next()) {
				contact.add(new Contact(resultSet.getInt(1), resultSet
						.getString(2), resultSet.getString(3), resultSet
						.getString(4)));
			}
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception3"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem retrieving records.", sqlex);
		} finally {
			closeDB();
		}
		return contact;
	}

	/**
	 * Returns a list of contacts given the email
	 * 
	 * @return list of contacts
	 */
	public ArrayList<Contact> getContactByEmail(String email) {
		ArrayList<Contact> contact = new ArrayList<Contact>();
		Statement statement = null;
		ResultSet resultSet = null;
		if (email != null && email.length() > 0) {
			connectToDB();
			try {
				// Send the SQL statement
				statement = connection.createStatement();
				resultSet = statement
						.executeQuery("SELECT * FROM contact WHERE Email = '"
								+ email + "'");

				while (resultSet.next()) {
					contact.add(new Contact(resultSet.getInt(1), resultSet
							.getString(2), resultSet.getString(3), resultSet
							.getString(4)));
				}

			} catch (SQLException sqlex) {
				JOptionPane.showMessageDialog(null,
						"Problem retrieving records.", "JDBC Exception",
						JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING,
						JEEPapp.bundle.getString("sqlexception3"), sqlex);
			} finally {
				closeDB();
			}
		}
		return contact;
	}

	/**
	 * Saves the contact (insert/update)
	 * 
	 * @param contact
	 * @return boolean if it was successful
	 */
	public boolean saveContact(Contact contact) {
		boolean retVal = false;
		if (contact != null) {
			if (contact.getId() == -1) {
				retVal = insertContact(contact);
			} else {
				retVal = updateContact(contact);
			}
		}
		return retVal;
	}

	/**
	 * Inserts new contact into the database
	 * 
	 * @param contact
	 * @return boolean if it was successful
	 */
	private boolean insertContact(Contact contact) {
		boolean retVal = true;
		PreparedStatement statement = null;
		connectToDB();
		try {
			// Send the SQL statement
			statement = connection
					.prepareStatement("INSERT INTO contact (Email, Name, PhoneNum) "
							+ "VALUES ('"
							+ contact.getEmail()
							+ "','"
							+ contact.getName()
							+ "','"
							+ contact.getPhoneNum()
							+ "')");

			int records = statement.executeUpdate();
			// Must be 1 because ID is unique
			if (records != 1)
				retVal = false;
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception4"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem inserting a record.", sqlex);
			retVal = false;
		} finally {
			closeDB();
		}
		return retVal;
	}

	/**
	 * Changes the contact information given the contact
	 * 
	 * @param mail
	 * @return boolean if it was successful
	 */
	private boolean updateContact(Contact contact) {
		boolean retVal = true;
		PreparedStatement statement = null;
		connectToDB();
		try {
			// Send the SQL statement

			statement = connection
					.prepareStatement("UPDATE contact SET Email = '"
							+ contact.getEmail() + "', Name = '"
							+ contact.getName() + "', PhoneNum = '"
							+ contact.getPhoneNum() + "' WHERE ID = "
							+ contact.getId());

			int records = statement.executeUpdate();
			// Must be 1 because ID is unique
			if (records != 1)
				retVal = false;
		} catch (SQLException sqlex) {
			JOptionPane.showMessageDialog(null,
					JEEPapp.bundle.getString("sqlexception2"),
					"JDBC Exception", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING, "Problem updating a record.", sqlex);
			retVal = false;
		} finally {
			closeDB();
		}
		return retVal;
	}
	// --------------END methods for Contact-------------------//
}
