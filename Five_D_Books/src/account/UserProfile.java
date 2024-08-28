package account;

import dbConnector.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class UserProfile {

	public JFrame frame;
	public JTextField firstNameTextFiled;
	public JTextField lastNameTextFiled;
	public JTextField userNameTextField;
	public JTextField emailTextField;
	public JTextField phoneTextField;
	public JTextField pincodeTextField;
	public JTextPane addressTextPane;
	public JPanel profilePanel;
	public JLabel lblFirstName;
	public static String username;
	JComboBox<String> districtComboBox,stateComboBox;
	
	public UserProfile(String username){
		UserProfile.username = username;
		initialize();
		userData();
	}
	
	public void userData() {
		
		try {
			connectDataBase dataBase = new connectDataBase(username,"root","root@14");
			String query = "SELECT * FROM ACCOUNT";
			dataBase.getData(query);
			dataBase.data.next();
			lblFirstName.setText(dataBase.data.getString("FIRSTNAME").toUpperCase());
			firstNameTextFiled.setText(dataBase.data.getString("FIRSTNAME"));
			lastNameTextFiled.setText(dataBase.data.getString("LASTNAME"));
			userNameTextField.setText(dataBase.data.getString("USERNAME"));
			emailTextField.setText(dataBase.data.getString("EMAILID"));
			query = "SELECT * FROM USER_ADDRESS";
			dataBase.getData(query);
			dataBase.data.next();
			if(dataBase.data.getString("ADDRESS") != null) {
				if(dataBase.data.getString("ADDRESS").compareTo("") != 0) {
					addressTextPane.setText(dataBase.data.getString("ADDRESS"));
				}
			}
		
			if(dataBase.data.getString("PHONE_NUMBER") != null) {
				if(dataBase.data.getString("PHONE_NUMBER").compareTo("") != 0) {
					String phone_number = String.valueOf(dataBase.data.getLong("PHONE_NUMBER"));
					phoneTextField.setText(phone_number);
				}	
			}
			
			if(dataBase.data.getString("PINCODE") != null) {
				if(dataBase.data.getString("PINCODE").compareTo("") != 0) {
					pincodeTextField.setText(dataBase.data.getString("PINCODE"));
				}
			}
			dataBase.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}	
	}
	
	public void updateProfile() {
		
		String query;
		String state = stateComboBox.getSelectedItem().toString();
		String district = districtComboBox.getSelectedItem().toString();
		connectDataBase database = new connectDataBase(username,"root","root@14");
		if(addressTextPane.getText() != null && 
				phoneTextField.getText()!= null &&
				pincodeTextField.getText() != null &&
				state != "None" && district != "None") {
			if(addressTextPane.getText().compareTo("") != 0 && 
					phoneTextField.getText().compareTo("") != 0 &&
					pincodeTextField.getText().compareTo("") != 0) {
				String phone_number = String.valueOf(phoneTextField.getText());
				query = "UPDATE USER_ADDRESS SET ADDRESS = '" 
				+ addressTextPane.getText()+ "',PHONE_NUMBER = '" 
				+ phone_number + "',PINCODE = '"
				+ pincodeTextField.getText() + "',STATE = '" + state + "',DISTRICT = '" + district + "'";
				if(database.update(query) >= 1) {
					JOptionPane.showMessageDialog(frame, "Successfully updated...");
					connectDataBase db = new connectDataBase("USER","root","root@14");
					query = "UPDATE ACCOUNTS SET PHONE_NUMBER = '" + phone_number + "' WHERE USERNAME = '" + username + "'";
					if(!(db.update(query) >= 1))
						JOptionPane.showMessageDialog(frame, "Error!\nCan't update your profile\nContact Administrator","Account", JOptionPane.ERROR_MESSAGE, null);
				}
				else 
					JOptionPane.showMessageDialog(frame, "Error!\nCan't update your profile\nContact Administrator","Account", JOptionPane.ERROR_MESSAGE, null);
			}
			else
				JOptionPane.showMessageDialog(frame, "Please update your Profile");
		}
		else 
			JOptionPane.showMessageDialog(frame, "Please update your Profile");
	}
	

	public void initialize() {
		frame = new JFrame("My Account");
		frame.setBounds(100, 100, 853, 699);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JPanel helloPanel = new JPanel();
		helloPanel.setBounds(0, 0, 839, 172);
		frame.getContentPane().add(helloPanel);
		helloPanel.setLayout(null);
		
		JLabel lblHello = new JLabel("Hello,");
		lblHello.setFont(new Font("Vijaya", Font.PLAIN, 39));
		lblHello.setBounds(34, 88, 96, 61);
		helloPanel.add(lblHello);
		
		lblFirstName = new JLabel();
		lblFirstName.setFont(new Font("Vijaya", Font.BOLD, 50));
		lblFirstName.setBounds(123, 85, 291, 63);
		helloPanel.add(lblFirstName);
		
		JLabel profileIcon = new JLabel("");
		profileIcon.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\man.png"));
		profileIcon.setBounds(672, 23, 142, 139);
		helloPanel.add(profileIcon);
		
		profilePanel = new JPanel();
		profilePanel.setBounds(0, 169, 839, 493);
		frame.getContentPane().add(profilePanel);
		profilePanel.setLayout(null);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		firstNameLabel.setBounds(20, 51, 125, 46);
		profilePanel.add(firstNameLabel);
		
		firstNameTextFiled = new JTextField();
		firstNameTextFiled.setFont(new Font("Tahoma", Font.PLAIN, 18));
		firstNameTextFiled.setBounds(160, 51, 234, 39);
		profilePanel.add(firstNameTextFiled);
		firstNameTextFiled.setColumns(10);
		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		lastNameLabel.setBounds(20, 115, 125, 46);
		profilePanel.add(lastNameLabel);
		
		lastNameTextFiled = new JTextField();
		lastNameTextFiled.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lastNameTextFiled.setColumns(10);
		lastNameTextFiled.setBounds(160, 115, 234, 39);
		profilePanel.add(lastNameTextFiled);
		
		JLabel userNameLabel = new JLabel("User Name");
		userNameLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		userNameLabel.setBounds(20, 185, 125, 46);
		profilePanel.add(userNameLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userNameTextField.setColumns(10);
		userNameTextField.setBounds(160, 185, 234, 39);
		profilePanel.add(userNameTextField);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		emailLabel.setBounds(20, 251, 125, 46);
		profilePanel.add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailTextField.setColumns(10);
		emailTextField.setBounds(160, 251, 234, 39);
		profilePanel.add(emailTextField);
		
		JLabel phoneLabel = new JLabel("Phone No");
		phoneLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		phoneLabel.setBounds(20, 318, 125, 46);
		profilePanel.add(phoneLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phoneTextField.setColumns(10);
		phoneTextField.setBounds(160, 318, 234, 39);
		profilePanel.add(phoneTextField);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		addressLabel.setBounds(20, 394, 125, 46);
		profilePanel.add(addressLabel);
		
		JLabel districtLabel = new JLabel("District");
		districtLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		districtLabel.setBounds(452, 120, 125, 46);
		profilePanel.add(districtLabel);
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		stateLabel.setBounds(452, 59, 125, 46);
		profilePanel.add(stateLabel);
		
		stateComboBox = new JComboBox<String>();
		stateComboBox.setFont(new Font("Verdana", Font.PLAIN, 15));
		stateComboBox.setBounds(547, 59, 186, 39);
		stateComboBox.addItem("None");
		profilePanel.add(stateComboBox);
		
		districtComboBox = new JComboBox<String>();
		districtComboBox.addItem("NONE");
		districtComboBox.setFont(new Font("Verdana", Font.PLAIN, 15));
		districtComboBox.setBounds(547, 123, 186, 39);
		profilePanel.add(districtComboBox);
		
		try {
			connectDataBase database = new connectDataBase(username,"root","root@14");
			String query;
			query = "SELECT STATE,DISTRICT FROM USER_ADDRESS";
			database.getData(query);
			database.data.next();
			if(database.data.getRow() >= 1) {
				String state = database.data.getString("STATE");
				String district = database.data.getString("DISTRICT");
				if(state != null && district != null) {
					stateComboBox.getModel().setSelectedItem(state);
					districtComboBox.getModel().setSelectedItem(district);
				}
			}
			database.data.close();
			database.connect.close();
			
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
		
		
		JLabel pincodeLabel = new JLabel("Pincode");
		pincodeLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		pincodeLabel.setBounds(452, 185, 91, 46);
		profilePanel.add(pincodeLabel);
		
		pincodeTextField = new JTextField();
		pincodeTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pincodeTextField.setColumns(10);
		pincodeTextField.setBounds(547, 185, 100, 39);
		profilePanel.add(pincodeTextField);
		
		JButton saveButton = new JButton("Save and Continue");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateProfile();
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		saveButton.setBounds(625, 418, 186, 46);
		profilePanel.add(saveButton);
		
		userNameTextField.setEditable(false);
		emailTextField.setEditable(false);
		
		addressTextPane = new JTextPane();
		addressTextPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressTextPane.setBounds(160, 380, 234, 103);
		profilePanel.add(addressTextPane);
		
		frame.setVisible(true);
	}
}
