package cs516.gabrielGheorghian.mainViews;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.toolbar.MailToolBar;
import cs516.gabrielGheorghian.tree.MailTree;

public class MailContent extends JPanel{
	private static final long serialVersionUID = 1L;
	private MailTree mailTree;
	private MailRightPanel mailRightPanel;
	private DBMailAccess database;
	
	/**
	 * default constructor
	 */
	public MailContent(DBMailAccess database){
		super();
		this.database = database;
		this.setLayout(new BorderLayout());
		initialize();
	}
	
	/**
	 * initializes the class
	 */
	private void initialize(){
		mailRightPanel = new MailRightPanel(database);
		add(mailRightPanel, BorderLayout.CENTER);
		
		mailTree = new MailTree(mailRightPanel.cardSwitchRP(), mailRightPanel.getMailModel(), database);
		add(mailTree, BorderLayout.WEST);
	}
	
	/** setter for toolbar
	 * @param bar
	 */
	public void setToolBar(MailToolBar bar){
		mailRightPanel.setToolBar(bar);
	}
	
	/** getter for the MailRightPanel needed to change JPanel via card layout
	 * @return
	 */
	public MailRightPanel cardSwitchC(){
		return mailRightPanel.cardSwitchRP();
	}
	
	/** getter for MailSendPanel needed for the toolbar
	 * @return
	 */
	public MailSendPanel getSendPanel(){
		return mailRightPanel.getSendPanel();
	}
	
	/** getter for MailContactPanel needed for toolbar
	 * @return
	 */
	public MailContactPanel getContactPanel(){
		return mailRightPanel.getContactPanel();
	}
}
