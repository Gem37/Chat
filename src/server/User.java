package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class User {
	String ip;
	String username;
	
	Socket socket;
	
	DataInputStream in;
	DataOutputStream out;
	
	public User(String ip, DataInputStream in, DataOutputStream out, Socket socket) {
		this.ip = ip;
		this.in = in;
		this.out = out;
		this.socket = socket;
	}
	
}
