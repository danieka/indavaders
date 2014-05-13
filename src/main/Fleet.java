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
	
	/**
	 * Order the fleet to move to planet dest.
	 * This can be any planet on the map but the fleet will only move one planet.
	 * @param dest
	 */
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
	
	/**
	 * This is only to be used by the Move class.
	 * @param to
	 */
	public void setLocation(Planet to) {
		location = to;
		desiredX = location.getX();
		desiredY = location.getY();
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * This method draws the fleet on the Graphics object g.
	 */
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
	
	@Override
	public String toString() {
		return "Fleet with size: " + size + " at: " + location.getName();
	}
}
