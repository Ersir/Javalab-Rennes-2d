package ersir.com.game.bean;

import ersir.com.game.abstracts.Element;
import ersir.com.game.global.BonusType;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.ElementType;

public class Bonus extends Element{
	private BonusType bonusType;
	public Bonus(String _path, float _x, float _y, int _hp,
			Difficulties _difficulty,BonusType bonusType) {
		super(_path, _x, _y, _hp, _difficulty,ElementType.Bonus);
		setBonusType(bonusType);
	}
	public void setBonusType(BonusType bonusType) {
		this.bonusType = bonusType;
	}
	public BonusType getBonusType() {
		return bonusType;
	}

}
