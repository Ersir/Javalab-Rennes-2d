package ersir.com.game.abstracts;

import ersir.com.game.bean.Game;
import ersir.com.game.bean.Image;
import ersir.com.game.bean.Shoot;
import ersir.com.game.global.Difficulties;
import ersir.com.game.global.Direction;
import ersir.com.game.global.ElementType;
import org.newdawn.slick.Color;

public abstract class Element {
    
    private Image image;
    private String path;
    private Float x;
    private Float y;
    private Integer hp;
    private Difficulties difficulty;
    private ElementType type;
    private Float rotation = 0f;
    private Long lastShoot;
    private Boolean lastShootIsLeft = false;
    private Long id;
    
    public void rotate(Float angle) {
        this.setRotation(this.getRotation() + angle);
    }
    
    public Element(String _path, Float _x, Float _y, Integer _hp, Difficulties _difficulty, ElementType _type) {
        this.setPath(_path);
        this.setX(_x);
        this.setY(_y);
        this.setHp(_hp);
        this.setDifficulty(_difficulty);
        this.setType(_type);
        this.setId(Game.elementId++);
        this.setImage(new Image(this));
        Game.elements.add(this);
        lastShoot = Game.getTime();
    }
    
    public void destroy() {
        Game.elements.remove(this);
    }
    public void doEncounter(Element el){
        if(this.encounters(el)){
            System.out.println(el.getType().toString());
            
            switch(el.getType()){
                case Bonus:
                    Game.bonusList.remove(el);
                    el.destroy();
                    break;
                case IA:
                    el.destroy();
                    break;
                case Shoot:
                    this.setHp(this.getHp()-1);
                    if(!this.isAlive()){
                    this.destroy();
                    if(this.getType()==ElementType.Player){
                        Game.msg = "Vous avez perdu";
                        Game.color = Color.red;
                        Game.win = true;
                        Game.alive = false;
                        }
                    }
                    if(el.getType()!=ElementType.Player)
                    el.destroy();
                    break;
                case Explosion:
                    break;
                case Wall:
                   break;
                default:
                    break;
            }
        }
    }
    
    public Boolean isBeetween(Float min,Float middle,Float max){
        return (min < middle && middle < max);
    }
    public Boolean wouldEncounter(Float xPosition, Float yPosition,Element el,Direction direction) {       
        Boolean predicateEncounter = (
                isBeetween(yPosition, el.getY() + el.getImage().getTexture().getTextureHeight(),yPosition + this.getImage().getTexture().getTextureHeight()) && 
                isBeetween(xPosition, el.getX() +  el.getImage().getTexture().getTextureWidth(), xPosition + this.getImage().getTexture().getTextureWidth())
                );
        
        if(predicateEncounter && direction!=null)  {Game.directionBlocked = direction; System.out.println("direction bloquée:"+direction.toString());}
        else Game.directionBlocked = null;
        return predicateEncounter;
    }
    
    public Boolean encounters(Element el) {
       return isBeetween(el.getX(),  this.getX() + this.getImage().getTexture().getTextureWidth(), el.getX() + el.getImage().getTexture().getTextureWidth()) &&
              isBeetween(el.getY(), this.getY() + this.getImage().getTexture().getTextureHeight(), el.getY() + el.getImage().getTexture().getTextureHeight());
    }
    
    public void shoot(Boolean changeWeaponFire, Integer shootNbr) {
        if (lastShoot < Game.getTime() - 250) {
            lastShoot = Game.getTime();
            float canonCoord;
            if (lastShootIsLeft && changeWeaponFire) {
                canonCoord = this.getX() - 20;
                lastShootIsLeft = false;
            } else {
                canonCoord = this.getX() + this.getImage().getTexture().getTextureWidth();
                lastShootIsLeft = true;
            }
            int adjustShootPosition = this.getType() == ElementType.Player ? -this.getImage().getTexture().getTextureHeight() : this.getImage().getTexture().getTextureHeight();
            for (int i = 0; i < shootNbr; i++) {
                Game.shoots.add(new Shoot("res/spaceinvaders/shot.gif", canonCoord, this.getY() + adjustShootPosition, 15, Difficulties.Easy, this));
            }
        }
    }
    
    public Boolean isAlive(){
        return (this.getHp()>0);
    }
    
    public void setType(ElementType type) {
        this.type = type;
    }
    
    public ElementType getType() {
        return type;
    }
    
    public void setRotation(Float rotation) {
        this.rotation = rotation;
    }
    
    public Float getRotation() {
        return rotation;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    
    public Float getX() {
        return x;
    }
    
    public void setX(Float x) {
        this.x = x;
    }
    
    public Float getY() {
        return y;
    }
    
    public void setY(Float y) {
        this.y = y;
    }
    
    public void setDifficulty(Difficulties difficulty) {
        this.difficulty = difficulty;
    }
    
    public Difficulties getDifficulty() {
        return difficulty;
    }
    
    public void setHp(Integer hp) {
        this.hp = hp;
    }
    
    public int getHp() {
        return hp;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }
    
    void setImage(Image image) {
        this.image = image;
    }
    
    public Image getImage() {
        return image;
    }

}
