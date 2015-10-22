package game.base;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class SkinsCollection {

	public static Map<String, Image> skins = new HashMap<String, Image>();

	public static void load() {
		skins.put("doge", new ImageIcon("res/doge.png").getImage());
	}
	
	public static Image getSkin(String key){
		return skins.get(key);
	}

}
