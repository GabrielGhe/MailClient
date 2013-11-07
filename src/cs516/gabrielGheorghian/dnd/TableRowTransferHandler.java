package cs516.gabrielGheorghian.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import cs516.gabrielGheorghian.data.Mail;
import cs516.gabrielGheorghian.model.MailTableModel;

/**
 * TransferHandler for dragging the row number
 */
public class TableRowTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 1L;

	// This is the DataFlavor we will drag
	private final DataFlavor localObjectFlavor = new ActivationDataFlavor(
			Mail.class, DataFlavor.javaJVMLocalObjectMimeType,
			"Integer Row Index");
	private JTable table = null;

	/**
	 * Constructor
	 * 
	 * @param table
	 */
	public TableRowTransferHandler(JTable table) {
		this.table = table;
	}

	/* 
	 * Define the DataHandler for the drag operation
	 * (non-Javadoc)
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 */
	@Override
	protected Transferable createTransferable(JComponent c) {
		assert (c == table);
		MailTableModel model = (MailTableModel) table.getModel();
		int row = table.getSelectedRow();
		Mail mail = model.getMailMessageData(row);
				
		return new DataHandler(mail,
				localObjectFlavor.getMimeType());
	}
	
	/**Turns a string into a arrayList
	 * @param input
	 * @return
	 */
	public ArrayList<String> turnIntoArray(String input){
		ArrayList<String> list = new ArrayList<String>();
		if(input != null){
			String[] stringList = input.split(";");
			for(String recipient: stringList){
				list.add(recipient);
			}
		}
		return list;
	}

	/* 
	 * We only want to copy data
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

}