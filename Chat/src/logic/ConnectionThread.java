package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private Socket client;

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

				client = server.getServerSocket().accept();
				this.clientSockets.add(client);
				int indexControl = clientSockets.indexOf(client);
				BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
				this.clientBuffersIn.add(input);
				PrintStream output = new PrintStream(client.getOutputStream());
				this.clientBuffersOut.add(output);
				String name = input.readLine();
				this.getClientList().add(new ClientLogic(name, client));
				ReaderThread reader = new ReaderThread(input, this, clientBuffersOut, client, indexControl);
				this.clientThread.add(reader);
				reader.start();
				this.setClientBuffersIn(clientBuffersIn);
				this.setClientBuffersOut(clientBuffersOut);
				this.setClientSockets(clientSockets);
				this.setClientThread(clientThread);
				// this.getClientThread().get(indexControl).start();

				System.out.println(clientSockets.size() + " Clients connected");
				server.setClientSockets(clientSockets);
				server.setClientBuffersIn(clientBuffersIn);
				server.setClientBuffersOut(clientBuffersOut);
				server.setClientThread(clientThread);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
