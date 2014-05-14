package main;
import org.newdawn.slick.Color;

/**
 * The class for the human player.
 * @author danieka
 *
 */
public class Player {
	private String name;
	private Color color;
	
	/**
	 * Constructor for objects of class Player
     * Creates a player with the parameters name and color.   
	 * @param name
	 * @param color
	 */
	protected Player (String name, Color color){
		this.name = name;
		this.color = color;
	}
	
	public String getName(){
		return name;
	}
	
	public Color getColor(){
		return color;
	}
}
