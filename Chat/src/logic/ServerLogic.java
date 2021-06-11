package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ServerExample class, to create a simple example to connect two computer via a
 * TCPIP connection
 */
public class ServerLogic {

	/* We keep the port in a constant */
	private final static int PORT = 3501;
	//Socket del servidor que esperara conexiones
	private static ServerSocket serverSocket;
	//Lista de conexiones activas de clientes
	private ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	//Lista de buffered de entrada
	private ArrayList<BufferedReader> clientBuffersIn = new ArrayList<BufferedReader>();
	//Lista de buffered de salida para cada cliente
	private ArrayList<PrintStream> clientBuffersOut = new ArrayList<PrintStream>();
	//Lista de lectores para cada cliente
	private ArrayList<ReaderThread> clientThread = new ArrayList<ReaderThread>();
	//Lista de cliente(Objetos)
	private ArrayList<ClientLogic> clientList = new ArrayList<ClientLogic>();
////////Getter & Setters
	public ArrayList<ClientLogic> getClientList() {
		return clientList;
	}

	public void setClientList(ArrayList<ClientLogic> clientList) {
		this.clientList = clientList;
	}
	public static ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static void setServerSocket(ServerSocket serverSocket) {
		ServerLogic.serverSocket = serverSocket;
	}

	public ArrayList<Socket> getClientSockets() {
		return clientSockets;
	}

	public void setClientSockets(ArrayList<Socket> clientSockets) {
		this.clientSockets = clientSockets;
	}

	public ArrayList<BufferedReader> getClientBuffersIn() {
		return clientBuffersIn;
	}

	public void setClientBuffersIn(ArrayList<BufferedReader> clientBuffersIn) {
		this.clientBuffersIn = clientBuffersIn;
	}

	public ArrayList<PrintStream> getClientBuffersOut() {
		return clientBuffersOut;
	}

	public void setClientBuffersOut(ArrayList<PrintStream> clientBuffersOut) {
		this.clientBuffersOut = clientBuffersOut;
	}

	public ArrayList<ReaderThread> getClientThread() {
		return clientThread;
	}

	public void setClientThread(ArrayList<ReaderThread> clientThread) {
		this.clientThread = clientThread;
	}

	public ServerLogic(ServerSocket server) {
		this.setServerSocket(server);
	}
///////End Getters Setters
	public static void main(String[] args) {

		try {
			//El servidor se conecta y espera conexiones
			ServerLogic server = new ServerLogic(new ServerSocket(PORT));
			System.out.println("Server started");
			ConnectionThread conn = new ConnectionThread(server);
			conn.start();

			while (true) {}


		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

	

}
