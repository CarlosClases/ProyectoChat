package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JTextPane;

public class WriterThread extends Thread{
	BufferedReader bufferinput;
	JTextPane show;
	public WriterThread(BufferedReader buffer, JTextPane show){
		this.bufferinput = buffer;
		this.show=show;
	}
	@Override
	public void run(){
		while(true) {
			try {
				String message;
				String protocol;
				protocol = bufferinput.readLine();
				if(protocol.equalsIgnoreCase(show.getToolTipText())) {
				//clientBufferOut.println(message);
				//lee una linea de entrada del servidor
				message=bufferinput.readLine();
				System.out.println(message + "/////From the server");
				//Imprime el mensaje mas el cotenido que ya habia
				show.setText(show.getText()+"\n"+ message);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
