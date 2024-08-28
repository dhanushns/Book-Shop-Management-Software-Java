package cart;

import main.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import dbConnector.connectDataBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyCart {

	public JFrame frame;
	public JTable table;
	public static int selectedBooks;
	public static double total_amount = 0;
	public static DefaultTableCellRenderer cellRenderer;
	public static String username;
	
	public MyCart(String username) {
		MyCart.username = username;
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("My Cart");
		frame.setBounds(100, 100, 775, 695);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
		frame.setVisible(true);
		displayAddress();
	}
	
	public void displayAddress() {
		
		String fullname,address,phone_number;
		try {
			connectDataBase db = new connectDataBase(username,"root","root@14");
			String query = "SELECT * FROM USER_ADDRESS";
			db.getData(query);
			db.data.next();
			phone_number = String.valueOf(db.data.getLong("PHONE_NUMBER"));
			address = db.data.getString("ADDRESS");
			query = "SELECT * FROM ACCOUNT";
			db.getData(query);
			db.data.next();
			fullname = db.data.getString("FIRSTNAME") + " " + db.data.getString("LASTNAME");
			db.data.close();
			db.connect.close();
			
			JLabel lblDeliverTo = new JLabel("Deliver to : ");
			lblDeliverTo.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 20));
			lblDeliverTo.setBounds(28, 79, 160, 42);
			frame.getContentPane().add(lblDeliverTo);
			
			JLabel lblName = new JLabel(fullname);
			lblName.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 35));
			lblName.setBounds(27, 118, 565, 57);
			frame.getContentPane().add(lblName);
			
			JLabel lblPhone_number = new JLabel(phone_number);
			lblPhone_number.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 13));
			lblPhone_number.setBounds(28, 244, 108, 23);
			frame.getContentPane().add(lblPhone_number);
			
			JTextPane addressArea = new JTextPane();
			addressArea.setFont(new java.awt.Font("MS Reference Sans Serif", java.awt.Font.PLAIN, 18));
			addressArea.setBounds(28, 175, 387, 92);
			addressArea.setBackground(frame.getBackground());
			addressArea.setEditable(false);
			addressArea.setText(address);
			frame.getContentPane().add(addressArea);
			displayCart();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void displayCart() {
	
		JLabel lblNewLabel = new JLabel("My Cart");
		lblNewLabel.setFont(new java.awt.Font("Trajan Pro", java.awt.Font.PLAIN, 18));
		lblNewLabel.setBounds(28, 277, 129, 31);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 15));
		scrollPane.setBounds(27, 318, 709, 231);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		scrollPane.setViewportView(table);
		
		JLabel lblTotalAmount = new JLabel("Total Amount : ");
		lblTotalAmount.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
		lblTotalAmount.setBounds(28, 587, 129, 42);
		frame.getContentPane().add(lblTotalAmount);
		
		JLabel lblAmount = new JLabel("amount");
		lblAmount.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
		lblAmount.setBounds(152, 587, 160, 42);
		frame.getContentPane().add(lblAmount);
		
		String[] header = {"BookID","Book Name","Quantity","Price"}; 
		DefaultTableModel cartModel = new DefaultTableModel(0,0);
		cartModel.setColumnIdentifiers(header);
		table.setModel(cartModel);
		table.setRowHeight(50);
		table.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 15));
		table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 15));
		table.setBounds(45, 164, 565, 332);
		
		try {
			connectDataBase db = new connectDataBase(username,"root","root@14");
			String query = "SELECT * FROM CART";
			db.getData(query);
			db.data.next();
			if(db.data.getRow() >= 1) {
				do {
					cartModel.addRow(new Object[] {db.data.getInt("BOOKID"), db.data.getString("BOOKNAME"),db.data.getInt("QUANTITY"),db.data.getLong("PRICE")});
					total_amount += db.data.getLong("PRICE");
				}while(db.data.next());
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(2).setPreferredWidth(1);
		table.getColumnModel().getColumn(3).setPreferredWidth(1);
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		lblAmount.setText("Rs." + String.valueOf(total_amount));
		
		JButton btnRemove = new JButton("Remove");
		try {
			connectDataBase db = new connectDataBase("dhanush","root","root@14");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String query;
					if(table.getSelectedRowCount() != 0) {
						int res = JOptionPane.showConfirmDialog(frame,"Are you sure?\nYou want to remove the seleted items","My Cart",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(res == JOptionPane.YES_OPTION) {
							selectedBooks = table.getSelectedRows().length;
							String str,book;
							for(int i = selectedBooks - 1 ; i >= 0 ; i--) {
								str  = cartModel.getValueAt(table.getSelectedRow(), 3).toString();
								book = cartModel.getValueAt(table.getSelectedRow(), 1).toString();
								total_amount -= Integer.parseInt(str);
								cartModel.removeRow(table.getSelectedRow());
								lblAmount.setText("Rs." + String.valueOf(total_amount));
								query = "DELETE FROM CART WHERE BOOKNAME = '" + book + "'";
								if(!(db.update(query) >= 1))
									JOptionPane.showMessageDialog(frame, "Error!\nCan't update your profile\nContact Administrator","My Cart", JOptionPane.ERROR_MESSAGE, null);
								else
									Home.displayBookDetails(book);
							}
						}
					}
					else 
						JOptionPane.showMessageDialog(frame, "No items Selected.", "My Cart", JOptionPane.OK_OPTION,null);
				}
			});
		}catch(Exception e) {System.out.println(e);}
		btnRemove.setBounds(651, 281, 85, 21);
		
		JButton btnOrderNow = new JButton("Order now");
		btnOrderNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cartModel.getRowCount() > 0) {
					Home.confirmOrder(table,total_amount);
				}
				else
					JOptionPane.showMessageDialog(frame, "Your cart is empty", "My Cart", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnOrderNow.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 18));
		btnOrderNow.setBounds(506, 587, 201, 42);
		frame.getContentPane().add(btnOrderNow);
		
		frame.getContentPane().add(btnRemove);
	}
}
