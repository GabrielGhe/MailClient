package cs516.gabrielGheorghian.JEEPapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import cs516.gabrielGheorghian.data.MailConfig;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.mail.MailProperties;
import cs516.gabrielGheorghian.mail.MailReceiver;
import cs516.gabrielGheorghian.mail.MailSender;
import cs516.gabrielGheorghian.mainViews.MailContent;
import cs516.gabrielGheorghian.mainViews.MailRightPanel;
import cs516.gabrielGheorghian.splash.SplashScreen;
import cs516.gabrielGheorghian.toolbar.MailToolBar;

public class JEEPapp extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int xDim = 900;
	private final int yDim = 750;
	private MailToolBar toolbar;
	private MailContent content;
	private MailRightPanel cards;
	private DBMailAccess database;
	public static ResourceBundle bundle = null;
	public static MailConfig mailConfig = null;

	// help
	private HelpSet hs = null;
	private HelpBroker hb = null;
	private String helpHS;

	/**
	 * Constructor for Email client
	 */
	public JEEPapp() {
		super("JEEPapp");
		setLayout(new BorderLayout());
		makeHelp();
		initialize();
	}

	/**
	 * Creates Help
	 */
	private void makeHelp() {
		// Find the HelpSet file and create the HelpSet object:
		helpHS = "hs/main.hs";
		ClassLoader cl = JEEPapp.class.getClassLoader();
		try {
			URL hsURL = HelpSet.findHelpSet(cl, helpHS);
			hs = new HelpSet(null, hsURL);
		} catch (Exception ee) {
			// Say what the exception really is
			System.out.println("HelpSet " + ee.getMessage());
			System.out.println("HelpSet " + helpHS + " not found");
			return;
		}
		// Create a HelpBroker object:
		hb = hs.createHelpBroker();

	}

	/**
	 * Initialize the help system and create a help menu item
	 */
	private void makeHelpMenu() {

		// Enable window level help on RootPane with F1
		JRootPane rootpane = getRootPane();
		hb.enableHelpKey(rootpane, "Email", null);

		// Create a "Help" menu item to trigger the help viewer:
		JMenuBar menuBar = new JMenuBar();
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		JMenuItem menu_help = new JMenuItem("Launch Help");
		menu_help.addActionListener(new CSH.DisplayHelpFromSource(hb));
		help.add(menu_help);

		// Create a "print menu"
		JMenu print = new JMenu("Print");
		menuBar.add(print);
		JMenuItem menu_print = new JMenuItem("Print content");
		menu_print.addActionListener(new MyActionListener());
		print.add(menu_print);

		this.setJMenuBar(menuBar);
	}

	/**
	 * initializes the app
	 */
	public void initialize() {
		this.setPreferredSize(new Dimension(xDim, yDim));
		makeHelpMenu();

		database = new DBMailAccess();

		content = new MailContent(database);
		cards = content.cardSwitchC();
		add(content, BorderLayout.CENTER);

		MailSender sender = new MailSender(mailConfig);
		MailReceiver receiver = new MailReceiver(mailConfig);

		toolbar = new MailToolBar(cards, content.getContactPanel(),
				content.getSendPanel(), database, sender, receiver);
		add(toolbar, BorderLayout.NORTH);

		content.setToolBar(toolbar);
		pack();
		centerScreen();
		this.setMinimumSize(new Dimension(xDim, yDim));
		setVisible(true);
	}

	/**
	 * Standard method for centering a frame
	 */
	private void centerScreen() {
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
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
			JEditorPane pane = cards.printMail();
			if (pane != null)
				try {
					pane.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
		}
	}

	/**
	 * The main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Change the locale to use the appropriate bundle
		Locale currentLocale = new Locale("en", "CA");
		// Locale currentLocale = new Locale("fr","CA");

		JEEPapp.bundle = ResourceBundle.getBundle("MessagesBundle",
				currentLocale);
		JEEPapp.mailConfig = new MailProperties().loadProperties();

		new SplashScreen(3000, JEEPapp.bundle.getString("splash"))
				.showSplashAndExit();

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Create and set up the window.
				JEEPapp frame = new JEEPapp();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
