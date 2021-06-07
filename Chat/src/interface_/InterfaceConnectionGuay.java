package interface_;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import logic.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfaceConnectionGuay extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIpDirecction;
	private JTextField textFieldPortNumber;
	private JTextField textFieldUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceConnectionGuay frame = new InterfaceConnectionGuay();
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
	public InterfaceConnectionGuay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(145, 190, 85, 35);
		contentPane.add(lblUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setColumns(10);
		textFieldUsername.setBounds(235, 200, 130, 20);
		contentPane.add(textFieldUsername);
		textFieldUsername.setText("Paco");
		JLabel lblNewLabel = new JLabel("IP direction");
		lblNewLabel.setBounds(145, 100, 85, 35);
		contentPane.add(lblNewLabel);

		JLabel lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setBounds(145, 150, 85, 35);
		contentPane.add(lblPortNumber);

		JButton btnNewButton = new JButton("Connect");

		btnNewButton.setBounds(235, 245, 90, 25);
		contentPane.add(btnNewButton);

		textFieldIpDirecction = new JTextField();
		textFieldIpDirecction.setBounds(235, 110, 130, 20);
		contentPane.add(textFieldIpDirecction);
		textFieldIpDirecction.setColumns(10);
		textFieldIpDirecction.setText("localhost");

		textFieldPortNumber = new JTextField();
		textFieldPortNumber.setColumns(10);
		textFieldPortNumber.setBounds(235, 155, 130, 20);
		contentPane.add(textFieldPortNumber);
		textFieldPortNumber.setText("3501");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int port = Integer.parseInt(textFieldPortNumber.getText());
				System.out.println(port);
				ClientLogic cli = new ClientLogic(textFieldUsername.getText(), port, textFieldIpDirecction.getText());
				InterfaceMultiRoomChat fc = new InterfaceMultiRoomChat(cli);
				fc.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				fc.setVisible(true);
				dispose();
				// System.out.println(cli.toString());
			}
		});
	}
}
