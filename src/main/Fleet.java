package main;

import org.newdawn.slick.Graphics;

import ui.Drawable;
import ui.ImageCache;

public class Fleet implements Drawable {
	private int size;
	private Planet location;
	private Player owner;
	private int x;
	private int y;
	
	
	Fleet(int size, Player owner, Planet location){
		this.size = size;
		this.owner = owner;
		setLocation(location);
	}
	
	public int getSize(){
		return size;
	}
	
	public void moveTo(Planet dest){
		GameObject G = GameObject.getInstance();
		int[] path = G.path(location, dest);
		G.addMove(new Move(this, G.getPlanet(path[1])));
		x -= ((location.getX() - dest.getX()) / 2);
		y -= ((location.getY() - dest.getY()) / 2);
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
		x = location.getX();
		y = location.getY();
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void draw(Graphics g) {
			ImageCache.getImage("starship.gif").draw(x, y);
			g.setColor(owner.getColor());
			g.drawString("[" + size + "]", x + 30, y + 10);

	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
}
