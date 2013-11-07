package cs516.gabrielGheorghian.splash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;


public class SplashScreen extends JWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int duration;
	private String image = "images/mail.png";
	private String splash = null;

	
	/**
	 * default constructor for SplashScreen
	 */
	public SplashScreen(){
		duration = 3000;
		splash = "hi";
	}
	
	/**
	 * constructor for SplashScreen
	 * @param d
	 */
	public SplashScreen(int d, String splash) {
		duration = d;
		this.splash = splash;
	}
	
	
	/**
	 * shows the SplashScreen panel
	 */
	public void showSplash() {
		setBackground(Color.white);
		
		

		// Build the splash screen
		JLabel label = new JLabel(createImageIcon(image));
		JLabel copyrt = new JLabel(splash, JLabel.CENTER);
		copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 15));
		add(label, BorderLayout.CENTER);
		add(copyrt, BorderLayout.SOUTH);
		pack();
		
		// Retrieve the window's bounds, centering the window
		int width = this.getWidth();
		int height = this.getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);
				
		// Display it
		setVisible(true);

		// Wait a little while, maybe while loading resources
	}
	
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = SplashScreen.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	
	/**
	 * Runs and closes
	 */
	public void showSplashAndExit() {
		showSplash();
		try {
			Thread.sleep(duration);
		} catch (Exception e) {
		}

		setVisible(false);
		this.dispose();
	}
}
