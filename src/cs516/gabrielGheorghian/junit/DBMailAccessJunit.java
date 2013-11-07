package cs516.gabrielGheorghian.junit;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
import cs516.gabrielGheorghian.data.Contact;
import cs516.gabrielGheorghian.data.Folder;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.db.DBMailAccess;

public class DBMailAccessJunit {

	// --------------- test for getAllMail ----------------------//
	/**
	 * tests if it can get all the mail
	 */
	@Test
	public void testGetAllMail1() {
		DBMailAccess db = new DBMailAccess();
		ArrayList<Mail> list = db.getAllMail();
		for (Mail mail : list) {
			System.out.println(mail);
		}
	}

	// ---------------END test for getAllMail -------------------//

	// --------------- test for saveMail ------------------------//
	/**
	 * tests to save email insert
	 */
	@Test
	public void testSaveMail1() {

		DBMailAccess db = new DBMailAccess();

		// creating toRecipients array and populating it
		ArrayList<String> toRecipients = new ArrayList<String>();
		toRecipients.add("toRecipient1@hotmail.com");
		toRecipients.add("toRecipient2@hotmail.com");

		// creating ccRecipients array and populating it
		ArrayList<String> ccRecipients = new ArrayList<String>();
		ccRecipients.add("ccRecipient1@hotmail.com");
		ccRecipients.add("ccRecipient2@hotmail.com");

		// creating bccRecipients array and populating it
		ArrayList<String> bccRecipients = new ArrayList<String>();
		bccRecipients.add("bccRecipient1@hotmail.com");
		bccRecipients.add("bccRecipient2@hotmail.com");
		Mail mail1 = new Mail(-1, "sender1@hotmail.com", toRecipients, ccRecipients,
				bccRecipients, "subject", "content", new Date(), 1);
		boolean good = db.saveMail(mail1);
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	/**
	 * tests to save email update
	 */
	@Test
	public void testSaveMail2() {
		DBMailAccess db = new DBMailAccess();

		// creating toRecipients array and populating it
		ArrayList<String> toRecipients = new ArrayList<String>();
		toRecipients.add("toRecipient3@hotmail.com");
		toRecipients.add("toRecipient4@hotmail.com");

		// creating ccRecipients array and populating it
		ArrayList<String> ccRecipients = new ArrayList<String>();
		ccRecipients.add("ccRecipient3@hotmail.com");
		ccRecipients.add("ccRecipient4@hotmail.com");

		// creating bccRecipients array and populating it
		ArrayList<String> bccRecipients = new ArrayList<String>();
		bccRecipients.add("bccRecipient3@hotmail.com");
		bccRecipients.add("bccRecipient4@hotmail.com");
		Mail mail1 = new Mail(-1, "sender2@hotmail.com", toRecipients, ccRecipients,
				bccRecipients, "subject", "content", new Date(), 1);
		db.saveMail(mail1);

		ArrayList<Mail> list = db.getAllMail();
		Mail mail2 = list.get(0);
		mail2.setContent("IT WORKS, YATAAA");
		boolean good = db.saveMail(mail2);
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	// ---------------END test for saveMail --------------------------//

	// --------------- test for deleteMail --------------------------//
	@Test
	public void testDeleteMail() {

		DBMailAccess db = new DBMailAccess();
		ArrayList<Mail> list = db.getAllMail();

		boolean good = db.deleteMail(list.get(0));
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	// ---------------END test for deleteMail ------------------------//

	// --------------- test for getMailByEmail -----------------------//
	/**
	 * tests to get mail given an email (sender)
	 */
	@Test
	public void testGetMailByEmail1() {

		DBMailAccess db = new DBMailAccess();

		// creating toRecipients array and populating it
		ArrayList<String> toRecipients = new ArrayList<String>();
		toRecipients.add("toRecipient5@hotmail.com");
		toRecipients.add("toRecipient6@hotmail.com");

		// creating ccRecipients array and populating it
		ArrayList<String> ccRecipients = new ArrayList<String>();
		ccRecipients.add("ccRecipient5@hotmail.com");
		ccRecipients.add("ccRecipient6@hotmail.com");

		// creating bccRecipients array and populating it
		ArrayList<String> bccRecipients = new ArrayList<String>();
		bccRecipients.add("bccRecipient5@hotmail.com");
		bccRecipients.add("bccRecipient6@hotmail.com");
		Mail mail1 = new Mail(-1, "sender3@hotmail.com", toRecipients, ccRecipients,
				bccRecipients, "subject1", "content1", new Date(), 1);

		db.saveMail(mail1);

		ArrayList<Mail> list = db.getMailByEmail("sender3@hotmail.com");
		assertEquals(mail1.getSubject() + "" + mail1.getContent(), list.get(0)
				.getSubject() + "" + list.get(0).getContent());
	}

	// ----------------END test for getMailByEmail ----------------------//

	// --------------- test for getMailByFolder -----------------------//
	/**
	 * tests to get mail given an email (sender)
	 */
	@Test
	public void testGetMailByFolder() {

		DBMailAccess db = new DBMailAccess();

		// creating toRecipients array and populating it
		ArrayList<String> toRecipients = new ArrayList<String>();
		toRecipients.add("toRecipient7@hotmail.com");
		toRecipients.add("toRecipient8@hotmail.com");

		// creating ccRecipients array and populating it
		ArrayList<String> ccRecipients = new ArrayList<String>();
		ccRecipients.add("ccRecipient7@hotmail.com");
		ccRecipients.add("ccRecipient8@hotmail.com");

		// creating bccRecipients array and populating it
		ArrayList<String> bccRecipients = new ArrayList<String>();
		bccRecipients.add("bccRecipient7@hotmail.com");
		bccRecipients.add("bccRecipient8@hotmail.com");
		Mail mail1 = new Mail(-1, "sender4@hotmail.com", toRecipients, ccRecipients,
				bccRecipients, "subject1", "content1", new Date(), 1);

		db.saveMail(mail1);

		ArrayList<Mail> list = db.getMailByFolder(1);
		for (Mail mail : list) {
			System.out.println(mail);
		}
	}

	// ----------------END test for getMailByfolder ----------------------//
	
	// --------------- test for getFolderByName -----------------------//
		/**
		 * tests to get mail given an email (sender)
		 */
		@Test
		public void testGetFolderByName() {

			DBMailAccess db = new DBMailAccess();
			
			ArrayList<Folder> folders = db.getFolderByName("Inbox");

			for (Folder folder : folders) {
				System.out.println(folder);
			}
		}

		// ----------------END test for getMailByfolder ----------------------//

	// --------------- test for getAllContact ---------------------------//
	/**
	 * tests if it can get all the contacts
	 */
	@Test
	public void testGetAllContact1() {
		DBMailAccess db = new DBMailAccess();
		ArrayList<Contact> list = db.getAllContacts();
		System.out.println();
		for (Contact contact : list) {
			System.out.println(contact);
		}
	}

	// ---------------END test for getAllContact ------------------------//

	// --------------- test for saveContact -----------------------------//
	/**
	 * tests to save contact - insert
	 */
	@Test
	public void testSaveContact1() {

		DBMailAccess db = new DBMailAccess();

		Contact contact = new Contact(-1, "email1@hotmail.com", "name1", "(123)123-1234");
		boolean good = db.saveContact(contact);
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	/**
	 * tests to save contact - update
	 */
	@Test
	public void testSaveContact2() {
		DBMailAccess db = new DBMailAccess();

		Contact contact = new Contact(-1, "email2@hotmail.com", "name2", "(234)234-2345");
		db.saveContact(contact);

		ArrayList<Contact> list = db.getAllContacts();
		Contact contact2 = list.get(0);
		contact2.setName("YATAAA");
		boolean good = db.saveContact(contact2);
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	// ---------------END test for saveContact -------------------------//

	// --------------- test for deleteContact --------------------------//
	/**
	 * Tests to delete contact
	 */
	@Test
	public void testDeleteContact() {

		DBMailAccess db = new DBMailAccess();
		ArrayList<Contact> list = db.getAllContacts();

		boolean good = db.deleteContact(list.get(0));
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	// ---------------END test for deleteContact ----------------------//

	// ---------------test for getContactByEmail ----------------------//
	/**
	 * tests to get contact given an email
	 */
	@Test
	public void testGetContactByEmail1() {

		DBMailAccess db = new DBMailAccess();

		Contact contact = new Contact(-1, "email3@hotmail.com", "name3", "(345)345-4567");
		db.saveContact(contact);

		ArrayList<Contact> list = db.getContactByEmail("email3@hotmail.com");
		assertEquals(contact.getName() + "" + contact.getEmail(), list.get(0)
				.getName() + "" + list.get(0).getEmail());
	}

	// -------------END test for getContactByEmail--------------------//

	// --------------- test for getAllFolders ------------------------//
	/**
	 * tests if it can get all the folders
	 */
	@Test
	public void testGetAllFoders() {
		DBMailAccess db = new DBMailAccess();
		ArrayList<Folder> list = db.getAllFolders();
		System.out.println();
		for (Folder folder : list) {
			System.out.println(folder);
		}
	}

	// ---------------END test for getAllFolders --------------------//

	// --------------- test for saveFolder -----------------------------//
	/**
	 * tests to save folder - insert
	 */
	@Test
	public void testSaveFolder1() {

		DBMailAccess db = new DBMailAccess();

		Folder folder = new Folder(-1, "Another Folder");
		boolean good = db.saveFolder(folder);

		assertEquals(true, good);
	}

	// ---------------END test for saveFolder -------------------------//

	// --------------- test for deleteFolder --------------------------//
	/**
	 * Tests to delete folder
	 */
	@Test
	public void testDeleteFolder() {

		DBMailAccess db = new DBMailAccess();
		ArrayList<Folder> list = db.getAllFolders();

		boolean good = db.deleteFolder(list.get(3));
		boolean isGood = true;

		assertEquals(isGood, good);
	}

	// ---------------END test for deleteFolder ----------------------//

}
