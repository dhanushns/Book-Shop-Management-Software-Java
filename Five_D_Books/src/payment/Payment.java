package payment;

import dbConnector.*;
import main.Home;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JRadioButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Payment {

	private JFrame frame;
	private JTextField pincodeField;
	private static JTextArea addressTextArea;
	private static boolean editFlag = false;
	private static JComboBox<String> stateComboBox,districtComboBox;
	private JTextField phoneNumberField;
	private static JButton btnSave,btnEdit;
	private String username,fullname,price;
	private static JPanel headPanel,userPanel;
	private static String address,state,district,phone_number,emailID;
	private int pincode;
	private JTextField lblphone_number;
	public static JTable billTable;
	public static DefaultTableModel model;
	static DefaultTableCellRenderer cellRenderer;
	private JTextField lblAccount;
	private JTable productTable;
	private static JLabel lblTitle;
	public static double total_amount;

	public Payment(String username,DefaultTableModel model,String price){
		this.username = username;
		Payment.model = model;
		this.price = price;
		getUserData();
		initialize();
	}
	
	public void getUserData() {
		try {
			connectDataBase database = new connectDataBase(username,"root","root@14");
			String query = "SELECT * FROM ACCOUNT";
			database.getData(query);
			database.data.next();
			emailID = database.data.getString("EMAILID");
			fullname = database.data.getString("FIRSTNAME") + " " + database.data.getString("LASTNAME");
			query = "SELECT * FROM USER_ADDRESS";
			database.getData(query);
			database.data.next();
			address = database.data.getString("ADDRESS");
			phone_number = String.valueOf(database.data.getLong("PHONE_NUMBER"));
			state = database.data.getString("STATE");
			district = database.data.getString("DISTRICT");
			pincode = Integer.parseInt(database.data.getString("PINCODE"));
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private void initialize() {
		
		frame = new JFrame("5D Book-Payment");
		frame.setBounds(100, 100, 727, 743);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		headPanel = new JPanel();
		headPanel.setBounds(29, 30, 655, 74);
		frame.getContentPane().add(headPanel);
		headPanel.setLayout(null);
		
		lblTitle = new JLabel();
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitle.setBounds(271, 10, 200, 54);
		headPanel.add(lblTitle);
		
		userPanel = new JPanel();
		userPanel.setBounds(29, 132, 674, 564);
		frame.getContentPane().add(userPanel);
		userPanel.setLayout(null);
		addressDetails();
	}
	
	public void addressDetails() {
		
		lblTitle.setText("Address");
		JLabel lblUserName = new JLabel(username.toUpperCase());
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblUserName.setBounds(32, 28, 468, 46);
		userPanel.add(lblUserName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAddress.setBounds(32, 98, 83, 46);
		userPanel.add(lblAddress);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editFlag = true;
				addressTextArea.setEditable(editFlag);
				stateComboBox.setEnabled(editFlag);
				districtComboBox.setEnabled(editFlag);
				phoneNumberField.setEnabled(editFlag);
				pincodeField.setEditable(editFlag);
				btnEdit.setVisible(false);
				btnSave.setVisible(true);
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEdit.setBounds(575, 10, 70, 25);
		userPanel.add(btnEdit);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateProfile();
				editFlag = false;
				addressTextArea.setEditable(editFlag);
				stateComboBox.setEnabled(editFlag);
				districtComboBox.setEnabled(editFlag);
				phoneNumberField.setEnabled(editFlag);
				pincodeField.setEditable(editFlag);
				btnSave.setVisible(false);
				btnEdit.setVisible(true);
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBounds(575, 10, 70, 25);
		btnSave.setVisible(false);
		userPanel.add(btnSave);
		
		addressTextArea = new JTextArea(address);
		addressTextArea.setEditable(editFlag);
		addressTextArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addressTextArea.setBounds(32, 150, 313, 120);
		addressTextArea.setLineWrap(true);
		userPanel.add(addressTextArea);
		
		JLabel lblPhone_number = new JLabel("Phone number");
		lblPhone_number.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhone_number.setBounds(32, 292, 130, 46);
		userPanel.add(lblPhone_number);
		
		JLabel lblState = new JLabel("State");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblState.setBounds(392, 114, 55, 25);
		userPanel.add(lblState);
		stateComboBox = new JComboBox<String>();
		stateComboBox.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		stateComboBox.setFont(new Font("Verdana", Font.PLAIN, 15));
		stateComboBox.setBounds(479, 109, 143, 33);
		stateComboBox.addItem("None");
		userPanel.add(stateComboBox);
		
		districtComboBox = new JComboBox<String>();
		districtComboBox.addItem("NONE");
		districtComboBox.setFont(new Font("Verdana", Font.PLAIN, 15));
		districtComboBox.setBounds(479, 173, 143, 33);
		userPanel.add(districtComboBox);
		
		try {
			String query;
			stateComboBox.getModel().setSelectedItem(state);
			districtComboBox.getModel().setSelectedItem(district);
			
			connectDataBase country = new connectDataBase("COUNTRY","root","root@14");
			query = "SELECT STATE FROM CITY GROUP BY STATE";
			country.getData(query);
			while(country.data.next())
				stateComboBox.addItem(country.data.getString("STATE"));
			country.data.close();
			country.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		stateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connectDataBase country = new connectDataBase("COUNTRY","root","root@14");
					String query = "SELECT DISTRICT FROM CITY WHERE STATE = '" + stateComboBox.getSelectedItem().toString() + "'";
					country.getData(query);
					districtComboBox.removeAllItems();
					districtComboBox.addItem("None");
					while(country.data.next()) {
						districtComboBox.addItem(country.data.getString("DISTRICT"));
					}
					country.data.close();
					country.connect.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		stateComboBox.setEnabled(editFlag);
		districtComboBox.setEnabled(editFlag);
		
		JLabel lblDistrict = new JLabel("District");
		lblDistrict.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDistrict.setBounds(392, 177, 70, 25);
		userPanel.add(lblDistrict);
		
		JLabel lblPincode = new JLabel("Pincode");
		lblPincode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPincode.setBounds(392, 245, 70, 25);
		userPanel.add(lblPincode);
		
		pincodeField = new JTextField(String.valueOf(pincode));
		pincodeField.setDisabledTextColor(UIManager.getColor("Button.focus"));
		pincodeField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pincodeField.setColumns(10);
		pincodeField.setBounds(479, 238, 83, 36);
		pincodeField.setEditable(editFlag);
		userPanel.add(pincodeField);
		
		phoneNumberField = new JTextField(phone_number);
		phoneNumberField.setDisabledTextColor(UIManager.getColor("Button.foreground"));
		phoneNumberField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneNumberField.setBounds(32, 340, 173, 36);
		phoneNumberField.setEnabled(editFlag);
		userPanel.add(phoneNumberField);
		phoneNumberField.setColumns(10);
		
		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext.setBounds(500, 480, 134, 52);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(editFlag) {
					JOptionPane.showMessageDialog(frame, "Save to continue.");
				}
				else {
					userPanel.removeAll();
					userPanel.repaint();
					orderSummary();
				}
			}
		});
		userPanel.add(btnNext);
		headPanel.setVisible(true);
		userPanel.setVisible(true);
		userPanel.repaint();
		frame.repaint();
	}
	
	public void orderSummary() {
		
		JButton btnBack,btnNext;
		
		lblTitle.setText("Order Summary");
		
		lblphone_number = new JTextField(phone_number);
		lblphone_number.setBorder(null);
		lblphone_number.setDisabledTextColor(UIManager.getColor("Button.focus"));
		lblphone_number.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblphone_number.setBounds(29, 202, 129, 34);
		userPanel.add(lblphone_number);
		lblphone_number.setEditable(false);
		lblphone_number.setColumns(10);
		
		JLabel lblDeliverTo = new JLabel("Deliver to : ");
		lblDeliverTo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDeliverTo.setBounds(29, 10, 160, 42);
		userPanel.add(lblDeliverTo);
		
		JLabel lblFirstName = new JLabel(fullname);
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblFirstName.setBounds(29, 48, 517, 57);
		userPanel.add(lblFirstName);
		
		JTextArea addressArea = new JTextArea(address);
		addressArea.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
		addressArea.setBounds(29, 106, 302, 130);
		addressArea.setBackground(frame.getBackground());
		addressArea.setLineWrap(true);
		addressArea.setEditable(false);
		userPanel.add(addressArea);
		
		JLabel lblProductDetails = new JLabel("Product Details");
		lblProductDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProductDetails.setBounds(28, 237, 160, 42);
		userPanel.add(lblProductDetails);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 285, 592, 164);
		userPanel.add(scrollPane);
		
		productTable = new JTable(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		productTable.setModel(model);
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
		
		JLabel lblTotalAmount = new JLabel("Total Amount : ");
		lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalAmount.setBounds(373, 235, 142, 46);
		userPanel.add(lblTotalAmount);
		
		JLabel lblAmount = new JLabel(String.valueOf(price));
		lblAmount.setBounds(507, 235, 138, 46);
		userPanel.add(lblAmount);
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.setVisible(true);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(29, 480, 134, 52);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.removeAll();
				userPanel.repaint();
				addressDetails();
			}
		});
		userPanel.add(btnBack);
		
		btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext.setBounds(500, 480, 134, 52);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.removeAll();
				userPanel.repaint();
				payment();
			}
		});
		userPanel.add(btnNext);
	}
	
	public void payment() {
		
		lblTitle.setText("Payment");
		total_amount = Double.parseDouble(price);
		total_amount += 40;
		
		JLabel lblSuggestion = new JLabel("Suggested for you");
		lblSuggestion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSuggestion.setBounds(23, 25, 233, 32);
		userPanel.add(lblSuggestion);
		
		JRadioButton btnGPay = new JRadioButton("Google Pay UPI");
		btnGPay.setSelected(true);
		btnGPay.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGPay.setBounds(23, 76, 163, 32);
		userPanel.add(btnGPay);
		
		JLabel GpayIcon = new JLabel("GPay img");
		GpayIcon.setBounds(600, 83, 45, 25);
		userPanel.add(GpayIcon);
		
		JLabel lblOptions = new JLabel("All other options");
		lblOptions.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOptions.setBounds(23, 147, 163, 32);
		userPanel.add(lblOptions);
		
		JRadioButton rdbtnUpi = new JRadioButton("UPI");
		rdbtnUpi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnUpi.setBounds(23, 190, 163, 32);
		userPanel.add(rdbtnUpi);
		
		JRadioButton rdbtnWalletPostpaid = new JRadioButton("Wallet / Postpaid");
		rdbtnWalletPostpaid.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnWalletPostpaid.setBounds(23, 232, 163, 32);
		userPanel.add(rdbtnWalletPostpaid);
		
		JRadioButton rdbtnCreditDebit = new JRadioButton("Credit / Debit / ATM Card");
		rdbtnCreditDebit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnCreditDebit.setBounds(23, 272, 233, 32);
		userPanel.add(rdbtnCreditDebit);
		
		JRadioButton rdbtnNetBanking = new JRadioButton("Net Banking");
		rdbtnNetBanking.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnNetBanking.setBounds(23, 317, 130, 32);
		userPanel.add(rdbtnNetBanking);
		
		JRadioButton rdbtnCashOnDelivery = new JRadioButton("Cash on Delivery");
		rdbtnCashOnDelivery.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtnCashOnDelivery.setBounds(23, 356, 163, 32);
		userPanel.add(rdbtnCashOnDelivery);
		
		lblAccount = new JTextField();
		lblAccount.setBorder(null);
		lblAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAccount.setText(emailID);
		lblAccount.setBounds(47, 103, 209, 32);
		lblAccount.setBackground(frame.getBackground());
		userPanel.add(lblAccount);
		lblAccount.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(290, 190, 355, 215);
		userPanel.add(scrollPane);
		
		scrollPane.setViewportView(productTable);
		
		JLabel lblProductDetails = new JLabel("Product Details");
		lblProductDetails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProductDetails.setBounds(290, 147, 143, 32);
		userPanel.add(lblProductDetails);
		
		JLabel lblCharge = new JLabel("Delivery charge : ");
		lblCharge.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCharge.setBounds(23, 430, 163, 32);
		userPanel.add(lblCharge);
		
		JLabel lblChargeAmount = new JLabel("Rs.40");
		lblChargeAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChargeAmount.setBounds(183, 432, 58, 32);
		userPanel.add(lblChargeAmount);
		
		JLabel lblTotalAmount = new JLabel("Total Amount : ");
		lblTotalAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTotalAmount.setBounds(368, 430, 143, 32);
		userPanel.add(lblTotalAmount);
		
		JLabel lblAmount = new JLabel(String.valueOf(total_amount));
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAmount.setBounds(515, 430, 130, 32);
		userPanel.add(lblAmount);
		
		JButton btnPay = new JButton("Pay");
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPay.setBounds(500, 480, 134, 52);
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Payment Successfull.\nThank you.");
				updatedb();
				frame.setVisible(false);
			}
		});
		userPanel.add(btnPay);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(29, 480, 134, 52);
		userPanel.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.removeAll();
				userPanel.repaint();
				orderSummary();
			}
		});
	}
	
	public void updateProfile() {
		
		String query;
		String state = stateComboBox.getSelectedItem().toString();
		String district = districtComboBox.getSelectedItem().toString();
		
		address = addressTextArea.getText();
		phone_number = phoneNumberField.getText();
		state = stateComboBox.getSelectedItem().toString();
		district = districtComboBox.getSelectedItem().toString();
		pincode = Integer.parseInt(pincodeField.getText());
		
		connectDataBase database = new connectDataBase(username,"root","root@14");
		if(state != "None" && district != "None") {
			if(address.compareTo("") != 0 && 
					phone_number.compareTo("") != 0 &&
					String.valueOf(pincode).compareTo("") != 0) {
				query = "UPDATE USER_ADDRESS SET ADDRESS = '" 
				+ address + "',PHONE_NUMBER = '" 
				+ phone_number + "',PINCODE = '"
				+ pincode + "',STATE = '" + state + "',DISTRICT = '" + district + "'";
				if(database.update(query) > 0) 
					JOptionPane.showMessageDialog(frame, "Successfully updated...");
				else 
					JOptionPane.showMessageDialog(frame, "Error!\nCan't update your profile\nContact Administrator");
			}
			else
				JOptionPane.showMessageDialog(frame, "Please update your Profile");
		}
		else 
			JOptionPane.showMessageDialog(frame, "Please update your Profile");
	}
	
	public void updatedb() {
		
		try {
			
			connectDataBase database = new connectDataBase("ManageBooks","root","root@14");
			connectDataBase db = new connectDataBase(username,"root","root@14");
			String query;
			SimpleDateFormat format = new SimpleDateFormat("yyy/MM/dd");
			Date date = new Date();
			String orderdDate = format.format(date);
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 7);
			dt = c.getTime();
			String deliveryDate = format.format(dt);
			for(int i = 0 ; i < productTable.getRowCount();i++) {
				
				String bookName = productTable.getValueAt(i, 1).toString();
				int quantity = Integer.parseInt((String) productTable.getValueAt(i, 2));
				query = "SELECT QUANTITY FROM BOOKS WHERE BOOKNAME = '" + bookName + "'";
				database.getData(query);
				database.data.next();
				int totalQuantity = database.data.getInt("QUANTITY") - quantity;
				
				query = "UPDATE BOOKS SET QUANTITY = '" + totalQuantity + "' WHERE BOOKNAME = '" + bookName + "'";
				if(!(database.update(query) >= 1)) 
					JOptionPane.showMessageDialog(frame, "Error!\nPayment unsuccessfull\nContact Administrator");
						
				query = "SELECT * FROM BOOKS WHERE BOOKNAME = '" + bookName + "'";
				database.getData(query);
				database.data.next();
				query = "INSERT INTO MYORDERS VALUES('" 
				+ database.data.getString("BOOKID") 
				+ "','" + bookName 
				+ "','" + quantity 
				+ "','" + total_amount 
				+ "','" + orderdDate 
				+ "','" + deliveryDate 
				+ "'," +  false + ")"; 
				if(!(db.update(query) >= 1))
					JOptionPane.showMessageDialog(frame, "Error!\nPayment unsuccessfull\nContact Administrator");
			}
			database.data.close();
			database.connect.close();
			Home.billAmount = 0;
			Home.lblBillRupee.setText("Rs.0");
			Home.model.getDataVector().removeAllElements();
			model.fireTableDataChanged();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
