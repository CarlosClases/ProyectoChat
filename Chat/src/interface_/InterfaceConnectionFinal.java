package interface_;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logic.ClientLogic;
import logic.SecurityDatabase;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class InterfaceConnectionFinal extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;
	private final int port = 3501;
	private final String ipAddress = "127.0.0.1";
	boolean niceConnection = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceConnectionFinal frame = new InterfaceConnectionFinal();
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
	public InterfaceConnectionFinal() throws ClassNotFoundException, SQLException {
		
		Connection connect = SecurityDatabase.connectionDB();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 340, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTitle = new JLabel("LOGIN");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		textFieldUsername = new JTextField();
		textFieldUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldUsername.setColumns(10);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldPassword.setColumns(10);
		
		JButton btnAccess = new JButton("ACCESS");
	
		btnAccess.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JLabel lblInfoAccess = new JLabel("");
		lblInfoAccess.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(64)
							.addComponent(btnAccess, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
							.addGap(73))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(105)
							.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(102))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldPassword, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
								.addComponent(textFieldUsername, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))))
					.addGap(7))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(46)
					.addComponent(lblInfoAccess, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
					.addGap(49))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblInfoAccess, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(textFieldPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
					.addGap(26)
					.addComponent(btnAccess, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addGap(21))
		);
		contentPane.setLayout(gl_contentPane);
		btnAccess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					niceConnection = SecurityDatabase.VerifyPassword(textFieldUsername, textFieldPassword, lblInfoAccess, connect);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(niceConnection) {
					System.out.println(port);
					ClientLogic cli = new ClientLogic(textFieldUsername.getText(), port, ipAddress);
					ArrayList<String> rooms = new ArrayList<String>();
					cli.setRooms(rooms);
					
					try {
						connect.close();
						InterfaceMultiRoomChat fc;
						fc = new InterfaceMultiRoomChat(cli);
						fc.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						fc.setVisible(true);
						dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
			}
		});
	}
}
