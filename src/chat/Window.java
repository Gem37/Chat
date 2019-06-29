package chat;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Window extends JPanel implements ActionListener {
	JFrame frame = new JFrame();
	JTextField input;
	JLabel usernamePrompt;
	static JPanel usernameList = new JPanel();
	JPanel inputPanel = new JPanel();
	JSplitPane splitPane;
	
	public Window() {
		frame.setPreferredSize(new Dimension(600, 400));
		frame.setVisible(true);
		frame.add(this);
//		frame.setLayout(new GridLayout());
		
		
		input = new JTextField(24);
		usernamePrompt = new JLabel("Please enter your username:");
		input.addActionListener(this);
		inputPanel.add(usernamePrompt);
		inputPanel.add(input);
		usernameList.setLayout(new BoxLayout(usernameList, BoxLayout.Y_AXIS));
		
		usernameList.removeAll();
		for(String user : Networking.usersOnline) {
			usernameList.add(new JLabel(user));
		}
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, usernameList, inputPanel);
		splitPane.setPreferredSize(new Dimension(600, 400));
		
		
		this.add(splitPane);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Networking.sendMessage("DISCONNECTING");
				System.exit(0);
			}
		});
		
		frame.pack();
		frame.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == input && Main.hasUsername) {
			
			Main.networking.sendMessage(input.getText());
			
			System.out.println("Sent Message: " + input.getText());
			
			input.setText("");
			
		} else if(!Main.hasUsername) {
			
			Main.networking.sendMessage(input.getText());
			
			System.out.println("Username Set To: " + input.getText());
			
			input.setText("");
			
			Main.hasUsername = true;
			inputPanel.remove(usernamePrompt);
			
			frame.repaint();
		}
	}
	
	public static void updateList() {
		usernameList.removeAll();
		for(String user : Networking.usersOnline) {
			usernameList.add(new JLabel(user));
		}
		usernameList.repaint();
	}
}
