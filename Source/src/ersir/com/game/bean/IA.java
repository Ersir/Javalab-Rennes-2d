package ersir.com.game.bean;

import ersir.com.game.abstracts.Element;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.Direction;
import ersir.com.game.global.ElementType;
import ersir.com.game.global.IAType;

public class IA extends Element{
    public float lastShoot = 0;
    public IAType iatype;
    public IA(String path,float x, float y, int hp, Difficulties diff,IAType iatype){
        super(path, x, y, hp, diff,ElementType.IA);
        this.iatype = iatype;
    }
    
    public void avoid(Element el){
        if(iatype==IAType.Stupid)
            return;
        if(el.getX()!=this.getX())
            if(el.getX()<this.getX())
                this.setX(this.getX()+1);
            else this.setX(this.getX()-1);
        if(el.getY() != this.getY())
            if(el.getY()<this.getY())
                this.setY(this.getY()+1);
            else this.setY(this.getY()-1);
    }
    public boolean hasInRange(Element el,float range){
        if(iatype==IAType.Stupid)
            return false;
        if(range==0f)
            range=10f;
        if(this.getX()-range<el.getX() && el.getX() < this.getX()+range){
            return true;
        }
        else return false;
    }
    public boolean hasInRangeForCanon(Element el,float range){
        if(iatype==IAType.Stupid)
            return false;
        if(range==0f)
            range=10f;
        if(this.getY()-range<el.getY() && el.getY() < this.getY()+range){
            return true;
        }
        else return false;
    }
    public void moveTo(Element el){
        if(iatype==IAType.Stupid){
            moveTo(Direction.Left);
            return;
        }
        if(el.getX()!=this.getX())
        {if(el.getX()>this.getX())
            this.setX(this.getX()+1);
        else this.setX(this.getX()-1);}
        if(this.getDifficulty() != Difficulties.Easy){
            if(el.getY() != this.getY())
            {if(el.getY()>this.getY())
                this.setY(this.getY()+1);
            else this.setY(this.getY()-1);
            }
        }
        if(hasInRange(el,10f))
            this.shoot(false, 1);
    }
    
    public void moveTo(Direction direction) {
        if(direction == Direction.Left || direction == Direction.Right)
            if(this.getX() >=  Game.widthScreen)
                this.setX(this.getX()-1);
            else if(this.getX() < Game.widthScreen)
                this.setX(this.getX()+1);
        
    }
}
