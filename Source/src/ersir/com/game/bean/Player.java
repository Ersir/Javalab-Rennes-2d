package ersir.com.game.bean;

import ersir.com.game.abstracts.Element;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.ElementType;

public class Player extends Element{
	public Player(String path,float x, float y, int hp, Difficulties diff){
		super(path, x, y, hp, diff,ElementType.Player);
	}
}
