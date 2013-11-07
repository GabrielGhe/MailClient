package cs516.gabrielGheorghian.subViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import cs516.gabrielGheorghian.JEEPapp.JEEPapp;
import cs516.gabrielGheorghian.data.Contact;
import cs516.gabrielGheorghian.data.Mail;

public class MailSendDisplay extends JPanel implements FocusListener {
	private static final long serialVersionUID = 1L;
	private JTextField toRecipientsField;
	private JTextField ccRecipientsField;
	private JTextField bccRecipientsField;
	private JTextField subjectField;
	private JEditorPane contentField;
	private JLabel toRecipients;
	private JLabel ccRecipients;
	private JLabel bccRecipients;
	private JLabel subjectLabel;
	private GridBagLayout gridBagLayout;
	private JTextField currentFocus;
	private final String EMAILREGEX = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

	/**
	 * Constructor
	 * 
	 * @param mailReceiver
	 *            reference to mail sending object
	 */
	public MailSendDisplay() {
		super();
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initialize();
	}

	/**
	 * Create the GUI
	 */
	private void initialize() {
		// Create the labels.
		toRecipients = new JLabel("To:");
		add(toRecipients, getConstraints(0, 0, 1, 1, GridBagConstraints.EAST, 0, 0));

		// Create the labels.
		ccRecipients = new JLabel("Cc:");
		add(ccRecipients, getConstraints(0, 1, 1, 1, GridBagConstraints.EAST, 0, 0));

		// Create the labels.
		bccRecipients = new JLabel("Bcc:");
		add(bccRecipients, getConstraints(0, 2, 1, 1, GridBagConstraints.EAST, 0, 0));

		subjectLabel = new JLabel("Subject:");
		add(subjectLabel, getConstraints(0, 3, 1, 1, GridBagConstraints.EAST, 0, 0));

		// Only accept valid email addresses
		toRecipientsField = new JTextField();
		toRecipientsField.setColumns(40);
		toRecipientsField.setEditable(true);
		toRecipientsField.setMinimumSize(new Dimension(400, 20));
		toRecipientsField.addFocusListener(this);

		toRecipientsField.setBackground(Color.WHITE);
		add(toRecipientsField,
				getConstraints(1, 0, 4, 1, GridBagConstraints.WEST, 1, 0));

		// Only accept valid email addresses
		ccRecipientsField = new JTextField();
		ccRecipientsField.setColumns(40);
		ccRecipientsField.setEditable(true);
		ccRecipientsField.setMinimumSize(new Dimension(400, 20));
		ccRecipientsField.addFocusListener(this);

		ccRecipientsField.setBackground(Color.WHITE);
		add(ccRecipientsField,
				getConstraints(1, 1, 4, 1, GridBagConstraints.WEST, 1, 0));

		// Only accept valid email addresses
		bccRecipientsField = new JTextField();
		bccRecipientsField.setColumns(40);
		bccRecipientsField.setEditable(true);
		bccRecipientsField.setMinimumSize(new Dimension(400, 20));
		bccRecipientsField.addFocusListener(this);

		bccRecipientsField.setBackground(Color.WHITE);
		add(bccRecipientsField,
				getConstraints(1, 2, 4, 1, GridBagConstraints.WEST, 1, 0));

		subjectField = new JTextField();
		subjectField.setColumns(40);
		subjectField.setEditable(true);
		subjectField.setMinimumSize(new Dimension(400, 20));
		subjectField.setBackground(Color.WHITE);
		add(subjectField, getConstraints(1, 3, 4, 1, GridBagConstraints.WEST, 1, 0));

		contentField = new JEditorPane();
		contentField.setContentType("text/plain;");
		contentField.setEditable(true);
		contentField.setPreferredSize(new Dimension(500, 200));

		JScrollPane jb = new JScrollPane(contentField);
		jb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jb, getConstraints(0, 4, 5, 1, GridBagConstraints.CENTER, 1, 1));

		currentFocus = toRecipientsField;
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
			int gridwidth, int gridheight, int anchor, float weightx, float weighty) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor = anchor;
		c.weightx = 1.0;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}

	/**
	 * returns the email
	 * 
	 * @return
	 */
	public Mail getEmail() {
		Mail mail = null;
		if (!toRecipientsField.getText().equals("") && !subjectField.getText().equals("")
				&& !contentField.getText().equals("")) {
			// for toRecipients
			ArrayList<String> toRecipints = new ArrayList<String>();
			String[] toReci = toRecipientsField.getText().split(";");
			for (String recipient : toReci) {
				toRecipints.add(recipient);
			}

			// for ccRecipients
			ArrayList<String> ccRecipints = new ArrayList<String>();
			String[] ccReci = ccRecipientsField.getText().split(";");
			for (String recipient : ccReci) {
				ccRecipints.add(recipient);
			}

			// for ccRecipients
			ArrayList<String> bccRecipents = new ArrayList<String>();
			String[] bccReci = ccRecipientsField.getText().split(";");
			for (String recipient : bccReci) {
				bccRecipents.add(recipient);
			}

			mail = new Mail(-1, JEEPapp.mailConfig.getUserEmail(), toRecipints,
					ccRecipints, bccRecipents, subjectField.getText(),
					contentField.getText(), new Date(), 3);
		}

		return mail;
	}

	/**
	 * clears the fields
	 */
	public void clear() {
		toRecipientsField.setText("");
		ccRecipientsField.setText("");
		bccRecipientsField.setText("");
		subjectField.setText("");
		contentField.setText("");
	}

	/**
	 * Fill the fields on the form
	 * 
	 * @param mail
	 */
	public void addContact(Contact contact) {
		// todo
		if (contact != null) {
			// if to recipients has focus
			if (currentFocus == toRecipientsField) {
				if (toRecipientsField.getText().length() == 0) {
					toRecipientsField.setText(toRecipientsField.getText()
							+ contact.getEmail());
				} else {
					toRecipientsField.setText(toRecipientsField.getText() + ";"
							+ contact.getEmail());
				}
			}// if cc recipients has focus
			else if (currentFocus == ccRecipientsField) {
				if (ccRecipientsField.getText().length() == 0) {
					ccRecipientsField.setText(ccRecipientsField.getText()
							+ contact.getEmail());
				} else {
					ccRecipientsField.setText(ccRecipientsField.getText() + ";"
							+ contact.getEmail());
				}
			}// if bcc recipients has focus
			else if (currentFocus == bccRecipientsField) {
				if (bccRecipientsField.getText().length() == 0) {
					bccRecipientsField.setText(bccRecipientsField.getText()
							+ contact.getEmail());
				} else {
					bccRecipientsField.setText(bccRecipientsField.getText()
							+ ";" + contact.getEmail());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		currentFocus = (JTextField) e.getSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		JTextField field = (JTextField) e.getSource();
		String[] emails = field.getText().split(";");
		boolean acceptable = true;
		
		for(String email: emails){
			if(!email.matches(EMAILREGEX))
				acceptable = false;
			if(!acceptable)
				break;
		}
		if(field.getText().equals(""));
		
		if(!acceptable)
			field.setText("");
	}
}
