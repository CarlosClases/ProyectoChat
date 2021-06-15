package logic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import interface_.InterfaceMultiRoomChat;

public class ListenersRoomButton implements ActionListener{
	private ClientLogic client;
	private JTabbedPane tabPlace;
	private PrintStream output;
public ListenersRoomButton (ClientLogic client, JTabbedPane tabPlace, PrintStream output) {
	this.client = client;
	this.tabPlace = tabPlace;
	this.output= output;
}
	@Override
	public void actionPerformed(ActionEvent e) {
		
			JButton guilty = (JButton)e.getSource(); 
			String id_sala = guilty.getToolTipText();
			
			if(!client.getRooms().contains(id_sala)) {
				client.addRoom(id_sala);
				try {
					InterfaceMultiRoomChat.createNewChatTab(tabPlace, id_sala, client);
					
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.output.println(id_sala);
				this.output.flush();
				this.output.println(client.getName() + " //Connected");
				this.output.flush();
			}
			for(String name : client.getRooms()) {
				System.out.println(name);
			}
		}


}
