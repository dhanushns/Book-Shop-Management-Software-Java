package main;

import account.UserProfile;
import cart.MyCart;
import dbConnector.*;
import login.Login;
import myorders.MyOrder;
import payment.*;
import recoverAccount.*;
import wishList.WishList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JButton; 
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;

public class Home extends JFrame{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static JTextField searchField;
	public static JLabel lblLanguage_2,lblBillAmount,lblBillRupee,recommanedLabel;
	public static JButton btnRemoveBill,btnOrderNow,btnLike,btnAddToBill;
	public static JPanel head,authorPanel,recommandPanel,availableBooksPanel;
	public static String username;
	public static JComboBox<String> category;
	public static JComboBox<Integer> QuantityComboBox;
	
	public static String bookName,authorName,description,bookCategory,language,imgLocation;
	public static int edition,quantity,bookID;
	public static float ratings;
	public static double bookPrice,bookPriceAmount;
	public static ArrayList<String> bill = new ArrayList<String>();
	
	public static JPanel resultPanel =  new JPanel();
	public static JTable billTable;
	public static DefaultTableModel model;
	public static DefaultTableModel cartModel;
	static DefaultTableCellRenderer cellRenderer;
	public static double billAmount = 0,total_amount;
	public static boolean btnRemoveFlag = false;
	public static boolean btnOrderFlag = false;
	static int selectedBooks;

