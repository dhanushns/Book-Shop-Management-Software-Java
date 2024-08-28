package wishList;

import main.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import dbConnector.connectDataBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class WishList {

	public JFrame frame;
	public JTable table;
	public static int selectedBooks,countItem = 0;
	public static String book;
	public static DefaultTableCellRenderer cellRenderer;
	public static JLabel lblItems;
	public static String username;
	
	public WishList(String username) {
		WishList.username = username;
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("My Wishlist");
		frame.setBounds(100, 100, 775, 695);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
		frame.setVisible(true);
		displayWishList();
	}
	
	
	public void displayWishList() {
	
		JLabel lblWishList = new JLabel("My Wishlist");
		lblWishList.setFont(new java.awt.Font("Trajan Pro", java.awt.Font.PLAIN, 18));
		lblWishList.setBounds(37, 119, 129, 31);
		frame.getContentPane().add(lblWishList);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 15));
		scrollPane.setBounds(37, 191, 691, 381);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		scrollPane.setViewportView(table);
		
		String[] header = {"BookID","Book Name"}; 
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
			String query = "SELECT * FROM WISHLIST";
			db.getData(query);
			db.data.next();
			if(db.data.getRow() >= 1) {
				do {
					model.addRow(new Object[] {db.data.getInt("BOOKID"), db.data.getString("BOOKNAME")});
					countItem++;
				}while(db.data.next());
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(1);
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 15));
		try {
			connectDataBase db = new connectDataBase(username,"root","root@14");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String query;
					if(table.getSelectedRowCount() != 0) {
						int res = JOptionPane.showConfirmDialog(frame,"Are you sure?\nYou want to remove the seleted items","My Wishlist",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
						if(res == JOptionPane.YES_OPTION) {
							selectedBooks = table.getSelectedRows().length;
							for(int i = selectedBooks - 1 ; i >= 0 ; i--) {
								book = model.getValueAt(table.getSelectedRow(), 1).toString();
								model.removeRow(table.getSelectedRow());
								--countItem;
								lblItems.setText(String.valueOf(countItem) + " items");
								query = "DELETE FROM WISHLIST WHERE BOOKNAME = '" + book + "'";
								if(!(db.update(query) >= 1))
									JOptionPane.showMessageDialog(frame, "Error!\nCan't update your profile\nContact Administrator","My Wishlist", JOptionPane.ERROR_MESSAGE, null);
								else {
									Home.btnLike.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\unliked.png"));
									Home.displayBookDetails(book);
								}
							}
						}
					}
					else 
						JOptionPane.showMessageDialog(frame, "No items Selected.", "My Cart", JOptionPane.OK_OPTION,null);
				}
			});
		}catch(Exception e) {System.out.println(e);}
		btnRemove.setBounds(606, 144, 122, 37);
		
		frame.getContentPane().add(btnRemove);
		
		JLabel lblPrivate = new JLabel("Private : ");
		lblPrivate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrivate.setBounds(75, 160, 57, 21);
		frame.getContentPane().add(lblPrivate);
		
		JLabel privateIcon = new JLabel("");
		privateIcon.setIcon(new ImageIcon("D:\\Mini Project\\BookShop\\Five_D_Books\\res\\icons\\lock.png"));
		privateIcon.setFont(new Font("Tahoma", Font.PLAIN, 13));
		privateIcon.setBounds(37, 160, 28, 21);
		frame.getContentPane().add(privateIcon);
		
		lblItems = new JLabel(String.valueOf(countItem) + " items");
		lblItems.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblItems.setBounds(127, 160, 57, 21);
		frame.getContentPane().add(lblItems);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRowCount() != 0) {
					if(table.getSelectedRowCount() == 1) {
						book = model.getValueAt(table.getSelectedRow(), 1).toString();
						frame.setVisible(false);
						Home.displayBookDetails(book);
					}
				}
				else
					JOptionPane.showMessageDialog(frame, "No items Selected.", "My Wishlist", JOptionPane.INFORMATION_MESSAGE,null);
			}
		});
		btnOpen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOpen.setBounds(468, 144, 122, 37);
		frame.getContentPane().add(btnOpen);
	}
}
