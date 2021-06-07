package interface_;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import logic.*;

import java.awt.event.ActionListener;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class InterfaceChat extends JFrame {

	private JPanel contentPane;
	private JTextField mensaje;
	private JTextPane TextChat = new JTextPane();
	private BufferedReader input;
	private PrintStream output;
	private WriterThread writer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceChat frame = new InterfaceChat(null);
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
	public InterfaceChat(ClientLogic cli) {
		// To read from the server
		try {
			input = new BufferedReader(new InputStreamReader(cli.getSocket().getInputStream()));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			output = new PrintStream(cli.getSocket().getOutputStream());
			output.println(cli.getName());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// reader= new ReaderThread(input, output);
		// reader.start();
		writer = new WriterThread(input, output, TextChat);
		writer.start();
		///
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 725, 449);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel LabelName = new JLabel(cli.getName());
		LabelName.setForeground(Color.GREEN);
		LabelName.setFont(new Font("Times New Roman", Font.BOLD, 17));
		LabelName.setHorizontalAlignment(SwingConstants.CENTER);
		LabelName.setBounds(10, 5, 131, 20);
		contentPane.add(LabelName);

		TextChat.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		TextChat.setEditable(false);
		TextChat.setBackground(Color.BLACK);
		TextChat.setForeground(Color.GREEN);
		TextChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		TextChat.setBounds(10, 36, 548, 321);
		contentPane.add(TextChat);

		mensaje = new JTextField();
		mensaje.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE));
		mensaje.setBounds(10, 368, 548, 31);
		contentPane.add(mensaje);
		mensaje.setColumns(10);

		JButton sendButton = new JButton("Send");
		sendButton.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE));
		sendButton.setForeground(Color.GREEN);
		sendButton.setBackground(Color.BLACK);
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String line = "";
				System.out.println("Client mensaje: ");
				line = mensaje.getText();
				System.out.println(line + " ////To the server");
				output.println(line);
				mensaje.setText("");
			}
		});
		sendButton.setBounds(568, 368, 131, 31);
		contentPane.add(sendButton);

		JButton buttonDisconect = new JButton("Disconnect");
		buttonDisconect.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
		buttonDisconect.setBackground(Color.BLACK);
		buttonDisconect.setForeground(Color.RED);
		buttonDisconect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					int closeNumber = cli.getSocket().getPort() + cli.getSocket().getLocalPort();
					output.println(cli.getName() + " disconnected");
					output.println(closeNumber + "AAA");
					cli.getSocket().close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		buttonDisconect.setFont(new Font("Tahoma", Font.PLAIN, 13));
		buttonDisconect.setBounds(560, 62, 139, 30);

		buttonDisconect.setFont(new Font("Tahoma", Font.BOLD, 15));
		buttonDisconect.setBounds(560, 62, 130, 30);
		contentPane.add(buttonDisconect);

	}
}
