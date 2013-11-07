package cs516.gabrielGheorghian.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import cs516.gabrielGheorghian.JEEPapp.JEEPapp;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.data.MailConfig;

public class MailSender {
	private final static boolean DEBUG = true;

	private MailConfig mailConfig;
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

	public MailSender(MailConfig mailConfig) {
		this.mailConfig = mailConfig;

	}

	/**
	 * Decide whether to use smtp or gmail
	 * 
	 * @param mmd
	 *            the MailMessageData
	 */
	public boolean sendMail(Mail mail) {
		boolean retVal = true;

		if (mailConfig.isGmail()) {
			retVal = gmailSend(mail);
		} else {
			retVal = smtpSend(mail);
		}
		return retVal;
	}

	/**
	 * Sent the message to a plain SMTP server like Waldo
	 * 
	 * @param mmd
	 *            the MailMessageData to send
	 * @return success or failure
	 */
	private boolean smtpSend(Mail mail) {
		boolean retVal = true;
		Session session = null;

		try {
			// Create a properties object
			Properties smtpProps = new Properties();

			// Add mail configuration to the properties
			smtpProps.put("mail.transport.protocol", "smtp");

			smtpProps.put("mail.smtp.host", mailConfig.getUrlSmtp());
			smtpProps.put("mail.smtp.port", mailConfig.getPortSmtp());

			if (mailConfig.isSMTPAuth()) {
				Authenticator auth = new SMTPAuthenticator();
				session = Session.getInstance(smtpProps, auth);
			} else
				session = Session.getDefaultInstance(smtpProps, null);

			// Display the conversation between the client and server
			if (DEBUG)
				session.setDebug(true);

			// Create a new message
			MimeMessage msg = new MimeMessage(session);

			// Set the single from field
			msg.setFrom(new InternetAddress(mailConfig
					.getUserEmail()));

			// Set the To, CC, and BCC from their ArrayLists
			for (String emailAddress : mail.getToRecipients())
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						emailAddress, false));

			if (mail.getCcRecipients() != null)
				for (String emailAddress : mail.getCcRecipients())
					if(!emailAddress.equals(""))
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress(emailAddress, false));

			if (mail.getBccRecipients() != null)
				for (String emailAddress : mail.getBccRecipients())
					if(!emailAddress.equals(""))
					msg.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(emailAddress, false));

			// Set the subject
			msg.setSubject(mail.getSubject());

			// Set the message body
			msg.setText(mail.getContent());

			// Set some other header information
			msg.setHeader("X-Mailer", "Comp Sci Tech Mailer");
			msg.setSentDate(new Date());

			if (mailConfig.isSMTPAuth()) {
				Transport transport = session.getTransport();

				transport.connect();
				transport.sendMessage(msg, msg
						.getRecipients(Message.RecipientType.TO));
				transport.close();
			} else
				Transport.send(msg);

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is no server at the SMTP address.",
					"SMTP-NoSuchProviderException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is no server at the SMTP address.", e);
			retVal = false;
		} catch (AddressException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is an error in a recipient's address.",
					"SMTP-AddressException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is an error in a recipient's address.", e);
			retVal = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is a problem with the message.",
					"SMTP-MessagingException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is a problem with the message.", e);
			retVal = false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There has been an unknown error.",
					"SMTP-UnknownException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There has been an unknown error.", e);
			retVal = false;
		}
		return retVal;
	}

	/**private class SMTPAuthenticator
	 * @author 0737019
	 *
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = mailConfig.getUserNamePop3();
			String password = mailConfig.getUserPassPop3();
			System.out.println(username + "\t" + password);
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * Sent the message to a Gmail account
	 * 
	 * @param mmd
	 *            the MailMessageData to send
	 * @return success or failure
	 */
	public boolean gmailSend(Mail mail) {
		boolean retVal = true;
		Transport transport = null;

		try {
			// Create a properties object
			Properties smtpProps = new Properties();

			// Add mail configuration to the properties
			smtpProps.put("mail.transport.protocol", "smtps");
			smtpProps.put("mail.smtps.host", mailConfig.getUrlSmtp());
			smtpProps.put("mail.smtps.auth", "true");
			smtpProps.put("mail.smtps.quitwait", "false");

			// Create a mail session
			Session mailSession = Session.getDefaultInstance(smtpProps);

			// Display the conversation between the client and server
			if (DEBUG)
				mailSession.setDebug(true);

			// Instantiate the transport object
			transport = mailSession.getTransport();

			// Create a new message
			MimeMessage msg = new MimeMessage(mailSession);

			// Set the To, CC, and BCC from their ArrayLists
			for (String emailAddress : mail.getToRecipients())
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						emailAddress, false));

			if (mail.getCcRecipients() != null)
				for (String emailAddress : mail.getCcRecipients())
					msg.addRecipient(Message.RecipientType.CC,
							new InternetAddress(emailAddress, false));

			if (mail.getBccRecipients() != null)
				for (String emailAddress : mail.getBccRecipients())
					msg.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(emailAddress, false));

			// Set the subject line
			msg.setSubject(mail.getSubject());
			// Set the message body
			msg.setText(mail.getContent());

			// Set some other header information
			msg.setHeader("X-Mailer", "Comp Sci Tech Mailer");
			msg.setSentDate(new Date());

			// Connect and authenticate to the server
			transport.connect(mailConfig.getUrlSmtp(), mailConfig
					.getPortSmtp(), mailConfig.getUserNamePop3(),
					mailConfig.getUserPassPop3());

			// Send the message
			transport.sendMessage(msg, msg.getAllRecipients());

			// Close the connection
			transport.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is no server at the SMTP address.",
					"Gmail-NoSuchProviderException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is no server at the SMTP address.", e);
			retVal = false;
		} catch (AddressException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is an error in a recipient's address.",
					"Gmail-AddressException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is an error in a recipient's address.", e);
			retVal = false;
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There is a problem with the message.",
					"Gmail-MessagingException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There is a problem with the message.", e);
			retVal = false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"There has been an unknown error.",
					"Gmail-UnknownException", JOptionPane.ERROR_MESSAGE);
			logger.log(Level.WARNING,
					"There has been an unknown error.", e);
			retVal = false;
		}
		return retVal;
	}
}
