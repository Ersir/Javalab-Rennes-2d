package ersir.com.game.bean;

import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	private Audio wavEffect;
	
	/**
	 * Start the test 
	 */
	public Sound(String wav){
        try {
    	    wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(wav));
            } catch (IOException e) {
    	}
	}

	public Audio getWavEffect() {
		return wavEffect;
	}

	public void setWavEffect(Audio wavEffect) {
		this.wavEffect = wavEffect;
	}

	public void play(boolean repeat) {
		// polling is required to allow streaming to get a chance to
		// queue buffers.
            
            if(Game.playSound){
		wavEffect.playAsSoundEffect(1.0f, 1.0f, repeat);
		SoundStore.get().poll(0);
            }
	}
}
