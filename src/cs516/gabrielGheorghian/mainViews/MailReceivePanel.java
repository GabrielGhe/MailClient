package cs516.gabrielGheorghian.mainViews;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import cs516.gabrielGheorghian.model.MailTableModel;
import cs516.gabrielGheorghian.subViews.MailReceiveDisplay;
import cs516.gabrielGheorghian.subViews.MailTable;

public class MailReceivePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private GridBagLayout gridBagLayout;
	private MailReceiveDisplay mailMessage;
	private MailTableModel mailTableModel;
	private MailTable mailTable;
	
	/**
	 * 
	 */
	public MailReceivePanel() {
		super();
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initialize();
	}

	/**
	 * Initializes the class
	 */
	public void initialize() {
		// Create the Model for the table
		mailTableModel = new MailTableModel();

		// Create a panel to display the message
		mailMessage = new MailReceiveDisplay();

		// Create and set up the panel for the Table
		mailTable = new MailTable(mailMessage, mailTableModel);

		add(mailTable, getConstraints(0, 1, 1, 1, GridBagConstraints.CENTER));
		add(mailMessage, getConstraints(0, 2, 1, 1, GridBagConstraints.CENTER));

	}

	/**
	 * A method for setting grid bag constraints
	 * 
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param anchor
	 * @return GridBagConstraints
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
	
	
	/**Returns a JEditorPane with the right text for printing
	 * @return JEditorPane
	 */
	public JEditorPane printMail(){
		return mailMessage.printMail();
	}
	
	
	/** getter for th e MailTableModel used for JTree
	 * @return MailTableModel
	 */
	public MailTableModel getMailModel(){
		return mailTableModel;
	}
}
