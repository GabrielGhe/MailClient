package cs516.gabrielGheorghian.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import cs516.gabrielGheorghian.data.Contact;

public class ContactTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	private ArrayList<Contact> contact = null;
	
	/**
	 * Constructor
	 */
	public ContactTableModel() {
		super();
		contact = new ArrayList<Contact>();
	}
	
	/**
	 * Receivers the ArrayList of messages from the MailReceiver
	 * 
	 * @param mailMessageDataList
	 */
	public void setContactList(ArrayList<Contact> contact) {
		this.contact = contact;
		this.fireTableDataChanged();
	}

	/**
	 * Return the mail message data bean at the specified row
	 * 
	 * @param row
	 * @return
	 */
	public Contact getContactData(int row) {
		if (contact.size() > row)
			return contact.get(row);
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
		return 3;
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
			retVal = "  Email";
			break;
		case 1:
			retVal = "  Name";
			break;
		case 2:
			retVal = "  Phone Number";
		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return contact.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:
			return contact.get(row).getEmail();
		case 1:
			return contact.get(row).getName();
		case 2:
			return contact.get(row).getPhoneNum();
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
