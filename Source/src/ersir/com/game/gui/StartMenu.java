package ersir.com.game.gui;

import ersir.com.game.listener.ExitListener;
import ersir.com.game.listener.LaunchListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class StartMenu {
	public JFrame mainFrame;
	public JPanel mainPanel;
	public JTextPane mainText;
	public JPanel jPanelContent;
	public JButton launch;
	public JButton load;
	public JButton exit;
	
	private JPanel getJPanelContent() {
		if (jPanelContent == null) {
			jPanelContent = new JPanel();
			jPanelContent.setLayout(new BorderLayout());
			jPanelContent.setPreferredSize(new Dimension(795, 520));
		}
		return jPanelContent;
	}
	
	public JFrame setMainFrame() {
		launch = new JButton("Lancer");
		launch.setName("launch");
		launch.setPreferredSize(new Dimension(74, 18));
		launch.setForeground(Color.WHITE);
		launch.setBorderPainted(false);
		launch.addActionListener(new LaunchListener(this));
		load = new JButton("Continuer");
		load.setName("continue");
		load.setPreferredSize(new Dimension(74, 18));
		load.setForeground(Color.WHITE);
		load.setBorderPainted(false);
		load.addActionListener(new LaunchListener(this));
		exit = new JButton("Quitter");
		exit.setName("exit");
		exit.setPreferredSize(new Dimension(74, 18));
		exit.setForeground(Color.WHITE);
		exit.setBorderPainted(false);
		exit.addActionListener(new ExitListener(this));
		mainPanel = new JPanel();
		mainText = new JTextPane();
		mainText.setBackground(mainPanel.getBackground());
		mainText.setText("Bienvenue dans le jeu GameTest \n");
		mainPanel.add(mainText);
		mainPanel.add(launch);
		mainPanel.add(load);
		mainPanel.add(exit);
		mainFrame = new JFrame();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(new Dimension(800, 604));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(getJPanelContent(), BorderLayout.CENTER);
		mainFrame.setTitle("Game Test");
		mainFrame.setContentPane(mainPanel);
		mainFrame.setResizable(false);
		return mainFrame;
	}
}
