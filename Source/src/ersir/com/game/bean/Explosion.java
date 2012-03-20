package ersir.com.game.bean;

import ersir.com.game.abstracts.Element;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.ElementType;

public class Explosion extends Element{
	private float time;
	public Explosion(String _path, float _x, float _y, int _hp,
			Difficulties _difficulty,float time) {
		super(_path, _x, _y, _hp, _difficulty,ElementType.Explosion);
		this.setTime(time);
	}
	public void setTime(float time) {
		this.time = time;
	}
	public float getTime() {
		return time;
	}

}