	public Home(String username) {
		Home.username = username;
		initialize();
		authorsPanel();
		recommendedBooksPanel();
	}
	
	
	public static void initialize() {
		
		frame = new JFrame("5D Book - Home");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setBounds(100, 100, 1622, 930);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		head = new JPanel();
		head.setBounds(10, 10, 1139, 81);
		frame.getContentPane().add(head);
		head.setLayout(null);
		
		JLabel title = new JLabel();
		title.setFont(new Font("Tahoma", Font.PLAIN, 40));
		title.setBounds(10, 10, 186, 55);
		title.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		head.add(title);
		
		category = new JComboBox<String>();
		category.setModel(new DefaultComboBoxModel<String>(new String[] {"All", "English Poetry", "Tamil Noval", "Tamil Poetry", "Science Fiction", "English Novals", "Short stories"}));
		category.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = category.getSelectedItem().toString();
				lblLanguage_2.setText(selectedCategory);
				getAvailableBooks(selectedCategory);
			}
		});
		category.setFont(new Font("Tahoma", Font.PLAIN, 18));
		category.setBounds(640, 20, 201, 40);
		head.add(category);
		
		searchField = new JTextField();
		searchField.setBounds(200, 20, 330, 40);
		head.add(searchField);
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		searchField.setColumns(10);
		
		JButton profileButton = new JButton("");
		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserProfile(username);
			}
		});
		
		profileButton.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\profile.png"));
		profileButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		profileButton.setBorder(null);
		profileButton.setContentAreaFilled(false);
		profileButton.setBounds(860, 20, 40, 40);
		head.add(profileButton);
		
		JButton wishListButton = new JButton("");
		wishListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WishList(username);
			}
		});
		wishListButton.setToolTipText("Wishlist");
		wishListButton.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\wishlist.png"));
		wishListButton.setForeground(frame.getForeground());
		wishListButton.setBounds(1000, 20, 40, 40);
		wishListButton.setBorder(null);
		wishListButton.setContentAreaFilled(false);
		head.add(wishListButton);
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem m1 = new JMenuItem("My Orders");
		JMenuItem m2 = new JMenuItem("Change password");
		JMenuItem m3 = new JMenuItem("Log out");
		m1.setBackground(Color.WHITE);
		m2.setBackground(Color.WHITE);
		m3.setBackground(Color.WHITE);
		m1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		m2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		m3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		
		m1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MyOrder(username);
			}
		});
		
		m2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new RecoverAccount(1,username);
				connectDataBase database = new connectDataBase("USER","root","root@14");
				database.update("TRUNCATE LOGINID");
			}
		});
		
		m3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectDataBase database = new connectDataBase("USER","root","root@14");
				database.update("TRUNCATE LOGINID");
				frame.setVisible(false);
				new Login();
			}
		});
		
		JButton menuButton = new JButton("");
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.show(frame,985,110);
			}
		});
		menuButton.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\menu.png"));
		menuButton.setForeground(frame.getForeground());
		menuButton.setBounds(1067, 20, 40, 40);
		menuButton.setBorder(null);
		menuButton.setContentAreaFilled(false);
		head.add(menuButton);
		
		JButton cartButton = new JButton("");
		cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MyCart(username);
			}
		});
		cartButton.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\add-to-cart.png"));
		cartButton.setForeground(frame.getForeground());
		cartButton.setBounds(930, 20, 40, 40);
		cartButton.setBorder(null);
		cartButton.setContentAreaFilled(false);
		head.add(cartButton);
		
		authorPanel = new JPanel();
		authorPanel.setBounds(10, 102, 1139, 275);
		frame.getContentPane().add(authorPanel);
		authorPanel.setLayout(null);
		
		JLabel authorLabel = new JLabel("Authors");
		authorLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		authorLabel.setBounds(10, 0, 84, 35);
		authorPanel.add(authorLabel);
		
		recommandPanel = new JPanel();
		recommandPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		recommandPanel.setBounds(10, 390, 1139, 445);
		frame.getContentPane().add(recommandPanel);
		recommandPanel.setLayout(null);
		
		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(1159, 10, 371, 825);
		frame.getContentPane().add(bookPanel);
		bookPanel.setLayout(null);
		
		btnOrderNow = new JButton("Order now");
		btnOrderNow.setVisible(btnOrderFlag);
		btnOrderNow.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnOrderNow.setBounds(10, 774, 134, 41);
		btnOrderNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model.getRowCount() > 0)
					confirmOrder(billTable,billAmount);
				else
					JOptionPane.showMessageDialog(frame, "No items Selected.", "Bill", JOptionPane.OK_OPTION,null);
			}
		});
		bookPanel.add(btnOrderNow);
		
		JScrollPane AvailableScrollPane = new JScrollPane();
		AvailableScrollPane.setBounds(0, 90, 361, 316);
		bookPanel.add(AvailableScrollPane);
		
		availableBooksPanel = new JPanel();
		AvailableScrollPane.setColumnHeaderView(availableBooksPanel);
		AvailableScrollPane.setViewportView(availableBooksPanel);
		availableBooksPanel.setLayout(new GridLayout(0,1,5,5));
		
		
		JLabel lblAvailableBooks = new JLabel("Available Books");
		lblAvailableBooks.setBounds(10, 11, 144, 35);
		bookPanel.add(lblAvailableBooks);
		lblAvailableBooks.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblCategory_1 = new JLabel("Category  : ");
		lblCategory_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCategory_1.setBounds(10, 48, 98, 35);
		bookPanel.add(lblCategory_1);
		
		lblLanguage_2 = new JLabel();
		lblLanguage_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLanguage_2.setBounds(107, 48, 120, 35);
		bookPanel.add(lblLanguage_2);
		
		lblBillAmount = new JLabel("Amount : ");
		lblBillAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBillAmount.setBounds(182, 774, 89, 35);
		bookPanel.add(lblBillAmount);
		
		lblBillRupee = new JLabel("Rs.0");
		lblBillRupee.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBillRupee.setBounds(272, 774, 89, 35);
		bookPanel.add(lblBillRupee);
		
		JLabel lblYourBill = new JLabel("Your Bill");
		lblYourBill.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblYourBill.setBounds(10, 416, 81, 35);
		bookPanel.add(lblYourBill);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayBookDetails(searchField.getText());
			}
		});

		searchButton.setBounds(530, 20, 85, 40);
		head.add(searchButton);
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		String selectedCategory = category.getSelectedItem().toString();
		lblLanguage_2.setText(selectedCategory);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 458, 361, 309);
		bookPanel.add(scrollPane);
		
		billTable = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		billTable.setRowHeight(50);
		billTable.setFont(new Font("Serif", Font.PLAIN, 15));
		billTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 15));
		
		model = new DefaultTableModel(0,0);
		String[] header = {"Book ID" , "Book Name" , "Quantity", "Price"};
		model.setColumnIdentifiers(header);
		billTable.setModel(model);
		scrollPane.setViewportView(billTable);
		
		btnRemoveBill = new JButton("Remove");
		btnRemoveBill.setVisible(btnRemoveFlag);
		btnRemoveBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(billTable.getSelectedRowCount() != 0) {
					int res = JOptionPane.showConfirmDialog(frame,"Are you sure?\nYou want to remove the seleted items","Bill",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if(res == JOptionPane.YES_OPTION) {
						selectedBooks = billTable.getSelectedRows().length;
						String str,bookName = null;
						for(int i = selectedBooks - 1 ; i >= 0 ; i--) {
							str  = model.getValueAt(billTable.getSelectedRow(), 3).toString();
							bookName = model.getValueAt(billTable.getSelectedRow(), 1).toString();
							billAmount -= Double.parseDouble(str);
							bill.remove(billTable.getSelectedRow());
							model.removeRow(billTable.getSelectedRow());
							lblBillRupee.setText(String.valueOf(billAmount));
						}
						if(billTable.getRowCount() == 0) {
							btnRemoveFlag = false;
							btnOrderFlag = false;
							btnOrderNow.setVisible(btnOrderFlag);
							btnRemoveBill.setVisible(btnRemoveFlag);
						}
						Home.displayBookDetails(bookName);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "No items Selected.", "Bill", JOptionPane.OK_OPTION,null);
				}
			}
		});
		btnRemoveBill.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRemoveBill.setBounds(248, 416, 113, 32);
		bookPanel.add(btnRemoveBill);
		getAvailableBooks(selectedCategory);
		authorsPanel();
		recommendedBooksPanel();
		frame.setVisible(true);
	}
	
	public static void authorsPanel() {
		
		int x = 40,y = 45,s = 225, j = 10;
		String[] authorsName = {"Kalki Krishnamurthy","William Shakespeare","Jayakanthan","William Wordsworth","Virginia Woolf"};
		String[] img = {"author1.png","author2.png","author3.png","author4.png","author5.png"};
		JLabel[] authorImg = new JLabel[5];
		JLabel[] nameLabel = new JLabel[5];
		
		for(int i = 0 ; i < 5; i++) {
			
			authorImg[i] = new JLabel();
			nameLabel[i] = new JLabel(authorsName[i]);
			nameLabel[i].setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
			
			authorImg[i].setBounds(x,y, 183, 185);
			authorImg[i].setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Author Images\\" + img[i]));
			authorPanel.add(authorImg[i]);
			authorImg[i].setLayout(null);
			if(i == 2 || i== 4)
				j += 20;
			nameLabel[i].setBounds(x + j, 230, 185, 50);
			authorPanel.add(nameLabel[i]);
			x += s;
		}
	}
	
	public static void recommendedBooksPanel() {
		
		int x = 10,y = 60,s = 230;
		JButton btnSeeMore;
		JPanel[] bookPanel = new JPanel[5];
		JLabel[] bookImage = new JLabel[5];
		JLabel[] bookName = new JLabel[5];
		JLabel[] bookPrice = new JLabel[5];
		JLabel[] lblPrice  = new JLabel[5];
		
		recommanedLabel = new JLabel();
		recommanedLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		recommanedLabel.setBounds(20, 13, 240, 32);
		recommandPanel.add(recommanedLabel);
		recommanedLabel.setText("Recommended for you");
		
		String[] img = {"Be loved","Cathedral","Jayakanthan kathaigal","Frankenstein","Symbols and Signs"};
		connectDataBase database = new connectDataBase("ManageBooks","root","root@14");
		String query;
		try {
			int i;
			for(i = 0 ; i < 5 ; i++) {
				query = "SELECT PRICEAMOUNT FROM BOOKS WHERE BOOKNAME = '" + img[i] + "'";
				database.getData(query);
				database.data.next();
				bookPanel[i] = new JPanel();
				bookImage[i] = new JLabel();
				bookName[i] = new JLabel();
				bookPrice[i] = new JLabel();
				lblPrice[i] = new JLabel();
			
				bookPanel[i].setBackground(Color.WHITE);
				bookPanel[i].setBounds(x, y, 220, 349);
				recommandPanel.add(bookPanel[i]);
				bookPanel[i].setLayout(null);
			
				bookImage[i].setText("Book Image");
				bookImage[i].setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Books Images\\recommanded\\" + img[i] + ".jpg"));
				bookImage[i].setFont(new Font("Tahoma", Font.PLAIN, 20));
				bookImage[i].setBounds(10, 10, 197, 234);
				bookImage[i].setBackground(new Color(255,0,0));
				bookPanel[i].add(bookImage[i]);
			
				bookName[i].setText(img[i]);
				bookName[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				bookName[i].setBounds(10, 254, 208, 36);
				bookPanel[i].add(bookName[i]);
				
				lblPrice[i].setText("Price : ");
				lblPrice[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblPrice[i].setBounds(10, 300, 208, 36);
				bookPanel[i].add(lblPrice[i]);
			
				bookPrice[i].setText(String.valueOf(database.data.getDouble("PRICEAMOUNT")));
				bookPrice[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				bookPrice[i].setBounds(65, 300, 208, 36);
				bookPanel[i].add(bookPrice[i]);
				x += s;
				String bookname = img[i];
				bookPanel[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						displayBookDetails(bookname);
					}
				});
				
			}
			btnSeeMore = new JButton("see more");
			btnSeeMore.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					recommandPanel.removeAll();
					recommandPanel.repaint();
					bestOf2022();
				}
			});
			btnSeeMore.setBounds(1016, 13, 113, 32);
			btnSeeMore.setFont(new Font("Tahoma", Font.PLAIN, 15));
			recommandPanel.add(btnSeeMore);
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void bestOf2022() {
		
		recommanedLabel = new JLabel();
		recommanedLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		recommanedLabel.setBounds(20, 13, 240, 32);
		recommandPanel.add(recommanedLabel);
		recommanedLabel.setText("Best of 2022");
		JButton btnPrevious = new JButton("Previous");
		JButton btnNext = new JButton("Next");
		int x = 10,y = 60,s = 230;
		JPanel[] bookPanel = new JPanel[5];
		JLabel[] bookImage = new JLabel[5];
		JLabel[] bookName = new JLabel[5];
		JLabel[] bookPrice = new JLabel[5];
		JLabel[] lblPrice  = new JLabel[5];
		
		String[] img = {"It starts with us","Diper overlode","It end with us","The boys from biloxi","The light we carry"};
		connectDataBase database = new connectDataBase("ManageBooks","root","root@14");
		String query;
		try {
			int i;
			for(i = 0 ; i < 5 ; i++) {
				query = "SELECT PRICEAMOUNT FROM BOOKS WHERE BOOKNAME = '" + img[i] + "'";
				database.getData(query);
				database.data.next();
				bookPanel[i] = new JPanel();
				bookImage[i] = new JLabel();
				bookName[i] = new JLabel();
				bookPrice[i] = new JLabel();
				lblPrice[i] = new JLabel();
			
				bookPanel[i].setBackground(Color.WHITE);
				bookPanel[i].setBounds(x, y, 220, 349);
				recommandPanel.add(bookPanel[i]);
				bookPanel[i].setLayout(null);
			
				bookImage[i].setText("Book Image");
				bookImage[i].setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Books Images\\recommanded\\" + img[i] + ".jpg"));
				bookImage[i].setFont(new Font("Tahoma", Font.PLAIN, 20));
				bookImage[i].setBounds(10, 10, 197, 234);
				bookImage[i].setBackground(new Color(255,0,0));
				bookPanel[i].add(bookImage[i]);
			
				bookName[i].setText(img[i]);
				bookName[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				bookName[i].setBounds(10, 254, 208, 36);
				bookPanel[i].add(bookName[i]);
				
				lblPrice[i].setText("Price : ");
				lblPrice[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblPrice[i].setBounds(10, 300, 208, 36);
				bookPanel[i].add(lblPrice[i]);
			
				bookPrice[i].setText(String.valueOf(database.data.getDouble("PRICEAMOUNT")));
				bookPrice[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				bookPrice[i].setBounds(65, 300, 208, 36);
				bookPanel[i].add(bookPrice[i]);
				x += s;
				String bookname = img[i];
				bookPanel[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						displayBookDetails(bookname);
					}
				});
				
			}
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPrevious.setBounds(879, 13, 113, 32);
		recommandPanel.add(btnPrevious);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recommandPanel.removeAll();
				recommandPanel.repaint();
				trendingBooks();
			}
		});
		btnNext.setBounds(1016, 13, 113, 32);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 15));
		recommandPanel.add(btnNext);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recommandPanel.removeAll();
				recommandPanel.repaint();
				recommendedBooksPanel();
			}
		});
	}
	
	public static void trendingBooks() {
		
		recommanedLabel = new JLabel();
		recommanedLabel.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		recommanedLabel.setBounds(20, 13, 240, 32);
		recommandPanel.add(recommanedLabel);
		recommanedLabel.setText("Trending Books");
		JButton btnPrevious;
		int x = 10,y = 60,s = 230;
		JPanel[] bookPanel = new JPanel[5];
		JLabel[] bookImage = new JLabel[5];
		JLabel[] bookName = new JLabel[5];
		JLabel[] bookPrice = new JLabel[5];
		JLabel[] lblPrice  = new JLabel[5];
		
		String[] img = {"Those violent delights","The song of achilles","Yellow wife","The invisible life of addie larue","Ordinary grace"};
		connectDataBase database = new connectDataBase("ManageBooks","root","root@14");
		String query;
		try {
			int i;
			for(i = 0 ; i < 5 ; i++) {
				query = "SELECT PRICEAMOUNT FROM BOOKS WHERE BOOKNAME = '" + img[i] + "'";
				database.getData(query);
				database.data.next();
				bookPanel[i] = new JPanel();
				bookImage[i] = new JLabel();
				bookName[i] = new JLabel();
				bookPrice[i] = new JLabel();
				lblPrice[i] = new JLabel();
			
				bookPanel[i].setBackground(Color.WHITE);
				bookPanel[i].setBounds(x, y, 220, 349);
				recommandPanel.add(bookPanel[i]);
				bookPanel[i].setLayout(null);
			
				bookImage[i].setText("Book Image");
				bookImage[i].setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Books Images\\recommanded\\" + img[i] + ".jpg"));
				bookImage[i].setFont(new Font("Tahoma", Font.PLAIN, 20));
				bookImage[i].setBounds(10, 10, 197, 234);
				bookImage[i].setBackground(new Color(255,0,0));
				bookPanel[i].add(bookImage[i]);
			
				bookName[i].setText(img[i]);
				bookName[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				bookName[i].setBounds(10, 254, 208, 36);
				bookPanel[i].add(bookName[i]);
				
				lblPrice[i].setText("Price : ");
				lblPrice[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblPrice[i].setBounds(10, 300, 208, 36);
				bookPanel[i].add(lblPrice[i]);
			
				bookPrice[i].setText(String.valueOf(database.data.getDouble("PRICEAMOUNT")));
				bookPrice[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
				bookPrice[i].setBounds(65, 300, 208, 36);
				bookPanel[i].add(bookPrice[i]);
				x += s;
				String bookname = img[i];
				bookPanel[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						displayBookDetails(bookname);
					}
				});
				
			}
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		
		btnPrevious = new JButton("Previous");
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPrevious.setBounds(1016, 13, 113, 32);
		recommandPanel.add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recommandPanel.removeAll();
				recommandPanel.repaint();
				recommandPanel.remove(btnPrevious);
				bestOf2022();
			}
		});
		btnPrevious.setVisible(true);
		
	}
	
	
	public static void getAvailableBooks(String item) {
		
		String[] books = null;
		int size = 0,i = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/ManageBooks","root","root@14");
			Statement stmt = connect.createStatement();
			ResultSet data;
			String query;
			if(item == "All") {
				query = "SELECT COUNT(BOOKNAME) FROM BOOKS";
				data = stmt.executeQuery(query);
				data.next();
				size = data.getInt(1);
				query = "SELECT BOOKNAME FROM BOOKS";
			}
			else {
				query = "SELECT COUNT(BOOKNAME) FROM BOOKS WHERE CATEGORY = '" + item + "'";
				data = stmt.executeQuery(query);
				data.next();
				size = data.getInt(1);
				query = "SELECT BOOKNAME FROM BOOKS WHERE CATEGORY = '" + item + "'";
			}
			data = stmt.executeQuery(query);
			data.next();
			books = new String[size];
			data = stmt.executeQuery(query);
			while(data.next()) {
				books[i] = data.getString("BOOKNAME");
				i++;
			}
		  }catch(Exception e) {
				System.out.println(e);
		}
		i = 0;
		availableBooksPanel.removeAll();
		JButton bookButtons[] = new JButton[size];
		for(String bookName : books) {
			bookButtons[i] = new JButton(bookName);
			availableBooksPanel.add(bookButtons[i]);
			bookButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					displayBookDetails(bookName);
				}
			});
		}
	}
	
	public static void displayBookDetails(String book) {
		
		try {
			connectDataBase database = new connectDataBase("ManageBooks","root","root@14");
			String query = "SELECT * FROM BOOKS WHERE BOOKNAME = '" + book + "'";
			database.getData(query);
			if(database.data.next() == false) {
				JOptionPane.showMessageDialog(frame, "Sorry! \n The Book is not Available.");
			}
			else {
				recommandPanel.setVisible(false);
				recommanedLabel.setVisible(false);
				authorPanel.setVisible(false);
				recommandPanel.setVisible(false);
				
				resultPanel.removeAll();
				resultPanel.repaint();
				resultPanel.setBounds(10, 200, 1119, 425);
				frame.getContentPane().add(resultPanel);
				resultPanel.setLayout(null);
				
				JButton backButton = new JButton("Back");
				backButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						resultPanel.setVisible(false);
						backButton.setVisible(false);
						recommandPanel.setVisible(true);
						recommanedLabel.setVisible(true);
						authorPanel.setVisible(true);
						recommandPanel.setVisible(true);
					}
				});
				backButton.setBounds(30, 150, 97, 35);
				frame.getContentPane().add(backButton);
				frame.getContentPane().repaint();
				
				JLabel bookImagelbl = new JLabel();
				JLabel bookNamelbl = new JLabel();
				JLabel authorNamelbl = new JLabel();
				JTextPane descriptionTextArea = new JTextPane();
				JLabel lblNum = new JLabel();
				JLabel edition_value = new JLabel();
				JLabel languageLabel = new JLabel();
				JLabel lblCategoryOptions = new JLabel();
				JLabel priceAmount = new JLabel();
				JLabel lblAmount = new JLabel();
				JLabel lblRatings = new JLabel("Ratings : ");
				JLabel lblDescription = new JLabel("Description");
				JLabel lblPrice = new JLabel("Price : ");
				JLabel lblQuantity = new JLabel("Quantity");
				JLabel lblLanguage = new JLabel("Language : ");
				btnLike = new JButton();
				JButton btnBuynow = new JButton("Buy now");;
				JLabel lblCategory = new JLabel("Category  : ");
				JLabel lblEdition = new JLabel("Edition : ");
				btnAddToBill = new JButton("Add to Bill");
				JLabel lblTotalAmount = new JLabel("Total Amount : ");
				JButton btnCart = new JButton("Add to Cart");
				
				bookID = database.data.getInt("BOOKID");
				bookName = database.data.getString("BOOKNAME");
				imgLocation = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Books Images\\" + bookName + ".jpg";
				authorName = database.data.getString("AUTHORNAME");
				edition = database.data.getInt("EDITION");
				ratings = database.data.getFloat("RATINGS");
				description = database.data.getString("DESCRIPTION");
				bookPriceAmount = database.data.getDouble("PRICEAMOUNT");
				bookCategory = database.data.getString("CATEGORY");
				language = database.data.getString("LANGUAGE");
				
				bookImagelbl.setFont(new Font("Tahoma", Font.PLAIN, 25));
				bookImagelbl.setBounds(20, 10, 295, 405);
				resultPanel.add(bookImagelbl);
				
				bookNamelbl.setFont(new Font("Tahoma", Font.PLAIN, 35));
				bookNamelbl.setBounds(343, 10, 600, 55);
				resultPanel.add(bookNamelbl);
				
				authorNamelbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
				authorNamelbl.setBounds(342, 66, 220, 35);
				resultPanel.add(authorNamelbl);
				
				lblRatings.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblRatings.setBounds(486, 147, 78, 35);
				resultPanel.add(lblRatings);
				
				lblDescription.setFont(new Font("Tahoma", Font.BOLD, 16));
				lblDescription.setBounds(343, 148, 98, 35);
				resultPanel.add(lblDescription);
				
				descriptionTextArea.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 17));
				descriptionTextArea.setBounds(343, 193, 280, 150);
				descriptionTextArea.setEditable(false);
				resultPanel.add(descriptionTextArea);
				
				lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 30));
				lblPrice.setBounds(343, 365, 98, 55);
				resultPanel.add(lblPrice);
				
				quantity = database.data.getInt("QUANTITY");
				Integer[] q = new Integer[quantity];
				if(quantity != 0) {
					for(int i = 1,j = 0 ; i <= quantity ; i++,j++)
						q[j] = i;
					
					lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 18));
					lblQuantity.setBounds(691, 202, 69, 35);
					resultPanel.add(lblQuantity);
					QuantityComboBox = new JComboBox<Integer>(q);
					QuantityComboBox.setBounds(770, 205, 129, 35);
					String selectedQuantity = QuantityComboBox.getSelectedItem().toString(); 
					total_amount = Integer.parseInt(selectedQuantity) * bookPriceAmount; 
					lblAmount.setText("Rs." + String.valueOf(total_amount)); 
					QuantityComboBox.addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent e) { 
							String selectedQuantity = QuantityComboBox.getSelectedItem().toString(); 
							total_amount = Integer.parseInt(selectedQuantity) * bookPriceAmount; 
							lblAmount.setText("Rs." + String.valueOf(total_amount)); 
							} 
						}); 
					resultPanel.add(QuantityComboBox); 
					btnBuynow.setFont(new Font("Tahoma", Font.PLAIN, 20));
					btnBuynow.setBounds(919, 355, 176, 55); 
					btnBuynow.addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent e) { 
							confirmOrder(bookID,bookName,QuantityComboBox.getSelectedItem().toString(),total_amount); 
							} 
						}); 
					lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 30));
					lblTotalAmount.setBounds(691, 274, 217, 55);
					resultPanel.add(lblTotalAmount);
					resultPanel.add(btnBuynow);
				}
				else {
					JLabel lb1 = new JLabel("Currently unavailable");
					lb1.setFont(new Font("Tahoma", Font.PLAIN, 25));
					lb1.setForeground(Color.red);
					lb1.setBounds(691, 274, 240, 55);
					resultPanel.add(lb1);
				}
				
				
				lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblCategory.setBounds(691, 102, 98, 35);
				resultPanel.add(lblCategory);
				
				lblCategoryOptions.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblCategoryOptions.setBounds(799, 102, 130, 35);
				resultPanel.add(lblCategoryOptions);
				
				lblLanguage.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblLanguage.setBounds(691, 147, 98, 35);
				resultPanel.add(lblLanguage);
				
				languageLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
				languageLabel.setBounds(801, 147, 98, 35);
				resultPanel.add(languageLabel);
				
				lblEdition.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblEdition.setBounds(343, 108, 98, 35);
				resultPanel.add(lblEdition);
				
				edition_value.setFont(new Font("Tahoma", Font.PLAIN, 18));
				edition_value.setBounds(416, 108, 98, 35);
				resultPanel.add(edition_value);
				
				for(String name : bill) {
					if(name.compareTo(bookName) == 0) {
						btnAddToBill.setEnabled(false);
					}
				}
				btnAddToBill.setFont(new Font("Tahoma", Font.PLAIN, 15));
				btnAddToBill.setBounds(942, 199, 140, 43);
				btnAddToBill.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						bill.add(bookName);
						btnAddToBill.setEnabled(false);
						model.addRow(new Object[] {bookID,bookName,QuantityComboBox.getSelectedItem().toString(),total_amount});
						billTable.getColumnModel().getColumn(0).setPreferredWidth(1);
						billTable.getColumnModel().getColumn(2).setPreferredWidth(1);
						billTable.getColumnModel().getColumn(3).setPreferredWidth(1);
						cellRenderer = new DefaultTableCellRenderer();
					    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
					    billTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
					    billTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
					    billTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
					    billAmount += total_amount;
					    lblBillRupee.setText(String.valueOf(billAmount));
					    btnRemoveFlag = true;
					    btnOrderFlag = true;
					    btnRemoveBill.setVisible(btnRemoveFlag);
					    btnOrderNow.setVisible(btnOrderFlag);
					}
				});
				resultPanel.add(btnAddToBill);
				
				lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 30));
				lblAmount.setBounds(919, 274, 180, 55);
				resultPanel.add(lblAmount);
				
				btnCart.setBounds(920, 22, 110, 35);
				boolean flag_cart = false;
				connectDataBase user = new connectDataBase(username,"root","root@14");
				query = "SELECT BOOKNAME FROM CART";
				user.getData(query);
				user.data.next();
				if(user.data.getRow() >= 1) {
					do {			
						if(user.data.getString("BOOKNAME").compareTo(bookName) == 0) {
							btnCart.setEnabled(false);
							flag_cart = true;
							break;
						}
						
					}while(user.data.next());
					if(!flag_cart)
						btnCart.setEnabled(true);
				}
				btnCart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String query = "SELECT BOOKNAME FROM CART";
							String price = String.valueOf(total_amount);
							boolean flag = false;
							user.getData(query);
							user.data.next();
							if(user.data.getRow() >= 1) {
								do {
									if(user.data.getString("BOOKNAME").compareTo(bookName) != 0)
										flag = true;
									else {
										flag = false;
										break;
									}
								}while(user.data.next());
								if(flag) {
									query = "INSERT INTO CART VALUES ('" + bookID + "','" + bookName + "','" + QuantityComboBox.getSelectedItem() + "','" + price + "')";
									if(!(user.update(query) >= 1))
										JOptionPane.showMessageDialog(frame, "Error!\nContact Administrator","Add to cart", JOptionPane.ERROR_MESSAGE, null);
									else
										btnCart.setEnabled(false);
								}
							}
							else {
								query = "INSERT INTO CART VALUES ('" + bookID + "','" + bookName + "','" + QuantityComboBox.getSelectedItem() + "','" + String.valueOf(total_amount) + "')";
								if(!(user.update(query) >= 1))
									JOptionPane.showMessageDialog(frame, "Error!\nContact Administrator","Add to cart", JOptionPane.ERROR_MESSAGE, null);
								else
									btnCart.setEnabled(false);
							}
						}catch(Exception e1) {
							System.out.println(e1);
						}
					}
				});
				resultPanel.add(btnCart);
				
				btnLike.setBounds(1040, 22, 40, 35);
				btnLike.setBackground(frame.getBackground());
				btnLike.setBorder(null);
				btnLike.setContentAreaFilled(false);
				boolean flag_like = false;
				query = "SELECT BOOKNAME FROM WISHLIST";
				user.getData(query);
				user.data.next();
				btnLike.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\unliked.png"));
				btnLike.setDisabledIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\liked.png"));
				if(user.data.getRow() >= 1) {
					do {			
						if(user.data.getString("BOOKNAME").compareTo(bookName) == 0) {
							btnLike.setEnabled(false);
							flag_like = true;
							break;
						}
						
					}while(user.data.next());
					if(!flag_like) {
						btnLike.setEnabled(true);
					}
				}
				btnLike.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String query = "SELECT BOOKNAME FROM WISHLIST";
							boolean flag = false;
							user.getData(query);
							user.data.next();
							if(user.data.getRow() >= 1) {
								do {
									if(user.data.getString("BOOKNAME").compareTo(bookName) != 0)
										flag = true;
									else {
										flag = false;
										break;
									}
								}while(user.data.next());
								if(flag) {
									query = "INSERT INTO WISHLIST VALUES ('" + bookID + "','" + bookName + "')";
									if(!(user.update(query) >= 1))
										JOptionPane.showMessageDialog(frame, "Error!\nContact Administrator","Add to cart", JOptionPane.ERROR_MESSAGE, null);
									else
										btnLike.setEnabled(false);
								}
							}
							else {
								query = "INSERT INTO WISHLIST VALUES('" + bookID + "','" + bookName + "')";
								if(!(user.update(query) >= 1))
									JOptionPane.showMessageDialog(frame, "Error!\nContact Administrator","Add to cart", JOptionPane.ERROR_MESSAGE, null);
								else
									btnLike.setEnabled(false);
							}
						}catch(Exception e1) {
							System.out.println(e1);
						}
					}
				});
				resultPanel.add(btnLike);
				
				lblNum.setFont(new Font("Tahoma", Font.PLAIN, 18));
				lblNum.setBounds(566, 147, 45, 35);
				resultPanel.add(lblNum);
				
				priceAmount.setFont(new Font("Tahoma", Font.PLAIN, 30));
				priceAmount.setBounds(440, 365, 154, 55);
				resultPanel.add(priceAmount);
				resultPanel.setVisible(true);
				
				bookImagelbl.setIcon(new ImageIcon(imgLocation));
				bookNamelbl.setText(bookName);
				authorNamelbl.setText(authorName);
				edition_value.setText(String.valueOf(edition));
				lblNum.setText(String.valueOf(ratings));
				descriptionTextArea.removeAll();
				descriptionTextArea.setText(description);
				priceAmount.setText("Rs." + bookPriceAmount);
				lblCategoryOptions.setText(bookCategory);
				languageLabel.setText(language);
				resultPanel.repaint();
			}
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void confirmOrder(int id,String bookName,String quantity,double price) {
		
		JFrame confirmOrderFrame = new JFrame("5D Book-Order"); 
		confirmOrderFrame.setBounds(100, 100, 662, 678);
		confirmOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		confirmOrderFrame.setResizable(false);
		confirmOrderFrame.getContentPane().setLayout(null);
		
		JLabel lbltitile = new JLabel("CONFIRM YOUR ORDER");
		lbltitile.setForeground(Color.ORANGE);
		lbltitile.setBounds(215, 29, 214, 52);
		lbltitile.setFont(new Font("Tahoma", Font.PLAIN, 20));
		confirmOrderFrame.getContentPane().add(lbltitile);
		
		String[] header = {"Book ID","Book Name","Quantity","Price"};
		DefaultTableModel orderModel = new DefaultTableModel(0,4);
		orderModel.setColumnIdentifiers(header);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 164, 565, 224);
		
		JTable productTable = new JTable(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		orderModel.addRow(new Object[] {id,bookName,quantity,price});
		productTable.setModel(orderModel);
		productTable.setRowHeight(50);
		productTable.setFont(new Font("Serif", Font.PLAIN, 15));
		productTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 15));
		productTable.setBounds(45, 164, 565, 332);
		
		productTable.getColumnModel().getColumn(0).setPreferredWidth(1);
		productTable.getColumnModel().getColumn(2).setPreferredWidth(1);
		productTable.getColumnModel().getColumn(3).setPreferredWidth(1);
		cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
	    productTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	    productTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
	    productTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
	    scrollPane.setViewportView(productTable);
		confirmOrderFrame.getContentPane().add(scrollPane);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnContinue.setBounds(476, 535, 134, 52);
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateProfile()) {
					confirmOrderFrame.setVisible(false);
					new Payment(username,orderModel,String.valueOf(price));
				}
				else {
					confirmOrderFrame.setVisible(false);
					JOptionPane.showMessageDialog(frame, "Please update your Profile");
				}
			}
		});
		confirmOrderFrame.getContentPane().add(btnContinue);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCancel.setBounds(76, 535, 134, 52);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmOrderFrame.setVisible(false);
			}
		});
		confirmOrderFrame.getContentPane().add(btnCancel);
		
		JLabel lblSubTittle = new JLabel("Product Details");
		lblSubTittle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSubTittle.setBounds(47, 101, 155, 34);
		confirmOrderFrame.getContentPane().add(lblSubTittle);
		
		JLabel lblTotalAmount = new JLabel("Total Amount : ");
		lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalAmount.setBounds(366, 431, 142, 46);
		confirmOrderFrame.getContentPane().add(lblTotalAmount);
		
		JLabel lblAmount = new JLabel(String.valueOf(price));
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAmount.setBounds(513, 431, 97, 46);
		confirmOrderFrame.getContentPane().add(lblAmount);
		confirmOrderFrame.setVisible(true);
	}
	
	public static void confirmOrder(JTable billTable,Double billAmount) {
		
		JFrame confirmOrderFrame = new JFrame("5D Book-Order"); 
		confirmOrderFrame.setBounds(100, 100, 662, 678);
		confirmOrderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		confirmOrderFrame.setResizable(false);
		confirmOrderFrame.getContentPane().setLayout(null);
		
		JLabel lbltitile = new JLabel("CONFIRM YOUR ORDER");
		lbltitile.setForeground(Color.ORANGE);
		lbltitile.setBounds(215, 29, 214, 52);
		lbltitile.setFont(new Font("Tahoma", Font.PLAIN, 20));
		confirmOrderFrame.getContentPane().add(lbltitile);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 164, 565, 224);
		
		JTable productTable = new JTable(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		DefaultTableModel billModel = (DefaultTableModel) billTable.getModel();
		productTable.setModel(billModel);
		productTable.setRowHeight(50);
		productTable.setFont(new Font("Serif", Font.PLAIN, 15));
		productTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 15));
		productTable.setBounds(45, 164, 565, 332);
		
		productTable.getColumnModel().getColumn(0).setPreferredWidth(1);
		productTable.getColumnModel().getColumn(2).setPreferredWidth(1);
		productTable.getColumnModel().getColumn(3).setPreferredWidth(1);
		cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
	    productTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	    productTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
	    productTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
	    scrollPane.setViewportView(productTable);
		confirmOrderFrame.getContentPane().add(scrollPane);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateProfile()) {
					confirmOrderFrame.setVisible(false);
					new Payment(username,billModel,String.valueOf(billAmount));
				}
				else {
					confirmOrderFrame.setVisible(false);
					JOptionPane.showMessageDialog(frame, "Please update your Profile");
				}
			}
		});
		btnContinue.setBounds(476, 535, 134, 52);
		confirmOrderFrame.getContentPane().add(btnContinue);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCancel.setBounds(76, 535, 134, 52);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmOrderFrame.setVisible(false);
			}
		});
		confirmOrderFrame.getContentPane().add(btnCancel);
		
		JLabel lblSubTittle = new JLabel("Product Details");
		lblSubTittle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSubTittle.setBounds(47, 101, 155, 34);
		confirmOrderFrame.getContentPane().add(lblSubTittle);
		
		JLabel lblTotalAmount = new JLabel("Total Amount : ");
		lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalAmount.setBounds(366, 431, 142, 46);
		confirmOrderFrame.getContentPane().add(lblTotalAmount);
		
		JLabel lblAmount = new JLabel(String.valueOf(billAmount));
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAmount.setBounds(513, 431, 97, 46);
		confirmOrderFrame.getContentPane().add(lblAmount);
		confirmOrderFrame.setVisible(true);
	}
	
	public static boolean updateProfile() {
		
		String query = "SELECT * FROM USER_ADDRESS";
		connectDataBase database = new connectDataBase(username,"root","root@14");
		database.getData(query);
		
		try {
			database.data.next();
			if(database.data.getString("ADDRESS") != null && 
					database.data.getString("PHONE_NUMBER") != null &&
					database.data.getString("STATE") != null &&
					database.data.getString("ADDRESS") != null) {
					return true;
				}
				else
					return false;
			}catch(Exception e) {
				System.out.println(e);
			}
		return false;
	}
}
