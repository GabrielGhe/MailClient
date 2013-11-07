package cs516.gabrielGheorghian.mainViews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import test.RegexFormatter;
import cs516.gabrielGheorghian.JEEPapp.JEEPapp;
import cs516.gabrielGheorghian.data.MailConfig;
import cs516.gabrielGheorghian.mail.MailProperties;

public class MailSettingPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GridBagLayout gridBagLayout;
	private final String EMAILREGEX = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

	// user variables
	private JTextField userNameField;
	private JTextField userEmailField;

	// pop3 variables
	private JTextField urlPop3Field;
	private JTextField userNamePop3Field;
	private JTextField userPassPop3Field;
	private JTextField portPop3Field;

	// smtp variables
	private JTextField urlSmtpField;
	private JTextField portSmtpField;

	/**
	 * default constructor
	 */
	public MailSettingPanel() {
		super();
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initialize();
	}

	/**
	 * Initializes the variables and adds them to the panels
	 */
	public void initialize() {
		// Setting the Labels

		// user settings
		add(new JLabel("User Information"),
				getConstraints(1, 0, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("User Name:"),
				getConstraints(0, 1, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("User Email:"),
				getConstraints(0, 2, 1, 1, GridBagConstraints.CENTER, 0.1));

		// pop3
		add(new JLabel("POP3"),
				getConstraints(1, 3, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("Url for Pop3:"),
				getConstraints(0, 4, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("User name for POP3:"),
				getConstraints(0, 5, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("Password for POP3:"),
				getConstraints(0, 6, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("Port for POP3:"),
				getConstraints(0, 7, 1, 1, GridBagConstraints.CENTER, 0.1));

		// Smtp
		add(new JLabel("SMTP:"),
				getConstraints(1, 8, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("Url for Smtp:"),
				getConstraints(0, 9, 1, 1, GridBagConstraints.CENTER, 0.1));
		add(new JLabel("Port for Smtp:"),
				getConstraints(0, 10, 1, 1, GridBagConstraints.CENTER, 0.1));

		// initializing
		userNameField = new JTextField();
		userEmailField = new JFormattedTextField(new RegexFormatter(EMAILREGEX));
		urlPop3Field = new JTextField();
		userNamePop3Field = new JTextField();
		userPassPop3Field = new JTextField();
		portPop3Field = new JTextField();
		urlSmtpField = new JTextField();
		portSmtpField = new JTextField();

		// user text fields
		makeTextField(userNameField, 40);
		makeTextField(userEmailField, 40);

		// pop 3 textfields
		makeTextField(urlPop3Field, 40);
		makeTextField(userNamePop3Field, 40);
		makeTextField(userPassPop3Field, 40);
		makeTextField(portPop3Field, 40);

		// smtp textfields
		makeTextField(urlSmtpField, 40);
		makeTextField(portSmtpField, 40);

		// adding the textfields to the panel;

		// user
		add(userNameField, getConstraints(1, 1, 4, 1, GridBagConstraints.WEST, 1));
		add(userEmailField, getConstraints(1, 2, 4, 1, GridBagConstraints.WEST, 1));

		// pop3
		add(urlPop3Field, getConstraints(1, 4, 4, 1, GridBagConstraints.WEST, 1));
		add(userNamePop3Field,
				getConstraints(1, 5, 4, 1, GridBagConstraints.WEST, 1));
		add(userPassPop3Field,
				getConstraints(1, 6, 4, 1, GridBagConstraints.WEST, 1));
		add(portPop3Field, getConstraints(1, 7, 4, 1, GridBagConstraints.WEST, 1));

		// smtp
		add(urlSmtpField, getConstraints(1, 9, 4, 1, GridBagConstraints.WEST, 1));
		add(portSmtpField, getConstraints(1, 10, 4, 1, GridBagConstraints.WEST, 1));

		// creating and adding the button
		JButton save = new JButton("Save");
		save.addActionListener(new MyActionListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(save);

		add(buttonPanel, getConstraints(0, 11, 5, 1, GridBagConstraints.WEST, 1));

		// focus traverse policy
		Vector<Component> order = new Vector<Component>(8);
		order.add(userNameField);
		order.add(userEmailField);
		order.add(urlPop3Field);
		order.add(userNamePop3Field);
		order.add(userPassPop3Field);
		order.add(portPop3Field);
		order.add(urlSmtpField);
		order.add(portSmtpField);

		this.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy(order));

		loadProperties();
	}

	/**
	 * Loads the properties into the textfields
	 */
	public void loadProperties() {
		userNameField.setText(JEEPapp.mailConfig.getUserName());
		userEmailField.setText(JEEPapp.mailConfig.getUserEmail());
		urlPop3Field.setText(JEEPapp.mailConfig.getUrlPop3());
		userNamePop3Field.setText(JEEPapp.mailConfig.getUserNamePop3());
		userPassPop3Field.setText(JEEPapp.mailConfig.getUserPassPop3());
		portPop3Field.setText("" + JEEPapp.mailConfig.getPortPop3());
		urlSmtpField.setText(JEEPapp.mailConfig.getUrlSmtp());
		portSmtpField.setText("" + JEEPapp.mailConfig.getPortSmtp());
	}

	/**
	 * Creates textfields given the variable
	 * 
	 * @param text
	 * @param column
	 */
	private void makeTextField(JTextField text, int column) {
		text.setColumns(column);
		text.setEditable(true);
		text.setBackground(Color.WHITE);
	}

	/**
	 * Saves the properties input to file
	 */
	public void saveProperties() {
		MailConfig newConfig = new MailConfig();
		newConfig.setUserName(userNameField.getText());
		newConfig.setUserEmail(userEmailField.getText());
		newConfig.setUrlPop3(urlPop3Field.getText());
		newConfig.setUserNamePop3(userNamePop3Field.getText());
		newConfig.setUserPassPop3(userPassPop3Field.getText());
		newConfig.setPortPop3(Integer.parseInt(portPop3Field.getText()));
		newConfig.setUrlSmtp(urlSmtpField.getText());
		newConfig.setPortSmtp(Integer.parseInt(portSmtpField.getText()));
		newConfig.setSMTPAuth(JEEPapp.mailConfig.isSMTPAuth());
		newConfig.setIsGmail(JEEPapp.mailConfig.isGmail());
		newConfig.setUrlMySql(JEEPapp.mailConfig.getUrlMySql());
		newConfig.setUserMySql(JEEPapp.mailConfig.getUserMySql());
		newConfig.setPassMySql(JEEPapp.mailConfig.getPassMySql());
		
		MailProperties properties = new MailProperties();
		properties.writeProperties(newConfig);
		JEEPapp.mailConfig = properties.loadProperties();
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
			int gridwidth, int gridheight, int anchor, double weightx) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor = anchor;
		c.weightx = weightx;
		c.fill = GridBagConstraints.BOTH;
		return c;
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
			saveProperties();
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