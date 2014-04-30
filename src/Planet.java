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
	
	Planet (int size, String name, Player owner){
		this.size = size;
		this.name = name;
		this.productionCapacity = size;
		this.owner = owner;
	}
	
	public int getProductionCapacity() {
		return 0;
	}
	
	public void setOwner(Player player){
		return;
	}
	
	public Player getOwner(){
		return owner;
	}
}
