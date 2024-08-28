package dbConnector;

import java.sql.*;


public class connectDataBase{
	
	String dbName,username,password;
	public ResultSet data;
	public Connection connect;
	public connectDataBase(String dbName,String username,String password){
		this.dbName = dbName;
		this.username = username;
		this.password = password;
	}
	
	public void createUserDatatbase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "USER",username,password);
			Statement stmt = connect.createStatement();
			String query = "CREATE DATABASE " + dbName;
			stmt.execute(query);
			connect.close();
			createTable();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void createTable() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName ,"root","root@14");
			Statement stmt = connect.createStatement();
			String query;
			query = "CREATE TABLE ACCOUNT (ID INT NOT NULL UNIQUE AUTO_INCREMENT,"
					+ "FIRSTNAME VARCHAR(30) NOT NULL,"
					+ "LASTNAME VARCHAR(30) NOT NULL,"
					+ "USERNAME VARCHAR(30) NOT NULL UNIQUE, "
					+ "EMAILID VARCHAR(50) NOT NULL PRIMARY KEY,"
					+ "PASSWORD VARCHAR(18) NOT NULL)";
			stmt.execute(query);
			query = "CREATE TABLE USER_ADDRESS (ID INT NOT NULL UNIQUE AUTO_INCREMENT,"
					+ "USERNAME VARCHAR(30) NOT NULL UNIQUE,"
					+ "PHONE_NUMBER DOUBLE PRECISION UNIQUE,"
					+ "ADDRESS VARCHAR(100),"
					+ "DISTRICT VARCHAR(20),"
					+ "STATE VARCHAR(20),"
					+ "PINCODE VARCHAR(10))";
			stmt.execute(query);
			query = "CREATE TABLE WISHLIST (BOOKID INT NOT NULL,BOOKNAME VARCHAR(50) NOT NULl);";
			stmt.execute(query);
			query = "CREATE TABLE CART (BOOKID INT NOT NULL UNIQUE, "
					+ "BOOKNAME VARCHAR(50) NOT NULL PRIMARY KEY,"
					+ "QUANTITY INT NOT NULL,"
					+ "PRICE DOUBLE NOT NULL)";
			stmt.execute(query);
			query = "CREATE TABLE MYORDERS "
					+ "(BOOKID INT NOT NULL UNIQUE,"
					+ "BOOKNAME VARCHAR(50)NOT NULL, "
					+ "QUANTITY INT NOT NULL,"
					+ "TOTAL_AMOUNT DOUBLE NOT NULL,"
					+ "ORDER_DATE DATE NOT NULL,"
					+ "DELIVERY_DATE DATE NOT NULL,"
					+ "DELIVERY_STATUS BOOLEAN NOT NULL)";
			stmt.execute(query);
			connect.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public int update(String query) {
		
		int res = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName,username,password);
			Statement stmt = connect.createStatement();
			res = stmt.executeUpdate(query);
			connect.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return res;
	}
	
	public void getData(String query) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName,username,password);
			Statement stmt = connect.createStatement();
			data = stmt.executeQuery(query);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}