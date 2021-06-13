package logic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import interface_.InterfaceMultiRoomChat;

public class ListenersRoomButton implements ActionListener{
	private ClientLogic client;
	private JTabbedPane tabPlace;
public ListenersRoomButton (ClientLogic client, JTabbedPane tabPlace) {
	this.client = client;
	this.tabPlace = tabPlace;
}
	@Override
	public void actionPerformed(ActionEvent e) {
		
			JButton guilty = (JButton)e.getSource(); 
			String id_sala = guilty.getToolTipText();
			
			if(!client.getRooms().contains(id_sala)) {
				client.addRoom(id_sala);
				InterfaceMultiRoomChat.createNewChatTab(tabPlace, id_sala, client);
			}
			for(String name : client.getRooms()) {
				System.out.println(name);
			}
		}


}
