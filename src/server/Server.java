package server;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import chat.Window;

import java.io.*;

public class Server extends Thread {
	private ServerSocket serverSocket;
	
	HashMap<String, User> users = new HashMap<String, User>();
	
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void run() {
		while(true) {
			try {
				Socket server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				User user = new User(server.getInetAddress().toString(), in, out, server);
				new UserListener(user).start();
				users.put(server.getInetAddress().toString(), user);
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String [] args) {
		int port = 8765;
		try {
			Thread t = new Server(port);
			t.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class UserListener extends Thread {
		User user;
		
		public UserListener(User user) {
			this.user = user;
		}
		@Override
		public void run() {
			while(true) {
				try {
					String message = user.in.readUTF();
					if(user.username == null) {
						user.username = message;
						System.out.println(user.ip + " set their username to " + user.username);
						
						String usernames = "";
						
						for(User u : users.values()) {
							usernames = usernames + u.username + "   ";
						}
						
						for(User u : users.values()) {
							u.out.writeUTF("Users Online: " + usernames);
						}
					} else {
						
					}
				} catch (IOException e) {
					break;
				}
			}
		}
	}
}
