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
	
	Planet (int size, String name, Player owner){
		this.size = size;
		this.name = name;
		this.productionCapacity = size;
		this.owner = owner;
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
	
	public void getGraphic(){
		
	}
}
