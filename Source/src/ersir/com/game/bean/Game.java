package ersir.com.game.bean;

import ersir.com.game.abstracts.Element;
import ersir.com.game.dao.FileSaving;
import ersir.com.game.global.*;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Game {

    static public int fps = 0,score = 0,iaToDefeat,widthScreen,heightScreen;
    static public float x,y;
    static public float rotation = 0;
    static public long lastFrame = 0,lastFPS = 0,elementId = 0;
    static public boolean alive = true,win = false,generic = false,continueGameLoop = true;
    static public boolean playSound = false;
    static public Player player = null;
    static public String msg = "";
    static public Color color = Color.white;
    static public Message message = new Message();
    static public ArrayList<Element> elements = new ArrayList<Element>();
    static public ArrayList<Shoot> shoots = new ArrayList<Shoot>();
    static public ArrayList<Explosion> explosions= new ArrayList<Explosion>();
    static public ArrayList<Bonus> bonusList = new ArrayList<Bonus>();
    static public Direction directionBlocked,myDirection=null;
    static public Float oldX,oldY;

    public Game(int width, int height) {
        widthScreen = width;
        heightScreen = height;
    }

    public static void initVars(boolean load) {
        x = widthScreen / 2;
        y = heightScreen / 2;
        iaToDefeat = 10;
        if (load) {
            elements = FileSaving.getElements();
            FileSaving.debug();
            player = (Player) elements.get(0);
            x = player.getX();
            y = player.getY();
        } else {
            player = new Player("res/spaceinvaders/ship.gif", x, y, 100, Difficulties.Easy);
            new Wall("res/mur.png", 0, 0, 0);
              for (int k = 0; k < widthScreen; k = k + 100) {
                  new Wall("res/mur.png", k, 400, 100);
             }
            new IA("res/Bomb.gif", 100, 100, 100, Difficulties.Easy,IAType.Stupid);
            new IA("res/Bomb.gif",100,200,100,Difficulties.Hard,IAType.Smart);
        }
    }

    public static void start(boolean load) {
        initGL(widthScreen, heightScreen);// init OpenGL
        getDelta();
        initVars(load);
        message.init();
        lastFPS = getTime();
        Sound s = new Sound("res/techno.wav");
        s.play(true);
        msg = "Score:" + score;
        color = Color.green;
        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            update(delta);
            renderGL();
            Display.update();
            Display.sync(60); // cap fps to 60fps
        }
        Display.destroy();
    }

    public static void update(int delta) {
        if (continueGameLoop) {
            action(delta);
            collision();
            updateFPS();
        }
    }

    public static int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public static void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(GL11.GL_BLEND);         // enable alpha blending
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public static void renderGL() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Color.white.bind();
        for (Element element : elements) {
            element.getImage().render();
        }
        message.display(25, 25, msg, color);
    }

    public static void save() {
        FileSaving.save();
    }

    public static void action(int delta) {
        if (!alive) {
            return;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            player.setRotation(0.15f * delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            player.setRotation(-0.15f * delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            player.setRotation(0.15f * delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            player.setRotation(-0.15f * delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            x -= 0.35f * delta;
            myDirection = Direction.Left;

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            x += 0.35f * delta;
            myDirection = Direction.Right;

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
            y -= 0.35f * delta;
            myDirection = Direction.Top;

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            y += 0.35f * delta;
            myDirection = Direction.Bottom;

        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            player.shoot(true, 1);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            player.shoot(true, 3);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
            save();
        }

        if (x < 0) {
            x = 0;
        }
        if (x > widthScreen) {
            x = widthScreen;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > heightScreen) {
            y = heightScreen;
        }
        oldX = x;
        oldY = y;
    }

    public static void collision() {

        for (int j = 0; j < elements.size(); j++) {
            Element el = elements.get(j);
            
            /**
             * Update player position whatever happened
             */


            if (el != player) {
                if (!player.wouldEncounter(x, y, el, myDirection) && player.isAlive()) {
                    player.doEncounter(el);
                } else {
                    x = oldX;
                    y = oldY;
                }
            }

            player.setX(x);
            player.setY(y);
            /**
             *  Update shoot position whatever happened
             */
            if(el.getType() == ElementType.Shoot){
            
            
            /**
             * Shoot out of Screen
             */
            Element shoot = el;
            if (shoot.getX() >= widthScreen || shoot.getX() <= 0 || shoot.getY() >= heightScreen || shoot.getY() <= 0) {
                shoots.remove(el);
                elements.remove(shoot);
                break;
            }

            /**
             * Shoot on something
             */
            if (((Shoot) shoot).getShooter() == player) {
                shoot.setY(shoot.getY() - 7);
            } else {
                shoot.setY(shoot.getY() + 7);
            }
            }
            else{
                for(int i =0 ; i < elements.size();i++){
                Element something = elements.get(i);
                if (el.encounters(something)) {
                    switch(something.getType()){
                        case Shoot:
                        Explosion explosion = new Explosion("res/Fire.gif", el.getX(), el.getY(), 0, null, getTime());
                        explosions.add(explosion);
                        new Sound("res/explosion.wav").play(false);
                        shoots.remove(something);
                        something.destroy();
                        el.destroy();
                        break;
                        default:
                        case Explosion:
                        break;
                        case IA:
                        if(alive&&!win) score ++; msg = "Score:" + score;
                        break;
                    }
                    }
            }
                    if (score > (iaToDefeat - 1) && alive) {
                        win = true;
                        msg = "Vous avez gagné";
                    }
                }

            if (el.getType() == ElementType.IA) {
                ((IA) el).moveTo(player);
            }
        }
        
        if (win && !generic) {
            generic = true;
            elements = new ArrayList<Element>();
            (new Sound("/res/applause.wav")).play(false);
        }

        /**
         * Explosion disappears
         */
        for (int k = 0; k < explosions.size(); k++) {
            Explosion explosion = explosions.get(k);
            if (getTime() > explosion.getTime() + 1000) {
                explosions.remove(explosion);
                elements.remove(explosion);
                double alea = Math.random() * 10;
                Math.floor(alea);
                /**
                 * Bonus appears
                 */
                if (alea < 0) {
                    bonusList.add(new Bonus("res/bonus.png", explosion.getX(), explosion.getY(), k, null, BonusType.weapon));
                }
            }
        }

    }

    public static void stop() {
        continueGameLoop = false;

    }
}
