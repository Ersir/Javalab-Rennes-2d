package ersir.com.game.bean;
import ersir.com.game.abstracts.Element;
import ersir.com.game.global.ElementType;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Image {
	private Element element;
	private Texture texture;
	private float lastAngle=0;
	public Element getElement() {
		return element;
	}
	public void setElement(Element element) {
		this.element = element;
	}

	/**
	 * Initialize resources by loading a texture
	 * @param element
	 */
	public Image(Element el){
		this.setElement(el);
		this.load();
	}
 public void load() {
		try {
                    String extension =  element.getPath().split("\\.")[1].toUpperCase();
		    setTexture(TextureLoader.getTexture(extension, ResourceLoader.getResourceAsStream(element.getPath())));
		} catch (IOException e) {
		}
	}

	/**
	 * Renders Actual graphics by creating quad and binding texture
	 */
 public void render() {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		{
		float x = element.getX();
		float y = element.getY();
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(x+texture.getTextureWidth(),y);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(x+texture.getTextureWidth(),y+texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(x,y+texture.getTextureHeight());
		}
		GL11.glEnd();
		if(lastAngle != element.getRotation() && element.getType() == ElementType.Player){
			GL11.glTranslatef(element.getX(),element.getY(),0);
			GL11.glRotatef(element.getRotation(), 0f, 0f, 1f);
			GL11.glTranslatef(-element.getX(),-element.getY(),0);
			lastAngle = element.getRotation();
			}
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public Texture getTexture() {
		return texture;
	}
}
