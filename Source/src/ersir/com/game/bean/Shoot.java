package ersir.com.game.bean;

import ersir.com.game.abstracts.Element;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.ElementType;

public class Shoot extends Element{
	private Element shooter;
	public Shoot(String path,float x, float y, int hp, Difficulties diff, Element shooter){
		super(path, x, y, hp, diff,ElementType.Shoot);
		this.setShooter(shooter);
	}
	public void setShooter(Element shooter) {
		this.shooter = shooter;
	}
	public Element getShooter() {
		return shooter;
	}
}
