package register;

import javax.swing.JFrame;
import login.Login;
import dbConnector.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Label;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;


public class CreateAccount {
	
	static JFrame frame,Messageframe;
	static JPasswordField password;
	static JPanel panel = new JPanel();
	private static JTextField firstName;
	private static JTextField lastName;
	private static JTextField emailID;
	private static JTextField userName;

	public static void getData() {
		
		frame = new JFrame("5D Books-Register");
		frame.setBounds(100, 100, 800, 710);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Create Account");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(294, 37, 206, 37);
		frame.getContentPane().add(lblNewLabel);
		
		
		panel.setBounds(40, 84, 701, 504);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("First Name");
		label.setFont(new Font("Nirmala UI", Font.PLAIN, 25));
		label.setBounds(55, 50, 164, 38);
		panel.add(label);
		
		Label label_1 = new Label("Last Name");
		label_1.setFont(new Font("Nirmala UI", Font.PLAIN, 25));
		label_1.setBounds(360, 50, 164, 38);
		panel.add(label_1);
		
		Label label_2 = new Label("Email");
		label_2.setFont(new Font("Nirmala UI", Font.PLAIN, 25));
		label_2.setBounds(55, 168, 164, 38);
		panel.add(label_2);
		
		Label label_2_1 = new Label("User Name");
		label_2_1.setFont(new Font("Nirmala UI", Font.PLAIN, 25));
		label_2_1.setBounds(55, 283, 164, 38);
		panel.add(label_2_1);
		
		Label label_2_2 = new Label("Password");
		label_2_2.setFont(new Font("Nirmala UI", Font.PLAIN, 25));
		label_2_2.setBounds(55, 389, 164, 38);
		panel.add(label_2_2);
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		password.setBounds(55, 446, 417, 35);
		panel.add(password);
		
		firstName = new JTextField();
		firstName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		firstName.setBounds(55, 109, 272, 35);
		panel.add(firstName);
		firstName.setColumns(10);
		
		lastName = new JTextField();
		lastName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lastName.setColumns(10);
		lastName.setBounds(362, 109, 272, 35);
		panel.add(lastName);
		
		emailID = new JTextField();
		emailID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailID.setColumns(10);
		emailID.setBounds(55, 228, 417, 35);
		panel.add(emailID);
		
		userName = new JTextField();
		userName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userName.setColumns(10);
		userName.setBounds(55, 337, 417, 35);
		panel.add(userName);
		
		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(firstName.getText().compareTo("") != 0 && lastName.getText().compareTo("") != 0
						&& emailID.getText().compareTo("") != 0 && userName.getText().compareTo("") != 0
						&& password.getText().compareTo("") != 0) {
					RegisterNewUser();
	
				}
				else
					JOptionPane.showMessageDialog(frame, "Somefields are missing.\nEnter all details to conitnue.", "Acocunt-New user", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(327, 598, 178, 46);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Login();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.setBounds(92, 47, 93, 29);
		frame.getContentPane().add(btnNewButton_1);
		frame.setVisible(true);
	}
	
	public static void RegisterNewUser(){
		
		try {
			connectDataBase dataBase = new connectDataBase("USER","root","root@14");
			String userPassword = new String(password.getPassword());
			String query = "INSERT INTO ACCOUNTS (FIRSTNAME,LASTNAME,USERNAME,EMAILID,PASSWORD) VALUES"
					+ "('" + firstName.getText() + "',"
					+ "'" + lastName.getText() + "',"
					+ "'" + userName.getText() + "',"
					+ "'" + emailID.getText() + "',"
					+ "'" + userPassword + "')";
			if(dataBase.update(query) >= 1) {
				createDB();
				JOptionPane.showMessageDialog(frame, "Account created Successfully \n Login to continue.");
			}
			else
				JOptionPane.showMessageDialog(frame, "The Account Already registered.\nTry to Login.");
		}catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "Error!\nPlease contact Administrator");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void createDB() {
		
		try {	
			connectDataBase database = new connectDataBase(userName.getText(),"root","root@14");
			database.createUserDatatbase();
			String query;
			query = "INSERT INTO ACCOUNT (FIRSTNAME,LASTNAME,USERNAME,EMAILID,PASSWORD) VALUES"
					+ "('" + firstName.getText() + "',"
					+ "'" + lastName.getText() + "',"
					+ "'" + userName.getText() + "',"
					+ "'" + emailID.getText() + "',"
					+ "'" + password.getText() + "')";
			if(!(database.update(query) >= 1))
				JOptionPane.showMessageDialog(frame, "Error!\nPlease contact Administrator");
			query = "INSERT INTO USER_ADDRESS (USERNAME) VALUES ('" + userName.getText() + "')";
			database.update(query);
		}catch(Exception e) {
			System.out.println(e);
		}	
	}
	
}