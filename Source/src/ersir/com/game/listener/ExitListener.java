package ersir.com.game.listener;

import ersir.com.game.bean.Game;
import ersir.com.game.gui.StartMenu;
import ersir.com.game.main.Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitListener implements ActionListener{
	private StartMenu jframe;
	public ExitListener(StartMenu j){
		this.setJframe(j);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(Main.launched){
			Main.launched = false;
			Game.stop();
		}
	}


	public void setJframe(StartMenu jframe) {
		this.jframe = jframe;
	}


	public StartMenu getJframe() {
		return jframe;
	}
}
