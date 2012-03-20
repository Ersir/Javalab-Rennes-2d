package ersir.com.game.main;

import ersir.com.game.bean.Game;
import ersir.com.game.gui.StartMenu;

public class Main {
static public boolean launched = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	new StartMenu().setMainFrame();
            new Game(800,600).start(false);
	}

}
