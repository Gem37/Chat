package chat;

import java.net.*;
import java.io.*;

public class Networking extends Thread {
	
	public static DataOutputStream out;
	public static DataInputStream in;
	public static Socket client;
	
	public Networking(int port, String serverName) {
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			client = new Socket(serverName, port);

			System.out.println("Just connected to " + client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			out = new DataOutputStream(outToServer);

			InputStream inFromServer = client.getInputStream();
			in = new DataInputStream(inFromServer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				System.out.println(in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage (String message) {
		try {
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
