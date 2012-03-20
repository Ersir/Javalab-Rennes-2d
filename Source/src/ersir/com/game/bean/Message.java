package ersir.com.game.bean;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
 
@SuppressWarnings("deprecation")
public class Message {
 
	private TrueTypeFont font;
	private static boolean antiAlias = true;

        public void init() {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, antiAlias);
	}
 
         public void display(float x, float y,String msg, Color color) {
		font.drawString(x, y, msg, color);
 	}
        
        public void erase(){
            font = null;
        }
 

}
