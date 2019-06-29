package chat;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Networking extends Thread {
	
	public static DataOutputStream out;
	public static DataInputStream in;
	public static Socket client;
	public static ArrayList<String> usersOnline = new ArrayList<String>();
	
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
				String message = in.readUTF();
				acceptMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sendMessage (String message) {
		try {
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void acceptMessage(String message) {
		switch (message.substring(0, message.indexOf(" "))) {
			case "@users":
				updateUsers(message.substring(message.indexOf(" ") + 1));
				
				break;
			
			default:
				
				break;
		}
	}
	
	public static void updateUsers(String usersString) {
		String[] usersSplit = usersString.split("   ");
		
		usersOnline.clear();
		for(String user : usersSplit) {
			usersOnline.add(user);
		}
		
		System.out.println(usersOnline);
		Window.updateList();
	}
}
