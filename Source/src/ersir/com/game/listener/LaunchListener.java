package ersir.com.game.listener;

import ersir.com.game.bean.Game;
import ersir.com.game.gui.StartMenu;
import ersir.com.game.main.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class LaunchListener implements ActionListener{
	private StartMenu jframe;
	public LaunchListener(StartMenu j){
		this.setJframe(j);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent button) {
		if(!Main.launched)
			Main.launched = true;
			boolean load = !"launch".equals(((JButton) button.getSource()).getName());
			Game.start(load);
	}


	public void setJframe(StartMenu jframe) {
		this.jframe = jframe;
	}


	public StartMenu getJframe() {
		return jframe;
	}
}
