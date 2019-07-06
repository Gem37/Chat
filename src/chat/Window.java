package chat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class Window extends JPanel implements ActionListener, MouseListener {
	static JFrame frame = new JFrame();
	JTextField input;
	JLabel usernamePrompt;
	static JPanel usernameList = new JPanel();
	JPanel inputPanel = new JPanel();
	static JSplitPane splitPane;
	static String username;
	static String selectedUser;
	
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
		usernameList.addMouseListener(this);
		usernameList.setLayout(new BoxLayout(usernameList, BoxLayout.Y_AXIS));
		
		
		usernameList.removeAll();
		for(String user : Networking.usersOnline) {
			JLabel userLabel = new JLabel(user);
			
			usernameList.add(userLabel);
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
		System.out.println("We Recieved an Event");
		if(e.getSource() == input) {
			if(Main.hasUsername) {
				Main.networking.sendMessage(input.getText());
			
			System.out.println("Sent Message: " + input.getText());
			
			input.setText("");
			
			} else {
				
				username = input.getText();
				
				Main.networking.sendMessage(username);
				System.out.println("Username Set To: " + username);
				
				input.setText("");
				
				Main.hasUsername = true;
				inputPanel.remove(usernamePrompt);
				
				frame.repaint();
			}
			
		}
	}
	
	public static boolean isMouseWithinComponent(Component c) {
	    Point mousePos = MouseInfo.getPointerInfo().getLocation();
	    Rectangle bounds = c.getBounds();
	    bounds.setLocation(c.getLocationOnScreen());
	    return bounds.contains(mousePos);
	}
	
	public static void updateList() {
		usernameList.removeAll();
		for(String user : Networking.usersOnline) {
			if(!user.equals(username)) {
				usernameList.add(new JLabel(user));
			}
		}
		frame.pack();
		splitPane.resetToPreferredSizes();
		usernameList.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(usernameList.findComponentAt(e.getPoint()) instanceof JLabel) {
			selectedUser = ((JLabel) usernameList.findComponentAt(e.getPoint())).getText();
			System.out.println(selectedUser);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
