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

import org.apache.log4j.Logger;

import com.mysql.cj.jdbc.CallableStatement;

import com.ust.model.Login;

/***
 * 
 * @author Chithra
 * 29-07-2021
 *
 */
public class LoginDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	/***
	 * 
	 * @param login
	 * @return boolean
	 * @throws ClassNotFoundException
	 */
	public boolean validateEmployee(Login login) throws ClassNotFoundException {
		logger.info("Entering validateEmployee ");

		boolean result = false;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try (Connection connection = DriverManager

				.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "Chithra1!");


				CallableStatement cs=(CallableStatement) connection.prepareCall("select id,username,password from employee where username = ? and password = ? and id = 1 ")){

			cs.setString(1,login.getUsername());
			cs.setString(2,login.getPassword());
			ResultSet rs =cs.executeQuery();
			result = rs.next();




		} catch (SQLException e) {

			e.printStackTrace();
		}

		if(result==true) {
			return true;
		}else {
			logger.error("Not logged in ");
			return false;
		}

	}
	public boolean validateUserEmployee(Login login) throws ClassNotFoundException {
		logger.info("Entering User Employee ");

		boolean result = false;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try (Connection connection = DriverManager

				.getConnection("jdbc:mysql://localhost:3306/employees?useSSL=false", "root", "Chithra1!");


				CallableStatement cs=(CallableStatement) connection.prepareCall("select id,username,password from employee where username = ? and password = ? and id != 1")){
			final String secretKey = "ssshhhhhhhhhhh!!!!";
			String pass=EmployeeDAO.encrypt(login.getPassword(), secretKey) ;
			cs.setString(1,login.getUsername());
			cs.setString(2,pass);
			ResultSet rs =cs.executeQuery();
			result = rs.next();




		} catch (SQLException e) {

			e.printStackTrace();
		}

		if(result==true) {
			return true;
		}else {
			logger.error("Not logged in as user exception occoured");
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


}
