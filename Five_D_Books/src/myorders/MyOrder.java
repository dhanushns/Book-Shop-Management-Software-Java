package myorders;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dbConnector.connectDataBase;

public class MyOrder {

	private JFrame frame;
	private JTable table;
	private DefaultTableCellRenderer cellRenderer;
	public static String username;
	
	public MyOrder(String username) {
		MyOrder.username = username;
		initialize();
	}

	private void initialize() {
		frame = new JFrame("My Orders");
		frame.setBounds(100, 100, 830, 695);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
		frame.setVisible(true);
		updateMyOrders();
		displayPayments();
	}
	
	public void updateMyOrders() {
		
		connectDataBase db = new connectDataBase(username,"root","root@14");
		String query;
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		Date date = new Date();
		String currentDate = format.format(date);
		try {
			query = "SELECT DELIVERY_DATE FROM MYORDERS";
			db.getData(query);
			db.data.next();
			if(db.data.getRow() >= 1) {
				do {
					String delivery_date = db.data.getDate("DELIVERY_DATE").toString();
					if(currentDate.compareTo(delivery_date) == 0) {
						query = "UPDATE MYORDERS SET DELIVERY_STATUS = " + true + " WHERE DELIVERY_DATE = '" + delivery_date + "'";
						if(!(db.update(query) >= 1))
							JOptionPane.showMessageDialog(frame, "Error!\nCan't update your profile\nContact Administrator","My Wishlist", JOptionPane.ERROR_MESSAGE, null);
					}
				}while(db.data.next());
			}
			db.data.close();
			db.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void displayPayments() {
		
		JLabel lblWishList = new JLabel("My Orders");
		lblWishList.setFont(new java.awt.Font("Trajan Pro", java.awt.Font.PLAIN, 18));
		lblWishList.setBounds(10, 86, 129, 31);
		frame.getContentPane().add(lblWishList);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 15));
		scrollPane.setBounds(10, 127, 796, 498);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		scrollPane.setViewportView(table);
		
		String[] header = {"BookID","Book Name","Qunatity","Price","Ordered Date","Delivery Date","Delivery Status"}; 
		DefaultTableModel model = new DefaultTableModel(0,0);
		model.setColumnIdentifiers(header);
		table.setModel(model);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(50);
		table.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 15));
		table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 15));
		table.setBounds(45, 164, 565, 332);
		
		try {
			connectDataBase db = new connectDataBase(username,"root","root@14");
			String query = "SELECT * FROM MYORDERS";
			db.getData(query);
			db.data.next();
			if(db.data.getRow() >= 1) {
				do {
					boolean flag = db.data.getBoolean("DELIVERY_STATUS");
					String status;
					if(flag)
						status = "Delivered";
					else
						status = "Not Delivered";
					model.addRow(new Object[] {db.data.getInt("BOOKID"), 
							db.data.getString("BOOKNAME"),
							db.data.getInt("QUANTITY"),
							db.data.getLong("TOTAL_AMOUNT"),
							db.data.getDate("ORDER_DATE"),
							db.data.getDate("DELIVERY_DATE"),
							status});
				}while(db.data.next());
			}
			db.data.close();
			db.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(1);
		table.getColumnModel().getColumn(2).setPreferredWidth(1);
		table.getColumnModel().getColumn(3).setPreferredWidth(1);
		table.getColumnModel().getColumn(4).setPreferredWidth(1);
		table.getColumnModel().getColumn(5).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setResizable(true);
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
	}

}
