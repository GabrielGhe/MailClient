package cs516.gabrielGheorghian.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.activation.ActivationDataFlavor;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import cs516.gabrielGheorghian.data.Folder;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.db.DBMailAccess;
import cs516.gabrielGheorghian.model.MailTableModel;

public class JTreeTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 1L;

	// Using tree models allows us to add/remove nodes from a tree and pass the
	// appropriate messages.
	// This sample code only reads from the tree

	protected DefaultTreeModel tree;
	private DBMailAccess database;
	private MailTableModel table;

	private final DataFlavor localObjectFlavor = new ActivationDataFlavor(
			Mail.class, DataFlavor.javaJVMLocalObjectMimeType,
			"Integer Row Index");

	/**
	 * Creates a JTreeTransferHandler to handle a certain tree.
	 * 
	 * @param tree
	 */
	public JTreeTransferHandler(JTree tree, DBMailAccess database,
			MailTableModel table) {
		super();
		this.table = table;
		this.tree = (DefaultTreeModel) tree.getModel();
		this.database = database;
	}

	/**
	 * This component is not a source for dragging
	 * 
	 * @param c
	 * @return
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.NONE;
	}

	/**
	 * Verify that the data dropped is the DataFlavor (Type) expected
	 * 
	 * @param supp
	 * @return
	 */
	@Override
	public boolean canImport(TransferSupport supp) {
		// Setup so we can always see what it is we are dropping onto.
		supp.setShowDropLocation(true);
		if (supp.isDataFlavorSupported(localObjectFlavor)) {
			return true;
		}
		// something prevented this import from going forward
		return false;
	}

	/**
	 * Extract the data from the drop from the table This data is the row number
	 * dropped
	 * 
	 * @param supp
	 * @return
	 */
	@Override
	public boolean importData(TransferSupport supp) {
		if (this.canImport(supp)) {
			try {
				// Fetch the data to transfer
				Transferable t = supp.getTransferable();
				Mail theRow = (Mail) t.getTransferData(localObjectFlavor);
				// Fetch the drop location
				TreePath loc = ((javax.swing.JTree.DropLocation) supp
						.getDropLocation()).getPath();

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) loc
						.getLastPathComponent();
				Folder folder = (Folder) node.getUserObject();
				int pos = folder.getId();

				theRow.setFolder(pos);

				// Immediately update the record on disk, don't wait for use to
				// press update
				database.saveMail(theRow);

				table.setMailMessageDataList(database.getMailByFolder(folder.getId()));

				return true;
			} catch (UnsupportedFlavorException e) {
				// In theory, this shouldn't be reached because we already
				// checked to make sure imports were valid.
				e.printStackTrace();
			} catch (IOException e) {
				// In theory, this shouldn't be reached because we already
				// checked to make sure imports were valid.
				e.printStackTrace();
			}
		}
		// import isn't allowed at this time.
		return false;
	}
}
