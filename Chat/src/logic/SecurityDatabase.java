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

	public static void noRepiteName(JTextField textField_Username, JLabel lblInfo, JButton btn_Login,
			Connection connect) {
		boolean repite = false;
		try {

			Statement statement = connect.createStatement();

			ResultSet rs = statement.executeQuery("select * from client");

			String newName = textField_Username.getText();
			String textInfo;
			int numLetters = newName.length();
			if (numLetters < 5) {
				textInfo = "Enter at least 5 letters";
				lblInfo.setText(textInfo);
				lblInfo.setForeground(new Color(165, 42, 42));
				btn_Login.setEnabled(false);
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
					btn_Login.setEnabled(false);

				} else {
					textInfo = "Name available";
					lblInfo.setText(textInfo);
					lblInfo.setForeground(new Color(0, 128, 0));
					btn_Login.setEnabled(true);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void goodPassword(JTextField textField_Password, JLabel lblInfo, JButton btn_Login) {
		String newPassword = textField_Password.getText();
		int numLetters = newPassword.length();
		if (numLetters < 6) {
			lblInfo.setText("Enter at least 6 letters");
			lblInfo.setForeground(new Color(165, 42, 42));
			btn_Login.setEnabled(false);
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
					System.out.println("Ce mamo");
					end = true;
				}
			}
			if (!cerrojo1) {
				lblInfo.setText("Please enter at least one capital letter");
				lblInfo.setForeground(new Color(165, 42, 42));
				btn_Login.setEnabled(false);
				System.out.println("1");
			}
			else if (!cerrojo2) {
				lblInfo.setText("Please enter at least one lowercase letter");
				lblInfo.setForeground(new Color(165, 42, 42));
				btn_Login.setEnabled(false);
			}

			else if (!cerrojo3) {
				lblInfo.setText("Please enter at least one number");
				lblInfo.setForeground(new Color(165, 42, 42));
				btn_Login.setEnabled(false);
			} 
			
			else {
				lblInfo.setText("Good password bro");
				lblInfo.setForeground(new Color(0, 128, 0));
				btn_Login.setEnabled(true);
			}
		}
	}
	
	public static void EmailCorrect(JTextField textField_EmaiL, JLabel lblInfo, JButton btn_Login) {
		String newEmail = textField_EmaiL.getText();
		if (!newEmail.contains("@") || !newEmail.contains(".")) { // Comprueba si el nuevo nombre cumple el minimo de caracteres
			lblInfo.setText("Introduzca un correo real");
			lblInfo.setForeground(new Color(165, 42, 42));
			btn_Login.setEnabled(false);
		}
		else {
			lblInfo.setText("Email Correcto");
			lblInfo.setForeground(new Color(0, 128, 0));
			btn_Login.setEnabled(true);
		}
	}
}
