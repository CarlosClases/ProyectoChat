package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JTextPane;

public class WriterThread extends Thread{
	BufferedReader bufferinput;
	PrintStream clientBufferOut;
	JTextPane show;
	public WriterThread(BufferedReader buffer, PrintStream output, JTextPane show){
		this.bufferinput = buffer;
		this.clientBufferOut = output;
		this.show=show;
	}
	@Override
	public void run(){
		while(true) {
			try {
				String message;
				//clientBufferOut.println(message);
				message=bufferinput.readLine();
				System.out.println(message + "/////From the server");
				show.setText(show.getText()+"\n"+ message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
