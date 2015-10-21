package game.base;


import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class AudioPlayer {

	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	
	public static void load(){
		

		// Modify the links to add music and sounds
		
		//soundMap.put("menu_sound", new Sound("res/namefile.wav"));
		//musicMap.put("music", new Music("res/namefile.wav"));

		
	}
	
	public static Music getMusic(String key){
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key){
		return soundMap.get(key);
	}
}
