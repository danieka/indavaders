package main;

import java.util.Arrays;

import org.newdawn.slick.Graphics;

import ui.Drawable;
import ui.ImageCache;

public class Fleet implements Drawable {
	private int size;
	private Planet location;
	private Player owner;
	private int x;
	private int y;
	private int desiredX;
	private int desiredY;
	private static final int pixelDelta = 2;
	
	
	Fleet(int size, Player owner, Planet location){
		this.size = size;
		this.owner = owner;
		this.location = location;
		x = location.getX();
		y = location.getY();
		desiredX = x;
		desiredY = y;
	}
	
	public int getSize(){
		return size;
	}
	
	public void moveTo(Planet dest){
		if(dest == location) return;
		GameObject G = GameObject.getInstance();
		int[] path = G.path(location, dest);
		G.addMove(new Move(this, G.getPlanet(path[1])));
		desiredX -= ((location.getX() - dest.getX()) / 2);
		desiredY -= ((location.getY() - dest.getY()) / 2);
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
		desiredX = location.getX();
		desiredY = location.getY();
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void draw(Graphics g) {
		if(x != desiredX || y != desiredY){
			if(Math.sqrt(Math.pow(desiredX, 2) + Math.pow(desiredY, 2)) < 2*pixelDelta){
				x = desiredX;
				y = desiredY;
			} else {
				if(x > desiredX) x -= pixelDelta;
				if(x < desiredX) x += pixelDelta;
			 	if(y > desiredY) y -= pixelDelta;
				if(y < desiredY) y += pixelDelta;
			}
		}
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
