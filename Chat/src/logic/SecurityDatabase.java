package logic;

import java.awt.Color;
import java.awt.Font;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SecurityDatabase {
	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean correctName(JTextField textField_Username, JLabel lblInfo,Connection connect) {
		boolean repite = false, correctName=false;
		try {

			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery("select * from client");
			
			String newName = textField_Username.getText();
			String textInfo;
			int numLetters = newName.length();
			if (numLetters < 4) {
				textInfo = "Enter at least 4 letters";
				lblInfo.setText(textInfo);
				lblInfo.setForeground(new Color(165, 42, 42));
				correctName=false;
			} else {
				while (rs.next()) {
					if (rs.getString(2).equals(newName)) {
						repite = true;
					}
				}
				if (repite) {
					lblInfo.setFont(new Font("Times New Roman", Font.BOLD, 12));
					textInfo = "Name not available, enter a new one";
					lblInfo.setText(textInfo);
					lblInfo.setForeground(new Color(165, 42, 42));
					correctName=false;

				} else {
					textInfo = "Name available";
					lblInfo.setText(textInfo);
					lblInfo.setForeground(new Color(0, 128, 0));
					correctName=true;
				}
			}
			return correctName;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return correctName;
	}

	public static boolean goodPassword(JTextField textField_Password, JLabel lblInfo) {
		String newPassword = textField_Password.getText();
		boolean goodPassword=false;
		int numLetters = newPassword.length();
		if (numLetters < 6) {
			lblInfo.setText("Enter at least 6 letters");
			lblInfo.setForeground(new Color(165, 42, 42));
		} else {
			char clave;
			//int contNun = 0, contCapLet = 0, contLowLet = 0;
			boolean cerrojo1 = false, cerrojo2 = false, cerrojo3 = false, end = false;
			for (int i = 0; !end && i < numLetters; i++) {
				clave = newPassword.charAt(i);
				String passValue = String.valueOf(clave);
				if (passValue.matches("[A-Z]")) {
					// System.out.println("1");
					cerrojo1 = true;
				} else if (passValue.matches("[a-z]")) {
					// System.out.println("2");
					cerrojo2 = true;
				} else if (passValue.matches("[0-9]")) {
					// System.out.println("3");
					cerrojo3 = true;
				}
				if (cerrojo1 && cerrojo2 && cerrojo3) {
					//System.out.println("Ce mamo");
					end = true;
				}
			}
			if (!cerrojo1) {
				lblInfo.setText("Please enter at least one capital letter");
				lblInfo.setForeground(new Color(165, 42, 42));
				System.out.println("1");
			}
			else if (!cerrojo2) {
				lblInfo.setText("Please enter at least one lowercase letter");
				lblInfo.setForeground(new Color(165, 42, 42));
			}

			else if (!cerrojo3) {
				lblInfo.setText("Please enter at least one number");
				lblInfo.setForeground(new Color(165, 42, 42));
			} 
			
			else {
				lblInfo.setText("Good password bro");
				lblInfo.setForeground(new Color(0, 128, 0));
				goodPassword=true;
			}
		}
		return goodPassword;
	}
	
	public static boolean emailCorrect(JTextField textField_EmaiL, JLabel lblInfo) {
		String newEmail = textField_EmaiL.getText();
		boolean emailCorrect= false;
		if (!newEmail.contains("@") || !newEmail.contains(".")) { // Comprueba si el nuevo nombre cumple el minimo de caracteres
			lblInfo.setText("Write a correct email address");
			lblInfo.setForeground(new Color(165, 42, 42));
		}
		else {
			lblInfo.setText("Email Correct");
			lblInfo.setForeground(new Color(0, 128, 0));
			emailCorrect=true;
		}
		return emailCorrect;
	}
	public static void allGood(boolean correctName,boolean goodPassword, boolean emailCorrect, JButton btn_Login) {
		if(!correctName || !goodPassword || !emailCorrect) {
			btn_Login.setEnabled(false);
		}
		else {
		btn_Login.setEnabled(true);
		}
	}
	
	public static boolean VerifyPassword(JTextField textFieldUsername,JTextField textFieldPassword, JLabel lblInfo, Connection connect) throws SQLException {
		boolean niceConnection=false, cerrojo=false;
		String nameUser = textFieldUsername.getText();
		String passUser = getMD5(textFieldPassword.getText());
		Statement statement = connect.createStatement();
		
		ResultSet rs = statement.executeQuery("select * from client where Name='"+nameUser+"';");
		while (rs.next()) {
			cerrojo=true;
			String PassDB = rs.getString(3);
			if(!PassDB.equals(passUser)) {
				System.out.println(passUser);
				System.out.println(rs.getString(3));
				textFieldUsername.setText("");
				textFieldPassword.setText("");
				
				lblInfo.setText("Name or password incorrect");
				lblInfo.setForeground(new Color(165, 42, 42));
				niceConnection=false;
			}
			else {
				niceConnection=true;
				System.out.println(passUser);
				System.out.println(rs.getString(3));
				
			}
		}
		if(!cerrojo) {
			textFieldUsername.setText("");
			textFieldPassword.setText("");
			lblInfo.setText("Name or password incorrect");
			lblInfo.setForeground(new Color(165, 42, 42));
			niceConnection=false;
		}
		return niceConnection;
	}
}
