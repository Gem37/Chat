package chat;

public class Main {
	static Window window = new Window();
	static Networking networking;
	static boolean hasUsername = false;
	public static void main(String[] args) {
		networking = new Networking(8765, "127.0.0.1");
		networking.start();
	}
}
