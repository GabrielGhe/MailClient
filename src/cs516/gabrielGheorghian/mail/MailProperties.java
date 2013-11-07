package cs516.gabrielGheorghian.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import cs516.gabrielGheorghian.JEEPapp.JEEPapp;
import cs516.gabrielGheorghian.data.MailConfig;

public class MailProperties {
	private String propFileName;
	private Properties prop = null;

	/**
	 * Constructor
	 */
	public MailProperties() {
		super();
		// SMTP properties
		this.propFileName = "MailConfig.properties";

		// Gmail properties
		// this.propFileName = "GMailConfig.properties";

		prop = new Properties();
	}

	/**
	 * Load the properties into the MailConfig object
	 * 
	 * @return if successful or not
	 */
	public MailConfig loadProperties() {

		MailConfig mailConfig = new MailConfig();
		FileInputStream propFileStream = null;
		File propFile = new File(propFileName);

		// File must exist
		if (propFile.exists()) {
			try {
				propFileStream = new FileInputStream(propFile);
				prop.load(propFileStream);
				propFileStream.close();

				// Store the properties in a mailConfigData object
				mailConfig.setUserName(prop.getProperty("userName"));
				mailConfig.setUserEmail(prop
						.getProperty("userEmailAddress"));
				mailConfig.setUrlPop3(prop
						.getProperty("urlPOP3server"));
				mailConfig.setUrlSmtp(prop
						.getProperty("urlSMTPserver"));
				mailConfig.setUserNamePop3(prop
						.getProperty("loginPOP3server"));
				mailConfig.setUserPassPop3(prop
						.getProperty("passwordPOP3server"));
				mailConfig.setPortPop3(Integer.parseInt(prop
						.getProperty("portNumberPOP3")));
				mailConfig.setPortSmtp(Integer.parseInt(prop
						.getProperty("portNumberSMTP")));
				mailConfig.setUserNamePop3(prop
						.getProperty("loginPOP3server"));
				mailConfig.setUrlMySql((prop
						.getProperty("urlMySQL")));
				mailConfig.setUrlMySql((prop
						.getProperty("urlMySQL")));
				mailConfig.setUserMySql((prop
						.getProperty("userMySQL")));
				mailConfig.setPassMySql((prop
						.getProperty("passwordMySQL")));
				

				// parseBoolean returns false if the string is not "true"
				// so no need for an exception handler
				mailConfig.setIsGmail(Boolean.parseBoolean(prop
						.getProperty("isGmail")));

				mailConfig.setSMTPAuth(Boolean.parseBoolean(prop
						.getProperty("isSMTPAuth")));

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"The properties file has not been found.",
						"Missing Properties File", JOptionPane.ERROR_MESSAGE);
				mailConfig = null;
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane
						.showMessageDialog(
								null,
								"There was an error reading the Properties file.",
								"Properties File Read Error",
								JOptionPane.ERROR_MESSAGE);
				mailConfig = null;
				e.printStackTrace();
			}
		} else
			mailConfig = null;

		return mailConfig;
	}

	/**
	 * Write the contents of the MailConfig object to the properties file
	 * 
	 * @param mailConfig
	 * @return success or failure
	 */
	public boolean writeProperties(MailConfig mailConfig) {
		boolean retVal = true;

		prop.setProperty("userName", mailConfig.getUserName());
		prop.setProperty("userEmailAddress",
				mailConfig.getUserEmail());
		prop.setProperty("urlPOP3server", mailConfig.getUrlPop3());
		prop.setProperty("urlSMTPserver", mailConfig.getUrlSmtp());
		prop.setProperty("loginPOP3server", mailConfig.getUserNamePop3());
		prop.setProperty("passwordPOP3server",
				mailConfig.getUserPassPop3());
		prop.setProperty("portNumberPOP3",
				"" + mailConfig.getPortPop3());
		prop.setProperty("portNumberSMTP",
				"" + mailConfig.getPortSmtp());
		prop.setProperty("isGmail",
				Boolean.toString(mailConfig.isGmail()));
		prop.setProperty("isSMTPAuth",
				Boolean.toString(mailConfig.isSMTPAuth()));
		
		prop.setProperty("urlMySQL", mailConfig.getUrlMySql());
		prop.setProperty("userMySQL", mailConfig.getUserMySql());
		prop.setProperty("passwordMySQL", mailConfig.getPassMySql());		

		FileOutputStream propFileStream = null;
		File propFile = new File(propFileName);
		try {
			propFileStream = new FileOutputStream(propFile);
			prop.store(propFileStream, "-- MailConfig Properties --");
			propFileStream.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"The properties file has not been found.",
					"Missing Properties File", JOptionPane.ERROR_MESSAGE);
			retVal = false;
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"There was an error writing the Properties file.",
					"Properties File Write Error", JOptionPane.ERROR_MESSAGE);
			retVal = false;
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Diagnostic method to display the properties
	 */
	public void displayProperties() {
		prop.list(System.out);
	}

}
