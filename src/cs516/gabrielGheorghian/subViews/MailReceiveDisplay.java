package cs516.gabrielGheorghian.subViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import cs516.gabrielGheorghian.data.Mail;

/**
 * @author 0737019
 * 
 */
public class MailReceiveDisplay extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField fromField;
	private JTextField toField;
	private JTextField subjectField;
	private JEditorPane contentField;
	private JLabel fromLabel;
	private JLabel subjectLabel;
	private JLabel toLabel;
	private GridBagLayout gridBagLayout;

	/**
	 * Constructor
	 * 
	 * @param mailReceiver
	 *            reference to mail sending object
	 */
	public MailReceiveDisplay() {
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
		fromLabel = new JLabel("From:");
		add(fromLabel,
				getConstraints(0, 0, 1, 1, GridBagConstraints.EAST, 0, 0));

		toLabel = new JLabel("To:");
		add(toLabel, getConstraints(0, 1, 1, 1, GridBagConstraints.EAST, 0, 0));

		subjectLabel = new JLabel("Subject:");
		add(subjectLabel,
				getConstraints(0, 2, 1, 1, GridBagConstraints.EAST, 0, 0));

		// create text fields
		// from
		fromField = new JTextField();
		fromField.setColumns(40);
		fromField.setEditable(false);
		fromField.setBackground(Color.WHITE);
		fromField.setMinimumSize(new Dimension(400, 20));
		add(fromField,
				getConstraints(1, 0, 4, 1, GridBagConstraints.WEST, 1, 0));

		// to
		toField = new JTextField();
		toField.setColumns(40);
		toField.setEditable(false);
		toField.setBackground(Color.WHITE);
		fromField.setMinimumSize(new Dimension(400, 20));
		add(toField, getConstraints(1, 1, 4, 1, GridBagConstraints.WEST, 1, 0));

		// subject
		subjectField = new JTextField();
		subjectField.setColumns(40);
		subjectField.setEditable(false);
		subjectField.setBackground(Color.WHITE);
		fromField.setMinimumSize(new Dimension(400, 20));
		add(subjectField,
				getConstraints(1, 2, 4, 1, GridBagConstraints.WEST, 1, 0));

		// content
		contentField = new JEditorPane();
		contentField.setContentType("text/plain;");
		contentField.setEditable(false);
		contentField.setPreferredSize(new Dimension(500, 200));

		JScrollPane jb = new JScrollPane(contentField);
		jb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jb, getConstraints(0, 3, 5, 1, GridBagConstraints.CENTER, 1, 1));
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
			int gridwidth, int gridheight, int anchor, float weightx,
			float weighty) {
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
	 * Fill the fields on the form
	 * 
	 * @param mail
	 */
	public void displayMessage(Mail mail) {
		if (mail != null) {
			// from
			fromField.setText(mail.getSender());
			fromField.setCaretPosition(1);
			fromField.setToolTipText(mail.getSender());

			// to
			ArrayList<String> emails = mail.getToRecipients();
			String display = "";
			for (String dis : emails) {
				display += dis;
				display += ";";
			}
			display = display.substring(0, display.length() - 1);
			toField.setText(display);
			toField.setCaretPosition(1);
			toField.setToolTipText(mail.getSender());

			// subject
			subjectField.setText(mail.getSubject());
			subjectField.setCaretPosition(1);
			subjectField.setToolTipText(mail.getSubject());

			// content
			contentField.setText(mail.getContent());
			contentField.setCaretPosition(1);
		}
	}

	/**
	 * Returns a JEditorPane with the right text for printing
	 * 
	 * @return
	 */
	public JEditorPane printMail() {
		JEditorPane pane = null;
		if (!fromField.getText().equals("")) {
			String display = "";
			display += fromLabel.getText() + "              ".substring(0, 10 - fromLabel.getText().length()) + fromField.getText() + "\n";
			display += toLabel.getText() + "              ".substring(0, 10 - toLabel.getText().length()) + toField.getText() + "\n";
			display += subjectLabel.getText() + "              ".substring(0, 10 - subjectLabel.getText().length()) + subjectField.getText() + "\n";
			display += contentField.getText() +"\n";
			
			pane = new JEditorPane("text/plain", display);
			pane.setPreferredSize(new Dimension(300, 300));
		}

		return pane;

	}
}
