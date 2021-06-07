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
	private ArrayList<PrintStream> clientBuffersOut = new ArrayList<PrintStream>();
	private Socket cli;
	private boolean kill = true;
	private int index;
	private ConnectionThread conn;

	public ReaderThread(BufferedReader buffer, ConnectionThread conn, ArrayList<PrintStream> allOuts, Socket cli,
			int index) {
		this.bufferInput = buffer;
		this.conn = conn;
		this.clientBuffersOut = allOuts;
		this.cli = cli;
	}

	public void kill() {
		this.kill = false;
	}

	@Override
	public void run() {
		while (kill) {
			try {
				String message;
				message = bufferInput.readLine();
				index = conn.getClientSockets().indexOf(cli);
				
				System.out.println(message + " ///// Before if");
				int closeNumber = cli.getPort() + cli.getLocalPort();
				String compare = closeNumber + "AAA";
				if (message.equals(compare)) {
					kill();
					conn.getClientBuffersIn().remove(index);
					conn.getClientBuffersOut().remove(index);
					conn.getClientSockets().get(index).close();
					conn.getClientSockets().remove(index);
					conn.getClientThread().remove(index);
					conn.getClientList().remove(index);
					System.out.println("Client disconnected");
					

				} else {
					System.out.println("boi");
					String name = conn.getClientList().get(index).getName();
					for (int i = 0; i < clientBuffersOut.size(); i++) {
						clientBuffersOut.get(i).println(name+":"+message);
						clientBuffersOut.get(i).flush();
					}
				}

				System.out.println(message + " /////Client message " + index);
				// Scanner tec = new Scanner(System.in);
				// message= tec.nextLine();
				// output.println(message);
				// output.flush();
				// output.flush();
				// output.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}

}
