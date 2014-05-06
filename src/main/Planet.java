package main;
import java.util.ArrayList;

/**
 * Class representing planets in the game.
 * @author danieka
 *
 */
public class Planet {
	private int size;
	private String name;
	private int productionCapacity;
	private Player owner;
	private int x;
	private int y;
	private ArrayList<Fleet> fleets; 
	
	Planet (int x, int y, int size, String name, Player owner){
		this.size = size;
		this.name = name;
		this.productionCapacity = size;
		this.owner = owner;
		this.x = x;
		this.y = y;
		fleets = new ArrayList<Fleet>();
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
}
