package login;

import main.Home;
import dbConnector.*;
import register.CreateAccount;
import recoverAccount.RecoverAccount;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;

public class Login {

	public static JFrame frame;
	public static JTextField username;
	public static JPasswordField password;
	public static JPanel panel;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Login() {
		
		String username,data,query;
		try {
			connectDataBase database = new connectDataBase("user","root","root@14");
			query = "SELECT * FROM LOGINID";
			database.getData(query);
			database.data.next();
			if(database.data.getRow() == 1) {
				if(database.data.getString("USERNAME/EMAIL").compareTo("") != 0) {
					data = database. data.getString("USERNAME/EMAIL");
					query = "SELECT USERNAME FROM ACCOUNTS WHERE USERNAME = '" + data + "' OR EMAILID = '" + data + "'";
					database.getData(query);
					database.data.next();
					username = database.data.getString("USERNAME");
					new Home(username);
				}else {
					initialize();
				}
			}
			else
				initialize();
			database.data.close();
			database.connect.close();
		}catch(Exception e) {System.out.println(e);}
	}

	public static void initialize() {
		
		frame = new JFrame("5D Book-Login");
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 700);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(44, 143, 669, 428);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(69, 78, 142, 46);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(69, 166, 127, 42);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panel.add(lblPassword);
		
		JLabel lblNewLabel_1 = new JLabel("Don't have an Account?");
		lblNewLabel_1.setBounds(133, 373, 193, 21);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Create Account");
		btnNewButton.setBounds(336, 368, 157, 33);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				CreateAccount.getData();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(btnNewButton);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(275, 280, 133, 33);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userLogin();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(btnLogin);
		
		username = new JTextField();
		username.setBounds(250, 86, 327, 40);
		username.setToolTipText("username/email");
		username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setToolTipText("password");
		password.setBounds(250, 173, 327, 40);
		password.setFont(new Font("Tahoma", Font.PLAIN, 17));
		panel.add(password);
		
		String hideIcon = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\hidden.png";
		String showIcon = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\show.png";
		JLabel visiblity = new JLabel("");
		visiblity.setBounds(586, 182, 31, 26);
		panel.add(visiblity);
		visiblity.setIcon(new ImageIcon(hideIcon));
		
		visiblity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String icon = visiblity.getIcon().toString();
				if(icon.compareTo(hideIcon) == 0) {
					visiblity.setIcon(new ImageIcon(showIcon));
					password.setEchoChar((char)0);
				}
				else{
					visiblity.setIcon(new ImageIcon(hideIcon));
					password.setEchoChar('*');
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 45));
		lblNewLabel.setBounds(303, 48, 191, 69);
		frame.getContentPane().add(lblNewLabel);
		frame.setVisible(true);
	}

	public static void userLogin() {
		
		try {	
			String userPassword = new String(password.getPassword());
			boolean flagName = false,flagEmail = false;
			JLabel nameWarningField = new JLabel();
			nameWarningField.setForeground(Color.DARK_GRAY);
			nameWarningField.setBackground(Color.YELLOW);
			nameWarningField.setText("<--Required field");
			nameWarningField.setBounds(588, 75, 100, 59);
			JLabel passwordWarningField = new JLabel();
			passwordWarningField.setForeground(Color.DARK_GRAY);
			passwordWarningField.setBackground(Color.YELLOW);
			passwordWarningField.setText("<--Required field");
			passwordWarningField.setBounds(587, 160, 100, 59);
			if(username.getText().compareTo("") == 0) {	
				panel.add(nameWarningField);
				SwingUtilities.updateComponentTreeUI(panel);
				frame.repaint();
			}
			else {
				panel.remove(nameWarningField);
				SwingUtilities.updateComponentTreeUI(panel);
				frame.repaint();
			}
			if(userPassword.compareTo("") == 0) {
				panel.add(passwordWarningField);
				SwingUtilities.updateComponentTreeUI(panel);
				frame.repaint();
			}
			else {
				panel.remove(passwordWarningField);
				SwingUtilities.updateComponentTreeUI(panel);
				frame.repaint();
			}
			connectDataBase database = new connectDataBase("user","root","root@14");
			String query = "SELECT USERNAME,EMAILID FROM ACCOUNTS";
			database.getData(query);
			while(database.data.next()) {
				if(database.data.getString("username").compareTo(username.getText()) == 0){
					flagName = true;
					break;
				}
				else if(database.data.getString("emailId").compareTo(username.getText()) == 0) {
					flagEmail = true;
					break;
				}
			}
			if(flagName) {
				query = "SELECT PASSWORD FROM ACCOUNTS WHERE USERNAME = " + "'" + username.getText() + "'";
				database.getData(query);
				while(database.data.next()) {
					if(database.data.getString("password").compareTo(userPassword) == 0) {
						flagName = true;
						break;
					}
					else {
						flagName = false;
					}
				}
			}
			else if(flagEmail) {
				query = "SELECT PASSWORD FROM ACCOUNTS WHERE EMAILID = " + "'" + username.getText() + "'";
				database.getData(query);
				while(database.data.next()) {
					if(database.data.getString("password").compareTo(userPassword) == 0) {
						flagEmail = true; 	
						break;
					}
					else {
						flagEmail = false;
					}
				}
			}
			if(!flagName && !flagEmail && username.getText().compareTo("") != 0 && userPassword.compareTo("") != 0) {
				
				JLabel lblNewLabel_2 = new JLabel("Invalid username/password");
				lblNewLabel_2.setForeground(Color.RED);
				lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblNewLabel_2.setBounds(275, 234, 202, 21);
				panel.add(lblNewLabel_2);
				
				JButton btnForgetPassword = new JButton("Forget password?");
				btnForgetPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
				btnForgetPassword.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.setVisible(false);
						new RecoverAccount(0,username.getText());
					}
				});
				btnForgetPassword.setBounds(475, 235, 135, 21);
				panel.add(btnForgetPassword);
				SwingUtilities.updateComponentTreeUI(panel);
			}
			if(flagName || flagEmail) {
				frame.setVisible(false);
				query = "INSERT INTO LOGINID VALUES('" + username.getText() + "')";
				database.update(query);
				query = "SELECT USERNAME FROM ACCOUNTS WHERE USERNAME = '" + username.getText() + "' OR EMAILID = '" + username.getText() + "'";
				database.getData(query);
				database.data.next();
				String username = database.data.getString("USERNAME");
				new Home(username);
			}
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
