package interface_;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import logic.*;

public class InterfaceRegister extends JFrame {

	private JPanel contentPane;
	private JTextField textField_Username;
	private JTextField textField_Password;
	//private JTextField textField_Email;
	private JTextField textField_EmaiL;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceRegister frame = new InterfaceRegister();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public InterfaceRegister() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		String url = "jdbc:mysql://localhost:3306/chat";
		String user = "Register";
		String password = "Register";
		Connection connect = DriverManager.getConnection(url, user, password);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lbl_Register = new JLabel("CREATE ACCOUNT");
		lbl_Register.setForeground(Color.BLACK);
		lbl_Register.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Register.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		
		JLabel lbl_Username = new JLabel("Username:");
		lbl_Username.setForeground(Color.BLACK);
		lbl_Username.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		textField_Username = new JTextField();
		textField_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_Username.setColumns(10);
		
		JLabel lblInfoName = new JLabel("");
		lblInfoName.setForeground(new Color(0, 0, 0));
		lblInfoName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblInfoName.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_Password = new JLabel("Password:");
		lbl_Password.setForeground(Color.BLACK);
		lbl_Password.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		textField_Password = new JTextField();
		textField_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_Password.setColumns(10);
		
		JLabel lblInfoPassword = new JLabel("");
		lblInfoPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoPassword.setFont(new Font("Tahoma", Font.ITALIC, 12));
		
		JLabel lbl_Email = new JLabel("Email:");
		lbl_Email.setForeground(Color.BLACK);
		lbl_Email.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		textField_EmaiL = new JTextField();
		textField_EmaiL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_EmaiL.setColumns(10);
		
		JLabel lblInfoEmail = new JLabel("");
		lblInfoEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoEmail.setFont(new Font("Tahoma", Font.ITALIC, 12));
		
		JButton btn_Login = new JButton("REGISTER");
		btn_Login.setEnabled(false);
		
		btn_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// This will load the MySQL driver, each DB has its own driver
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					// Setup the connection with the DB
					String url = "jdbc:mysql://localhost:3306/chat";
					String user = "Register";
					String password = "Register";
					Connection connect = DriverManager.getConnection(url, user, password);
					// Statements allow to issue SQL queries to the database
					Statement statement = connect.createStatement();
					String nameInsert = textField_Username.getText(),
						   passInsert = SecurityDatabase.getMD5(textField_Password.getText()),
						   emailInsert = textField_EmaiL.getText();
					
					statement.execute("insert into client (Name,Password,Email) values('"+nameInsert+"','"+passInsert+"','"+emailInsert+"');");
					// Result set get the result of the SQL query
					ResultSet rs = statement.executeQuery("select * from client");
					// writeResultSet(resultSet);
					System.out.println("ID" + " | " + "Name" + " | " + "Password" + "      | " + "Email");
					while (rs.next()) {
						System.out.println(rs.getString(1) +" | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4));
					}
					textField_Username.setText("");
					connect.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		});
		btn_Login.setForeground(Color.BLACK);
		btn_Login.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		textField_Username.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				SecurityDatabase.noRepiteName(textField_Username, lblInfoName, btn_Login, connect);
			}
		});
		textField_Password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SecurityDatabase.goodPassword(textField_Password, lblInfoPassword, btn_Login);
			}
		});
		textField_EmaiL.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SecurityDatabase.EmailCorrect(textField_EmaiL, lblInfoEmail, btn_Login);
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(lbl_Username, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(25)
					.addComponent(textField_Username, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
					.addGap(5))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(104)
					.addComponent(btn_Login, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addGap(130))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(43)
					.addComponent(lbl_Register, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
					.addGap(61))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(34)
					.addComponent(lblInfoName, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
					.addGap(37))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(37, Short.MAX_VALUE)
					.addComponent(lblInfoEmail, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
					.addGap(34))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(lblInfoPassword, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbl_Email, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
									.addGap(25)
									.addComponent(textField_EmaiL, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbl_Password, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
									.addGap(25)
									.addComponent(textField_Password, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)))
							.addGap(5))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lbl_Register, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(45)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lbl_Username, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_Username, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfoName, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lbl_Password, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_Password, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblInfoPassword, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_Email, GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
						.addComponent(textField_EmaiL, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblInfoEmail, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(btn_Login, GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
	}
}
