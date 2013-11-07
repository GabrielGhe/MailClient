package cs516.gabrielGheorghian.toolbar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.mail.MailReceiver;
import cs516.gabrielGheorghian.mail.MailSender;
import cs516.gabrielGheorghian.mainViews.MailContactPanel;
import cs516.gabrielGheorghian.mainViews.MailRightPanel;
import cs516.gabrielGheorghian.mainViews.MailSendPanel;

public class MailToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	private int xDim = 62;
	private int yDim = 48;
	private MailRightPanel cards;
	private final String CONTACTPANEL = "Contacts Panel";
	private final String SENDPANEL = "Send Panel";
	private final String RECEIVEPANEL = "Receive Panel";
	private final String SETTINGPANEL = "Setting Panel";
	private MailSendPanel send;
	private MailContactPanel contact;
	private JButton currentButton;
	private DBMailAccess database;
	private MailSender sender;
	private MailReceiver receiver;

	/**
	 * Constructor
	 */
	public MailToolBar() {
		initialize();
	}

	/**Six parameter Constructor 
	 * @param cards
	 * @param contact
	 * @param send
	 * @param database
	 * @param sender
	 * @param receiver
	 */
	public MailToolBar(MailRightPanel cards, MailContactPanel contact,
			MailSendPanel send, DBMailAccess database, MailSender sender, MailReceiver receiver)  {
		this.receiver = receiver;
		this.sender = sender;
		this.database = database;
		this.send = send;
		this.contact = contact;
		this.cards = cards;
		initialize();
	}

	/**
	 * Initialize the GUI
	 */
	private void initialize() {
		
		JButton button = null;

		button = makeToolBarButton("sendReceive", "SendReceive",
				"Send and Receive Mail", "Send and Receive");
		add(button);

		this.addSeparator(new Dimension(xDim, yDim));

		button = makeToolBarButton("newMail", "NewMail", "Compose a new email",
				"Compose Email");
		add(button);

		button = makeToolBarButton("openMail", "OpenMail", "Look at Emails",
				"Look at Emails");
		add(button);

		button = makeToolBarButton("contact", "Contact", "Look at contacts",
				"Contact");
		add(button);

		button = makeToolBarButton("settings", "Settings",
				"Change the settings", "Settings");
		add(button);

		this.addSeparator(new Dimension(xDim * 3, yDim));

		currentButton = makeToolBarButton("openMail", "Nothing",
				"Current Page", "Current");
		currentButton.setEnabled(false);
		add(currentButton);

		setFloatable(false);
		setRollover(false);
	}

	/**
	 * Create the buttons that will be placed in the tool bar
	 * 
	 * @param imageName
	 * @param actionCommand
	 * @param toolTipText
	 * @param altText
	 * @return
	 */
	private JButton makeToolBarButton(String imageName, String actionCommand,
			String toolTipText, String altText) {

		ToolBarEventHandler tbeh = new ToolBarEventHandler();

		// Look for the image.
		String imgLocation = "images/" + imageName + ".png";
		URL imageURL = MailToolBar.class.getResource(imgLocation);

		// Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(tbeh);

		if (imageURL != null) { // image found
			button.setIcon(new ImageIcon(imageURL, altText));
		} else { // no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}

		return button;
	}

	/**
	 * Changes the current image displayed
	 * 
	 * @param imgName
	 */
	public void changeCurrentIcon(String imageName) {
		// Look for the image.
		String imgLocation = "images/" + imageName + ".png";
		URL imageURL = MailToolBar.class.getResource(imgLocation);
		
		if (imageURL != null) { // image found
			currentButton.setIcon(new ImageIcon(imageURL, "Current"));
		} else { // no image found
			currentButton.setText("Current");
			System.err.println("Resource not found: " + imgLocation);
		}
	}
	
	/**
	 * change the current icon to the OpenMail
	 */
	public void treeNodeSelected(){
		changeCurrentIcon("openMail");
	}

	/**
	 * Rather than have a single event handler for all menu items here is an
	 * inner class specific to the tool bar
	 * 
	 */
	class ToolBarEventHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) (e.getSource());
			String s = source.getActionCommand();
			switch (s) {
			case "SendReceive":
				//send
				ArrayList<Mail> allMail = database.getMailByFolder(3);
				for(Mail mail: allMail){
					sender.sendMail(mail);
					mail.setFolder(2);
					database.saveMail(mail);
				}
				//receive
				allMail = receiver.getMail();
				for(Mail mail: allMail){
					mail.setId(-1);
					mail.setFolder(1);
					database.saveMail(mail);
				}
				break;
			case "OpenMail":
				changeCurrentIcon("openMail");
				cards.changeCard(RECEIVEPANEL);
				break;
			case "Contact":
				changeCurrentIcon("contact");
				cards.changeCard(CONTACTPANEL);
				contact.reloadContactTable();
				break;
			case "NewMail":
				changeCurrentIcon("newMail");
				cards.changeCard(SENDPANEL);
				send.reloadContactTable();
				break;
			case "Settings":
				changeCurrentIcon("settings");
				cards.changeCard(SETTINGPANEL);
				break;
			default:
				break;
			}
		}
	}

}
