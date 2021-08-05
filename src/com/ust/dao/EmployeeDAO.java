package com.ust.dao;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.mysql.cj.jdbc.CallableStatement;


import com.ust.model.Employee;
/****
 * 
 * @author Chithra
 * 28-07-2021
 *
 */
public class EmployeeDAO {



	/***
	 * Method to register employee 
	 * @param employee
	 * @return
	 * @throws ClassNotFoundException
	 */
	public boolean registerEmployee(Employee employee) throws ClassNotFoundException {
		final String secretKey = "ssshhhhhhhhhhh!!!!";
		String pass=EmployeeDAO.encrypt(employee.getPassword(), secretKey) ;


		String INSERT_USERS_SQL = "INSERT INTO employee" +
				"  ( first_name, last_name, username, password) VALUES " +
				" ( ?, ?, ?, ?);";


		int result = 0;


		Class.forName("com.mysql.cj.jdbc.Driver");

		try (Connection connection = DriverManager

				.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "Chithra1!");



				CallableStatement cs=(CallableStatement) connection.prepareCall(INSERT_USERS_SQL)){

			cs.setString(1,employee.getFirstName());
			cs.setString(2,employee.getLastName());
			cs.setString(3,employee.getUsername());
			cs.setString(4,pass);
			result = cs.executeUpdate();




		} catch (SQLException e) {

			e.printStackTrace();
		}

		if(result==1) {
			return true;
		}else {
			return false;
		}

	}
	
	private static SecretKeySpec secretKey;
	private static byte[] key;
	
	public static void setKey(String myKey) 
	{
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); 
			secretKey = new SecretKeySpec(key, "AES");
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/***
	 * 
	 * @param strToEncrypt
	 * @param secret
	 * @return String of encrypted password
	 */
	public static String encrypt(String strToEncrypt, String secret) 
	{
		try
		{
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} 
		catch (Exception e) 
		{
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}



	/***
	 * Method to check if Employee Already exists by username
	 * @param employee
	 * @return
	 * @throws ClassNotFoundException
	 */
	public boolean isAlreadyExists(Employee employee) throws ClassNotFoundException{
		boolean result = false;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try (Connection connection = DriverManager

				.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "Chithra1!");


				CallableStatement cs=(CallableStatement) connection.prepareCall("select username from employee where username = ?  ")){

			cs.setString(1,employee.getUsername());

			ResultSet rs =cs.executeQuery();
			result = rs.next();


		} catch (SQLException e) {

			e.printStackTrace();
		}

		if(result==true) {
			return true;
		}else {

			return false;
		}

	}


}
