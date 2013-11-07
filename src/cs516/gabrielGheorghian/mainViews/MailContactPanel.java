package cs516.gabrielGheorghian.mainViews;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.model.ContactTableModel;
import cs516.gabrielGheorghian.subViews.ContactDisplay;
import cs516.gabrielGheorghian.subViews.ContactTable;

public class MailContactPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GridBagLayout gridBagLayout;
	private ContactDisplay displayContact;
	private ContactTableModel contactTableModel;
	private ContactTable contactTable;
	private DBMailAccess database;
	private JButton saveContact;
	private JButton newContact;
	
	/**
	 * default constructor
	 */
	public MailContactPanel(DBMailAccess database) {
		super();
		this.database = database;
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initialize();
	}

	/**
	 * Initializes MailContactPanel
	 */
	public void initialize() {
		// Create a panel to display the message
		displayContact = new ContactDisplay();
		
		contactTableModel = new ContactTableModel();

		// Create and set up the panel for the Table
		contactTable = new ContactTable(displayContact, contactTableModel);

		add(contactTable, getConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));
		add(displayContact, getConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));
		
		saveContact = new JButton("Save");
		newContact = new JButton("New");
		saveContact.addActionListener(new MyActionListener());
		newContact.addActionListener(new MyActionListener());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveContact);
		buttonPanel.add(newContact);
		
		add(buttonPanel, getConstraints(0, 3, 1, 1, GridBagConstraints.CENTER));
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
	 * reloads the contacts in the ContactTable
	 */
	public void reloadContactTable(){
		contactTableModel.setContactList(database.getAllContacts());
	}
	
	/**
	 * Listener for button
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
			case "New":
				displayContact.newContact();
				break;
			case "Save":
				database.saveContact(displayContact.getContact());
				displayContact.newContact();
				reloadContactTable();
				break;
			default:
				break;
			}
		}
	}
}
