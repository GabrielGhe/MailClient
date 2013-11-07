package cs516.gabrielGheorghian.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import cs516.gabrielGheorghian.data.Mail;

/**
 * @author 0737019
 *
 */
public class MailTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private ArrayList<Mail> mail = null;
	
	/**
	 * Constructor
	 */
	public MailTableModel() {
		super();
		mail = new ArrayList<Mail>();
	}
	
	/**
	 * Receivers the ArrayList of messages from the MailReceiver
	 * 
	 * @param mailMessageDataList
	 */
	public void setMailMessageDataList(ArrayList<Mail> mail) {
		this.mail = mail;
		this.fireTableDataChanged();
	}

	/**
	 * Return the mail message data bean at the specified row
	 * 
	 * @param row
	 * @return
	 */
	public Mail getMailMessageData(int row) {
		if (mail.size() > row)
			return mail.get(row);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		// Only 3 columns
		return 4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		// Hard coded column names
		String retVal = "";
		switch(col) {
		case 0:
			retVal = "  From";
			break;
		case 1:
			retVal = "  To";
			break;
		case 2:
			retVal = "  Subject";
			break;
		case 3:
			retVal = "  Content";
		
		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return mail.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:
			return mail.get(row).getSender();
		case 1:
			ArrayList<String> receivers = mail.get(row).getToRecipients();
			String toReceivers = "";
			for(String toMail: receivers){
				toReceivers += toMail;
			}
			if(toReceivers.length() > 20){
				toReceivers = toReceivers.substring(0, 20);
				toReceivers += "...";
			}
			return toReceivers;
		case 2:
			return mail.get(row).getSubject();
		case 3:
			String content = mail.get(row).getContent();
			String extra = "";
			int size = 0;
			if(content.length() > 30){
				extra = "...";
				size = 30;
			}
			else
				size = content.length();
			return content.substring(0, size) + extra;
		}
		// Should throw exception since this must never happen
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class<? extends Object> getColumnClass(int c) {
		switch (c) {
		case 0:
		case 1:
		case 2:
		case 3:
			return String.class;
		default:
			return String.class;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {
		// Do not allow editing
		return false;
	}
}
