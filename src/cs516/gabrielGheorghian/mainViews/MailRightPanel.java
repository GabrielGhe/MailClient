package cs516.gabrielGheorghian.mainViews;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.model.MailTableModel;
import cs516.gabrielGheorghian.toolbar.MailToolBar;

/**
 * @author 0737019
 *
 */
public class MailRightPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final String CONTACTPANEL = "Contacts Panel";
	private final String SENDPANEL = "Send Panel";
	private final String RECEIVEPANEL = "Receive Panel";
	private final String SETTINGPANEL = "Setting Panel";
	
	private CardLayout layout;
	private MailReceivePanel receive;
	private MailSendPanel send;
	private MailContactPanel contact;
	private DBMailAccess database;
	private MailSettingPanel setting;
	private MailToolBar bar;
	
	
	/**Single parameter constructor for MailRightPanel
	 * @param database
	 */
	public MailRightPanel(DBMailAccess database) {
		super();
		this.database = database;
		layout = new CardLayout();
		setLayout(layout);
		initialize();
	}

	/**
	 * Initializes class
	 */
	public void initialize() {
		
		// creating the panels
		receive = new MailReceivePanel();
		send = new MailSendPanel(database);
		contact = new MailContactPanel(database);
		setting = new MailSettingPanel();

		// adding them to the card layout panel
		add(receive, RECEIVEPANEL);
		add(setting, SETTINGPANEL);
		add(send, SENDPANEL);
		add(contact, CONTACTPANEL);
		
		//focus traverse policy
		Vector<Component> order = new Vector<Component>(4);
		order.add(receive);
		order.add(receive);
		order.add(receive);
		order.add(receive);
		
		this.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy(order));
	}
	
	/**
	 * setter for the toolbar
	 */
	public void setToolBar(MailToolBar bar){
		this.bar = bar;
	}

	/**getter for MailRightPanel used for JPanel switching via cardlayout
	 * @return
	 */
	public MailRightPanel cardSwitchRP() {
		return this;
	}

	
	/**Change JPanel with cardlayout
	 * @param card
	 */
	public void changeCard(String card) {
		switch (card) {
		case CONTACTPANEL:
			((CardLayout) this.getLayout()).show(this, CONTACTPANEL);
			break;
		case SENDPANEL:
			((CardLayout) this.getLayout()).show(this, SENDPANEL);
			break;
		case RECEIVEPANEL:
			((CardLayout) this.getLayout()).show(this, RECEIVEPANEL);
			bar.treeNodeSelected();
			break;
		case SETTINGPANEL:
			((CardLayout) this.getLayout()).show(this, SETTINGPANEL);
			break;
		default:
			break;
		}
	}
	
	/**returns a JEditorPane with the right text for printing
	 * @return
	 */
	public JEditorPane printMail(){
		return receive.printMail();
	}
	
	/**getter for MailTableModel
	 * @return MailTableModel
	 */
	public MailTableModel getMailModel(){
		return receive.getMailModel();
	}

	/**getter for MailSendPanel
	 * @return MailSendPanel
	 */
	public MailSendPanel getSendPanel(){
		return send;
	}
	
	/**getter for MailContactPanel
	 * @return MailContactPanel
	 */
	public MailContactPanel getContactPanel(){
		return contact;
	}

	/**Getter for MailSettingPanel
	 * @return MailSettingPanel
	 */
	public MailSettingPanel getSettingPanel(){
		return setting;
	}
	
	/**
	 * Custom FocusTraversalPolicy
	 * When the user presses Tab or Shift-Tab this determines where
	 * the focus goes
	 * @author neon
	 *
	 */
	class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<Component> order;

		/**
		 * Constructor
		 * Copies the contents of the vector parameter to the
		 * class vector
		 * @param order
		 */
		public MyOwnFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}

		/* (non-Javadoc)
		 * @see java.awt.FocusTraversalPolicy#getComponentAfter(java.awt.Container, java.awt.Component)
		 */
		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		/* (non-Javadoc)
		 * @see java.awt.FocusTraversalPolicy#getComponentBefore(java.awt.Container, java.awt.Component)
		 */
		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		/* (non-Javadoc)
		 * @see java.awt.FocusTraversalPolicy#getDefaultComponent(java.awt.Container)
		 */
		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		/* (non-Javadoc)
		 * @see java.awt.FocusTraversalPolicy#getLastComponent(java.awt.Container)
		 */
		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		/* (non-Javadoc)
		 * @see java.awt.FocusTraversalPolicy#getFirstComponent(java.awt.Container)
		 */
		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}
}
