package cs516.gabrielGheorghian.mainViews;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.model.ContactTableModelSend;
import cs516.gabrielGheorghian.subViews.ContactTableSend;
import cs516.gabrielGheorghian.subViews.MailSendDisplay;

public class MailSendPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagLayout gridBagLayout;
	private MailSendDisplay mailMessage;
	private ContactTableModelSend contactTableModel;
	private ContactTableSend contactTable;
	private DBMailAccess database;
	private JButton send;
	private JButton clear;

	/**
	 * default constructor
	 */
	public MailSendPanel(DBMailAccess database) {
		super();
		this.database = database;
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initialize();
	}

	/**
	 * Initializes the MailSendPanel
	 */
	public void initialize() {
		// Create a panel to display the message
		mailMessage = new MailSendDisplay();

		// needs a toolbar too
		contactTableModel = new ContactTableModelSend();
		contactTable = new ContactTableSend(mailMessage, contactTableModel);

		add(contactTable, getConstraints(0, 1, 2, 1, GridBagConstraints.CENTER));
		add(mailMessage, getConstraints(0, 2, 2, 1, GridBagConstraints.CENTER));

		send = new JButton("Send");
		clear = new JButton("Clear");
		send.addActionListener(new MyActionListener());
		clear.addActionListener(new MyActionListener());

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(send);
		buttonPanel.add(clear);

		add(buttonPanel, getConstraints(0, 3, 2, 1, GridBagConstraints.CENTER));
	}

	/**
	 * A method for setting grid bag constraints
	 * 
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param anchor
	 * @return
	 */
	private GridBagConstraints getConstraints(int gridx, int gridy,
			int gridwidth, int gridheight, int anchor) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor = anchor;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}

	/**
	 * reloads the contacts into the table
	 */
	public void reloadContactTable() {
		contactTableModel.setContactList(database.getAllContacts());
	}

	/**
	 * Listener for button
	 * 
	 * @author 0737019
	 * 
	 */
	private class MyActionListener implements ActionListener {

		/**
		 * Send the button text to the input handler
		 * 
		 * @param e
		 *            the ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			String buttonText = ((JButton) e.getSource()).getText();

			switch (buttonText) {
			case "Send":
				Mail mail = mailMessage.getEmail();

				if (mail != null) {
					database.saveMail(mail);
					mailMessage.clear();
				}
				break;
			case "Clear":
				mailMessage.clear();
				break;
			default:
				break;
			}
		}
	}
}
