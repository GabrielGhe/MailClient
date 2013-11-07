package cs516.gabrielGheorghian.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JOptionPane;

import com.sun.mail.pop3.POP3SSLStore;

import cs516.gabrielGheorghian.JEEPapp.JEEPapp;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.data.MailConfig;

/**
 * Class that receives the data
 * @author 0737019
 *
 */
public class MailReceiver {
	private final static boolean DEBUG = true;
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
	
	private MailConfig mailConfig = null;
	private ArrayList<Mail> mailList = null;

	/**
	 * Constructor Requires the configuration data loaded from the properties
	 * file
	 * 
	 * @param mailConfigData
	 */
	public MailReceiver(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
		mailList = new ArrayList<Mail>();
	}

	/**
	 * Retrieve the mail If something goes wrong then return a null
	 * 
	 * @return an ArrayList of mail message data
	 */
	public ArrayList<Mail> getMail() {

		boolean retVal = true;

		retVal = mailReceive();
		if (!retVal)
			mailList.clear();

		return mailList;
	}

	/**
	 * Do the actual work to receive the mail
	 * 
	 * @return success or failure
	 */
	private boolean mailReceive() {

		boolean retVal = true;

		Store store = null;
		Folder folder = null;
		Session session = null;

		Properties pop3Props = new Properties();

		try {

			if (mailConfig.isGmail()) { // Gmail config

				// Store configuration information for accessing the
				// server in the properties object
				pop3Props.put("mail.pop3.host",
						mailConfig.getUrlPop3());
				pop3Props.setProperty("mail.user",
						mailConfig.getUserNamePop3());
				pop3Props.setProperty("mail.passwd",
						mailConfig.getUserPassPop3());
				pop3Props.setProperty("mail.pop3.port",
						"" + mailConfig.getPortPop3());
				String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				pop3Props.setProperty("mail.pop3.socketFactory.class",
						SSL_FACTORY);
				pop3Props.setProperty("mail.pop3.socketFactory.port", ""
						+ mailConfig.getPortPop3());
				pop3Props.setProperty("mail.pop3.ssl", "true");
				pop3Props.setProperty("mail.pop3.socketFactory.fallback",
						"false");

				URLName url = new URLName("pop3://"
						+ pop3Props.getProperty("mail.user") + ":"
						+ pop3Props.getProperty("mail.passwd") + "@"
						+ pop3Props.getProperty("mail.pop3.host") + ":"
						+ pop3Props.getProperty("mail.pop3.port"));

				// Create a mail session
				session = Session.getDefaultInstance(pop3Props, null);

				// Get hold of a POP3 message store, and connect to it
				store = new POP3SSLStore(session, url);

			} else { // POP3 config
				// Create a mail session
				session = Session.getDefaultInstance(pop3Props, null);

				// Get hold of a POP3 message store, and connect to it
				store = session.getStore("pop3");
			}

			if (DEBUG)
				session.setDebug(true);

			// Connect to server
			store.connect(mailConfig.getUrlPop3(),
					mailConfig.getUserNamePop3(),
					mailConfig.getUserPassPop3());

			// Get the default folder
			folder = store.getDefaultFolder();
			if (folder == null)
				throw new Exception(":No default folder");

			// Get the INBOX from the default folder
			folder = folder.getFolder("INBOX");
			if (folder == null)
				throw new Exception(":No POP3 INBOX");

			// Open the folder for read only, cannot delete messages
			// Some servers will still delete messages after they are delivered
			// folder.open(Folder.READ_ONLY);

			// Open the folder for read/write, can delete messages
			folder.open(Folder.READ_WRITE);

			// Get all the waiting messages
			Message[] msgs = folder.getMessages();

			// Process the messages into beans
			retVal = process(msgs);

		} catch (NoSuchProviderException e) {
			JOptionPane.showMessageDialog(null,
					"There is no server at the POP3 address.",
					"POP3-NoSuchProviderException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is no server at the POP3 address.", e);
			retVal = false;
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null,
					"There is a problem with the message.",
					"POP3-MessagingException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is a problem with the message.", e);
			retVal = false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"There has been an unknown error " + e.getMessage(),
					"POP3-UnknownException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There has been an unknown error " + e.getMessage(), e);
			retVal = false;
		} finally {
			// Close down nicely
			try {
				if (folder != null)
					folder.close(true); // true=expunge
				if (store != null)
					store.close();
			} catch (Exception ex2) {
				ex2.printStackTrace();
				JOptionPane
						.showMessageDialog(
								null,
								"There has been an error closing a folder\non the POP3 server.",
								"POP3-Folder Error", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING,
						"There has been an error closing a folder\non the POP3 server." + ex2.getMessage(), ex2);
				// There are messages in the ArrayList so do not change retVal
				// to false
			}
		}
		return retVal;
	}

	/**
	 * Process the message to fill the mail message data bean
	 * 
	 * @param messages
	 *            messages to process
	 * @return success or failure
	 */
	private boolean process(Message[] messages) {

		boolean retVal = true;
		Mail mail = null;

		for (int msgNum = 0; msgNum < messages.length; msgNum++) {
			mail = new Mail();
			try {

				// Get the From field
				// While it supports more than one sender we will accept only
				// the first. A blank address throws an exception so catch
				// it and set from to an empty string.
				String from = null;
				try {
					from = ((InternetAddress) messages[msgNum].getFrom()[0])
							.getPersonal();
					// Get email address of sender
					if (from == null)
						from = ((InternetAddress) messages[msgNum].getFrom()[0])
								.getAddress();
					else
						from = "["
								+ ((InternetAddress) messages[msgNum].getFrom()[0])
										.getAddress() + "]";
					mail.setSender(from);

				} catch (AddressException e) {
					from = "";
				}
				//GetReceiver
				String receiver = mailConfig.getUserEmail();
				ArrayList<String> toRecipients = new ArrayList<String>();
				toRecipients.add(receiver);
				mail.setToRecipients(toRecipients);
				
				// Get subject
				String subject = messages[msgNum].getSubject();
				mail.setSubject(subject);

				// Get date sent
				Date date = messages[msgNum].getSentDate();
				mail.setMessageDate(date);

				// Get the message part (i.e. the message itself)
				Part messagePart = messages[msgNum];

				// The message may be multipart which means there could
				// be attachments, images, and html
				String msgText = getMessageText(messagePart);

				mail.setContent(msgText);

				mailList.add(mail);

				// Delete message from server when folder is closed
				// valid only if folder opened in read/write mode
				messages[msgNum].setFlag(Flags.Flag.DELETED, true);

			} catch (MessagingException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"There is a problem reading a message.",
						"POP3-MessagingException", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING,
						"There is a problem reading a message." + e.getMessage(), e);
				retVal = false;
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"There has been an unknown error.",
						"POP3-UnknownException", JOptionPane.ERROR_MESSAGE);
				logger.log(Level.WARNING,
						"There has been an unknown error.", ex);
				retVal = false;
			}
		}

		return retVal;
	}

	/**
	 * This method examines a the message text and identifies all parts and
	 * recursively all sub parts. It is looking for the text/plain section that
	 * every email message will have.
	 * 
	 * @param part
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String getMessageText(Part part) throws MessagingException,
			IOException {

		if (part.isMimeType("text/*")) {
			String content = (String) part.getContent();
			return content;
		}

		if (part.isMimeType("multipart/alternative")) {
			// prefer plain text over html text
			Multipart multiPart = (Multipart) part.getContent();
			String text = null;
			for (int i = 0; i < multiPart.getCount(); i++) {
				Part bodyPart = multiPart.getBodyPart(i);
				if (bodyPart.isMimeType("text/html")) {
					if (text == null)
						text = getMessageText(bodyPart);
					continue;
				} else if (bodyPart.isMimeType("text/plain")) {
					String bodyPartMessageText = getMessageText(bodyPart);
					if (bodyPartMessageText != null)
						return bodyPartMessageText;
				} else {
					return getMessageText(bodyPart);
				}
			}
			return text;
		} else if (part.isMimeType("multipart/*")) {
			Multipart multiPart = (Multipart) part.getContent();
			for (int i = 0; i < multiPart.getCount(); i++) {
				String bodyPartMessageText = getMessageText(multiPart
						.getBodyPart(i));
				if (bodyPartMessageText != null)
					return bodyPartMessageText;
			}
		}
		return null;
	}
}
