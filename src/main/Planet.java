package main;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import ui.Drawable;

/**
 * Class representing planets in the game.
 * @author danieka
 *
 */
public class Planet implements Drawable {
	private int size;
	private String name;
	private int productionCapacity;
	private Player owner;
	private Player siegedBy = null;
	private int x;
	private int y;
	private ArrayList<Fleet> fleets; 
	private Circle image;
	
	/**
	 * Constructor for objects of class Planet
     * Creates a planet with the parameters x, y, size, name  and owner. 
     * X and y is the position of a planet on the map.     
	 * @param x
	 * @param y
	 * @param size
	 * @param name
	 * @param owner
	 */
	Planet (int x, int y, int size, String name, Player owner){
		this.size = size;
		this.name = name;
		this.productionCapacity = size;
		this.owner = owner;
		this.x = x;
		this.y = y;
		fleets = new ArrayList<Fleet>();
		image = new Circle(x, y, 15);
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
	
	/**
	 * Draws the planets on the map and sets color on the planet and 
	 * planet names.
	 */
	@Override
	public void draw(Graphics g) {
			if(owner != null){
				g.setColor(owner.getColor());
				
			}else{
				g.setColor(Color.white);
			}
			g.fill(image);
			g.setColor(Color.white);
			g.drawString(name, x, y-35);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Updates the ownership on the planets.
	 */
	public void updateOwnership() {
		for(Fleet f : fleets){
			if(f.getOwner() != owner){
				if(owner == null){
					owner = f.getOwner();
				}
				else if(siegedBy != null){
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
}
