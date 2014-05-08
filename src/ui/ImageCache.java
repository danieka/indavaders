package ui;

import java.util.HashMap;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageCache {
	private static ImageCache uniqInstance;
	
    private static HashMap<String, Image> mImages = new HashMap<String, Image>();

    public static Image getImage(String imageName) {
        Image result = mImages.get(imageName); //returns null if image not present.
        if (result == null) {
    		try {
    			result = new Image("resources/" + imageName);
    		} catch (SlickException e) {
    			e.printStackTrace();
    		}
            mImages.put(imageName, result);
        }
        return result;
    }
	public static synchronized ImageCache getInstance() {
	    if (uniqInstance == null) {
	      uniqInstance = new ImageCache();
	    }
	    return uniqInstance;
	  }

}
