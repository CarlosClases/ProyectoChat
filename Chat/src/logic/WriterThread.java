package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class WriterThread extends Thread{
	private BufferedReader bufferinput;
	private ArrayList<JPanel> panels;
	private JTextPane show;
	private boolean kill= false; 
	private Connection sqlConnection;
	public WriterThread(BufferedReader buffer, ArrayList<JPanel>  panels, Connection sqlConnection){
		this.bufferinput = buffer;
		this.panels=panels;
		this.sqlConnection=sqlConnection;
	}
	public void suicide() {
		kill= true;
	}
	@Override
	public void run(){
		while(!kill) {
			try {
				String message;
				String protocol;
				protocol = bufferinput.readLine();
				System.out.println("Protocol server ///////"+ protocol);
				message=bufferinput.readLine();
				for(JPanel jpanels : panels) {
					System.out.println("Protocol client ///////"+ jpanels.getName());
					if(protocol.equalsIgnoreCase(jpanels.getName())) {
						System.out.println(jpanels);
						
						for(int i=0; i< jpanels.getComponentCount();i++) {
						Object component = jpanels.getComponent(i);
						if(component instanceof JTextPane) {
							show = (JTextPane)component;
							System.out.println(show);
							 if(show.getName().equalsIgnoreCase("ChatPlace")) {
									System.out.println("ChatPlace " +" //Protocol From the server " +show.getName() +" //Local protocol");
									System.out.println(message + "/////From the server");
									//Imprime el mensaje mas el cotenido que ya habia
									show.setText(show.getText()+"\n"+ message);
								}
							}
						}
					}
					
					}		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
