package logic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import interface_.InterfaceMultiRoomChat;

public class ListenersRoomButton implements ActionListener{
	private ClientLogic client;
	private JTabbedPane tabPlace;
	private PrintStream output;
	private Connection sqlConnection;
public ListenersRoomButton (ClientLogic client, JTabbedPane tabPlace, PrintStream output, Connection sqlConnection) {
	this.client = client;
	this.tabPlace = tabPlace;
	this.output= output;
	this.sqlConnection=sqlConnection;
}
	@Override
	public void actionPerformed(ActionEvent e) {
		
			JButton guilty = (JButton)e.getSource(); 
			String id_sala = guilty.getToolTipText();
			
			if(!client.getRooms().contains(id_sala)) {
				client.addRoom(id_sala);
				try {
					Statement statement =sqlConnection.createStatement();
					String nameCli = client.getName();
					String nameRoom = id_sala;
					String sentence= "INSERT INTO CONNECTION VALUES( , 'SELECT ID FROM Client WHERE name LIKE'"+nameCli+", SELECT ID FROM Room WHERE name LIKE "+ nameRoom+";"; 
					statement.execute(sentence);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					InterfaceMultiRoomChat.createNewChatTab(tabPlace, id_sala, client);
					
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			for(String name : client.getRooms()) {
				System.out.println(name);
			}
		}


}
