package interface_;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import logic.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterfaceMultiRoomChat extends JFrame {

	private JPanel contentPane;
	
	private static BufferedReader input;
	private static PrintStream output;
	
	private static WriterThread writer;
	private static ArrayList<WriterThread> writeArray = new ArrayList<WriterThread>();
	
	private static ArrayList<JPanel> roomsArrayList= new ArrayList <JPanel>();
	//private static ClientLogic client = new ClientLogic();
	
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Connection sqlConnection;
	
	private ActionListener listen;
	/*public ClientLogic getClient() {
		return this.client;
	}*/
	/*public void setClient(logic.ClientLogic client) {
		this.client=client;
	};*/
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceMultiRoomChat frame = new InterfaceMultiRoomChat(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void createNewChatTab(JTabbedPane placeToInsertTab, String nameOfTheTab , ClientLogic client) throws ClassNotFoundException, SQLException {
		
		
		//String ID_Room = nameOfTheTab;
		JPanel newTab = new JPanel();
		newTab.setToolTipText("");
		newTab.setName(nameOfTheTab);
		//Añade el JPanel al layout de pestañas
		placeToInsertTab.addTab(nameOfTheTab, null, newTab, null);
		
		JTextPane chatTextPlace = new JTextPane();
		chatTextPlace.setEditable(false);
		chatTextPlace.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chatTextPlace.setName("ChatPlace");
		
		JTextPane clientConnected = new JTextPane();
		clientConnected.setEditable(false);
		clientConnected.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clientConnected.setText("BBBBBBBBBB");
		clientConnected.setName("ClientsConnected");
		JTextField messagePlace = new JTextField();
		messagePlace.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
				String idRoom = nameOfTheTab;
				output.println(idRoom);
				String line = "";
				System.out.println("Client mensaje: ");
				line = messagePlace.getText();
				System.out.println(line + " ////To the server");
				output.println(line);
				messagePlace.setText("");
				}
			}
		});
		messagePlace.setColumns(10);
		messagePlace.setText("");
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idRoom = nameOfTheTab;
				output.println(idRoom);
				String line = "";
				System.out.println("Client mensaje: ");
				line = messagePlace.getText();
				System.out.println(line + " ////To the server");
				output.println(line);
				messagePlace.setText("");
			}
		});
		
		JButton btn_Morirse = new JButton("");
		btn_Morirse.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/Moridura.png")));
		btn_Morirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.deleteRoom(nameOfTheTab);
				for(String name : client.getRooms()) {
					System.out.println(name + "// delete time");
				}
				placeToInsertTab.remove(newTab);
			}
		});
		
		JLabel nameLabel = new JLabel(client.getName());
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GroupLayout gl_rooms = new GroupLayout(newTab);
		gl_rooms.setHorizontalGroup(
			gl_rooms.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rooms.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_rooms.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_rooms.createSequentialGroup()
							.addGroup(gl_rooms.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_rooms.createSequentialGroup()
									.addComponent(messagePlace, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
									.addGap(18))
								.addGroup(gl_rooms.createSequentialGroup()
									.addComponent(chatTextPlace, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_rooms.createParallelGroup(Alignment.LEADING)
								.addComponent(clientConnected, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
								.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_rooms.createSequentialGroup()
							.addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 587, Short.MAX_VALUE)
							.addComponent(btn_Morirse)))
					.addContainerGap())
		);
		gl_rooms.setVerticalGroup(
			gl_rooms.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rooms.createSequentialGroup()
					.addGroup(gl_rooms.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_Morirse)
						.addComponent(nameLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_rooms.createParallelGroup(Alignment.LEADING)
						.addComponent(clientConnected, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
						.addComponent(chatTextPlace, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_rooms.createParallelGroup(Alignment.BASELINE)
						.addComponent(messagePlace, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(sendButton))
					.addGap(12))
		);
		createLayout(gl_rooms, messagePlace, chatTextPlace, sendButton, clientConnected);
		newTab.setLayout(gl_rooms);
		roomsArrayList.add(newTab);
		int index = roomsArrayList.indexOf(newTab);
		JTextPane AAAAA = (JTextPane) newTab.getComponent(1);
		//System.out.println(AAAAA.getText());
		
		////0= Message place
		////1= Chat 
		////2=  Send Button
		////3= Connected users
		////4= Morirse button 
	}
	private static void createLayout(GroupLayout gl_rooms , JTextField messagePlace, JTextPane chatTextPlace,
			JButton sendButton, JTextPane clientConnected) {
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public InterfaceMultiRoomChat(ClientLogic client) throws ClassNotFoundException, SQLException {
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					int closeNumber = client.getSocket().getPort() + client.getSocket().getLocalPort();
					//output.println(client.getName() + " disconnected");
					output.println(closeNumber + "AAA");
					for (WriterThread writerProcess : writeArray) {
						writerProcess.suicide();
						writeArray.remove(writerProcess);
					}
					client.getSocket().close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		try {
			input = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			output = new PrintStream(client.getSocket().getOutputStream());
			//Informacion inicial
			output.println(client.getName());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		sqlConnection = SecurityDatabase.connectionDB();
		ListenersRoomButton roomButtonListener = new ListenersRoomButton(client, tabbedPane, output, sqlConnection);
		writer = new WriterThread(input, roomsArrayList,sqlConnection);
		writer.start();
		setTitle("Chateito Wapo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		
		
		contentPane.add(tabbedPane, "name_3873949461500");

		
//////////////////////////////////////////////////Room selection Interface boi///////////////////////////////////
		
		JPanel rooms = new JPanel();
		tabbedPane.addTab("Room Select", null, rooms, null);
		
		JButton btn_Sport = new JButton("");
		btn_Sport.setToolTipText("Sport");
		btn_Sport.addActionListener(roomButtonListener);
		btn_Sport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_Sport.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/Deportes.png")));
		btn_Sport.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btn_Anime = new JButton("");
		btn_Anime.setToolTipText("Anime");
		btn_Anime.addActionListener(roomButtonListener);
		btn_Anime.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_Anime.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/Animes.png")));
		btn_Anime.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btn_Juegos = new JButton("");
		btn_Juegos.setToolTipText("Games");
		btn_Juegos.addActionListener(roomButtonListener);
		btn_Juegos.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_Juegos.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/Juegos.png")));
		btn_Juegos.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btn_Series = new JButton("");
		btn_Series.setToolTipText("Series");
		btn_Series.addActionListener(roomButtonListener);
		btn_Series.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_Series.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/Series.png")));
		btn_Series.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btn_Computing = new JButton("");
		btn_Computing.setToolTipText("Computing");
		btn_Computing.addActionListener(roomButtonListener);
		btn_Computing.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_Computing.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/Informatica.png")));
		btn_Computing.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JButton btn_Weapon = new JButton("");
		btn_Weapon.setToolTipText("Weapons");
		btn_Weapon.addActionListener(roomButtonListener);
		btn_Weapon.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn_Weapon.setIcon(new ImageIcon(InterfaceMultiRoomChat.class.getResource("/Iconos/revolver.png")));
		btn_Weapon.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lbl_Title = new JLabel("CHOSE A ROOM");
		lbl_Title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Title.setForeground(new Color(102, 205, 170));
		lbl_Title.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		GroupLayout gl_rooms = new GroupLayout(rooms);
		gl_rooms.setHorizontalGroup(
			gl_rooms.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rooms.createSequentialGroup()
					.addGroup(gl_rooms.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_rooms.createSequentialGroup()
							.addGap(151)
							.addComponent(btn_Sport, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
							.addGap(30)
							.addComponent(btn_Anime, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
							.addGap(30)
							.addComponent(btn_Juegos, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
						.addGroup(gl_rooms.createSequentialGroup()
							.addGap(151)
							.addComponent(btn_Series, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
							.addGap(30)
							.addComponent(btn_Computing, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
							.addGap(30)
							.addComponent(btn_Weapon, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
						.addGroup(gl_rooms.createSequentialGroup()
							.addGap(248)
							.addComponent(lbl_Title, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
							.addGap(85)))
					.addGap(179))
		);
		gl_rooms.setVerticalGroup(
			gl_rooms.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rooms.createSequentialGroup()
					.addGap(39)
					.addComponent(lbl_Title, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(41)
					.addGroup(gl_rooms.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_Sport, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE)
						.addComponent(btn_Anime, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE)
						.addComponent(btn_Juegos, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE))
					.addGap(30)
					.addGroup(gl_rooms.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_Series, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE)
						.addComponent(btn_Computing, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE)
						.addComponent(btn_Weapon, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE))
					.addGap(90))
		);
		rooms.setLayout(gl_rooms);
		createNewChatTab(tabbedPane, "aaaaaaaaaaaaaaa", client);
		System.out.println(client.getName());
	}
}
