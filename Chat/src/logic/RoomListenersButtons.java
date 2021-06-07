package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import interface_.InterfaceMultiRoomChat;

class RoomListenersButtons implements ActionListener{
	private JButton button;
	private InterfaceMultiRoomChat page;
	public RoomListenersButtons(JButton button) {
		this.setButton(button);
	}
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton button) {
		this.button = button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(this.getButton())) {
			
		}
	}
	

}
