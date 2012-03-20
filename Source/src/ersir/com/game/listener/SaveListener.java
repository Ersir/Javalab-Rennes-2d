package ersir.com.game.listener;

import ersir.com.game.bean.Game;
import ersir.com.game.gui.StartMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveListener implements ActionListener{
	private StartMenu jframe;
	public SaveListener(StartMenu j){
		this.setJframe(j);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Game.save();
	}


	public void setJframe(StartMenu jframe) {
		this.jframe = jframe;
	}


	public StartMenu getJframe() {
		return jframe;
	}
}
