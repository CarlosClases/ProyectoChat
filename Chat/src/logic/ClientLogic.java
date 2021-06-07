package logic;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
/** ClientExample class, to create a simple example to connect two computer via a 
 * TCPIP connection
 *  */
public class ClientLogic {
    /*Port 5002*/
	private Socket socket;
	
	private String name;
    
    private static boolean connection = true;
    
    private ArrayList<String> rooms = new ArrayList<String>();
    
    public ArrayList<String> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<String> rooms) {
		this.rooms = rooms;
	}

	public static boolean isConnection() {
		return connection;
	}

	public ClientLogic(String name, int port, String server) {
    	setName(name);
    	try {
			socket = new Socket(server,port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public ClientLogic(String name, Socket cli) {
    	this.setName(name);
    	this.setSocket(cli);
    }
	@Override
	public String toString() {
		return "ClientLogic [socket=" + socket + ", name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setConnection(boolean connection) {
		ClientLogic.connection = connection;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}