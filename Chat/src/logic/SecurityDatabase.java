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

	public static boolean noRepiteName(JTextField textField_Username, JLabel lblInfo, JButton btn_Login,
			Connection connect) {
		boolean repite = false;
		try {
			// Statements allow to issue SQL queries to the database
			Statement statement = connect.createStatement();
			// Result set get the result of the SQL query
			ResultSet rs = statement.executeQuery("select * from client");
			// writeResultSet(resultSet);
			String Nuevo_nombre = textField_Username.getText();
			String Aviso_texto;
			int Numero_Letras_Nombre = Nuevo_nombre.length();
			if (Numero_Letras_Nombre < 5) { // Comprueba si el nuevo nombre cumple el minimo de caracteres
				Aviso_texto = "Introduzca al menos 5 caracteres";
				lblInfo.setText(Aviso_texto);
				lblInfo.setForeground(new Color(165, 42, 42));
				btn_Login.setEnabled(false);
			} else {
				String newName = textField_Username.getText();
				while (rs.next()) {
					if (rs.getString(2).equals(newName)) {
						repite = true;
					}
				}
				if (repite) {
					lblInfo.setFont(new Font("Times New Roman", Font.BOLD, 12));
					Aviso_texto = "Nombre no disponible, intruduzca uno nuevo";
					lblInfo.setText(Aviso_texto);
					lblInfo.setForeground(new Color(165, 42, 42));
					btn_Login.setEnabled(false);

				} else {
					Aviso_texto = "Nombre disponible";
					lblInfo.setText(Aviso_texto);
					lblInfo.setForeground(new Color(0, 128, 0));
					btn_Login.setEnabled(true);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repite;
	}
	public static boolean EmailCorrect(JTextField textField_EmaiL, JLabel lblInfo, JButton btn_Login) {
		String newEmail = textField_EmaiL.getText();
		boolean goodEmail = true;
		if (!newEmail.contains("@") || !newEmail.contains(".")) { // Comprueba si el nuevo nombre cumple el minimo de caracteres
			lblInfo.setText("Introduzca un correo real");
			lblInfo.setForeground(new Color(165, 42, 42));
			btn_Login.setEnabled(false);
			goodEmail = false;
		}
		else {
			lblInfo.setText("Email Correcto");
			lblInfo.setForeground(new Color(0, 128, 0));
			btn_Login.setEnabled(true);
			goodEmail = true;
		}
		return goodEmail;
	}
}
