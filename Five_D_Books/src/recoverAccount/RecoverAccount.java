package recoverAccount;

import dbConnector.*;
import login.*;
import main.Home;
import register.CreateAccount;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class RecoverAccount {

	private static JFrame frame;
	private static JTextField email;
	private static JPasswordField password;
	private static JPanel userPanel;
	private static String data,firstName,username = "",userEmail;
	private static int gate = 0;

	public RecoverAccount(int gate,String email) {
		RecoverAccount.gate = gate;
		RecoverAccount.userEmail = email;
		if(gate == 1) {
			connectDataBase db = new connectDataBase("USER","root","root@14");
			String query = "SELECT USERNAME FROM ACCOUNTS WHERE USERNAME = '" + userEmail + "' OR EMAILID = '" + userEmail + "'";
			db.getData(query);
			try {
				db.data.next();
				username = db.data.getString("USERNAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		initialize();
	}

	private static void initialize() {
		
		frame = new JFrame("5D Book-Change password");
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 759, 671);
		frame.getContentPane().setLayout(null);
		
		userPanel = new JPanel();
		userPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		userPanel.setBounds(73, 87, 583, 513);
		frame.getContentPane().add(userPanel);
		userPanel.setLayout(null);
		frame.setVisible(true);
		if(gate == 0)
			getEmail();
		else if(gate == 1)
			changePassword(username);
	}
	
	public static void getEmail() {
		
		JLabel lblemail_username = new JLabel("Email/username");
		lblemail_username.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblemail_username.setBounds(28, 142, 160, 30);
		userPanel.add(lblemail_username);
		
		email = new JTextField();
		email.setForeground(Color.BLACK);
		email.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		email.setBounds(28, 182, 409, 46);
		userPanel.add(email);
		email.setColumns(10);
		
		JLabel lblTitle = new JLabel("Account recovery");
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setBounds(180, 10, 229, 51);
		userPanel.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data = email.getText();
				checkEmail();
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNext.setBounds(394, 390, 149, 46);
		userPanel.add(btnNext);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(30, 390, 149, 46);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Login();
			}
		});
		userPanel.add(btnBack);
		
		JButton btnForgetEmail = new JButton("Forget email?");
		btnForgetEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.removeAll();
				userPanel.repaint();
				getUserAccount();
			}
		});
		btnForgetEmail.setContentAreaFilled(false);
		btnForgetEmail.setBorder(null);
		btnForgetEmail.setBackground(frame.getBackground());
		btnForgetEmail.setForeground(Color.BLUE);
		btnForgetEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnForgetEmail.setBounds(28, 269, 78, 30);
		userPanel.add(btnForgetEmail);
		
		JButton btnCreateAccount = new JButton("Create account");
		btnCreateAccount.setContentAreaFilled(false);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				CreateAccount.getData();
			}
		});
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.setBorder(null);
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateAccount.setBounds(28, 309, 104, 33);
		userPanel.add(btnCreateAccount);
		
		JLabel lblSignIn = new JLabel("Sign in");
		lblSignIn.setForeground(Color.DARK_GRAY);
		lblSignIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSignIn.setBounds(246, 60, 70, 39);
		userPanel.add(lblSignIn);
		
		JLabel lblLogin = new JLabel("Already have an account?");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogin.setBounds(28, 341, 145, 13);
		userPanel.add(lblLogin);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.BLUE);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorder(null);
		btnLogin.setBounds(171, 337, 39, 21);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Login();
			}
		});
		userPanel.add(btnLogin);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		logo.setFont(new Font("Tahoma", Font.BOLD, 25));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
	}
	
	public static void checkEmail() {
		
		try {
			boolean flag = false;
			connectDataBase database = new connectDataBase("USER","root","root@14");
			String query = "SELECT FIRSTNAME,USERNAME,EMAILID FROM ACCOUNTS";
			database.getData(query);
			while(database.data.next()) {
				if(data.compareTo(database.data.getString("EMAILID")) == 0 || data.compareTo(database.data.getString("USERNAME")) == 0) {
					firstName = database.data.getString("FIRSTNAME");
					userEmail = database.data.getString("EMAILID");
					username = database.data.getString("USERNAME");
					flag = true;
					break;
				}
				else
					flag = false;
			}
			if(flag) {
				userPanel.removeAll();
				userPanel.repaint();
				getPassword();
			}
			else {
				JLabel invalidEmail = new JLabel("! couldn't find your account");
				invalidEmail.setForeground(Color.RED);
				invalidEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
				invalidEmail.setBounds(28, 238, 160, 15);
				userPanel.add(invalidEmail);
				email.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
				userPanel.repaint();
			}
			database.data.close();
			database.connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void getPassword() {
		
		JLabel lblFirstName = new JLabel("Hi " + firstName);
		lblFirstName.setForeground(Color.DARK_GRAY);
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFirstName.setBounds(230, 60, 200, 39);
		userPanel.add(lblFirstName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(28, 142, 160, 30);
		userPanel.add(lblPassword);
		
		password = new JPasswordField();
		password.setEchoChar('*');
		password.setForeground(Color.BLACK);
		password.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		password.setBounds(28, 182, 409, 46);
		userPanel.add(password);
		password.setColumns(10);
		
		String hideIcon = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\hidden.png";
		String showIcon = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\show.png";
		JLabel visiblity = new JLabel("");
		visiblity.setBounds(445, 186, 31, 26);
		userPanel.add(visiblity);
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
		
		JLabel lblTitle = new JLabel("Account recovery");
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setBounds(180, 10, 229, 51);
		userPanel.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JLabel invalidPassword = new JLabel("! Wrong password. Try again or click Forget password to reset it.");
		invalidPassword.setForeground(Color.RED);
		invalidPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		invalidPassword.setBounds(28, 238, 450, 15);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				int res = checkPassword(password.getText());
				if(res == 1) {
					userPanel.remove(invalidPassword);
					userPanel.repaint();
					password.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.GREEN, null));
					connectDataBase database = new connectDataBase("USER","root","root@14");
					String query = "INSERT INTO LOGINID VALUES('" + data + "')";
					if(database.update(query) >= 1) {
						frame.setVisible(false);
						new Login();
					}
					else
						JOptionPane.showMessageDialog(frame, "Error!\nPlease contact administrator.", "Error", JOptionPane.OK_OPTION);
				}
				else {
					userPanel.add(invalidPassword);
					password.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
					userPanel.repaint();
				}
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNext.setBounds(394, 390, 149, 46);
		userPanel.add(btnNext);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(30, 390, 149, 46);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.removeAll();
				userPanel.repaint();
				getEmail();
			}
		});
		userPanel.add(btnBack);
		
		JButton btnForgetPassword = new JButton("Forget password?");
		btnForgetPassword.setContentAreaFilled(false);
		btnForgetPassword.setBorder(null);
		btnForgetPassword.setBackground(frame.getBackground());
		btnForgetPassword.setForeground(Color.BLUE);
		btnForgetPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnForgetPassword.setBounds(28, 269, 100, 30);
		btnForgetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.remove(btnForgetPassword);
				userPanel.remove(lblPassword);
				userPanel.remove(password);
				userPanel.remove(btnNext);
				userPanel.remove(btnBack);
				userPanel.remove(invalidPassword);
				userPanel.remove(visiblity);
				userPanel.repaint();
				getPhoneNumber();
			}
		});
		userPanel.add(btnForgetPassword);
		
		JButton btnCreateAccount = new JButton("Create account");
		btnCreateAccount.setContentAreaFilled(false);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				CreateAccount.getData();
			}
		});
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.setBorder(null);
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateAccount.setBounds(28, 309, 104, 33);
		userPanel.add(btnCreateAccount);
		
		JLabel lblLogin = new JLabel("Already have an account?");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogin.setBounds(28, 341, 145, 13);
		userPanel.add(lblLogin);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.BLUE);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorder(null);
		btnLogin.setBounds(171, 337, 39, 21);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Login();
			}
		});
		userPanel.add(btnLogin);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("res\\\\Logo\\\\logo.png"));
		logo.setFont(new Font("Tahoma", Font.BOLD, 25));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
		userPanel.repaint();
	}
	
	public static int checkPassword(String userpassword) {
		
		try {
			connectDataBase database = new connectDataBase(username,"root","root@14");
			String query = "SELECT PASSWORD FROM ACCOUNT";
			database.getData(query);
			database.data.next();
			if(userpassword.compareTo(database.data.getString("PASSWORD")) == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public static void getUserAccount() {
		
		JButton btnBack;
		JLabel lblTitle = new JLabel("Account recovery");
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setBounds(180, 10, 229, 51);
		userPanel.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JLabel lblInput = new JLabel("Enter Phone number");
		lblInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInput.setBounds(40, 189, 193, 29);
		userPanel.add(lblInput);
		
		JTextField phone_number = new JTextField();
		phone_number.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phone_number.setBounds(40, 239, 188, 40);
		userPanel.add(phone_number);
		phone_number.setColumns(10);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(30, 389, 149, 46);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.removeAll();
				userPanel.repaint();
				if(username.compareTo("") == 0)
					getEmail();
				else {
					username = "";
					getUserAccount();
				}
			}
		});
		userPanel.add(btnBack);
		
		JTextPane message = new JTextPane();
		message.setForeground(SystemColor.windowBorder);
		message.setEditable(false);
		message.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 18));
		message.setBackground(frame.getBackground());
		message.setText("We can easily find you account by your phone number.");
		message.setBounds(80, 108, 458, 51);
		userPanel.add(message);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(phone_number.getText().compareTo("") != 0) {
					if(search_user(phone_number.getText())) {
						message.setText("Hey! We successfully recovered your account.");
						userPanel.remove(lblInput);
						userPanel.remove(phone_number);
						userPanel.remove(btnNext);
						userPanel.repaint();
						JLabel lblUsername = new JLabel("Username : ");
						lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblUsername.setBounds(60, 183, 108, 34);
						userPanel.add(lblUsername);
						
						JLabel lblEmail = new JLabel("Email : ");
						lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblEmail.setBounds(100, 238, 68, 34);
						userPanel.add(lblEmail);
						
						JLabel setUsername = new JLabel(username);
						setUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
						setUsername.setBounds(180, 183, 372, 34);
						userPanel.add(setUsername);
						
						JLabel setEmail = new JLabel(userEmail);
						setEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
						setEmail.setBounds(180, 238, 372, 34);
						userPanel.add(setEmail);
					}
					else {
						JOptionPane.showMessageDialog(frame, "Sorry!\nWe can't find your account.", "Account recovery", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Please enter the phone number.", "Account recovery", JOptionPane.OK_OPTION);
				}
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext.setBounds(393, 389, 149, 46);
		userPanel.add(btnNext);
		
		JButton btnCreateAccount = new JButton("Create account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				CreateAccount.getData();
			}
		});
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.setContentAreaFilled(false);
		btnCreateAccount.setBorder(null);
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCreateAccount.setBounds(40, 308, 90, 21);
		userPanel.add(btnCreateAccount);
		
		JButton btnLogin = new JButton("Create account");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.initialize();
			}
		});
		btnLogin.setForeground(Color.BLUE);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorder(null);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setBounds(40, 308, 90, 21);
		
		JButton btnLogin1 = new JButton("Login");
		btnLogin1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Login();
			}
		});
		btnLogin1.setForeground(Color.BLUE);
		btnLogin1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin1.setContentAreaFilled(false);
		btnLogin1.setBorder(null);
		btnLogin1.setBounds(183, 335, 39, 21);
		userPanel.add(btnLogin1);
		
		JLabel lblLogin = new JLabel("Already have an account?");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogin.setBounds(40, 339, 145, 13);
		userPanel.add(lblLogin);
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("D:\\Mini Project\\BookShop\\Five_D_Books\\res\\Logo\\logo.png"));
		logo.setFont(new Font("Tahoma", Font.BOLD, 25));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
	}
	
	public static boolean search_user(String phonenumber) {
		
		try {
			connectDataBase database = new connectDataBase("USER","root","root@14");
			String query = "SELECT EMAILID,USERNAME FROM ACCOUNTS WHERE PHONE_NUMBER = '" + Double.parseDouble(phonenumber) + "'";
			database.getData(query);
			database.data.next();
			if(database.data.getRow() == 1) {
				userEmail = database.data.getString("EMAILID");
				username = database.data.getString("USERNAME");
				database.data.close();
				database.connect.close();
				return true;
			}
			else 
				return false;
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public static void getPhoneNumber() {
		
		JLabel lblInput = new JLabel("Enter Phone number");
		lblInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInput.setBounds(30, 204, 193, 29);
		userPanel.add(lblInput);
		
		JTextField phone_number = new JTextField();
		phone_number.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phone_number.setBounds(30, 243, 188, 40);
		userPanel.add(phone_number);
		phone_number.setColumns(10);
		
		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNext.setBounds(393, 389, 149, 46);
		userPanel.add(btnNext);
		
		JTextPane message = new JTextPane();
		message.setForeground(Color.BLUE);
		message.setBackground(frame.getBackground());
		message.setBorder(null);
		message.setEditable(false);
		message.setFont(new Font("Tahoma", Font.PLAIN, 18));
		message.setText("Enter the phone number registered with " + userEmail);
		message.setBounds(126, 118, 372, 51);
		userPanel.add(message);
		
		JLabel invalidPhoneNumber = new JLabel("! The Phone number doesn't match,Try again.");
		invalidPhoneNumber.setForeground(Color.RED);
		invalidPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		invalidPhoneNumber.setBounds(30,290, 450, 15);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(30, 389, 149, 46);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userPanel.remove(btnBack);
				userPanel.remove(btnNext);
				userPanel.remove(phone_number);
				userPanel.remove(lblInput);
				userPanel.remove(message);
				userPanel.remove(invalidPhoneNumber);
				userPanel.repaint();
				getPassword();
			}
		});
		userPanel.add(btnBack);
		
		try {	
			connectDataBase database = new connectDataBase(username,"root","root@14");
			String query = "SELECT PHONE_NUMBER FROM USER_ADDRESS";
			database.getData(query);
			database.data.next();
			String userPhoneNumber = String.valueOf(database.data.getLong("PHONE_NUMBER"));
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(phone_number.getText().compareTo("") != 0) {
						if(phone_number.getText().compareTo(userPhoneNumber) == 0) {
							userPanel.removeAll();
							userPanel.repaint();
							changePassword(username);
						}
						else {
							userPanel.add(invalidPhoneNumber);
							phone_number.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
							userPanel.repaint();
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Please enter the phone number.", "Account recovery", JOptionPane.OK_OPTION);
					}
				}
			});
			database.data.close();
			database.connect.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static void changePassword(String username) {
		
		JLabel lblTitle = new JLabel("Change Password");
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setBounds(180, 10, 229, 51);
		userPanel.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon("D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\Logo\\logo.png"));
		logo.setFont(new Font("Tahoma", Font.BOLD, 25));
		logo.setBounds(267, 10, 186, 67);
		frame.getContentPane().add(logo);
		
		JButton btnCreateAccount = new JButton("Create account");
		btnCreateAccount.setContentAreaFilled(false);
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				CreateAccount.getData();
			}
		});
		btnCreateAccount.setForeground(Color.BLUE);
		btnCreateAccount.setBorder(null);
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateAccount.setBounds(28, 309, 104, 33);
		userPanel.add(btnCreateAccount);
		
		JLabel lblLogin = new JLabel("Already have an account?");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLogin.setBounds(28, 341, 145, 13);
		userPanel.add(lblLogin);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.BLUE);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBorder(null);
		btnLogin.setBounds(171, 337, 39, 21);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new Login();
			}
		});
		userPanel.add(btnLogin);
		
		String hideIcon = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\hidden.png";
		String showIcon = "D:\\Mini Project\\Book Shop Management System\\Five_D_Books\\res\\icons\\show.png";
		JLabel visiblity = new JLabel("");
		visiblity.setIcon(new ImageIcon(hideIcon));
		visiblity.setBounds(388, 160, 31, 26);
		userPanel.add(visiblity);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewPassword.setBounds(30, 119, 131, 26);
		userPanel.add(lblNewPassword);
		
		JLabel lblRetypePassword = new JLabel("Re-type password");
		lblRetypePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRetypePassword.setBounds(30, 216, 159, 26);
		userPanel.add(lblRetypePassword);
		
		JPasswordField password = new JPasswordField();
		password.setEchoChar('*');
		password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		password.setBounds(30, 155, 339, 38);
		
		userPanel.add(password);
		
		JPasswordField rePassword = new JPasswordField();
		rePassword.setEchoChar('*');
		rePassword.setBounds(30, 252, 339, 38);
		rePassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userPanel.add(rePassword);
		
		JButton btnChange = new JButton("Change");
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnChange.setBounds(393, 389, 149, 46);
		userPanel.add(btnChange);
		
		JLabel invalidPassword = new JLabel("! The password doesn't match,Enter again.");
		invalidPassword.setForeground(Color.RED);
		invalidPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		invalidPassword.setBounds(30,290, 450, 15);
		
		btnChange.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(password.getText().compareTo("") != 0 && rePassword.getText().compareTo("") != 0) {
					if(password.getText().compareTo(rePassword.getText()) == 0) {
						try {
							connectDataBase database = new connectDataBase("USER","root","root@14");
							String query = "UPDATE ACCOUNTS SET PASSWORD = '" + password.getText() + "' WHERE EMAILID = '" + userEmail + "' OR USERNAME = '" + userEmail + "'";
							if(database.update(query) >= 1) {
									JOptionPane.showMessageDialog(frame, "Password changed successfully\nLogin to continue.", "Account",JOptionPane.PLAIN_MESSAGE);
									frame.setVisible(false);
									new Login();
								}
							else
								JOptionPane.showMessageDialog(frame, "!Error\nContact administrator.","Account-Change Password",JOptionPane.OK_OPTION, null);
						}catch(Exception e1) {
							System.out.println(e1);
						}
					}
					else {
						userPanel.add(invalidPassword);
						rePassword.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, Color.RED));
						userPanel.repaint();
					}
				}
				else
					JOptionPane.showMessageDialog(frame, "Enter password to continue.","Account-Change Password",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(30, 389, 149, 46);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gate == 1) {
					frame.setVisible(false);
					new Home(username);
				}
				else {
					userPanel.remove(btnBack);
					userPanel.remove(btnChange);
					userPanel.remove(password);
					userPanel.remove(rePassword);
					userPanel.remove(visiblity);
					userPanel.remove(lblNewPassword);
					userPanel.remove(lblRetypePassword);
					userPanel.remove(invalidPassword);
					userPanel.repaint();
					getPhoneNumber();
				}
			}
		});
		userPanel.add(btnBack);
		
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
		frame.setVisible(true);
	}
}
