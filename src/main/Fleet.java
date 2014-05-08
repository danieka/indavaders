package main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ui.Drawable;
import ui.ImageCache;

public class Fleet implements Drawable {
	private int size;
	private Planet location;
	private Player owner;
	
	
	Fleet(int size, Player owner, Planet location){
		this.size = size;
		this.owner = owner;
		this.location = location;
	}
	
	public int getSize(){
		return size;
	}
	
	public void moveTo(Planet planet){
		GameObject G = GameObject.getInstance();
		int[] path = G.path(location, planet);
		G.addMove(new Move(this, G.getPlanet(path[1])));
		return;
	}
	
	public Planet getPlanet(){
		return location;
	}
	
	public Player getOwner(){
		return owner;
	}

	public void setLocation(Planet to) {
		location = to;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void draw(Graphics g) {
			int x = location.getX();
			int y = location.getY();
			ImageCache.getImage("starship.gif").draw(x, y);
			g.setColor(owner.getColor());
			g.drawString("[" + size + "]", x+30, y+10);

	}
}
