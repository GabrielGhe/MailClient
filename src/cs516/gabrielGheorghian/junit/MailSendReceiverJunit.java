package cs516.gabrielGheorghian.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.data.MailConfig;
import cs516.gabrielGheorghian.mail.MailProperties;
import cs516.gabrielGheorghian.mail.MailReceiver;
import cs516.gabrielGheorghian.mail.MailSender;

public class MailSendReceiverJunit {

	/**
	 * Sending mail smtp
	 */
	@Test
	public void testSendMail1() {
		MailProperties mailProperties = new MailProperties();
		MailConfig mailConfig = new MailConfig();
		mailConfig = mailProperties.loadProperties();
		MailSender sender = new MailSender(mailConfig);

		// creating toRecipients array and populating it
		ArrayList<String> toRecipients = new ArrayList<String>();
		toRecipients.add("M0737019@waldo.dawsoncollege.qc.ca");

		// creating ccRecipients array and populating it
		ArrayList<String> ccRecipients = null;

		// creating bccRecipients array and populating it
		ArrayList<String> bccRecipients = null;

		Mail mail = new Mail(4, "M0737019@waldo.dawsoncollege.qc.ca",
				toRecipients, ccRecipients, bccRecipients,
				"subject junit test", "IT WORKS!!", new Date(), 1);


		Mail mail2 = new Mail(4, "M0737019@waldo.dawsoncollege.qc.ca",
				toRecipients, ccRecipients, bccRecipients,
				"subject junit test", "IT WORKS!! version 2", new Date(), 1);
		
		sender.sendMail(mail2);

		assertEquals(true, sender.sendMail(mail));
	}

	/**
	 * Receiving mail pop3
	 */
	@Test
	public void testGetMail1() {
		MailProperties mailProperties = new MailProperties();
		MailConfig mailConfig = new MailConfig();
		mailConfig = mailProperties.loadProperties();
		MailReceiver receive = new MailReceiver(mailConfig);

		ArrayList<Mail> list = receive.getMail();

		for (Mail mail : list) {
			System.out.println(mail);
		}
	}
	
	
	
	/**
	 * Sending mail gmail
	 */
	@Test
	public void testSendMail2() {
		MailProperties mailProperties = new MailProperties();
		MailConfig mailConfig = new MailConfig();
		mailConfig = mailProperties.loadProperties();
		mailConfig.setIsGmail(true);
		mailConfig.setUrlSmtp("stmp.gmail.com");
		mailConfig.setUrlPop3("pop.gmail.com");
		mailConfig.setPortPop3(995);
		mailConfig.setPortSmtp(465);
		
		MailSender sender = new MailSender(mailConfig);

		// creating toRecipients array and populating it
		ArrayList<String> toRecipients = new ArrayList<String>();
		toRecipients.add("M0737019@waldo.dawsoncollege.qc.ca");

		// creating ccRecipients array and populating it
		ArrayList<String> ccRecipients = null;

		// creating bccRecipients array and populating it
		ArrayList<String> bccRecipients = null;

		Mail mail = new Mail(4, "M0737019@waldo.dawsoncollege.qc.ca",
				toRecipients, ccRecipients, bccRecipients,
				"subject junit test", "IT WORKS!!", new Date(), 1);


		Mail mail2 = new Mail(4, "M0737019@waldo.dawsoncollege.qc.ca",
				toRecipients, ccRecipients, bccRecipients,
				"subject junit test", "IT WORKS!! version 2", new Date(), 1);
		
		sender.gmailSend(mail2);

		assertEquals(true, sender.gmailSend(mail));
	}

	/**
	 * Receiving mail gmail
	 */
	@Test
	public void testGetMail2() {
		MailProperties mailProperties = new MailProperties();
		MailConfig mailConfig = new MailConfig();
		mailConfig = mailProperties.loadProperties();
		mailConfig.setIsGmail(true);
		mailConfig.setUrlSmtp("stmp.gmail.com");
		mailConfig.setUrlPop3("pop.gmail.com");
		mailConfig.setPortPop3(995);
		mailConfig.setPortSmtp(465);
		MailReceiver receive = new MailReceiver(mailConfig);

		ArrayList<Mail> list = receive.getMail();

		for (Mail mail : list) {
			System.out.println(mail);
		}
	}

}
