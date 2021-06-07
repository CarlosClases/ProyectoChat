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
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
	 */
	public InterfaceRegister() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 285, 345);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_Register = new JLabel("CREATE ACCOUNT");
		lbl_Register.setForeground(new Color(102, 205, 170));
		lbl_Register.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Register.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		lbl_Register.setBounds(10, 25, 250, 40);
		contentPane.add(lbl_Register);
		
		JLabel lbl_Username = new JLabel("Username:");
		lbl_Username.setForeground(new Color(102, 205, 170));
		lbl_Username.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Username.setBounds(10, 90, 110, 20);
		contentPane.add(lbl_Username);
		
		textField_Username = new JTextField();
		textField_Username.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_Username.setColumns(10);
		textField_Username.setBounds(145, 90, 110, 20);
		contentPane.add(textField_Username);
		
		
		JLabel lbl_Password = new JLabel("Password:");
		lbl_Password.setForeground(new Color(102, 205, 170));
		lbl_Password.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Password.setBounds(10, 150, 110, 20);
		contentPane.add(lbl_Password);
		
		textField_Password = new JTextField();
		textField_Password.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_Password.setColumns(10);
		textField_Password.setBounds(145, 150, 110, 20);
		contentPane.add(textField_Password);
		
		JLabel lbl_Email = new JLabel("Email:");
		lbl_Email.setForeground(new Color(102, 205, 170));
		lbl_Email.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbl_Email.setBounds(10, 210, 110, 20);
		contentPane.add(lbl_Email);
		
		textField_EmaiL = new JTextField();
		textField_EmaiL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_EmaiL.setColumns(10);
		textField_EmaiL.setBounds(145, 210, 110, 20);
		contentPane.add(textField_EmaiL);
		
		
		JButton btn_Login = new JButton("REGISTER");
		btn_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// This will load the MySQL driver, each DB has its own driver
					Class.forName("com.mysql.cj.jdbc.Driver");

					// Setup the connection with the DB
					String url = "jdbc:mysql://localhost:3306/chat";
					String user = "RootTrue";
					String password = "RootTrue";
					Connection connect = DriverManager.getConnection(url, user, password);

					// Statements allow to issue SQL queries to the database
					Statement statement = connect.createStatement();
					statement.execute("insert into client values("+textField_Username.getText()+","+textField_Password.getText()+","+textField_EmaiL+");");
					// Result set get the result of the SQL query
					ResultSet rs = statement.executeQuery("select * from client");
					// writeResultSet(resultSet);
					while (rs.next()) {System.out.println(rs.getInt("ID") + " " + rs.getString(2));}
					connect.close();
				} catch (Exception e) {System.out.println(e);}
			}
		});
		btn_Login.setForeground(Color.DARK_GRAY);
		btn_Login.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Login.setBounds(65, 265, 120, 25);
		contentPane.add(btn_Login);
		
	}
}
