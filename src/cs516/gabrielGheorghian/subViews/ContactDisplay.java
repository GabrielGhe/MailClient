package cs516.gabrielGheorghian.subViews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import test.RegexFormatter;
import cs516.gabrielGheorghian.data.Contact;

public class ContactDisplay extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFormattedTextField emailField;
	private JTextField nameField;
	private JFormattedTextField phoneField;
	private JLabel emailLabel;
	private JLabel nameLabel;
	private JLabel phoneLabel;
	private GridBagLayout gridBagLayout;
	private Contact currentContact;
	private final String PHONE = "(###)###-####";
	private final String EMAIL = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

	/**
	 * Constructor
	 * 
	 * @param mailReceiver
	 *            reference to mail sending object
	 */
	public ContactDisplay() {
		super();
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initialize();
	}

	/**
	 * Create the GUI
	 */
	private void initialize() {
		currentContact = new Contact();
		currentContact.setId(-1);

		// Create the labels.
		emailLabel = new JLabel("Email:");
		add(emailLabel, getConstraints(0, 0, 1, 1, GridBagConstraints.EAST));

		// Create the labels.
		nameLabel = new JLabel("Name:");
		add(nameLabel, getConstraints(0, 1, 1, 1, GridBagConstraints.EAST));

		// Create the labels.
		phoneLabel = new JLabel("Phone #:");
		add(phoneLabel, getConstraints(0, 2, 1, 1, GridBagConstraints.EAST));

		emailField = new JFormattedTextField(new RegexFormatter(EMAIL));
		emailField.setColumns(40);
		emailField.setEditable(true);

		emailField.setBackground(Color.WHITE);
		add(emailField, getConstraints(1, 0, 2, 1, GridBagConstraints.WEST));

		nameField = new JTextField();
		nameField.setColumns(40);
		nameField.setEditable(true);

		nameField.setBackground(Color.WHITE);
		add(nameField, getConstraints(1, 1, 2, 1, GridBagConstraints.WEST));

		phoneField = new JFormattedTextField(createFormatter(PHONE));
		phoneField.setColumns(40);
		phoneField.setEditable(true);

		phoneField.setBackground(Color.WHITE);
		add(phoneField, getConstraints(1, 2, 2, 1, GridBagConstraints.WEST));

		// focus traverse policy
		Vector<Component> order = new Vector<Component>(3);
		order.add(emailField);
		order.add(nameField);
		order.add(phoneField);

		this.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy(order));
	}

	/**
	 * Creates a new Contact with the id -1
	 */
	public void newContact() {
		currentContact = new Contact();
		currentContact.setId(-1);

		emailField.setText("");
		nameField.setText("");
		phoneField.setText("");
	}

	/**
	 * This method returns a MaskFormatter for the JFormattedTextField
	 * 
	 * @return javax.swing.MaskFormatter
	 */
	private MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	/**
	 * Returns currentContact ready for saving to database
	 * 
	 * @return
	 */
	public Contact getContact() {
		currentContact.setEmail(emailField.getText());
		currentContact.setName(nameField.getText());
		currentContact.setPhoneNum(phoneField.getText());

		return currentContact;
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
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor = anchor;
		// c.fill = GridBagConstraints.BOTH;
		return c;
	}

	/**
	 * Fill the fields on the form
	 * 
	 * @param mail
	 */
	public void addContact(Contact contact) {
		if (contact != null) {
			currentContact = contact;

			emailField.setText(contact.getEmail());
			nameField.setText(contact.getName());
			phoneField.setText(contact.getPhoneNum());
		}
	}

	/**
	 * Custom FocusTraversalPolicy When the user presses Tab or Shift-Tab this
	 * determines where the focus goes
	 * 
	 * @author neon
	 * 
	 */
	class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<Component> order;

		/**
		 * Constructor Copies the contents of the vector parameter to the class
		 * vector
		 * 
		 * @param order
		 */
		public MyOwnFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.FocusTraversalPolicy#getComponentAfter(java.awt.Container,
		 * java.awt.Component)
		 */
		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.FocusTraversalPolicy#getComponentBefore(java.awt.Container,
		 * java.awt.Component)
		 */
		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.FocusTraversalPolicy#getDefaultComponent(java.awt.Container)
		 */
		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.FocusTraversalPolicy#getLastComponent(java.awt.Container)
		 */
		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.FocusTraversalPolicy#getFirstComponent(java.awt.Container)
		 */
		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}
}
