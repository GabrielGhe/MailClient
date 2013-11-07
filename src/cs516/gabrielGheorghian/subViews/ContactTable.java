package cs516.gabrielGheorghian.subViews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import cs516.gabrielGheorghian.model.ContactTableModel;

/**
 * @author 0737019
 *
 */
public class ContactTable extends JPanel{
	private static final long serialVersionUID = 1L;
	private ContactDisplay contactDisplay = null;
	private ContactTableModel contactTableModel = null;
	private JScrollPane scrollPane;
	private int currentRow;
	
	public ContactTable(ContactDisplay contactDisplay, ContactTableModel contactTableModel) {
		super();
		setLayout(new BorderLayout());
		this.contactDisplay = contactDisplay;
		this.contactTableModel = contactTableModel;
		initialize();
	}

	/**
	 * Create the table and place it in the panel
	 */
	private void initialize() {

		JTable table = new JTable(contactTableModel);

		table.getTableHeader().setDefaultRenderer(new HeaderRenderer());

		// Do not allow the user to reorder columns
		table.getTableHeader().setReorderingAllowed(false);

		// Set column header height
		table.getTableHeader()
				.setPreferredSize(
						new Dimension(table.getColumnModel()
								.getTotalColumnWidth(), 25));

		// Set column widths
		TableColumn column = null;
		column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(100);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(200);
		column = table.getColumnModel().getColumn(2);
		column.setPreferredWidth(250);

		// Only allow a single row selection
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Initialize the row listener
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener(new RowListener());

		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(table);

		// Add the scroll pane to this window.
		
		add(scrollPane);
	}

	/**
	 * Inner class that listens for row selection events
	 * 
	 * @author neon
	 * 
	 */
	class RowListener implements ListSelectionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.
		 * event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// Ignore extra messages.
			if (e.getValueIsAdjusting())
				return;

			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				currentRow = lsm.getMinSelectionIndex();
				// Send to the message panel the 
				// mail message data bean to display
				contactDisplay.addContact(contactTableModel.getContactData(currentRow));
			}
		}
	}

	/**
	 * This class draws the column header of the table
	 * Allows for change in font, foreground and 
	 * background colour, border and text alignment.
	 * 
	 * Found at web site:
	 * http://www.chka.de/swing/table/faq.html
	 * 
	 * @author Christian Kaufhold (swing@chka.de)
	 *
	 */
	class HeaderRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public HeaderRenderer() {
			setHorizontalAlignment(SwingConstants.LEFT);
			setOpaque(true);

			// This call is needed because DefaultTableCellRenderer calls
			// setBorder()
			// in its constructor, which is executed after updateUI()
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		}

		public void updateUI() {
			super.updateUI();
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean selected, boolean focused, int row,
				int column) {
			JTableHeader h = table != null ? table.getTableHeader() : null;

			if (h != null) {
				setEnabled(h.isEnabled());
				setComponentOrientation(h.getComponentOrientation());

				setForeground(Color.WHITE);
				setBackground(Color.BLACK);
				
				Font originalFont = h.getFont();
				Font boldFont = new Font(originalFont.getName(), Font.BOLD,
						originalFont.getSize());
				h.setFont(boldFont);
				setFont(h.getFont());
			} else {
				/*
				 * Use sensible values instead of random leftover values from
				 * the last call
				 */
				setEnabled(true);
				setComponentOrientation(ComponentOrientation.UNKNOWN);

				setForeground(UIManager.getColor("TableHeader.foreground"));
				setBackground(UIManager.getColor("TableHeader.background"));
				setFont(UIManager.getFont("TableHeader.font"));
			}

			setValue(value);

			return this;
		}
	}
}
