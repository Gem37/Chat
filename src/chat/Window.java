package chat;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JPanel implements ActionListener {
	JFrame frame = new JFrame();
	JTextField input;
	JLabel usernamePrompt;
	
	
	public Window() {
		frame.setPreferredSize(new Dimension(400, 200));
		frame.setVisible(true);
		frame.add(this);
		frame.setLayout(new GridLayout());
		input = new JTextField(24);
		usernamePrompt = new JLabel("Please enter your username:");
		input.addActionListener(this);
		this.add(usernamePrompt);
		this.add(input);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					Networking.out.close();
					Networking.client.getInputStream().close();
					Networking.client.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
			remove(usernamePrompt);
			frame.repaint();
		}
	}
}
