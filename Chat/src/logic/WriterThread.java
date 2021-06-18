package logic;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class WriterThread extends Thread {
	private BufferedReader bufferinput;
	private ArrayList<JPanel> panels;
	private JTextPane show;
	private boolean kill = false;
	private Connection sqlConnection;

	public WriterThread(BufferedReader buffer, ArrayList<JPanel> panels, Connection sqlConnection) {
		this.bufferinput = buffer;
		this.panels = panels;
		this.sqlConnection = sqlConnection;
	}

	public void suicide() {
		kill = true;
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
				System.out.println("FFFFFFFFFF" + message);
				for(JPanel jpanels : panels) {
					System.out.println("Protocol client ///////"+ jpanels.getName());
					//if(protocol.equalsIgnoreCase(jpanels.getName())) {
						System.out.println(jpanels.getName()+"/////Jpanel");
						
						for(int i=0; i< jpanels.getComponentCount();i++) {
						Object component = jpanels.getComponent(i);
						if(component instanceof JTextPane) {
							show = (JTextPane)component;
							System.out.println(show);
							 if(show.getName().equalsIgnoreCase("ChatPlace")&&!message.equalsIgnoreCase(protocol+"FinoMaricon")) {
									System.out.println("ChatPlace " +" //Protocol From the server " +show.getName() +" //Local protocol");
									System.out.println(message + "/////From the server");
									//Imprime el mensaje mas el cotenido que ya habia
									show.setText(show.getText()+"\n"+ message);
								}
							 /*else if(show.getName().equalsIgnoreCase("ClientsConnected") && message.equalsIgnoreCase(protocol+"FinoMaricon")) {
								 Statement statement;
								try {
									statement = sqlConnection.createStatement();
									String nameRoom = protocol;
									String sentence= "SELECT cli.Name, r.Name FROM Client cli ,Connection con, Room r WHERE cli.ID = con.ID_Client && r.ID=con.ID_Room && r.name = '"+nameRoom+"';";
										System.out.println(sentence);	
									statement.execute(sentence);
									
									ResultSet rs = statement.executeQuery(sentence);
									while (rs.next()) {
										
										String names = rs.getString(1);
										System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA2");
										show.setText(show.getText()+"\n"+ names);
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
									
							}*/
						}
					}
					
					//}		
				}
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
