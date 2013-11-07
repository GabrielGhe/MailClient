package cs516.gabrielGheorghian.tree;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import cs516.gabrielGheorghian.data.Folder;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.dnd.JTreeTransferHandler;
import cs516.gabrielGheorghian.mainViews.MailRightPanel;
import cs516.gabrielGheorghian.model.MailTableModel;

/**
 * @author 0737019
 * 
 */
public class MailTree extends JPanel implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTree tree = null;
	private JScrollPane treeView = null;
	private static boolean DEBUG = false;
	// Optionally play with line styles. Possible values are
	// "Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";
	private MailRightPanel cards;
	private MailTableModel mailModel;
	private final String RECEIVEPANEL = "Receive Panel";
	private DBMailAccess database;
	private Folder deleteFolder;
	private DefaultMutableTreeNode top;
	private int deleteRow;
	
	/**
	 * Constructor
	 */
	public MailTree(MailRightPanel cards, MailTableModel mailModel, DBMailAccess database) {
		super(new BorderLayout());
		this.database = database;
		this.mailModel = mailModel;
		this.cards = cards;
		initialize();
	}

	/**
	 * Build the panel's components and add them to the panel
	 */
	public void initialize() {
		deleteFolder = new Folder(1, "");
		createTree();
		add(treeView, BorderLayout.CENTER);
	}

	/**
	 * Build the JTree by: 1: Create the top node 2: Create all the leaf nodes,
	 * adding them one at a time to the top node 3: Instantiate the tree with
	 * the top node 4: Set the selection mode such as single 4a: Change the icon
	 * for the leaf 4b: Change the font 4c: Add the popup menu 5: Add the
	 * selection listener 5a: Hide the root node 6: Optionally, change the line
	 * style 7: Add to the panel
	 */
	private void createTree() {

		// Retrieve arraylist of folders
		ArrayList<Folder> folders = database.getAllFolders();

		// Create the top node
		top = new DefaultMutableTreeNode("Folders");

		// Create the leaf nodes
		for (Folder folder : folders) {
			DefaultMutableTreeNode dmtn = createNode(folder);
			top.add(dmtn);
		}

		tree = new JTree(top);

		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Hide the root
		tree.setRootVisible(false);

		tree.setTransferHandler(new JTreeTransferHandler(tree, database,
				mailModel));

		ImageIcon leafIcon = createImageIcon("images/open_folder.png");
		if (leafIcon != null) {
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setLeafIcon(leafIcon);
			tree.setCellRenderer(renderer);

		} else {
			System.err.println("Leaf icon missing; using default.");
		}

		// Set the font for the tree
		tree.setFont(new Font(Font.DIALOG, Font.BOLD, 14));

		// Make the popup menu
		makeTreePopupMenu();

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		if (playWithLineStyle) {
			if (DEBUG)
				System.out.println("line style = " + lineStyle);
			tree.putClientProperty("JTree.lineStyle", lineStyle);
		}
		// Create the scroll pane and add the tree to it.
		treeView = new JScrollPane(tree);

	}

	/**
	 * Create a node for the tree Node objects must have a toString() method
	 * 
	 * @param text
	 * @return
	 */
	private DefaultMutableTreeNode createNode(Object data) {
		DefaultMutableTreeNode fish = null;
		fish = new DefaultMutableTreeNode(data);
		return fish;
	}

	/*
	 * Event handler for selecting a node
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event
	 * .TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			if (DEBUG)
				System.out.println(node.toString());
			DefaultMutableTreeNode dmt = (DefaultMutableTreeNode) node;
			Folder fd = (Folder) dmt.getUserObject();
			cards.changeCard(RECEIVEPANEL);
			mailModel
					.setMailMessageDataList(database.getMailByFolder(fd.getId()));
		}
		if (DEBUG) {
			System.out.println(nodeInfo.toString());
		}
	}

	/**
	 * Create the popup menu and attach it to the tree
	 */
	private void makeTreePopupMenu() {
		JMenuItem menuItem;

		// Create the popup menu.
		JPopupMenu popup = new JPopupMenu();
		menuItem = new JMenuItem("Add New Folder");
		menuItem.addActionListener(new MyActionListener());
		popup.add(menuItem);

		popup.addSeparator();

		menuItem = new JMenuItem("Delete Folder");
		menuItem.addActionListener(new MyActionListener());
		popup.add(menuItem);

		// Add mouse listener to the text area so the popup menu can come up.
		MouseListener popupListener = new PopupListener(popup);

		// add listener to the tree
		tree.addMouseListener(popupListener);
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
			JMenuItem buttonText = (JMenuItem) e.getSource();

			switch (buttonText.getText()) {
			case "Add New Folder":
				String input = JOptionPane.showInputDialog(null,
						"Enter name of Folder : ", "", 1);
				if (input != null)
					if (!input.equals("")) {
						Folder newFolder = new Folder(-1, input);
						database.saveFolder(newFolder);
						newFolder = (database.getFolderByName(input)).get(0);
						DefaultMutableTreeNode dmtn = createNode(newFolder);
						top.add(dmtn);
						((DefaultTreeModel)tree.getModel()).reload();
					}
				break;
			case "Delete Folder":
				if(deleteFolder.getId() != 0 && deleteFolder.getId() != 1 && deleteFolder.getId() != 2){
					top.remove(deleteRow);
					database.deleteFolder(deleteFolder);
					((DefaultTreeModel)tree.getModel()).reload();
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * This is a mouse listener that displays the popup menu
	 * 
	 * @author neon
	 * 
	 */
	class PopupListener extends MouseAdapter {

		JPopupMenu popup;

		/**
		 * Constructor for PopupListener
		 * 
		 * @param popupMenu
		 */
		public PopupListener(JPopupMenu popupMenu) {
			popup = popupMenu;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e, false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e, true);
		}

		/**
		 * Method to display the menu and if the user clicks a button
		 * 
		 * @param e
		 * @param pressed
		 */
		private void maybeShowPopup(MouseEvent e, boolean pressed) {
			// If this is the right mouse button
			if (e.isPopupTrigger()) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					popup.show(e.getComponent(), e.getX(), e.getY());
					TreePath selPath = tree.getPathForLocation(e.getX(),
							e.getY());
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();
					deleteFolder = (Folder)node.getUserObject();
					deleteRow = selRow;
				}
			}
		}
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param path
	 * @return
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MailTree.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}