package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionThread extends Thread {
	private ServerLogic server;
	private ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	private ArrayList<BufferedReader> clientBuffersIn = new ArrayList<BufferedReader>();
	private ArrayList<PrintStream> clientBuffersOut = new ArrayList<PrintStream>();
	private ArrayList<ReaderThread> clientThread = new ArrayList<ReaderThread>();
	private ArrayList<ClientLogic> clientList = new ArrayList<ClientLogic>();
	//private ObjectInputStream objectInput;
	//El Socket que guarda la conexion de un cliente
	private Socket clientSocket;

	private boolean kill = false;

	public ConnectionThread() {

	}
///Getters & Setters
	public boolean getKill() {
		return kill;
	}

	public void setKill(boolean kill) {
		this.kill = kill;
	}
	public ArrayList<ClientLogic> getClientList() {
		return clientList;
	}

	public void setClientList(ArrayList<ClientLogic> clientList) {
		this.clientList = clientList;
	}
	public ServerLogic getServer() {
		return server;
	}

	public void setServer(ServerLogic server) {
		this.server = server;
	}

	ConnectionThread(ServerLogic server) {
		this.setServer(server);
	}

	public ArrayList<Socket> getClientSockets() {
		return clientSockets;
	}

	public ArrayList<BufferedReader> getClientBuffersIn() {
		return clientBuffersIn;
	}

	public ArrayList<PrintStream> getClientBuffersOut() {
		return clientBuffersOut;
	}

	public ArrayList<ReaderThread> getClientThread() {
		return clientThread;
	}

	public void setClientSockets(ArrayList<Socket> clientSockets) {
		this.clientSockets = clientSockets;
	}

	public void setClientBuffersIn(ArrayList<BufferedReader> clientBuffersIn) {
		this.clientBuffersIn = clientBuffersIn;
	}

	public void setClientBuffersOut(ArrayList<PrintStream> clientBuffersOut) {
		this.clientBuffersOut = clientBuffersOut;
	}

	public void setClientThread(ArrayList<ReaderThread> clientThread) {
		this.clientThread = clientThread;
	}
/////End Getters & Setters
	//// Methods////
	public void kill() {
		this.setKill(true);
	}

	public void killAllFromClient() {

	}

	public void run() {
		try {
			// ConnectionThread conn= new ConnectionThread();
			while (!kill) {

				clientSocket = server.getServerSocket().accept();
				this.clientSockets.add(clientSocket);
				int indexControl = clientSockets.indexOf(clientSocket);
				/*this.objectInput = new ObjectInputStream(clientSocket.getInputStream());
				try {
					this.clientList.add((ClientLogic) objectInput.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//Crea un nuevo buffer de entrada
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				this.clientBuffersIn.add(input);
				
				//Crea un nuevo buffer de salida
				PrintStream output = new PrintStream(clientSocket.getOutputStream());
				this.clientBuffersOut.add(output);
				
				//Espera la informacion inicial (input.readLine();)
				String name = input.readLine();
				
				//Se añade nuevo objeto cliente
				this.getClientList().add(new ClientLogic(name, clientSocket));
				
				//Crear un nuevo thread de lectura
				ReaderThread reader = new ReaderThread(input, this, clientBuffersOut, clientSocket);
				this.clientThread.add(reader);
				
				//Inicia el buffer de lectura del cliente
				reader.start();
				
				//Aseguradura de que los datos funcionan correctamente
				this.setClientBuffersIn(clientBuffersIn);
				this.setClientBuffersOut(clientBuffersOut);
				this.setClientSockets(clientSockets);
				this.setClientThread(clientThread);
				// this.getClientThread().get(indexControl).start();
				
				//Cantidad de clientes conectados
				System.out.println(clientSockets.size() + " Clients connected");
				
				//Añade los datos al servidor
				server.setClientSockets(clientSockets);
				server.setClientBuffersIn(clientBuffersIn);
				server.setClientBuffersOut(clientBuffersOut);
				server.setClientThread(clientThread);
				server.setClientList(clientList);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
