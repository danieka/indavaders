package ui;

import org.newdawn.slick.Graphics;

/**
 * Draws this object on the Graphics object provided.
 * @author daniel
 *
 */
public interface Drawable {
	void draw(Graphics g);
	int getX();
	int getY();
}