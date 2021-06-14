package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextPane;

public class ReaderThread extends Thread {
	private BufferedReader bufferInput;
	//Todas las salidas de todos los clientes
	private ArrayList<PrintStream> clientBuffersOut = new ArrayList<PrintStream>();
	private ClientLogic client;
	private Socket cli;
	private boolean kill = true;
	private int index;
	private ConnectionThread conn;

	public ReaderThread(BufferedReader buffer, ConnectionThread conn, ArrayList<PrintStream> allOuts, Socket cli) {
		this.bufferInput = buffer;
		this.conn = conn;
		this.clientBuffersOut = allOuts;
		this.cli = cli;
	}

	public void kill() {
		this.kill = false;
	}
	private void deleteclient(int index) {
		conn.getClientBuffersIn().remove(index);
		conn.getClientBuffersOut().remove(index);
		try {
			conn.getClientSockets().get(index).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.getClientSockets().remove(index);
		conn.getClientThread().remove(index);
		conn.getClientList().remove(index);
		System.out.println("Client disconnected");
	}
	@Override
	public void run() {
		while (kill) {
			try {
				String protocol;
				protocol = bufferInput.readLine();
				System.out.println(protocol +"///// Protocol");
				String message;
				message = bufferInput.readLine();
				index = conn.getClientSockets().indexOf(cli);
				this.client = conn.getClientList().get(index);
				
				//System.out.println(message + " ///// Before if");
				
				//Codigo necesario para poder cerrar la conexion
				int closeNumber = cli.getPort() + cli.getLocalPort();
				String compare = closeNumber + "AAA";
				//Comprueba si me llega un codigo de cerrado de conexion o un mensaje normal
				if (protocol.equals(compare)) {
					kill();
					deleteclient(index);
				} else {
					//Leer la informacion inicial bufferInput.readLine();
					if(!client.getRooms().contains(protocol)) {
						client.addRoom(protocol);
					}
					for (int i = 0; i < clientBuffersOut.size(); i++) {
						System.out.println(protocol +"///// Protocol to the client");
						//Pilla el nombre del cliente
						String name = conn.getClientList().get(index).getName();
						//Escupe el mensaje a todos los clientes
						clientBuffersOut.get(i).println(protocol);
						clientBuffersOut.get(i).flush();
						System.out.println(message +"///// Message to the client");
						clientBuffersOut.get(i).println(name+":"+message);
						clientBuffersOut.get(i).flush();
					}
					
					System.out.println("boi");
				
				}
				//Clients connected
					System.out.println(client.getName()+ " /////Connected");
					for(String b: client.getRooms()) {
						System.out.println(b);
						if(b.equals(protocol)) {
							System.out.println(b);
						}
					}
				//System.out.println(message + " /////Client message " + index);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}

}
