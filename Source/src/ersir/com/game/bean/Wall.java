package ersir.com.game.bean;


import ersir.com.game.abstracts.Element;
import ersir.com.game.global.ElementType;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class Wall extends Element{
	private Element shooter;
	public Wall(String path,float x, float y, int hp){
		super(path, x, y, hp, null,ElementType.Wall);
		this.setShooter(shooter);
	}
	public void setShooter(Element shooter) {
		this.shooter = shooter;
	}
	public Element getShooter() {
		return shooter;
	}
}
