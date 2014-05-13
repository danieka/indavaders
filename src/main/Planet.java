package main;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import ui.Drawable;
import ui.ImageCache;

/**
 * Class representing planets in the game.
 * @author danieka
 *
 */
public class Planet implements Drawable {
	private String name;
	private int productionCapacity;
	private Player owner;
	private Player siegedBy = null;
	private int x;
	private int y;
	private ArrayList<Fleet> fleets; 
	private Circle image;
	int pic = 0;
	
	Planet (int x, int y, int size, String name, Player owner){
		this.name = name;
		this.productionCapacity = size;
		this.owner = owner;
		this.x = x;
		this.y = y;
		fleets = new ArrayList<Fleet>();
		image = new Circle(x, y, 18);
		Random r = new Random();
		pic = r.nextInt(4);
		}
	
	public int getProductionCapacity() {
		return productionCapacity;
	}
	
	public void setOwner(Player player){
		owner = player;
		return;
	}
	
	public Player getOwner(){
		return owner;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	
	public void addFleet(Fleet fleet){
		fleets.add(fleet);
	}
	
	public void removeFleet(Fleet fleet){
		fleets.remove(fleet);
	}
	
	public ArrayList<Fleet> getFleets(){
		return fleets;
	}	
	
	public String getName(){
		return name;
	}

	@Override
	public void draw(Graphics g) {
			if(owner != null){
				g.setColor(owner.getColor());
				
			}else{
				g.setColor(Color.white);
			}
			g.fill(image);
			ImageCache.getImage("planet" + pic + ".png").draw(x-15, y-15);
			g.setColor(Color.white);
			g.drawString(name, x, y-35);
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void updateOwnership() {
		for(Fleet f : fleets){
			if(f.getOwner() != owner){
				if(owner == null){
					owner = f.getOwner();
				}
				else if(siegedBy == f.getOwner()){
					owner = siegedBy;
					siegedBy = null;
					
				}
				else{
					siegedBy = f.getOwner();
				}
				return;			
			}
		siegedBy = null;
		}
	}

	public boolean siegedBy(Player player) {
		if(siegedBy == player){
			return true;
		}
		return false;
	}
}
